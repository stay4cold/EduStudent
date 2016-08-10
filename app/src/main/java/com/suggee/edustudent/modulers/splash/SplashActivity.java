package com.suggee.edustudent.modulers.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.suggee.edustudent.R;
import com.suggee.edustudent.api.ApiClient;
import com.suggee.edustudent.api.ApiException;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.OauthUser;
import com.suggee.edustudent.common.AppContext;
import com.suggee.edustudent.modulers.home.HomeActivity;
import com.suggee.edustudent.modulers.login.LoginActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            //调用登陆接口，自动登陆
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void login() {
        OauthUser user = Realm.getDefaultInstance().where(OauthUser.class).equalTo("logined", true).findFirst();
        if (user != null) {
            addSubscription(ApiClient.getApiService()
                                     .login(user.getName(), user.getPassword(), "1")
                                     .flatMap(new Func1<BaseResponse<OauthUser>, Observable<OauthUser>>() {
                                         @Override
                                         public Observable<OauthUser> call(BaseResponse<OauthUser> oauthUserBaseResponse) {
                                             if (oauthUserBaseResponse.getCode() == 2000) {
                                                 return Observable.just(oauthUserBaseResponse.getData());
                                             } else {
                                                 return Observable.error(new ApiException(oauthUserBaseResponse));
                                             }
                                         }
                                     })
                                     .doOnNext(new Action1<OauthUser>() {
                                         @Override
                                         public void call(OauthUser oauthUser) {
                                             Realm realm = Realm.getDefaultInstance();
                                             realm.beginTransaction();
                                             RealmResults<OauthUser> oauthUsers = realm.where(OauthUser.class)
                                                                                       .equalTo("logined", true)
                                                                                       .findAll();
                                             for (OauthUser user : oauthUsers) {
                                                 user.setLogined(false);
                                             }

                                             oauthUser.setId(oauthUser.getUser().getId());
                                             oauthUser.setLogined(true);
                                             realm.copyToRealmOrUpdate(oauthUser);
                                             realm.commitTransaction();
                                         }
                                     })
                                     .subscribeOn(Schedulers.io())
                                     .observeOn(AndroidSchedulers.mainThread())
                                     .subscribe(new Subscriber<OauthUser>() {
                                                    @Override
                                                    public void onCompleted() {
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onNext(OauthUser oauthUser) {
                                                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                                                        finish();
                                                    }
                                                }
                                     )
            );
        }
    }
}
