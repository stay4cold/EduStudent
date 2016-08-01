package com.suggee.edustudent.base.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.suggee.edustudent.R;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/23
 * Description:
 */
public abstract class BaseActivity extends AppBaseActivity {

    @Nullable
    protected Toolbar mToolbar;

    /**
     * 监听网络状态变化
     */
    private ReactiveNetwork mReactiveNetwork;

    private Subscription mNetConnectSubscription;

    /**
     * Subscription统一管理，方便进行统一解绑
     */
    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //利用ButterKnife来绑定view
        ButterKnife.bind(this);

        mToolbar = (Toolbar)findViewById(R.id.common_toolbar);

        initToolbar(mToolbar);

        initViewsAndEvents(savedInstanceState);

        mReactiveNetwork = new ReactiveNetwork();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetConnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterNetConnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    /**
     * 订阅监听网络状态变化
     */
    private void registerNetConnect() {
        mNetConnectSubscription = mReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ConnectivityStatus>() {
                    @Override
                    public void call(ConnectivityStatus status) {
                        if (status == ConnectivityStatus.UNKNOWN || status == ConnectivityStatus.OFFLINE) {
                            onNetworkDisConnected();
                        } else {
                            onNetworkConnected(status);
                        }
                    }
                });
    }

    /**
     * 取消订阅网络状态变化
     */
    private void unregisterNetConnect() {
        if (mNetConnectSubscription != null && !mNetConnectSubscription.isUnsubscribed()) {
            mNetConnectSubscription.unsubscribe();
        }
    }

    /**
     * 添加RX绑定
     *
     * @param subscription
     */
    protected void addSubscription(Subscription subscription) {
        if (subscription == null) {
            return;
        }

        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(subscription);
    }

    /**
     * 解除RX绑定
     */
    protected void unSubscribe() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 初始化view以及设置一些events
     *
     * @param savedInstanceState
     */
    protected abstract void initViewsAndEvents(Bundle savedInstanceState);

    /**
     * 设置loading、error、network等状态的targetView
     *
     * @return
     */
    protected abstract View getLoadingTargetView();


    /**
     * 网络连接上，有网状态
     *
     * @param status 网络类型 1.wifi 2.mobile
     */
    protected void onNetworkConnected(ConnectivityStatus status) {
    }

    /**
     * 网络断线，无网状态或者未知状态
     */
    protected void onNetworkDisConnected() {
    }

    protected void initToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
