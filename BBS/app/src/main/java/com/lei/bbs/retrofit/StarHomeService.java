package com.lei.bbs.retrofit;


import com.lei.bbs.bean.AnswerFeedList;
import com.lei.bbs.bean.BBS;
import com.lei.bbs.bean.AnswerFeed;
import com.lei.bbs.bean.Response;
import java.util.ArrayList;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Field;
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

    @POST("writeMainFeed.php")
    @FormUrlEncoded
    //@Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    //Content-Type: application/x-www-form-urlencoded
    //@Headers({"Content-Type: application/x-www-form-urlencoded","Accept: text/html"})//需要添加头
    Call<Response> postMainFeed(@Field("userId")int userId,@Field("title")String title,@Field("content")String content);

    /*@POST("writeMainFeed.php")
    @FormUrlEncoded
    Call<Response> postMainFeed(@FieldMap Map<String, String> fields);*/

        @GET("mainFeedList.php")
    Call<ArrayList<BBS>> getFeedList();


        @POST("answerFeedList.php")
        @FormUrlEncoded
    Call<ArrayList<AnswerFeed>> postAnswerFeedList(@Field("mid") int mid);

  /*  @POST("answerFeedList.php")
    @FormUrlEncoded
    Call<AnswerFeedList> postAnswerFeedList(@Field("mid") int mid);*/
}
