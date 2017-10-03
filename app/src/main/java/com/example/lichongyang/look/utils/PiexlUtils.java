package com.example.lichongyang.look.utils;

import android.content.Context;

import com.example.lichongyang.look.MyApplication;

/**
 * Created by lichongyang on 2017/9/29.
 */

public class PiexlUtils {
    public static int dp2px(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    public static int dp2px(float dpValue){
        final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

}
