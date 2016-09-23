package com.lei.bbs.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.lei.bbs.R;
import com.lei.bbs.adapter.MainAdapter;
import com.lei.bbs.bean.BBS;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.retrofit.HttpHelper;
import com.lei.bbs.retrofit.StarHomeService;
import com.lei.bbs.util.CircleImage;
import com.lei.bbs.util.DiyToolBar;
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
    //private Button btnToLogin;
    private CircleImage imgHead;
    private ImageButton imgSetting, imgWrite;
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

        setToolBar();
        initView();
    }

    private void initView(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        imgHead = (CircleImage) findViewById(R.id.imgHead);
        imgHead.setOnClickListener(this);
        lvMain = (ListView) findViewById(R.id.lvMain);
        mainAdapter = new MainAdapter(this,bbsList);
        lvMain.setAdapter(mainAdapter);

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
    public void setToolBar() {
        toolBar = (DiyToolBar) findViewById(R.id.toolbar);
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
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);

                break;
            case R.id.imgHead:
                showBelowDialog();
                break;
            default:

                break;
        }
    }

    private void showBelowDialog(){

    }



    @Override
    protected void onPause() {
        super.onPause();
    }
}
