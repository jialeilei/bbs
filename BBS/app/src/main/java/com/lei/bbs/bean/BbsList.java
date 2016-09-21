package com.lei.bbs.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 2016/9/21.
 */
public class BbsList {

    List<BBS> bbs = new ArrayList<BBS>();

    public BbsList(List<BBS> bbs){
        this.bbs = bbs;
    }

    public List<BBS> getBbs() {
        return bbs;
    }

    public void setBbs(List<BBS> bbs) {
        this.bbs = bbs;
    }
}
