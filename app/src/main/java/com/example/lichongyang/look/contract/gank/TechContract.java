package com.example.lichongyang.look.contract.gank;

import com.example.lichongyang.look.base.BasePresenter;
import com.example.lichongyang.look.base.BaseView;
import com.example.lichongyang.look.model.bean.gank.MeiziBean;
import com.example.lichongyang.look.model.bean.gank.TechBean;

import java.util.List;

/**
 * Created by lichongyang on 2017/9/29.
 */

public interface TechContract {
    interface View extends BaseView {
        void setPresenter(Presenter presenter);
        void showLeastTech(List<TechBean> techList);
        void showMoreTech(List<TechBean> techList);
        void showMeiziImage(List<MeiziBean> meiziList);
        void showError(String msg);
    }
    interface Presenter extends BasePresenter {
        void getLeastTech(String type);
        void getMoreTech(int index, String type);
        void getMeiziImage();
    }
}
