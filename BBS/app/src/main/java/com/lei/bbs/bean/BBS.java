package com.lei.bbs.bean;

/**
 * Created by lei on 2016/9/5.
 */

public class BBS {

    private int userId;
    private int mid; //main feed id
    private String name;
    private String sex;
    private int score;
    private String title;
    private String content;
    private String sendTime;

    // empty now
    private int answerTimes;
    private String avatar;


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


    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
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

    public int getScore() {

        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
