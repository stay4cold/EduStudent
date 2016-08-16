package com.suggee.edustudent.modulers.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.suggee.edustudent.R;
import com.suggee.edustudent.api.ApiClient;
import com.suggee.edustudent.api.ApiException;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.OauthUser;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/26
 * Description:
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.phone)
    TextInputEditText name;
    @BindView(R.id.input_phone)
    TextInputLayout inputName;
    @BindView(R.id.pwd)
    TextInputEditText pwd;
    @BindView(R.id.input_pwd)
    TextInputLayout inputPwd;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.reset_pwd)
    TextView resetPwd;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.tourist)
    TextView tourist;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        checkLoginState();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_login;
    }

    //检测登陆状态
    private void checkLoginState() {
        Observable<CharSequence> nameObservable = RxTextView.textChanges(name);
        Observable<CharSequence> pwdObservable = RxTextView.textChanges(pwd);

        addSubscription(Observable.combineLatest(nameObservable, pwdObservable, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence name, CharSequence pwd) {
                boolean nameValid = LoginUtils.nameValid(name);

                if (!nameValid) {
                    inputName.setError("用户名输入不合法");
                } else {
                    inputName.setError(null);
                }

                boolean pwdValid = LoginUtils.pwdValid(pwd);

                if (!pwdValid) {
                    inputPwd.setError("密码输入不合法");
                } else {
                    inputPwd.setError(null);
                }

                return nameValid && pwdValid;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                login.setEnabled(aBoolean);
            }
        }));
    }

    //注册
    @OnClick(R.id.register)
    public void register() {
        addSubscription(RxView.clicks(register)
                              .throttleFirst(500, TimeUnit.MILLISECONDS)
                              .observeOn(AndroidSchedulers.mainThread())
                              .subscribe(new Action1<Void>() {
                                  @Override
                                  public void call(Void aVoid) {
                                      startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                                  }
                              }));
    }

    //登陆
    @OnClick(R.id.login)
    public void login() {
        final String userName = name.getText().toString();
        final String userPwd = pwd.getText().toString();

        addSubscription(ApiClient.getApiService()
                                 .login(userName, userPwd,"1")
                                 .flatMap(new Func1<BaseResponse<OauthUser>, Observable<OauthUser>>() {
                                     @Override
                                     public Observable<OauthUser> call(final BaseResponse<OauthUser> oauthUserBaseResponse) {
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
                                         oauthUser.setName(userName);
                                         oauthUser.setPassword(userPwd);
                                         oauthUser.setId(oauthUser.getUser().getId());
                                         oauthUser.setLogined(true);
                                         realm.copyToRealmOrUpdate(oauthUser);
                                         realm.commitTransaction();
                                         realm.close();
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
                                                    Log.e("ministorm", "error = " + e);
                                                }

                                                @Override
                                                public void onNext(OauthUser oauthUser) {
                                                    startActivity(new Intent(LoginActivity.this, OptimizeDataActivity.class));
                                                    finish();
                                                }
                                            }
                                 ));
    }

    //重置密码
    @OnClick(R.id.reset_pwd)
    public void resetPwd() {
        addSubscription(RxView.clicks(resetPwd)
                              .throttleFirst(500, TimeUnit.MILLISECONDS)
                              .observeOn(AndroidSchedulers.mainThread())
                              .subscribe(new Action1<Void>() {
                                  @Override
                                  public void call(Void aVoid) {
                                      startActivity(new Intent(LoginActivity.this, ResetPwdActivity.class));
                                  }
                              }));
    }
}
