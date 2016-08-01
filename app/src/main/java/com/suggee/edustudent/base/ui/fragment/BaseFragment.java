package com.suggee.edustudent.base.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import butterknife.ButterKnife;
import butterknife.Unbinder;
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
public abstract class BaseFragment extends AppBaseFragment {

    /**
     * 监听网络状态变化
     */
    private ReactiveNetwork mReactiveNetwork;

    private Subscription mNetConnectSubscription;

    /**
     * Subscription统一管理，方便进行统一解绑
     */
    private CompositeSubscription mCompositeSubscription;

    //ButterKnife view解绑
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactiveNetwork = new ReactiveNetwork();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if (getContentViewLayoutID() != 0) {
            view = inflater.inflate(getContentViewLayoutID(), null);
        } else {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewsAndEvents(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerNetConnect();
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterNetConnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife将View进行解绑
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    /**
     * 订阅监听网络状态变化
     */
    private void registerNetConnect() {
        mNetConnectSubscription = mReactiveNetwork.observeNetworkConnectivity(getContext().getApplicationContext())
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
     * 网络连接上，有网状态
     *
     * @param status 网络类型 1.wifi 2.mobile
     */
    protected abstract void onNetworkConnected(ConnectivityStatus status);

    /**
     * 网络断线，无网状态或者未知状态
     */
    protected abstract void onNetworkDisConnected();
}
