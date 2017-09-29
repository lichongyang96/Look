package com.example.lichongyang.look.model;

/**
 * Created by lichongyang on 2017/9/27.
 */

public class ApiManager {
    private static volatile ApiManager instance;

    private ApiManager(){}

    public static ApiManager getInstance(){
        if (instance == null){
            synchronized (NetManager.class){
                if (instance == null){
                    instance = new ApiManager();
                }
            }
        }
        return instance;
    }

    public <T> T getService(Class<T> service){
        return NetManager.getInstance().create(service);
    }

}
