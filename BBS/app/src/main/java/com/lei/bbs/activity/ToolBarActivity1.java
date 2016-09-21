package com.lei.bbs.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.lei.bbs.R;
import com.lei.bbs.util.DiyToolBar;

public class ToolBarActivity1 extends BaseActivity {

    private DiyToolBar toolBar;
    private ImageButton imgRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar1);

        initView();
    }

    private void initView(){
        toolBar = (DiyToolBar) findViewById(R.id.toolbar);
        toolBar.getTvCenter().setText("title");
        toolBar.setBackgroundColor(getResources().getColor(R.color.title_blue));
        imgRight = toolBar.getImgRight();
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.mipmap.girl);
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void setToolBar() {

    }
}
