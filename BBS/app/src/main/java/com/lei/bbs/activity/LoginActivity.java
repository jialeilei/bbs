package com.lei.bbs.activity;

import android.content.Intent;
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
import com.lei.bbs.retrofit.StarHomeService;
import com.lei.bbs.util.BbsApplication;
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
    private BbsApplication mApp;

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




        Pair<String ,String> loginInfo = mApp.loadLoginInfo();
        if (!loginInfo.first.equals("") && !loginInfo.second.equals("")){
            edEmail.setText(loginInfo.first);
            edPassword.setText(loginInfo.second);
            cbRemember.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if (edPassword.getText().toString().equals("") || edEmail.getText().toString().equals("")){
                    MyToast.showLong(this,"用户名或密码不能为空");
                }else {
                    if (cbRemember.isChecked()) {
                        mApp.saveLoginInfo(edEmail.getText().toString(),edPassword.getText().toString());
                    }
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

    private void gotoLogin(String email,String password){
        StarHomeService service = HttpHelper.createHubService(Constants.base_url);
        HashMap<String,String> params = new HashMap<>();
        params.put("name",email);
        params.put("pwd", password);
        Call<Response>  login = service.postLogin(params);
        login.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                MyLog.i(TAG, "onResponse: "+response.body().getStatus());
                if (response.body().getStatus() != null){
                    int result = Integer.parseInt(response.body().getStatus());
                    switch (result){
                        case 1:
                            MyToast.showShort(LoginActivity.this,"login success");
                            Constants.onLine = true;
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
