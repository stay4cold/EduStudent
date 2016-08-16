package com.suggee.edustudent.modulers.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.suggee.edustudent.R;
import com.suggee.edustudent.api.ApiClient;
import com.suggee.edustudent.api.ApiException;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.OauthUser;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func4;
import rx.schedulers.Schedulers;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/26
 * Description:
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.phone)
    TextInputEditText phone;
    @BindView(R.id.input_phone)
    TextInputLayout inputPhone;
    @BindView(R.id.code)
    TextInputEditText code;
    @BindView(R.id.input_code)
    TextInputLayout inputCode;
    @BindView(R.id.send_code)
    Button sendCode;
    @BindView(R.id.pwd)
    TextInputEditText pwd;
    @BindView(R.id.input_pwd)
    TextInputLayout inputPwd;
    @BindView(R.id.confirm_pwd)
    TextInputEditText confirmPwd;
    @BindView(R.id.input_confirm_pwd)
    TextInputLayout inputConfirmPwd;
    @BindView(R.id.register)
    Button register;

    //倒计时的时间
    private static final int COUNT = 30;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        registerChangeObserver();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_register;
    }

    //注册textchange监听
    private void registerChangeObserver() {
        Observable<CharSequence> nameObservable = RxTextView.textChanges(phone);
        Observable<CharSequence> codeObservable = RxTextView.textChanges(code);
        Observable<CharSequence> pwdObservable = RxTextView.textChanges(pwd);
        Observable<CharSequence> confirmObservable = RxTextView.textChanges(confirmPwd);

        addSubscription(Observable.combineLatest(nameObservable, codeObservable, pwdObservable, confirmObservable, new Func4<CharSequence, CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence phone, CharSequence code, CharSequence pwd, CharSequence confirmPwd) {

                boolean phoneValid = LoginUtils.phoneValid(phone);
                if (phoneValid) {
                    inputPhone.setError(null);
                } else {
                    inputPhone.setError("输入手机号不合法");
                }

                boolean codeValid = LoginUtils.codeValid(code);
                if (codeValid) {
                    inputCode.setError(null);
                } else {
                    inputCode.setError("请输入验证码");
                }

                boolean pwdValid = LoginUtils.pwdValid(pwd);
                if (pwdValid) {
                    inputPwd.setError(null);
                } else {
                    inputPwd.setError("请输入正确密码");
                }

                boolean confirmValid = LoginUtils.pwdValid(confirmPwd);
                if (confirmValid) {
                    inputConfirmPwd.setError(null);
                } else {
                    inputConfirmPwd.setError("请输入正确密码");
                }

                boolean isEqualPwd = TextUtils.equals(pwd, confirmPwd);

                return phoneValid && codeValid && pwdValid && confirmValid && isEqualPwd;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                register.setEnabled(aBoolean);
            }
        }));
    }

    //注册
    @OnClick(R.id.register)
    public void register() {
        register.setEnabled(false);
        final String name = phone.getText().toString();
        final String passwd = pwd.getText().toString();
        final String vCode = code.getText().toString();

        addSubscription(ApiClient.getApiService()
                                 .register(name, passwd, "1", vCode)
                                 .flatMap(new Func1<BaseResponse<OauthUser>, Observable<OauthUser>>() {
                                     @Override
                                     public Observable<OauthUser> call(BaseResponse<OauthUser> response) {
                                         if (response.getCode() == 2000) {
                                             return Observable.just(response.getData());
                                         } else {
                                             return Observable.error(new ApiException(response));
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
                                         oauthUser.setName(name);
                                         oauthUser.setPassword(passwd);
                                         oauthUser.setId(oauthUser.getUser().getId());
                                         oauthUser.setLogined(true);
                                         realm.copyToRealmOrUpdate(oauthUser);
                                         realm.commitTransaction();
                                         realm.close();
                                     }
                                 })
                                 .subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(new Action1<OauthUser>() {
                                     @Override
                                     public void call(OauthUser oauthUser) {
                                         startActivity(new Intent(RegisterActivity.this, OptimizeDataActivity.class));
                                         finish();
                                     }
                                 }, new Action1<Throwable>() {
                                     @Override
                                     public void call(Throwable throwable) {
                                         register.setEnabled(false);
                                     }
                                 })
        );
    }

    //发送验证码
    @OnClick(R.id.send_code)
    public void sendCode() {
        if (!LoginUtils.phoneValid(phone.getText())) {
            inputPhone.setError("请输入正确的电话号码");
            return;
        }
        addSubscription(ApiClient.getApiService().smsRegister(phone.getText().toString(), "1")
                                 .flatMap(new Func1<BaseResponse<String>, Observable<String>>() {
                                     @Override
                                     public Observable<String> call(BaseResponse<String> response) {
                                         if (response.getCode() == 2000) {
                                             return Observable.just(response.getData());
                                         } else {
                                             return Observable.error(new ApiException(response
                                                     .getCode(), response
                                                     .getMsg()));
                                         }
                                     }
                                 })
                                 .subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(new Action1<String>() {
                                     @Override
                                     public void call(String s) {
                                         sendCode.setEnabled(false);
                                         new CountDownTimer(COUNT * 1000, 1000) {
                                             @Override
                                             public void onTick(long millisUntilFinished) {
                                                 sendCode.setText(millisUntilFinished / 1000 + "s后重新获取");
                                             }

                                             @Override
                                             public void onFinish() {
                                                 sendCode.setEnabled(true);
                                                 sendCode.setText("重新获取");
                                             }
                                         }.start();
                                     }
                                 }, new Action1<Throwable>() {
                                     @Override
                                     public void call(Throwable throwable) {
                                         sendCode.setEnabled(true);
                                         sendCode.setText("获取验证码");
                                     }
                                 })
        );
    }

    //注册协议
    @OnClick(R.id.license)
    public void license() {
        startActivity(new Intent(this, LicenseActivity.class));
    }
}
