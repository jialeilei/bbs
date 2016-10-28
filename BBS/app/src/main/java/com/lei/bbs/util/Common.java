package com.lei.bbs.util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * Created by lei on 2016/9/25.
 */
public class Common {


    /**
     * @param a
     * @param b
     * @return
     */
    public static Boolean isEmpty(String a, String b){
        if (a.equals("")||b.equals("")){
            return false;
        }else {
            return true;
        }
    }


    /**
     * @param score
     * @return level
     */
    public static int scoreToLevel(int score){

        if (score <= 10){
            return 1;
        }
        else if (10 < score && score <= 20){
            return 2;
        }
        else if (20 < score && score <= 40){
            return 3;
        }
        else if (40 < score && score <= 70){
            return 4;
        }
        else if (70 < score && score <= 110){
            return 5;
        }
        else {
            return 6;
        }
    }

    /**
     * @param bitmap
     * @return
     */
    public static String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray=byteArrayOutputStream.toByteArray();
        String avatar = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        return avatar;
    }

    /**
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string){
        byte[] byteArray=Base64.decode(string, Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
        //第三步:利用ByteArrayInputStream生成Bitmap
        Bitmap bitmap= BitmapFactory.decodeStream(byteArrayInputStream);
        return bitmap;
    }

}
