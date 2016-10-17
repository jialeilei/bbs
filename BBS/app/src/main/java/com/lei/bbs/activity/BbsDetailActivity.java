package com.lei.bbs.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.adapter.DetailAdapter;
import com.lei.bbs.bean.AnswerFeed;
import com.lei.bbs.bean.TalkFeed;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.retrofit.HttpHelper;
import com.lei.bbs.retrofit.RetrofitService;
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
 *
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
    private List<TalkFeed> mChildList = new ArrayList<TalkFeed>();
    private List<AnswerFeed> answerFeedList2 = new ArrayList<AnswerFeed>();
    public Handler detailActivityHandler = new Handler();
    //mainInfo
    private int uid,mid,mScore;
    private String mName,mSex,mTitle,mContent,mTime,mAvatar;
    private int answerModel = 0;//被回复的用户id，默认回复楼主
    private AnswerFeed answerFeedInfo;
    private TalkFeed talkFeedInfo;


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
        answerFeedList.add(new AnswerFeed(0,uid, mName, Common.scoreToLevel(mScore), mSex, mTime, mTitle, mContent,mAvatar));
        lvDetail = (ListView) findViewById(R.id.lvDetail);
        detailAdapter = new DetailAdapter(this, answerFeedList,mChildList);
        lvDetail.setAdapter(detailAdapter);
        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                detailAdapter.notifyDataSetChanged();
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.title_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                requestThread();
            }
        });

        requestListViewData();

        adapterListener();//监听适配器

        lvDetail.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                boolean result;
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    MyLog.i(TAG,"not  scrolling ");
                    result = false;
                    detailAdapter.scrollingState(result);
                    detailAdapter.notifyDataSetChanged();
                }else {
                    result = true;
                    detailAdapter.scrollingState(result);
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSend:
                if (!etContent.getText().toString().equals("")){

                    switch (answerModel){
                        case 0://回复楼主
                            sendAnswerFeed(mid,Constants.userId,etContent.getText().toString());
                            break;
                        case 1://回复层主
                            //int uid, String uname, int mid, int aid, int to_tid, String to_tname, String content
                            sendTalkFeed(
                                    Constants.userId, Constants.userName, mid, answerFeedInfo.getAid(),
                                    0, 0+"", etContent.getText().toString());
                            break;
                        case 2://回复二级评论
                            sendTalkFeed(
                                    Constants.userId, Constants.userName, mid, talkFeedInfo.getAid(),
                                    talkFeedInfo.getUid(), talkFeedInfo.getUname(), etContent.getText().toString());
                            break;
                        default:
                            break;
                    }
                }
                recoverEditText();//防止重复发送内容
                break;
            default:

                break;
        }
    }

    private void receiveData(){
        Bundle bundle = this.getIntent().getExtras();
        uid = bundle.getInt("userId");//楼主id
        mid = bundle.getInt("mainId");
        mName = bundle.getString("name");
        mTitle = bundle.getString("title");
        mContent = bundle.getString("content");
        mTime = bundle.getString("sendTime");
        mSex = bundle.getString("sex");
        mScore = bundle.getInt("score");
        mAvatar = bundle.getString("avatar");
    }

    public void refreshListView(){
        detailAdapter = new DetailAdapter(this, answerFeedList,mChildList);
        lvDetail.setAdapter(detailAdapter);
        detailAdapter.notifyDataSetChanged();

        adapterListener();
    }


    private void requestListViewData(){
        //(String name,int score,String sex,int floor,String sendTime,String title,String content )
        swipeRefreshLayout.setRefreshing(true);
        requestThread();

    }

    private void adapterListener(){
        detailAdapter.setAnswerFeedListener(new DetailAdapter.OnAnswerFeedListener() {
            @Override
            public void answerFeed(AnswerFeed answerFeed) {//回复层主

               /* if (isMyself(answerFeed.getUid())){
                    MyLog.i(TAG,"myself");
                    return;
                }*/
                MyLog.i(TAG, "uName: " + answerFeed.getName() + " aid: " + answerFeed.getAid());
                BbsDetailActivity.this.answerFeedInfo = answerFeed;
                setAnswerModel(1, answerFeed.getName());
            }

            @Override
            public void talkFeed(TalkFeed talkFeed) {//回复二级评论者
                if (isMyself(talkFeed.getUid())){
                    MyLog.i(TAG,"myself");
                    return;
                }
                BbsDetailActivity.this.talkFeedInfo = talkFeed;
                MyLog.i(TAG, "回复:" + talkFeed.getUname() + "的内容");
                setAnswerModel(2, talkFeed.getUname());
            }
        });
    }

    private boolean isMyself(int id){
        //是否自己评论自己
        if (Constants.userId == id){
            answerModel = 4;
            return true;
        }else {
            return false;
        }
    }

    private void setAnswerModel(int model,String toName){
        //0:回复楼主  1：回复层主 2：回复二级评论者
        answerModel = model;
        if (answerModel == 0){
            toName = "楼主";
        }
        etContent.setFocusableInTouchMode(true);
        etContent.requestFocus();
        etContent.setHint("回复" + toName);//被回复用户名
        InputMethodManager inputManager = (InputMethodManager) etContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(etContent, 0);

    }

    private void recoverEditText(){
        answerModel = 0;
        etContent.setText("");
        etContent.setHint("回复楼主");
    }

    private void requestThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAnswerFeedList(mid);//向服务器请求数据
                getTalkFeedList(mid);//请求讨论数据
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                detailActivityHandler.post(new Runnable() {
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
        answerFeedList.add(new AnswerFeed(0,uid,mName,Common.scoreToLevel(mScore),mSex,mTime,mTitle,mContent,mAvatar));
        for (int i = 0; i < feeds.size(); i++) {
            answerFeedList.add(new AnswerFeed(
                    feeds.get(i).getAid(),
                    feeds.get(i).getUid(),
                    feeds.get(i).getName(),
                    Common.scoreToLevel(feeds.get(i).getScore()),
                    feeds.get(i).getSex(),
                    feeds.get(i).getSendTime(),
                    feeds.get(i).getTitle(),
                    feeds.get(i).getContent(),
                    feeds.get(i).getAvatar()));
        }

        refreshListView();
    }

    private void sendTalkFeed(int uid, String uname, int mid, int aid, int to_tid, String to_tname, String content){
        RetrofitService service = HttpHelper.createHubService(Constants.base_url);
        Call<com.lei.bbs.bean.Response> sendTalkFeed = service.postTalkFeed(uid, uname, mid, aid, to_tid, to_tname, content);
        sendTalkFeed.enqueue(new Callback<com.lei.bbs.bean.Response>() {
            @Override
            public void onResponse(Call<com.lei.bbs.bean.Response> call, Response<com.lei.bbs.bean.Response> response) {
                if (response.body() != null){
                    if (response.body().getStatus().equals("1")){
                        MyToast.showShort(BbsDetailActivity.this, "回复成功");
                        swipeRefreshLayout.setRefreshing(true);
                        requestListViewData();
                        recoverEditText();
                    }else {
                        MyLog.i(TAG,"send talk feed fail");
                    }
                }
            }

            @Override
            public void onFailure(Call<com.lei.bbs.bean.Response> call, Throwable t) {
                MyLog.i(TAG,"send talk feed fail "+t);
            }
        });

    }

    private void sendAnswerFeed(int mid,int uid,String content){
        RetrofitService service = HttpHelper.createHubService(Constants.base_url);
        Call<com.lei.bbs.bean.Response> sendFeed = service.sendAnswerFeed(mid, uid, content);
        sendFeed.enqueue(new Callback<com.lei.bbs.bean.Response>() {
            @Override
            public void onResponse(Call<com.lei.bbs.bean.Response> call, Response<com.lei.bbs.bean.Response> response) {
                MyToast.showShort(BbsDetailActivity.this, "回复成功");
                swipeRefreshLayout.setRefreshing(true);
                requestListViewData();
                recoverEditText();
            }

            @Override
            public void onFailure(Call<com.lei.bbs.bean.Response> call, Throwable t) {
                MyToast.showShort(BbsDetailActivity.this, "请稍后重试");
            }
        });
    }

    private void getAnswerFeedList(int mid){
        RetrofitService service = HttpHelper.createHubService(Constants.base_url);
        Call<ArrayList<AnswerFeed>>  feedList = service.getAnswerFeedList(mid);
        feedList.enqueue(new Callback<ArrayList<AnswerFeed>>() {
            @Override
            public void onResponse(Call<ArrayList<AnswerFeed>> call, Response<ArrayList<AnswerFeed>> response) {
                if (response.body() != null){
                    answerFeedList2 = response.body();
                    MyLog.i(TAG," answer feed list: "+answerFeedList2);
                    MyLog.i(TAG,"position 0 aid:"+answerFeedList2.get(0).getAid());
                    addServerData2ListView(answerFeedList2);
                }else {
                    MyLog.i(TAG," response is null ");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AnswerFeed>> call, Throwable t) {
                MyLog.i(TAG, " fail " + t);
            }
        });

    }

    private void getTalkFeedList(int mid){
        RetrofitService service = HttpHelper.createHubService(Constants.base_url);
        Call<ArrayList<TalkFeed>> getFeedList = service.getTalkFeedList(mid);
        getFeedList.enqueue(new Callback<ArrayList<TalkFeed>>() {
            @Override
            public void onResponse(Call<ArrayList<TalkFeed>> call, Response<ArrayList<TalkFeed>> response) {
                if (response.body() != null) {
                    mChildList = response.body();
                    refreshListView();
                    MyLog.i(TAG,"child list: "+mChildList);
                }else {
                    MyLog.i(TAG,"child list is null");
                }

            }

            @Override
            public void onFailure(Call<ArrayList<TalkFeed>> call, Throwable t) {
                MyLog.i(TAG,"get child list fail "+t);

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
       /* toolBar.setImgRight(getResources().getDrawable(R.mipmap.write));
        toolBar.setRightImgListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswerModel(0,name);
            }
        });*/
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
