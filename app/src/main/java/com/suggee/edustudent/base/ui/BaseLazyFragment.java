package com.suggee.edustudent.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/23
 * Description:
 *             实现fragment懒加载，主要用于viewpager的加载
 */
public abstract class BaseLazyFragment extends BaseFragment {

    private boolean mViewInited;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            initView();
        }
    }

    private void initView() {
        if (mViewInited) {
            lazyLoad();
        } else {
            mViewInited = false;
        }
    }

    /**
     *  懒加载数据
     */
    protected abstract void lazyLoad();
}
