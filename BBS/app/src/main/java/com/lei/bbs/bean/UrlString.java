package com.lei.bbs.bean;

/**
 * Created by lei on 2016/9/5.
 */

public class UrlString extends Entity{

   private String url;

    public UrlString(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
