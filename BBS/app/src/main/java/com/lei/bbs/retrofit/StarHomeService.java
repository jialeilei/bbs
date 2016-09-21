package com.lei.bbs.retrofit;


import com.lei.bbs.bean.BBS;
import com.lei.bbs.bean.BbsList;
import com.lei.bbs.bean.Response;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface StarHomeService {


    @POST("register.php")
    @FormUrlEncoded
    Call<String> register(@FieldMap Map<String, String> fields);

    @POST("register.php")
    @FormUrlEncoded
    Call<Response> postRegister(@FieldMap Map<String, String> fields);

    @POST("login.php")
    @FormUrlEncoded
    Call<Response> postLogin(@FieldMap Map<String, String> fields);

    @GET("mainFeed.php")
    @FormUrlEncoded
    Call<BbsList> getFeedList();


}
