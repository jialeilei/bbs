package com.lei.bbs.bean;


import com.lei.bbs.constant.Constants;

/**
 * Created by lei on 2016/9/6.
 */

public class AnswerFeed extends Entity{

    private int aid;
    private int uid;
    private String content;
    private String name;
    private String sex;

    private String avatar;
    private int score;

    //private int floor;
    private String sendTime;
    private String title;


    public AnswerFeed(int aid,int uid,String name, int score, String sex, String sendTime, String title, String content,String avatar){
        this.aid = aid;
        this.name = name;
        this.score = score;
        this.sex = sex;

        this.uid = uid;
        this.sendTime = sendTime;
        this.title = title;
        this.content = content;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getScore() {
        return score;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
