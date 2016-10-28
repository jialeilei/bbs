package com.lei.bbs.bean;


/**
 * Created by lei on 2016/9/26.
 */

public class ContentImage {

    private int imageCount;
    private String contentImage;

    public ContentImage(int imageCount, String contentImage){
        this.imageCount = imageCount;
        this.contentImage = contentImage;
    }


    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }
}
