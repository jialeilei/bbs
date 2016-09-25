package com.lei.bbs.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.lei.bbs.R;
import com.lei.bbs.util.EqualEmpty;
import com.lei.bbs.util.MyToast;
import com.lei.bbs.util.MyToolBar;

/**
 * create by lei 2016/09/23
 */
public class WriteFeedActivity extends BaseActivity implements View.OnClickListener{

    private ImageButton imgBtnLeft;
    private EditText etTitle,etContent;
    private Button btnSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_feed);

        setToolBar();
        initView();
    }

    @Override
    public void setToolBar() {

        toolBar = (MyToolBar) findViewById(R.id.toolbar);
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

        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        btnSure = (Button) findViewById(R.id.btnSure);
        btnSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSure:
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();
                if (EqualEmpty.isValue(title, content)){
                    sendFeed(title,content);
                    MyToast.showShort(this, "内容已经提交");
                }else {
                    MyToast.showShort(this,"标题和内容不能为空");
                }
                break;
            default:
                break;
        }
    }

    private void sendFeed(String title,String content){

    }

}
