package com.lei.bbs.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //list
    private List<BBS> bbsList = new ArrayList<BBS>();
    //view
    private Button btnLogin;
    private ImageView imgSetting;
    private ListView lvMain;
    private DrawerLayout drawerLayout;
    //others
    private MainAdapter mainAdapter;




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
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        lvMain = (ListView) findViewById(R.id.lvMain);
        mainAdapter = new MainAdapter(this,bbsList);
        lvMain.setAdapter(mainAdapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,BbsDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addData(){
        bbsList.add(new BBS("西门吹雪","一小时前",5,"里约奥运的奇葩","点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪","一小时前",5,"里约奥运的奇葩","点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪","一小时前",5,"里约奥运的奇葩","点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪","一小时前",5,"里约奥运的奇葩","点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪","一小时前",5,"里约奥运的奇葩","点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪","一小时前",5,"里约奥运的奇葩","点击付款if你，分地方尽快发放红包。"));
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

}
