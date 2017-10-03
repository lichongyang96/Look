package com.example.lichongyang.look.model.bean.zhihu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/2.
 */

public class ZhihuDaily {
    @SerializedName("date")
    private String date;
    @SerializedName("stories")
    private ArrayList<ZhihuDailyItem> zhihuDailyItems;
    @SerializedName("top_stories")
    private ArrayList<ZhihuDailyTopItem> zhihuDailyTopItems;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ZhihuDailyItem> getZhihuDailyItems() {
        return zhihuDailyItems;
    }

    public void setZhihuDailyItems(ArrayList<ZhihuDailyItem> zhihuDailyItems) {
        this.zhihuDailyItems = zhihuDailyItems;
    }

    public ArrayList<ZhihuDailyTopItem> getZhihuDailyTopItems() {
        return zhihuDailyTopItems;
    }

    public void setZhihuDailyTopItems(ArrayList<ZhihuDailyTopItem> zhihuDailyTopItems) {
        this.zhihuDailyTopItems = zhihuDailyTopItems;
    }
}
