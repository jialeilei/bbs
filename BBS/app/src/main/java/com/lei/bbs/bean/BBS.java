package com.lei.bbs.bean;


import java.util.ArrayList;

/**
 * Created by lei on 2016/9/5.
 */

public class BBS extends Entity{

    private int userId;
    private int mid; //main feed id
    private String name;
    private String sex;
    private int score;
    private String title;
    private String content;
    private String sendTime;
    private int answerNumber;
    // empty now
    private String avatar;

    private ArrayList<ContentImage> imageList;


   /* public BBS(String name,String sendTime,int answerNumber,String title,String content,String avatar){
        this.name = name;
        this.sendTime = sendTime;
        this.answerNumber = answerNumber;
        this.title = title;
        this.content = content;
        this.avatar = avatar;
    }*/

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        this.answerNumber = answerNumber;
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

    public ArrayList<ContentImage> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<ContentImage> imageList) {
        this.imageList = imageList;
    }
}
