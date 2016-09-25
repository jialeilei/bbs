package com.lei.bbs.bean;

/**
 * Created by lei on 2016/9/14.
 */
public class User {

    private int id;
    private String userName;
    private String nickName;
    private String sex;
    private String avatar;

    public User(String userName,String nickName,String sex,String avatar){
        this.userName = userName;
        this.nickName = nickName;
        this.sex = sex;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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


}
