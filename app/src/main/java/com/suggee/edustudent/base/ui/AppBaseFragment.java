package com.suggee.edustudent.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/22
 * Description:
 *              项目Fragment的基类，一切Fragment的继承都是基于此
 */
public abstract class AppBaseFragment extends Fragment {

    protected static String TAG;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();
    }

    /**
     * 获取SupportFragmentManager
     *
     * @return
     */
    protected FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    /**
     * 设置layoutID
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();
}
