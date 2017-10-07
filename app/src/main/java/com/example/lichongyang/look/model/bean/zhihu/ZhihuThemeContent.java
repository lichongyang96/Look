package com.example.lichongyang.look.model.bean.zhihu;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/7.
 */

public class ZhihuThemeContent {
    private String description;
    private String background;
    private int color;
    private String name;
    private String image;
    private ArrayList<ZhihuThemeStory> stories;
    private ArrayList<ZhihuThemeEditor> editors;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<ZhihuThemeStory> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhihuThemeStory> stories) {
        this.stories = stories;
    }

    public ArrayList<ZhihuThemeEditor> getEditors() {
        return editors;
    }

    public void setEditors(ArrayList<ZhihuThemeEditor> editors) {
        this.editors = editors;
    }
}
