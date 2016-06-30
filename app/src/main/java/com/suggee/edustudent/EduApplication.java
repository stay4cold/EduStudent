package com.suggee.edustudent;

import android.app.Application;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/24
 * Description:
 */
public class EduApplication extends Application {

    private static EduApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    public static EduApplication getInstance() {
        return sApplication;
    }
}
