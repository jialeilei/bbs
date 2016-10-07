package com.lei.bbs.bean;

/**
 * Created by lei on 2016/10/5.
 */

public class TalkFeed extends Entity{


    private int tid;
    //评论者信息
    private int uid;
    private String uname;
    //主贴ID
    private int mid;
    //回帖ID
    private int aid;
    //被评论者信息
    private int to_tid;
    private String to_tname;
    //内容、时间
    private String content;
    private String talktime;


    public TalkFeed(int tid,int uid,String uname,int mid,int aid,int to_tid,String to_tname,String content,String talktime){
        this.tid = tid;
        this.uid = uid;
        this.uname = uname;
        this.mid = mid;
        this.aid = aid;
        this.to_tid = to_tid;
        this.to_tname = to_tname;
        this.content = content;
        this.talktime = talktime;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getTo_tid() {
        return to_tid;
    }

    public void setTo_tid(int to_tid) {
        this.to_tid = to_tid;
    }

    public String getTo_tname() {
        return to_tname;
    }

    public void setTo_tname(String to_tname) {
        this.to_tname = to_tname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTalktime() {
        return talktime;
    }

    public void setTalktime(String talktime) {
        this.talktime = talktime;
    }

    /*private int userId;
    private String title;
    private String content;

    public TalkFeed(int userId, String title, String content){
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
    }*/
}
