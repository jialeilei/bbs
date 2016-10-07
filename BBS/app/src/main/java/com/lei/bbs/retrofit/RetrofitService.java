package com.lei.bbs.retrofit;


import com.lei.bbs.bean.BBS;
import com.lei.bbs.bean.AnswerFeed;
import com.lei.bbs.bean.Response;
import com.lei.bbs.bean.TalkFeed;
import java.util.ArrayList;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RetrofitService {


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

    @GET("mainFeedList.php")
    Call<ArrayList<BBS>> getFeedList();

    @POST("answerFeedList.php")
    @FormUrlEncoded
    Call<ArrayList<AnswerFeed>> getAnswerFeedList(@Field("mid") int mid);

    @POST("answerFeed.php")
    @FormUrlEncoded
    Call<Response> sendAnswerFeed(@Field("mid") int mid,@Field("uid")int uid,@Field("content")String content);

    @POST("imgUpload.php")
    @FormUrlEncoded
    Call<Response> sendImageHead(@Field("uid")int uid,@Field("avatar")String avatar);

    @POST("talkFeedList.php")
    @FormUrlEncoded
    Call<ArrayList<TalkFeed>> getTalkFeedList(@Field("mid") int mid);

    @POST("writeTalkFeed.php")
    @FormUrlEncoded
    Call<Response> postTalkFeed(
                                @Field("uid") int uid,//用户id
                                @Field("uname") String uname,//用户名
                                @Field("mid") int mid,//主贴id
                                @Field("aid") int aid,//更贴id
                                @Field("to_tid") int to_tid,//被回复用户id，为0时默认回复层主
                                @Field("to_tname") String to_tname,//被回复用户姓名
                                @Field("content") String content
                                );

}
