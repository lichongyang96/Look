package com.example.lichongyang.look.contract.zhihu;

import com.example.lichongyang.look.base.BasePresenter;
import com.example.lichongyang.look.base.BaseView;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuSectionItem;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeItem;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/7.
 */

public interface ZhihuSectionContract {
    interface View extends BaseView<Presenter>{
        void showSectionList(ArrayList<ZhihuSectionItem> items);
        void showError(String error);
    }
    interface Presenter extends BasePresenter{
        void getSectionList();
    }
}
