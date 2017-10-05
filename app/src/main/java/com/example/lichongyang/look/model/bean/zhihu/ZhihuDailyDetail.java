package com.example.lichongyang.look.model.bean.zhihu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichongyang on 2017/10/5.
 */

public class ZhihuDailyDetail {
    private String body;

    private String image_source;

    private String title;

    private String image;

    private String share_url;

    private String ga_prefix;

    private int type;

    private int id;

    private ArrayList<String> js;

    private ArrayList<String> css;

    public void setBody(String body) {
        this.body = body;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setCss(ArrayList<String> css) {
        this.css = css;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public void setJs(ArrayList<String> js) {
        this.js = js;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public List<String> getCss() {
        return css;
    }

    public List<String> getJs() {
        return js;
    }

    public String getBody() {
        return body;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public String getImage() {
        return image;
    }

    public String getImage_source() {
        return image_source;
    }

    public String getShare_url() {
        return share_url;
    }

    public String getTitle() {
        return title;
    }
}
