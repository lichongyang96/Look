package com.example.lichongyang.look.model.bean.zhihu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/7.
 */

public class ZhihuTheme {
    private String limit;
    private ArrayList<String> subscribed;
    @SerializedName("others")
    private ArrayList<ZhihuThemeItem> items;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public ArrayList<String> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(ArrayList<String> subscribed) {
        this.subscribed = subscribed;
    }

    public ArrayList<ZhihuThemeItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ZhihuThemeItem> items) {
        this.items = items;
    }
}
