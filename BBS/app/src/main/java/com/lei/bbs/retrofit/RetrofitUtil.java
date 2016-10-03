package com.lei.bbs.retrofit;



import com.lei.bbs.bean.Response;
import com.lei.bbs.constant.Constants;
import com.lei.bbs.util.MyLog;
import retrofit2.Call;
import retrofit2.Callback;


public class RetrofitUtil{

    private static final String TAG="RetrofitUtil";
    RetrofitService retrofitService = HttpHelper.createHubService(Constants.base_url);



    public void sendImage(int uid,String avatar){
        Call<Response> sendImg = retrofitService.sendImageHead(uid,avatar);
        sendImg.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body().getStatus().equals("1")){
                    MyLog.i(TAG,"post image head success ");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

                MyLog.i(TAG," post image head fail ");
            }
        });
    }



}
