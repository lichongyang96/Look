package com.example.lichongyang.look.contract.zhihu;

import com.example.lichongyang.look.base.BasePresenter;
import com.example.lichongyang.look.base.BaseView;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDaily;

/**
 * Created by lichongyang on 2017/10/3.
 */

public interface ZhihuDailyContract {
    interface View extends BaseView<Presenter>{
        void showError(String error);
        void displayZhihuNews(ZhihuDaily zhihuDaily);
    }
    interface Presenter extends BasePresenter{
        void getLeastNews();
        void getBeforeNews(String time);
    }
}
