package com.example.lichongyang.look.model.bean.zhihu;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/8.
 */

public class ZhihuSectionStory {
    private int id;
    private ArrayList<String> images;
    private String title;
    private String date;
    private String display_date;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisplay_date() {
        return display_date;
    }

    public void setDisplay_date(String display_date) {
        this.display_date = display_date;
    }
}
