package com.lei.bbs.bean;

/**
 * Created by lei on 2016/9/26.
 */
public class MainFeed {
    private int userId;
    private String title;
    private String content;

    public MainFeed(int userId,String title,String content){
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
