package com.example.lichongyang.look.model.bean.zhihu;

/**
 * Created by lichongyang on 2017/10/2.
 */

public class ZhihuDailyItem {
    private String[] images;
    private int type;
    private String id;
    private String title;
    private String date;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage(){
        return images[0];
    }
}
