package com.suggee.edustudent.modulers.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.suggee.edustudent.R;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.common.AppContext;
import com.suggee.edustudent.modulers.login.LoginActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/25
 * Description:
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash)
    ImageView mSplash;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        addSubscription(Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        gotoNextActivity();
                    }
                }));
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_splash;
    }

    private void gotoNextActivity() {
        if (AppContext.getOauthUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            //调用登陆接口，自动登陆
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
