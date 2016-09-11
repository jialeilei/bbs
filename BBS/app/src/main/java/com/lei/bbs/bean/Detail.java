package com.lei.bbs.bean;

/**
 * Created by CCC on 2016/9/6.
 */
public class Detail {
    private String avatar;
    private String name;
    private int level;
    private String sex;
    private boolean isTopFloor;

    private int floor;
    private String sendTime;
    private String title;
    private String content;

    public Detail(String name,int level,String sex,int floor,String sendTime,String title,String content ){
        this.name = name;
        this.level = level;
        this.sex = sex;
        this.floor = floor;
        this.sendTime = sendTime;
        this.title = title;
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isTopFloor() {
        return isTopFloor;
    }

    public void setIsTopFloor(boolean isTopFloor) {
        this.isTopFloor = isTopFloor;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
