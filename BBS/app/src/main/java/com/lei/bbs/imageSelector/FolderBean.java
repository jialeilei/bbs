package com.lei.bbs.imageSelector;

/**
 * Created by lei on 2016/10/26.
 */
public class FolderBean {

    /*
    * 文件夹路径
    * */
    private String dir;
    /*
    * 第一张照片路径
    * */
    private String mFirstImgDir;
    /*
    * 文件夹名称
    * */
    private String name;
    private int count;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf+1);

    }

    public String getmFirstImgDir() {
        return mFirstImgDir;
    }

    public void setmFirstImgDir(String mFirstImgDir) {
        this.mFirstImgDir = mFirstImgDir;
    }

    public String getName() {
        return name;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
