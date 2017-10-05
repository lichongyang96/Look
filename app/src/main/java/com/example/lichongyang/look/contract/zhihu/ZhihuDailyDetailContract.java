package com.example.lichongyang.look.contract.zhihu;

import com.example.lichongyang.look.base.BasePresenter;
import com.example.lichongyang.look.base.BaseView;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDailyDetail;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDailyDetailExtra;

/**
 * Created by lichongyang on 2017/10/5.
 */

public interface ZhihuDailyDetailContract{
    interface View extends BaseView<Presenter>{
        void showContent(ZhihuDailyDetail zhihuDailyDetail);
        void showExtraMessage(ZhihuDailyDetailExtra zhihuDailyDetailExtra);
        int getId();
    }
    interface Presenter extends BasePresenter{
        void getContent(int id);
        void getExtraMessage(int id);

    }
}
