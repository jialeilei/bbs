package com.lei.bbs.activity;


import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.lei.bbs.util.MyToolBar;

/**
 * create by lei 2016/09/21
 */

public  class BaseActivity extends AppCompatActivity {

    public MyToolBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
    }


    public void setToolBar(){}

}
