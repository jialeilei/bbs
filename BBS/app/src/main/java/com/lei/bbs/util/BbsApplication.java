package com.lei.bbs.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lei on 2016/9/14.
 */
public class BbsApplication extends Application{

    //log info
    private String LOGIN_INFO = "loginInfo";
    private String NAME = "userName";
    private String PASSWORD = "password";
    //user info
    private String USER_INFO = "userInfo";
    private String ID = "userInfo";
    private String SEX = "userInfo";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * get info
     * @return
     */
    public Pair<String,String> loadLoginInfo(){
        SharedPreferences loginPreference = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        return new Pair<String,String>(loginPreference.getString(NAME, ""),loginPreference.getString(PASSWORD,""));
    }


    /**
     * @param strUserName
     * @param strPassword
     */
    public void saveLoginInfo(String strUserName, String strPassword) {
        SharedPreferences loginPreferences = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        loginPreferences.edit()
                .putString(NAME, strUserName)
                .putString(PASSWORD, strPassword)
                .commit();
    }


    /**
     * clear info
     */
    public void clearLoginInfo() {
        SharedPreferences loginPreferences =
                getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        loginPreferences.edit().clear().commit();
    }



    /**
     * @param id
     * @param name
     * @param sex
     */
    public void saveUserInfo(int id,String name,String sex){
        SharedPreferences userPreferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putInt("id",id);
        editor.putString("name", name);
        editor.putString("sex", sex);
        editor.commit();

    }

    public List<String> loadUserInfo(){

        SharedPreferences userInfo = getSharedPreferences(USER_INFO,MODE_PRIVATE);
        ArrayList<String> list = new ArrayList<String>();
        list.add(userInfo.getInt("id", 0) + "");
        list.add(userInfo.getString("name", ""));
        list.add(userInfo.getString("sex", ""));

        return list;

    }

}
