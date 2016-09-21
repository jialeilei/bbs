package com.lei.bbs.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.lei.bbs.R;
import com.lei.bbs.adapter.MainAdapter;
import com.lei.bbs.bean.BBS;
import com.lei.bbs.bean.BbsList;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.retrofit.HttpHelper;
import com.lei.bbs.retrofit.StarHomeService;
import com.lei.bbs.util.DiyToolBar;
import com.lei.bbs.util.MyLog;

import org.w3c.dom.ProcessingInstruction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    //list
    private List<BBS> bbsList = new ArrayList<BBS>();
    //view
    private Button btnToLogin;
    private ImageButton imgSetting;
    private ListView lvMain;
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    //others
    private MainAdapter mainAdapter;
    private String TAG="MainActivity";
    private android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addData();
        initView();
    }

    private void initView(){
        setToolBar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //imgSetting = (ImageView) findViewById(R.id.imgSetting);

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

        lvMain.setAdapter(mainAdapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, BbsDetailActivity.class);
                //Intent intent = new Intent(MainActivity.this, ToolBarActivity1.class);
                startActivity(intent);
            }
        });
        showToLoginActivity();
    }

    @Override
    public void setToolBar() {
        toolBar = (DiyToolBar) findViewById(R.id.toolbar);
        toolBar.setBackgroundColor(getResources().getColor(R.color.title_blue));
        //title
        toolBar.getTvCenter().setText(R.string.mainTitle);
        //imgRight
        toolBar.disableRight();
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

    private void addData(){

        if (bbsList.size() > 0){
            bbsList.clear();
        } 
        getFeedList();

        bbsList.add(new BBS("西门吹雪", "一小时前", 5, "里约奥运的奇葩", "点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪", "一小时前", 5, "里约奥运的奇葩", "点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪", "一小时前", 5, "里约奥运的奇葩", "点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪", "一小时前", 5, "里约奥运的奇葩", "点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪", "一小时前", 5, "里约奥运的奇葩", "点击付款if你，分地方尽快发放红包。"));
        bbsList.add(new BBS("西门吹雪", "一小时前", 5, "里约奥运的奇葩", "点击付款if你，分地方尽快发放红包。"));
    }

    private void getFeedList(){
        StarHomeService service = HttpHelper.createHubService(Constants.base_url);
        Call<BbsList>  getList = service.getFeedList();
        getList.enqueue(new Callback<BbsList>() {
            @Override
            public void onResponse(Call<BbsList> call, Response<BbsList> response) {
                if (response.body() != null) {
                    MyLog.i(TAG, "" + response.body().getBbs());
                    if (response.body().getBbs().size() > 0) {
                        bbsList = response.body().getBbs();
                    }
                }

            }

            @Override
            public void onFailure(Call<BbsList> call, Throwable t) {
                MyLog.i(TAG, "fail " + t);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

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
