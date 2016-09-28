package com.lei.bbs.activity;

import android.os.Bundle;
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
    //others
    private String TAG = "BbsDetailActivity";
    private DetailAdapter detailAdapter;
    private List<AnswerFeed> answerFeedList = new ArrayList<AnswerFeed>();
    private List<AnswerFeed> answerFeedList2 = new ArrayList<AnswerFeed>();
    //mainInfo
    private int uid,mid,score;
    private String name,sex,title,content,time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_detail);

        getData();
        setData();
        initView();
    }

    private void initView(){
        setToolBar();

        etContent = (EditText) findViewById(R.id.etContent);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        lvDetail = (ListView) findViewById(R.id.lvDetail);
        detailAdapter = new DetailAdapter(this, answerFeedList);
        lvDetail.setAdapter(detailAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            
        }
    }

    private void getData(){
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

    private void setData(){
        //(String name,int score,String sex,int floor,String sendTime,String title,String content )
        if (answerFeedList.size() > 0){
            answerFeedList.clear();
        }
        answerFeedList.add(new AnswerFeed(name, Common.scoreToLevel(score),sex,time,title,content));
        /*answerFeedList.add(new AnswerFeed("雪上飞鸟",5,"boy","2016-09-07","王宝强离婚1","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        answerFeedList.add(new AnswerFeed("雪上飞鸟",12,"girl","2016-09-07","王宝强离婚2","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        answerFeedList.add(new AnswerFeed("雪上飞鸟",2,"girl","2016-09-07","王宝强离婚3","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        answerFeedList.add(new AnswerFeed("雪上飞鸟",20,"boy","2016-09-07","王宝强离婚4","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        answerFeedList.add(new AnswerFeed("雪上飞鸟",2,"girl","2016-09-07","王宝强离婚5","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        answerFeedList.add(new AnswerFeed("雪上飞鸟",2,"girl","2016-09-07","王宝强离婚6","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        answerFeedList.add(new AnswerFeed("雪上飞鸟",2,"girl","2016-09-07","王宝强离婚7","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));*/
        getAnswerFeedList(mid);
    }

    private void addServerData(List<AnswerFeed> feeds){
        if (answerFeedList.size() > 0){
            answerFeedList.clear();
        }
        answerFeedList.add(new AnswerFeed(name,Common.scoreToLevel(score),sex,time,title,content));
        for (int i = 0; i < feeds.size(); i++) {
            answerFeedList.add(new AnswerFeed(
                    feeds.get(i).getName(),
                    Common.scoreToLevel(feeds.get(i).getScore()),
                    feeds.get(i).getSex(),
                    feeds.get(i).getSendTime(),
                    feeds.get(i).getTitle(),
                    feeds.get(i).getContent()));
        }

        refreshListView();

    }

    private void getAnswerFeedList(int mid){

        StarHomeService service = HttpHelper.createHubService(Constants.base_url);
        Call<ArrayList<AnswerFeed>>  feedList = service.postAnswerFeedList(mid);
        feedList.enqueue(new Callback<ArrayList<AnswerFeed>>() {
            @Override
            public void onResponse(Call<ArrayList<AnswerFeed>> call, Response<ArrayList<AnswerFeed>> response) {
                if (response.body() != null){
                    answerFeedList2 = response.body();
                    MyLog.i(TAG," answer feed list: "+answerFeedList2);
                    addServerData(answerFeedList2);
                }else {
                    MyLog.i(TAG," response is null ");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AnswerFeed>> call, Throwable t) {
                MyLog.i(TAG," fail "+t);
            }
        });

       /* Call<AnswerFeedList>  feedList = service.postAnswerFeedList(mid);
        feedList.enqueue(new Callback<AnswerFeedList>() {
            @Override
            public void onResponse(Call<AnswerFeedList> call, Response<AnswerFeedList> response) {
                MyLog.i(TAG," answer feed list: "+response.body().getFeedList());
            }

            @Override
            public void onFailure(Call<AnswerFeedList> call, Throwable t) {
                MyLog.i(TAG," answer feed list fail ");

            }
        });*/

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
