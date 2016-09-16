package com.lei.bbs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import com.lei.bbs.R;
import com.lei.bbs.adapter.MainAdapter;
import com.lei.bbs.bean.BBS;
import com.lei.bbs.constant.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MainActivity extends Activity implements View.OnClickListener {

    //list
    private List<BBS> bbsList = new ArrayList<BBS>();
    //view
    private Button btnToLogin;
    private ImageView imgSetting;
    private ListView lvMain;
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    //others
    private MainAdapter mainAdapter;


    private android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addData();
        initView();
    }

    private void initView(){
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        imgSetting = (ImageView) findViewById(R.id.imgSetting);
        imgSetting.setOnClickListener(this);
        btnToLogin = (Button) findViewById(R.id.btnLogin);
        btnToLogin.setOnClickListener(this);
        lvMain = (ListView) findViewById(R.id.lvMain);
        mainAdapter = new MainAdapter(this,bbsList);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.title_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                       handler.post(new Runnable() {
                           @Override
                           public void run() {
                               swipeRefreshLayout.setRefreshing(false);
                           }
                       });
                    }
                }).start();


            }
        });

        lvMain.setAdapter(mainAdapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, BbsDetailActivity.class);
                startActivity(intent);
            }
        });
        showToLoginActivity();
    }

    private void addData(){
        bbsList.add(new BBS("西门吹雪","一小时前", 5,"里约奥运的奇葩","点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪","一小时前", 5, "里约奥运的奇葩", "点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪","一小时前", 5,"里约奥运的奇葩","点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪","一小时前", 5,"里约奥运的奇葩","点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪","一小时前", 5, "里约奥运的奇葩", "点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪","一小时前", 5, "里约奥运的奇葩", "点击付款if你，分地方尽快发放红包。"));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgSetting:
                drawerLayout.openDrawer(Gravity.LEFT);

                break;
            case R.id.btnLogin:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);

                break;
            default:

                break;
        }
    }

    private void showToLoginActivity(){
        if (Constants.onLine){
            btnToLogin.setVisibility(View.GONE);
        }else {
            btnToLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        showToLoginActivity();
    }
}
