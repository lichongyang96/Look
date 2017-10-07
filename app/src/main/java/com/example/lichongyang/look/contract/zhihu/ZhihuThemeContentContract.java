package com.example.lichongyang.look.contract.zhihu;

import com.example.lichongyang.look.base.BasePresenter;
import com.example.lichongyang.look.base.BaseView;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeContent;

/**
 * Created by lichongyang on 2017/10/7.
 */

public interface ZhihuThemeContentContract {
    interface View extends BaseView<Presenter>{
        void showThemeContent(ZhihuThemeContent zhihuThemeContent);
        void showError(String error);
        int getId();
    }
    interface Presenter extends BasePresenter{
        void getThemeContent(int id);
    }
}
