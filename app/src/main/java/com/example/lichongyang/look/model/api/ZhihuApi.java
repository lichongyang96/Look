package com.example.lichongyang.look.model.api;

import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDaily;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDailyDetail;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDailyDetailExtra;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuTheme;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuThemeContent;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lichongyang on 2017/10/3.
 */

public interface ZhihuApi {
    String BASE_URL = Constants.ZHIHU_BASE_URL;

    /**
     * 获取知乎日报当天日报
     * @return
     */
    @GET("news/latest")
    Observable<ZhihuDaily> getLatestNews();

    /**
     * 获取知乎日报往期日报
     * @param date 日报的时间
     * @return
     */
    @GET("news/before/{date}")
    Observable<ZhihuDaily> getBeforeNews(@Path("date") String date);

    /**
     * 获取日报详情
     * @param id 日报id
     * @return
     */
    @GET("news/{id}")
    Observable<ZhihuDailyDetail> getDailyDetail(@Path("id") int id);

    /**
     * 获取日报对应的额外信息，评论数，点赞数等
     * @param id
     * @return
     */
    @GET("story-extra/{id}")
    Observable<ZhihuDailyDetailExtra> getDailyDetailExtra(@Path("id") int id);

    /**
     * 获取主题列表
     * @return
     */
    @GET("themes")
    Observable<ZhihuTheme> getTheme();

    @GET("theme/{id}")
    Observable<ZhihuThemeContent> getThemeContent(@Path("id") int id);
}
