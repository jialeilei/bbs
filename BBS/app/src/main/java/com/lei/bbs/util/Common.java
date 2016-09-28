package com.lei.bbs.util;


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



}
