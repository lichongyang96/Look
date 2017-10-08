package com.example.lichongyang.look.contract.zhihu;

import com.example.lichongyang.look.base.BasePresenter;
import com.example.lichongyang.look.base.BaseView;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuHotItem;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeItem;

import java.util.ArrayList;

/**
 * Created by lichongyang on 2017/10/7.
 */

public interface ZhihuHotContract {
    interface View extends BaseView<Presenter>{
        void showHotList(ArrayList<ZhihuHotItem> items);
        void showError(String error);
    }
    interface Presenter extends BasePresenter{
        void getHotList();
    }
}
