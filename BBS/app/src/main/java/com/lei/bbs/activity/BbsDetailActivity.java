package com.lei.bbs.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.adapter.DetailAdapter;
import com.lei.bbs.bean.Detail;
import java.util.ArrayList;
import java.util.List;

public class BbsDetailActivity extends AppCompatActivity {
    //view
    private ListView lvDetail;
    private TextView tvTitle;
    //others
    private DetailAdapter detailAdapter;
    private List<Detail> detailList = new ArrayList<Detail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_detail);
        setData();
        initView();
    }

    private void initView(){
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.detail_title));

        lvDetail = (ListView) findViewById(R.id.lvDetail);
        detailAdapter = new DetailAdapter(this,detailList);
        lvDetail.setAdapter(detailAdapter);

    }

    private void setData(){
        //(String name,int level,String sex,int floor,String sendTime,String title,String content )
        detailList.add(new Detail("雪上飞鸟",5,"boy",0,"2016-09-07","王宝强离婚","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        detailList.add(new Detail("雪上飞鸟",2,"girl",1,"2016-09-07","王宝强离婚","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        detailList.add(new Detail("雪上飞鸟",2,"girl",2,"2016-09-07","王宝强离婚","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        detailList.add(new Detail("雪上飞鸟",2,"boy",3,"2016-09-07","王宝强离婚","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        detailList.add(new Detail("雪上飞鸟",2,"girl",4,"2016-09-07","王宝强离婚","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        detailList.add(new Detail("雪上飞鸟",2,"girl",5,"2016-09-07","王宝强离婚","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
        detailList.add(new Detail("雪上飞鸟",2,"girl",6,"2016-09-07","王宝强离婚","各种内容，拒绝接口方法呢，和发布到本地。绝对妇女解放房间"));
    }
}
