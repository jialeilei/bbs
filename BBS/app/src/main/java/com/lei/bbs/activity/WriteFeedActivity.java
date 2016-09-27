package com.lei.bbs.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.lei.bbs.R;
import com.lei.bbs.bean.Response;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.retrofit.HttpHelper;
import com.lei.bbs.retrofit.StarHomeService;
import com.lei.bbs.util.EqualEmpty;
import com.lei.bbs.util.MyLog;
import com.lei.bbs.util.MyToast;
import com.lei.bbs.util.MyToolBar;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * create by lei 2016/09/23
 */
public class WriteFeedActivity extends BaseActivity implements View.OnClickListener{

    //view
    private ImageButton imgBtnLeft;
    private EditText etTitle,etContent;
    private Button btnSure;
    //others
    private String TAG="WriteFeedActivity";

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
                    sendFeed(Constants.userId, title, content);
                }else {
                    MyToast.showShort(this,"标题和内容不能为空");
                }
                break;
            default:
                break;
        }
    }

    private void sendFeed(int userId,String title,String content){
        StarHomeService service = HttpHelper.createHubService(Constants.base_url);

        Call<Response> write = service.postMainFeed(userId,title,content);
        write.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body() != null){
                    if (response.body().getStatus().equals("1")){
                        MyLog.i(TAG,"success");
                        finishThis();
                    }else if (response.body().getStatus().equals("0")){
                        MyLog.i(TAG,"response status: "+0);
                        MyToast.showShort(WriteFeedActivity.this, "发布失败，请稍后重试");
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                MyLog.i(TAG,"fail "+t);
                MyToast.showShort(WriteFeedActivity.this, "连接失败，请稍后重试");
            }
        });
    }

    private void finishThis(){
        WriteFeedActivity.this.finish();
    }

}
