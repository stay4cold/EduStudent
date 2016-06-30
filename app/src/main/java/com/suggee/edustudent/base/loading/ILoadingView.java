package com.suggee.edustudent.base.loading;

import android.view.View;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/29
 * Description:
 */
public interface ILoadingView {

    void restoreView();

    void showLayout(View view, View.OnClickListener listener);

    View inflate(int layoutId);
}
