package com.example.lichongyang.look.model.bean.zhihu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/8.
 */

public class ZhihuHot {
    @SerializedName("recent")
    private ArrayList<ZhihuHotItem> items;

    public ArrayList<ZhihuHotItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ZhihuHotItem> items) {
        this.items = items;
    }
}
