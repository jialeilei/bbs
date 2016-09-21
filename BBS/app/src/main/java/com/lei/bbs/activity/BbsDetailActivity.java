package com.lei.bbs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.adapter.DetailAdapter;
import com.lei.bbs.bean.Detail;
import com.lei.bbs.util.DiyToolBar;
import java.util.ArrayList;
import java.util.List;

public class BbsDetailActivity extends BaseActivity {
    //view
    private ListView lvDetail;
    private TextView tvTitle;
    private ImageButton imgBack;
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
        setToolBar();

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

    @Override
    public void setToolBar() {

        toolBar = (DiyToolBar) findViewById(R.id.toolbar);
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
