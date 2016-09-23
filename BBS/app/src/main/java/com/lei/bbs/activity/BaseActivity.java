package com.lei.bbs.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.lei.bbs.R;
import com.lei.bbs.util.DiyToolBar;


/**
 * create by lei 2016/09/21
 */

public abstract class BaseActivity extends AppCompatActivity {
    public DiyToolBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);

    }


     abstract public void setToolBar();

}
