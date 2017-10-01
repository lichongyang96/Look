package com.example.lichongyang.look;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lichongyang on 2017/9/27.
 */

public class MyApplication extends Application{
    private static volatile MyApplication instance;

    private Set<Activity> activities;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    public static synchronized MyApplication getInstance(){
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        activities = new HashSet<>();
        getScreenSize();
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public void clearAll(){
        synchronized (activities){
            for (Activity activity : activities){
                activity.finish();
            }
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    public void getScreenSize(){
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT){
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }
}
