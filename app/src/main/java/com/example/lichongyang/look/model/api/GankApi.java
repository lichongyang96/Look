package com.example.lichongyang.look.model.api;

import com.example.lichongyang.look.base.Constants;
import com.example.lichongyang.look.model.bean.gank.Meizi;
import com.example.lichongyang.look.model.bean.gank.Tech;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * gank api 接口
 * Created by lichongyang on 2017/9/27.
 */

public interface GankApi {
    String BASE_URL = Constants.GANK_BASE_URL;

    @GET("api/data/福利/20/{page}")
    Observable<Meizi> getMeizi(@Path("page") int page);

    @GET("api/data/{tech}/20/{page}")
    Observable<Tech> getTech(@Path("tech") String tech, @Path("page") int page);

    @GET("api/random/data/福利/{num}")
    Observable<Meizi> getRandomMeizi(@Path("num") int num);
}
