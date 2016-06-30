package com.suggee.edustudent.base.loading;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.suggee.edustudent.R;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/29
 * Description:
 */
public class LoadingViewController {

    private ILoadingView mHelper;

    public LoadingViewController(View targetView) {
        this(new LoadingViewHelper(targetView));
    }

    public LoadingViewController(ILoadingView helper) {
        mHelper = helper;
    }

    public void restore() {
        mHelper.restoreView();
    }

    public void showLoading() {
        mHelper.showLayout(mHelper.inflate(R.layout.cm_load_loading), null);
    }

    public void showLoading(@LayoutRes int layoutId) {
        mHelper.showLayout(mHelper.inflate(layoutId), null);
    }

    public void showEmpty() {
        showEmpty(R.layout.cm_load_empty);
    }

    public void showEmpty(@LayoutRes int layoutId) {
        showEmpty(layoutId, null);
    }

    public void showEmpty(@LayoutRes int layoutId, View.OnClickListener listener) {
        mHelper.showLayout(mHelper.inflate(layoutId), listener);
    }

    public void showError() {
        showError(R.layout.cm_load_error);
    }

    public void showError(@LayoutRes int layoutId) {
        showError(layoutId, null);
    }

    public void showError(@LayoutRes int layoutId, View.OnClickListener listener) {
        mHelper.showLayout(mHelper.inflate(layoutId), listener);
    }
}
