package com.lei.bbs.retrofit;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpHelper {

    /**
     * @param baseUrl
     * @return Service
     */
    public static StarHomeService createHubService(String baseUrl){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        okHttpBuilder.addInterceptor(logging);


        OkHttpClient client = okHttpBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                        //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                        //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  retrofit.create(StarHomeService.class);
    }

    /**
     * @param ipOrUrl
     * @return Service
     */
    public static StarHomeService createHubService2(String ipOrUrl){
        if(!ipOrUrl.startsWith("http://")){
            ipOrUrl="http://"+ipOrUrl;
        }
        if(!ipOrUrl.endsWith("/"))ipOrUrl=ipOrUrl+"/";
        return  createHubService(ipOrUrl);
    }

}
