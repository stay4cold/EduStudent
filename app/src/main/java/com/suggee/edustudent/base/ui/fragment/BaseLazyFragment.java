package com.suggee.edustudent.base.ui.fragment;

import android.os.Bundle;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/23
 * Description:
 *             实现fragment懒加载，主要用于viewpager的加载，而不是采用FragmentManager来进行show/hide的操作
 */
public abstract class BaseLazyFragment extends BaseFragment {

    /**
     * fragment lazy load
     */
    private boolean mIsPrepared = false;
    private boolean mIsFirstVisible = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsPrepared = true;
        initPrepare();
    }

    /**
     * ViewPager通过此来设置Fragment的显示和隐藏
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initPrepare();
        }
    }

    private void initPrepare() {
        if (mIsPrepared && getUserVisibleHint() && mIsFirstVisible) {
            mIsFirstVisible = false;
            lazyLoad();
        }
    }

    /**
     *  懒加载数据
     */
    protected abstract void lazyLoad();
}
