package com.lei.bbs.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lei.bbs.R;
import com.lei.bbs.adapter.MainAdapter;
import com.lei.bbs.bean.BBS;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.retrofit.HttpHelper;
import com.lei.bbs.retrofit.RetrofitService;
import com.lei.bbs.util.CircleImage;
import com.lei.bbs.util.Common;
import com.lei.bbs.util.MyToolBar;
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
    private CircleImage imgHead;
    private RelativeLayout rlHead;
    private ImageButton imgSetting, imgWrite;
    private ListView lvMain;
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvName, tvLevel;
    private Button btnLogout;
    private ImageView imgSex;
    //others
    private MainAdapter mainAdapter;
    private String TAG="MainActivity";
    private android.os.Handler handler = new android.os.Handler();
    private boolean isShowing = false;

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

        rlHead = (RelativeLayout) findViewById(R.id.rlHeadContent);
        rlHead.setOnClickListener(this);
        tvName = (TextView) findViewById(R.id.tvName);
        tvLevel = (TextView) findViewById(R.id.tvLevel);
        imgSex = (ImageView) findViewById(R.id.imgSex);

        lvMain = (ListView) findViewById(R.id.lvMain);
        mainAdapter = new MainAdapter(this,bbsList);
        lvMain.setAdapter(mainAdapter);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
        isUserOnLine();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.title_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestListView();
            }
        });


        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, BbsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("userId", bbsList.get(position).getUserId());
                bundle.putInt("mainId", bbsList.get(position).getMid());
                bundle.putString("name", bbsList.get(position).getName());
                bundle.putString("sex", bbsList.get(position).getSex());
                bundle.putInt("score", bbsList.get(position).getScore());
                bundle.putString("title", bbsList.get(position).getTitle());
                bundle.putString("content", bbsList.get(position).getContent());
                bundle.putString("sendTime", bbsList.get(position).getSendTime());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setRefreshing(true);
        requestListView();

    }

    private void refreshListView(){
        mainAdapter = new MainAdapter(this,bbsList);
        lvMain.setAdapter(mainAdapter);

    }

    private void requestListView(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getFeedList();
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

    @Override
    public void setToolBar() {
        toolBar = (MyToolBar) findViewById(R.id.toolbar);
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

                if (Constants.userName.equals("")||Constants.sex.equals("")){
                    isUserOnLine();
                }else {
                    Intent intent = new Intent(MainActivity.this,WriteFeedActivity.class);
                    startActivity(intent);
                }

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

                isUserOnLine();
            }
        });
    }


    private void getFeedList(){
        RetrofitService service = HttpHelper.createHubService(Constants.base_url);
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
            case R.id.btnLogout:
                if (!Constants.userName.equals("")){
                    showLogoutDialog();
                }

                break;
            /*case R.id.imgHead:
                isUserOnLine();

                break;*/
            case R.id.rlHeadContent:
                Intent intent = new Intent(MainActivity.this,UserInfoActivity.class);
                startActivity(intent);
                MyLog.i(TAG,"rlHeadContent was clicked ");
                break;
            default:

                break;
        }
    }


    private void showLogoutDialog(){
        View view = getLayoutInflater().inflate(R.layout.dialog_logout,null);
        final Dialog logoutDialog = new Dialog(this,R.style.transparentFrameWindowStyle);
        logoutDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        logoutDialog.setCanceledOnTouchOutside(true);
        Window window = logoutDialog.getWindow();
        logoutDialog.show();
        Button btnCancel = (Button) window.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.dismiss();
            }
        });

        Button btnConfirm = (Button) window.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isEmpty(Constants.userName, Constants.sex)){

                    SharedPreferences userInfo = getSharedPreferences(Constants.SHARE_USER_INFO,MODE_PRIVATE);
                    userInfo.edit().clear().commit();
                    SharedPreferences loginInfo = getSharedPreferences(Constants.SHARE_LOGIN_INFO,MODE_PRIVATE);
                    loginInfo.edit().clear().commit();
                    isShowing = false;
                    MyLog.i(TAG, "已经退出");
                    logoutDialog.dismiss();
                    clearUserInfo();
                    toLoginActivity();
                }
            }
        });
    }

    private void isUserOnLine(){

        if (Constants.userName.equals("") || Constants.sex.equals("")){
            SharedPreferences user = getSharedPreferences("userInfo", MODE_PRIVATE);

            if (user.getInt("id",0)==0
                    ||user.getString("name","").equals("")
                    ||user.getString("sex","").equals("")){

                MyLog.i(TAG, "没有信息,需要登录");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }else {
                Constants.userId = user.getInt("id",0);
                Constants.userName = user.getString("name", "");
                Constants.sex = user.getString("sex","");
                Constants.level = user.getInt("level", 1);
                Constants.avatar = user.getString(Constants.SHARE_AVATAR,"");
                MyLog.i(TAG, "无需登录");
                setUserInfo();
            }

        }
    }


    private void setUserInfo(){
        tvName.setText(Constants.userName);
        tvLevel.setText("LV."+Common.scoreToLevel(Constants.level));
        if (Constants.sex.equals("boy")){
            imgSex.setImageResource(R.mipmap.boy);
        }else {
            imgSex.setImageResource(R.mipmap.girl);
        }
        if (!Constants.avatar.equals("")){
            imgHead.setImageBitmap(Common.stringToBitMap(Constants.avatar));
        }
        MyLog.i(TAG,"name "+Constants.userName+" sex "+Constants.sex);
        isShowing = true;
    }

    private void clearUserInfo(){
        tvName.setText("");
        tvLevel.setText("");
        Constants.userName = "";
        Constants.sex = "";
        isShowing = false;
    }

    private void toLoginActivity(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        isUserOnLine();
        if (!isShowing){
            setUserInfo();
        }

        swipeRefreshLayout.setRefreshing(true);
        requestListView();
        MyLog.i(TAG, "is refreshing ... ");

    }



}
