package com.lei.bbs.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.lei.bbs.R;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.retrofit.HttpHelper;
import com.lei.bbs.retrofit.RetrofitUtil;
import com.lei.bbs.retrofit.StarHomeService;
import com.lei.bbs.util.MyToast;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    //view
    private Button btnRegister;
    private EditText edEmail,edPassword,edPasswordAgain;
    private ImageView imgLeft;
    //others
    private String TAG="RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView(){
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edPasswordAgain = (EditText) findViewById(R.id.edPasswordAgain);
        imgLeft = (ImageView) findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                if (edEmail.getText().toString().equals("")||
                        edPassword.getText().toString().equals("")||
                        edPasswordAgain.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"email or password can't null",Toast.LENGTH_LONG).show();
                }else {
                    if (edPassword.getText().toString().equals(edPasswordAgain.getText().toString())){
                        gotoRegister(edEmail.getText().toString(),edPassword.getText().toString());
                    }else {
                        Toast.makeText(RegisterActivity.this,"两次输入密码不一致",Toast.LENGTH_LONG).show();
                    }
                }

                break;
            case R.id.imgLeft:
                this.finish();

                break;
            default:

                break;
        }
    }


    private void gotoRegister(String email,String password){

        StarHomeService service = HttpHelper.createHubService2(Constants.base_url);
        HashMap<String,String> params=new HashMap<>();
        params.put("name",email);
        params.put("pwd", password);

        Call<com.lei.bbs.bean.Response> register = service.postRegister(params);
        register.enqueue(new Callback<com.lei.bbs.bean.Response>() {
            @Override
            public void onResponse(Call<com.lei.bbs.bean.Response> call, Response<com.lei.bbs.bean.Response> response) {
                Log.i(TAG, "onResponse: "+response.body().getStatus());
                int result ;
                if (response.body().getStatus() != null){
                    result = Integer.parseInt(response.body().getStatus());
                    switch (result){
                        case 1:
                            MyToast.showShort(RegisterActivity.this,"register success");
                            RegisterActivity.this.finish();
                            break;
                        case 2:
                            MyToast.showShort(RegisterActivity.this,"用户已经存在");

                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<com.lei.bbs.bean.Response> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t);
            }
        });
    }


}
