package com.lei.bbs.util.imageLoader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.FileDescriptor;


/**
 * Created by lei on 2016/10/12.
 */
public class ImageResizer {

    private static final String TAG = "ImageResizer";

    public ImageResizer(){}

    public Bitmap decodeSampledBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //first decode to check dimensions
        BitmapFactory.decodeResource(res,resId,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        //decode bitmap with sampleSize set
        return BitmapFactory.decodeResource(res,resId,options);
    }

    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,null,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd,null,options);
    }

    /**
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return isSampleSize
     */
    public int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){

        if (reqWidth == 0 ||reqHeight == 0){
            return 1;
        }
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth){
            int halfHeight = height/2;
            int halfWidth = width/2;

            while (halfHeight/inSampleSize >= reqHeight && halfWidth/inSampleSize >= reqWidth){
                inSampleSize = inSampleSize * 2;
            }
        }

        return inSampleSize;

    }



}
