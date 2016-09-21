package com.lei.bbs.bean;

/**
 * Created by lei on 2016/9/5.
 */

public class BBS {

    private int userId;
    private String name;

    private String avatar;

    private String sendTime;

    private int answerTimes;//

    private String title;
    private String content;

   /* public BBS(String name,String avatar,String sendTime,int answerTimes,String title,String content ){
        this.name = name;
        this.avatar = avatar;
        this.sendTime = sendTime;
        this.answerTimes = answerTimes;
        this.title = title;
        this.content = content;
    }*/

    public BBS(String name,String sendTime,int answerTimes,String title,String content ){
        this.name = name;
        this.sendTime = sendTime;
        this.answerTimes = answerTimes;
        this.title = title;
        this.content = content;
    }





    public int getAnswerTimes() {
        return answerTimes;
    }

    public void setAnswerTimes(int answerTimes) {
        this.answerTimes = answerTimes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


}
