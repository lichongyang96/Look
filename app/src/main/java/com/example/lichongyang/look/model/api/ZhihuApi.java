package com.example.lichongyang.look.model.api;

import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.model.bean.zhihu.ZhihuDaily;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lichongyang on 2017/10/3.
 */

public interface ZhihuApi {
    String BASE_URL = Constants.ZHIHU_BASE_URL;

    @GET("news/latest")
    Observable<ZhihuDaily> getLatestNews();

    @GET("news/before/{date}")
    Observable<ZhihuDaily> getBeforeNews(@Path("date") String date);

}