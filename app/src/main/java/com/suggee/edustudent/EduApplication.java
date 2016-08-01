package com.suggee.edustudent;

import android.app.Application;

import com.facebook.stetho.Stetho;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        Stetho.initializeWithDefaults(this);
        configRealm();
    }

    public static EduApplication getInstance() {
        return sApplication;
    }

    private void configRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder(this)
                .name("stuphone.realm")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(configuration);
    }
}
