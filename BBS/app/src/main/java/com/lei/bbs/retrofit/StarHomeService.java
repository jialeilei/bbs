package com.lei.bbs.retrofit;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * this Interface is the Service include all the method of HubAPI based on Rest
 * HUb Rest的API结构
 * /bulbs
 Collection of available bulbs in Hub
 HTTP服务地址：202.11.4.67:3001
 /bulbs GET POST
 /bulbs/{devMac} GET PUT DELETE
 /bulbs/{devMac}/switch GET PUT
 /bulbs/{devMac}/rgb GET PUT
 /bulbs/{devMac}/cct GET PUT
 /bulbs/{devMac}/brightness
 */

public interface StarHomeService {


        @PUT("register.php/")
    Call<String>  putRegister(@Body String name,String pwd);
    @FormUrlEncoded
    @POST("register.php")
    Call<String> register(@FieldMap Map<String, String> fields);


}
