package com.lei.bbs.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.adapter.MainAdapter;
import com.lei.bbs.bean.BBS;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.retrofit.HttpHelper;
import com.lei.bbs.retrofit.StarHomeService;
import com.lei.bbs.util.BbsApplication;
import com.lei.bbs.util.CircleImage;
import com.lei.bbs.util.MyToolBar;
import com.lei.bbs.util.MyLog;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    //list
    private List<BBS> bbsList = new ArrayList<BBS>();
    //view
    private CircleImage imgHead;
    private ImageButton imgSetting, imgWrite;
    private ListView lvMain;
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvName,tvSex;
    //others
    private MainAdapter mainAdapter;
    private String TAG="MainActivity";
    private android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolBar();
        initView();
    }

    private void initView(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        imgHead = (CircleImage) findViewById(R.id.imgHead);
        imgHead.setOnClickListener(this);
        tvName = (TextView) findViewById(R.id.tvName);
        tvSex = (TextView) findViewById(R.id.tvSex);
        lvMain = (ListView) findViewById(R.id.lvMain);
        mainAdapter = new MainAdapter(this,bbsList);
        lvMain.setAdapter(mainAdapter);

        //isUserOnLine();
        setUserInfo();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.title_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getFeedList();
                        try {
                            Thread.sleep(1500);
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


        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, BbsDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    private void refreshListView(){
        mainAdapter = new MainAdapter(this,bbsList);
        lvMain.setAdapter(mainAdapter);
        //mainAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        //isUserOnLine();
        setUserInfo();
    }

    @Override
    public void setToolBar() {
        toolBar = (MyToolBar) findViewById(R.id.toolbar);
        toolBar.setBackgroundColor(getResources().getColor(R.color.title_blue));
        //title
        toolBar.getTvCenter().setText(R.string.mainTitle);
        //imgRight
        //toolBar.disableRight();
        imgWrite = toolBar.getImgRight();
        imgWrite.setImageResource(R.mipmap.write);
        imgWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,WriteFeedActivity.class);
                startActivity(intent);

            }
        });
        //imgLeft
        toolBar.enableLeft();
        imgSetting = toolBar.getImgLeft();
        imgSetting.setImageResource(R.mipmap.setting);
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }


    private void getFeedList(){
        StarHomeService service = HttpHelper.createHubService(Constants.base_url);
        Call<ArrayList<BBS>>  getList = service.getFeedList();
        getList.enqueue(new Callback<ArrayList<BBS>>() {
            @Override
            public void onResponse(Call<ArrayList<BBS>> call, Response<ArrayList<BBS>> response) {
                if (response.body() != null) {
                    if (response.body().size() > 0) {
                        bbsList = response.body();
                    }
                    refreshListView();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<BBS>> call, Throwable t) {
                MyLog.i(TAG, "fail " + t);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnLogin:
                /*Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);*/

                break;
            case R.id.imgHead:
                showBelowDialog();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);

                break;
            default:

                break;
        }
    }

    private void showBelowDialog(){

    }

  /*  private void isUserOnLine(){

        SharedPreferences user = getSharedPreferences("userInfo", MODE_PRIVATE);
        if (user.getString("id","").equals("")
                ||user.getString("name","").equals("")
                ||user.getString("sex","").equals("")){

            MyLog.i(TAG, "没有信息,需要登录");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        }else {
            Constants.userId = user.getInt("id",0);
            Constants.userName = user.getString("name","");
            Constants.sex = user.getString("sex","");
            MyLog.i(TAG, "无需登录");
        }

    }*/

   /* public void isUserOnLine(){

        SharedPreferences user = getSharedPreferences("userInfo", MODE_PRIVATE);
        if (user.getInt("id",0)==0
                ||user.getString("name","").equals("")
                ||user.getString("sex","").equals("")){

            toLoginActivity();

        }else {
            Constants.userId = user.getInt("id",0);
            Constants.userName = user.getString("name", "");
            Constants.sex = user.getString("sex","");
            tvName.setText(Constants.userName);
            tvSex.setText(Constants.sex);

        }

    }*/

    private void setUserInfo(){
        tvName.setText(Constants.userName);
        tvSex.setText(Constants.sex);
        MyLog.i(TAG,"name "+Constants.userName+" sex "+Constants.sex);
    }

    private void toLoginActivity(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

}
