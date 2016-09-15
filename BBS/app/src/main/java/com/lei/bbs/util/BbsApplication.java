package com.lei.bbs.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Pair;

/**
 * Created by lei on 2016/9/14.
 */
public class BbsApplication extends Application{

    private String LOGIN_INFO = "loginInfo"; //info name
    private String NAME = "userName";
    private String PASSWORD = "password";


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




}
