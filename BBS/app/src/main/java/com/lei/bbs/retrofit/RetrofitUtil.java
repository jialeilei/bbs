package com.lei.bbs.retrofit;


import android.util.Log;

import com.lei.bbs.constant.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrofitUtil{

    private static final String TAG="RetrofitUtil";
    StarHomeService starHomeService = HttpHelper.createHubService(Constants.base_url);







    public void putRegister(String name,String pwd){

        Call<String> putRegister = starHomeService.putRegister(name,pwd);
        putRegister.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.i(TAG, "onFailure: ");
            }
        });
    }


}
