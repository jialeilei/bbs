package com.lei.bbs.imageSelector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * load local image class
 * Created by lei on 2016/10/25.
 */
public class LocalImageLoader {

    public static LocalImageLoader mInstance;

    private LruCache<String,Bitmap> mLruCache;

    /*
    * threadPool
    * */
    private ExecutorService mThreadPool;
    private static int DEFAULT_THREAD_COUNT = 1;
    /*
    * 队列调度方式
    * */
    private Type mType = Type.LIFO;

    public enum Type{
        FIFO,LIFO;
    }

    /*
    * 任务队列
    * */
    private LinkedList<Runnable> mTaskQueue;
    /*
    * 轮询线程
    * */
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;
    //信号量
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;

    private Handler mUIHandler;


    private LocalImageLoader(int threadCount,Type type){
        init(threadCount,type);
    }

    public static LocalImageLoader getInstance(int threadCount,Type type){
        if (mInstance == null){
            synchronized (LocalImageLoader.class){
                if (mInstance == null){
                    mInstance = new LocalImageLoader(threadCount,type);
                }
            }
        }
        return mInstance;
    }

    public static LocalImageLoader getInstance(){
        if (mInstance == null){
            synchronized (LocalImageLoader.class){
                if (mInstance == null){
                    mInstance = new LocalImageLoader(DEFAULT_THREAD_COUNT,Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     * @param threadCount
     * @param type
     */
    private void init(int threadCount, Type type) {
        /*
        * 后台轮询线程
        * */
        mPoolThread = new Thread(){
            @Override
            public void run() {

                Looper.prepare();
                mPoolThreadHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        mThreadPool.execute(getTask());
                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            }
        };
        mPoolThread.start();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;
        mLruCache = new LruCache<String,Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        /*
        * create threadPool
        * */
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;
        mSemaphoreThreadPool = new Semaphore(threadCount);
    }

    public void loadImage(final String path, final ImageView imageView){
        imageView.setTag(path);
        if (mUIHandler == null){
            mUIHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    //回调设置图片
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    Bitmap bp = holder.bitmap;
                    ImageView img = holder.imageView;
                    String path = holder.path;
                    if (img.getTag().toString().equals(path)){
                        img.setImageBitmap(bp);
                    }

                }
            };
        }

        Bitmap bitmap = getBitmapFromLruCache(path);
        if (bitmap != null){
            refreshBitmap(path,imageView,bitmap);
        }else {
            addTask(new Runnable() {
                @Override
                public void run() {
                    //获取图片需要显示的大小
                    ImageSize imageSize = getImageViewSize(imageView);
                    //压缩图片
                    Bitmap bt = decodeSampleBitmapFromPath(path,imageSize.width,imageSize.height);
                    //加入到缓存中
                    addBitmapToLruCache(path, bt);
                    refreshBitmap(path, imageView, bt);
                    mSemaphoreThreadPool.release();
                }
            });
        }

    }


    private void refreshBitmap(String path,ImageView imageView,Bitmap bitmap){
        Message message = new Message();
        //callback
        ImgBeanHolder holder = new ImgBeanHolder();
        holder.imageView = imageView;
        holder.path = path;
        holder.bitmap = bitmap;
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }


    private Bitmap decodeSampleBitmapFromPath(String path, int width, int height) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        options.inSampleSize = calculateInSampleSize(options, width, height);

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight){
            int widthRadio = Math.round(width *1f/reqWidth);
            int heightRadio = Math.round(height *1f/reqHeight);
            inSampleSize = Math.max(widthRadio,heightRadio);
        }
        return inSampleSize;
    }


    private ImageSize getImageViewSize(ImageView imageView) {
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ImageSize imageSize = new ImageSize();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        int width = imageView.getWidth();//实际宽度
        if (width <= 0){
            width = lp.width;//申明宽度
        }
        if (width <= 0){
            width = getImageViewFieldValue(imageView,"mMaxWidth");//获取最大宽度
        }
        if (width <= 0){
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getHeight();
        if (height <= 0){
            height = lp.height;
        }
        if (height <= 0){
            height = getImageViewFieldValue(imageView,"mMaxHeight");
        }
        if (height <= 0){
            height = displayMetrics.heightPixels;
        }
        imageSize.width = width;
        imageSize.height = height;

        return imageSize;
    }

    /**
     * 通过反射获取值
     * @param object
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(Object object,String fieldName){

        int value = 0;
        try {
            java.lang.reflect.Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(object);

            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE){
                value = fieldValue;
            }
        } catch (Exception e) {
        }

        return value;
    }

    private synchronized void addTask(Runnable runnable) {
        mTaskQueue.add(runnable);
        try {
            if (mPoolThreadHandler == null)
            mSemaphorePoolThreadHandler.acquire();
        } catch (InterruptedException e) {

        }
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    private Runnable getTask(){
        if (mType == Type.LIFO){
            return mTaskQueue.removeLast();
        }else {
            return mTaskQueue.removeFirst();
        }
    }

    private void addBitmapToLruCache(String path, Bitmap bt) {
        if (getBitmapFromLruCache(path) == null){
            if (bt != null){
                mLruCache.put(path,bt);
            }
        }

    }

    private Bitmap getBitmapFromLruCache(String key) {
        return mLruCache.get(key);
    }

    private class ImgBeanHolder{
        ImageView imageView;
        String path;
        Bitmap bitmap;
    }

    private class ImageSize{
        int width;
        int height;
    }

}
