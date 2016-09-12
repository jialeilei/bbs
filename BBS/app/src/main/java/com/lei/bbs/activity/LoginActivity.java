package com.lei.bbs.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.lei.bbs.R;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.retrofit.HttpHelper;
import com.lei.bbs.retrofit.RetrofitUtil;
import com.lei.bbs.retrofit.StarHomeService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnToLogin;
    private TextView tvRegister;
    private EditText edEmail,edPassword;
    private ImageView imgLeft;

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if (edPassword.getText().toString().equals("")||edEmail.getText().toString().equals("")){
                    Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_LONG).show();
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

    private void gotoLogin(String email,String password){

    }

}
