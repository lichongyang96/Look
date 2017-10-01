package com.example.lichongyang.look.contract.gank;

import com.example.lichongyang.look.base.BasePresenter;
import com.example.lichongyang.look.base.BaseView;
import com.example.lichongyang.look.model.bean.gank.MeiziBean;

import java.util.List;

/**
 * Created by lichongyang on 2017/9/28.
 */

public interface MeiziContract {
    interface View extends BaseView<Presenter> {
        void showLeastImage(List<MeiziBean> meiziList);
        void showMoreImage(List<MeiziBean> meiziList);
        void showError(String msg);
    }
    interface Presenter extends BasePresenter{
        void getLeastImage();
        void getMoreImage(int index);
    }
}
