package com.lei.bbs.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 2016/9/21.
 */
public class AnswerFeedList {

    List<AnswerFeed> feedList = new ArrayList<AnswerFeed>();

    public AnswerFeedList(List<AnswerFeed> feedList){
        this.feedList = feedList;
    }

    public List<AnswerFeed> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<AnswerFeed> feedList) {
        this.feedList = feedList;
    }
}
