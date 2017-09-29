package com.example.lichongyang.look.model;

import android.util.Log;

import com.example.lichongyang.look.MyApplication;
import com.example.lichongyang.look.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * create service
 * Created by lichongyang on 2017/9/28.
 */

public class NetManager {
    private static volatile NetManager instance;

    private NetManager(){}

    public static NetManager getInstance(){
        if (instance == null){
            synchronized (NetManager.class){
                if (instance == null){
                    instance = new NetManager();
                }
            }
        }
        return instance;
    }

    public <T> T create(Class<T> service){
        Retrofit retrofit = new Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getBaseUrl(service))
                .build();
        return retrofit.create(service);
    }

    private <T> String getBaseUrl(Class<T> service){
        try{
            Field field = service.getField("BASE_URL");
            return (String)field.get(service);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return "";
    }

    private OkHttpClient getClient(){
        OkHttpClient client = null;
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                if (NetWorkUtils.isNetWorkAvailable(MyApplication.getContext())){
                    int maxTime = 60;  //在线缓存一分钟
                    return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                            .build();
                }else {
                    int maxTime = 60 * 60 * 24 * 28;   //离线缓存四周
                    return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                            .build();
                }
            }
        };
        File httpCacheDirectory = new File(MyApplication.getContext().getCacheDir(), "look");
        int cacheSize = 20 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        client = new OkHttpClient.Builder().addNetworkInterceptor(interceptor).addInterceptor(interceptor)
                .cache(cache).build();
        return client;
    }
}
