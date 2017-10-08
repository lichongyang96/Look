package com.example.lichongyang.look.model.bean.zhihu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/8.
 */

public class ZhihuSection {
    @SerializedName("data")
    private ArrayList<ZhihuSectionItem> items;

    public ArrayList<ZhihuSectionItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ZhihuSectionItem> items) {
        this.items = items;
    }
}
