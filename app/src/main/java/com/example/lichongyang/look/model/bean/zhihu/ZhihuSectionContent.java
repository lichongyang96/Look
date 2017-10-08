package com.example.lichongyang.look.model.bean.zhihu;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/8.
 */

public class ZhihuSectionContent {
    private String timestamp;
    private ArrayList<ZhihuSectionStory> stories;
    private String name;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<ZhihuSectionStory> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhihuSectionStory> stories) {
        this.stories = stories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
