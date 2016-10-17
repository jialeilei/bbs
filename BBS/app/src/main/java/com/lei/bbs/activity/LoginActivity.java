package com.lei.bbs.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.bean.Response;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.retrofit.HttpHelper;
import com.lei.bbs.retrofit.RetrofitService;
import com.lei.bbs.util.Common;
import com.lei.bbs.util.MyLog;
import com.lei.bbs.util.MyToast;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * create by lei
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG="LoginActivity";
    private Button btnToLogin;
    private TextView tvRegister;
    private EditText edEmail,edPassword;
    private ImageView imgLeft;
    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView(){
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        btnToLogin = (Button) findViewById(R.id.btnLogin);
        btnToLogin.setOnClickListener(this);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(this);
        imgLeft = (ImageView) findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(this);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);

        Pair<String ,String> loginInfo = loadLoginInfo();
        if (Common.isEmpty(loginInfo.first, loginInfo.second)){
            edEmail.setText(loginInfo.first);
            edPassword.setText(loginInfo.second);
            cbRemember.setChecked(true);
        }

    }


    private void isUserOnLine(){

        SharedPreferences user = getSharedPreferences(Constants.SHARE_USER_INFO, MODE_PRIVATE);

        if (user.getInt("id",0)==0
                ||user.getString("name","").equals("")
                ||user.getString("sex","").equals("")){
            MyLog.i(TAG, "没有信息,需要登录");

        }else {

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();

            /* Constants.userId = user.getInt("id",0);
               Constants.userName = user.getString("name", "");
               Constants.sex = user.getString("sex","");
               MyLog.i(TAG, "无需登录");
               setUserInfo();*/
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if (edPassword.getText().toString().equals("") || edEmail.getText().toString().equals("")){
                    MyToast.showLong(this,"用户名或密码不能为空");
                }else {

                    gotoLogin(edEmail.getText().toString(),edPassword.getText().toString());
                }

                break;
            case R.id.tvRegister:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.imgLeft:
                this.finish();
                break;
            default:

                break;
        }
    }

    /**
     * @param strUserName
     * @param strPassword
     */
    public void saveLoginInfo(String strUserName, String strPassword) {
        SharedPreferences loginPreferences = getSharedPreferences(Constants.SHARE_LOGIN_INFO, MODE_PRIVATE);
        loginPreferences.edit()
                .putString(Constants.SHARE_NAME, strUserName)
                .putString(Constants.SHARE_PASSWORD, strPassword)
                .commit();
    }

    public Pair<String,String> loadLoginInfo(){
        SharedPreferences loginPreference = getSharedPreferences(Constants.SHARE_LOGIN_INFO, MODE_PRIVATE);
        return new Pair<String,String>(loginPreference.getString(Constants.SHARE_NAME, ""),loginPreference.getString(Constants.SHARE_PASSWORD,""));
    }

    public void saveUserInfo(int id,int level,String name,String sex,String avatar){
        SharedPreferences userPreferences = getSharedPreferences(Constants.SHARE_USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putInt("id",id);
        editor.putInt("level",level);
        editor.putString("name", name);
        editor.putString("sex", sex);
        editor.putString("avatar", avatar);
        editor.commit();

    }

    private void gotoLogin(String email,String password){
        RetrofitService service = HttpHelper.createHubService(Constants.base_url);
        HashMap<String,String> params = new HashMap<>();
        params.put("name",email);
        params.put("pwd", password);
        Call<Response>  login = service.postLogin(params);
        login.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                MyLog.i(TAG, "onResponse: "+response.body().getStatus());
                if (response.body().getStatus() != null){
                    int status = Integer.parseInt(response.body().getStatus());
                    int id = response.body().getUserId();
                    int level = response.body().getLevel();
                    String avatar = response.body().getAvatar();
                    MyLog.i("lei","id: "+id);
                    String sex = response.body().getSex();
                    String name = response.body().getUserName();
                    switch (status){
                        case 1: //success
                            MyToast.showShort(LoginActivity.this,"login success");
                            //储存
                            if (cbRemember.isChecked()) {
                                saveLoginInfo(edEmail.getText().toString(),edPassword.getText().toString());
                            }
                            saveUserInfo(id,level, name, sex, avatar);
                            LoginActivity.this.finish();
                            break;
                        case 2:

                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

                MyLog.i(TAG, "onFailure: ");
            }
        });
        
    }

}
