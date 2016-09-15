package com.lei.bbs.util;



import android.util.Log;

/**
 * Created by lei on 2016/9/15.
 */
public class MyLog {

    private static boolean isShow = true;

    public static void i(String tag,String msg){
        if (isShow){
            Log.i(tag,msg);
        }

    }

    public static void d(String tag,String msg){
        if (isShow) {
            Log.d(tag, msg);
        }

    }

    public static void e(String tag,String msg){
        if (isShow) {
            Log.e(tag,msg);
        }

    }
}
