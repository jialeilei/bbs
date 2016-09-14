package com.lei.bbs.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Pair;

/**
 * Created by lei on 2016/9/14.
 */
public class BbsApplication extends Application{


    public Pair<String,String> loadLoginInfo(String username,String password){
        SharedPreferences loginPreference = getSharedPreferences("loginInfo", MODE_PRIVATE);

        return new Pair<String,String>(loginPreference.getString(username, ""),loginPreference.getString(password,""));
    }


    public void saveLoginInfo(String strUserName, String strPassword) {
        SharedPreferences loginPreferences =
                getSharedPreferences("loginInfo", MODE_PRIVATE);
        loginPreferences.edit()
                .putString("userName", strUserName)
                .putString("password", strPassword)
                .commit();
    }



    public void clearLoginInfo() {
        SharedPreferences loginPreferences =
                getSharedPreferences("loginInfo", MODE_PRIVATE);
        loginPreferences.edit().clear().commit();
    }

}
