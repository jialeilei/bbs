package com.lei.bbs.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.lei.bbs.R;
import com.lei.bbs.util.DiyToolBar;

/**
 * create by lei 2016/09/23
 */
public class WriteFeedActivity extends BaseActivity {

    private ImageButton imgBtnLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_feed);

        setToolBar();
        initView();
    }

    @Override
    public void setToolBar() {

        toolBar = (DiyToolBar) findViewById(R.id.toolbar);
        toolBar.setBackgroundColor(getResources().getColor(R.color.title_blue));
        //title
        toolBar.getTvCenter().setText(R.string.write_feed);
        toolBar.disableRight();
        imgBtnLeft = toolBar.getImgLeft();
        imgBtnLeft.setImageResource(R.mipmap.left);
        imgBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteFeedActivity.this.finish();
            }
        });

    }

    private void initView(){

    }
}
