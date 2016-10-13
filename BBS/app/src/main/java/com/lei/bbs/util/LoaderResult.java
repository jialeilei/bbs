package com.lei.bbs.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by lei on 2016/10/12.
 */
public class LoaderResult {
    public ImageView imageView;
    public String uri;
    public Bitmap bitmap;

    public LoaderResult(ImageView imageView,String uri,Bitmap bitmap){
        this.imageView = imageView;
        this.uri = uri;
        this.bitmap = bitmap;
    }

}
