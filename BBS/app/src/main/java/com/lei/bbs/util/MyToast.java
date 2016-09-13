package com.lei.bbs.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lei on 2016/9/13.
 */
public class MyToast {


    public static boolean isShow = true;

    public MyToast(Context context,String Msg){
        Toast.makeText(context,Msg,Toast.LENGTH_LONG).show();
    }

    public static void showShort(Context context,CharSequence msg){
        if (isShow)
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Context context,int msg){

        if (isShow)
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context,CharSequence msg){
        if (isShow)
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static void showLong(Context context,int msg){
        if (isShow)
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

}
