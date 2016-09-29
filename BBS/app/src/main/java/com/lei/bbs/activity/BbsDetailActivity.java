package com.lei.bbs.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.adapter.DetailAdapter;
import com.lei.bbs.bean.AnswerFeed;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.retrofit.HttpHelper;
import com.lei.bbs.retrofit.StarHomeService;
import com.lei.bbs.util.Common;
import com.lei.bbs.util.MyLog;
import com.lei.bbs.util.MyToast;
import com.lei.bbs.util.MyToolBar;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * create by lei
 */

public class BbsDetailActivity extends BaseActivity implements View.OnClickListener{
    //view
    private ListView lvDetail;
    private TextView tvTitle;
    private ImageButton imgBack;
    private EditText etContent;
    private Button btnSend;
    private SwipeRefreshLayout swipeRefreshLayout;
    //others
    private String TAG = "BbsDetailActivity";
    private DetailAdapter detailAdapter;
    private List<AnswerFeed> answerFeedList = new ArrayList<AnswerFeed>();
    private List<AnswerFeed> answerFeedList2 = new ArrayList<AnswerFeed>();
    private Handler handler = new Handler();
    //mainInfo
    private int uid,mid,score;
    private String name,sex,title,content,time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_detail);

        receiveData();
        setToolBar();
        initView();
    }

    private void initView(){

        etContent = (EditText) findViewById(R.id.etContent);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        //展示楼主，信息来源于本地
        if (answerFeedList.size() > 0){
            answerFeedList.clear();
        }
        answerFeedList.add(new AnswerFeed(uid, name, Common.scoreToLevel(score), sex, time, title, content));
        lvDetail = (ListView) findViewById(R.id.lvDetail);
        detailAdapter = new DetailAdapter(this, answerFeedList);
        lvDetail.setAdapter(detailAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.title_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                requestThread();
            }
        });

        requestListViewData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSend:
                if (!etContent.getText().toString().equals("")){
                    sendAnswerFeed(mid,Constants.userId,etContent.getText().toString());
                }
                break;
            default:

                break;
        }
    }

    private void receiveData(){
        Bundle bundle = this.getIntent().getExtras();
        uid = bundle.getInt("userId");
        mid = bundle.getInt("mainId");
        name = bundle.getString("name");
        title = bundle.getString("title");
        content = bundle.getString("content");
        time = bundle.getString("sendTime");
        sex = bundle.getString("sex");
    }

    private void refreshListView(){
        detailAdapter = new DetailAdapter(this, answerFeedList);
        lvDetail.setAdapter(detailAdapter);
    }

    private void requestListViewData(){
        //(String name,int score,String sex,int floor,String sendTime,String title,String content )
        swipeRefreshLayout.setRefreshing(true);
        requestThread();

    }


    private void requestThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAnswerFeedList(mid);//向服务器请求数据
                try {
                    Thread.sleep(1000);
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

    private void addServerData2ListView(List<AnswerFeed> feeds){
        if (answerFeedList.size() > 0){
            answerFeedList.clear();
        }
        answerFeedList.add(new AnswerFeed(uid,name,Common.scoreToLevel(score),sex,time,title,content));
        for (int i = 0; i < feeds.size(); i++) {
            answerFeedList.add(new AnswerFeed(
                    feeds.get(i).getUid(),
                    feeds.get(i).getName(),
                    Common.scoreToLevel(feeds.get(i).getScore()),
                    feeds.get(i).getSex(),
                    feeds.get(i).getSendTime(),
                    feeds.get(i).getTitle(),
                    feeds.get(i).getContent()));
        }

        refreshListView();
    }


    private void sendAnswerFeed(int mid,int uid,String content){
        StarHomeService service = HttpHelper.createHubService(Constants.base_url);
        Call<com.lei.bbs.bean.Response> sendFeed = service.sendAnswerFeed(mid, uid, content);
        sendFeed.enqueue(new Callback<com.lei.bbs.bean.Response>() {
            @Override
            public void onResponse(Call<com.lei.bbs.bean.Response> call, Response<com.lei.bbs.bean.Response> response) {
                MyToast.showShort(BbsDetailActivity.this,"回复成功");
                swipeRefreshLayout.setRefreshing(true);
                requestListViewData();
                etContent.clearFocus();
                etContent.setText("");
            }

            @Override
            public void onFailure(Call<com.lei.bbs.bean.Response> call, Throwable t) {
                MyToast.showShort(BbsDetailActivity.this,"请稍后重试");
            }
        });
    }


    private void getAnswerFeedList(int mid){

        StarHomeService service = HttpHelper.createHubService(Constants.base_url);
        Call<ArrayList<AnswerFeed>>  feedList = service.getAnswerFeedList(mid);
        feedList.enqueue(new Callback<ArrayList<AnswerFeed>>() {
            @Override
            public void onResponse(Call<ArrayList<AnswerFeed>> call, Response<ArrayList<AnswerFeed>> response) {
                if (response.body() != null){
                    answerFeedList2 = response.body();
                    MyLog.i(TAG," answer feed list: "+answerFeedList2);
                    addServerData2ListView(answerFeedList2);
                }else {
                    MyLog.i(TAG," response is null ");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AnswerFeed>> call, Throwable t) {
                MyLog.i(TAG," fail "+t);
            }
        });

    }

    @Override
    public void setToolBar() {

        toolBar = (MyToolBar) findViewById(R.id.toolbar);
        toolBar.setBackgroundColor(getResources().getColor(R.color.title_blue));
        //title
        tvTitle = toolBar.getTvCenter();
        tvTitle.setText(getResources().getString(R.string.detail_title));
        //imgRight
        toolBar.disableRight();
        //imgLeft
        toolBar.enableLeft();
        imgBack = toolBar.getImgLeft();
        imgBack.setImageResource(R.mipmap.left);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BbsDetailActivity.this.finish();
            }
        });
    }
}
