package com.example.lichongyang.look.model.bean.zhihu;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/7.
 */

public class ZhihuThemeStory {
    private int id;
    private ArrayList<String> images;
    private String title;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
