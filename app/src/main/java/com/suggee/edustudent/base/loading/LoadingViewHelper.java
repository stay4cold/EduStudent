package com.suggee.edustudent.base.loading;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/29
 * Description:
 */
public class LoadingViewHelper implements ILoadingView {

    private static final String TAG = LoadingViewHelper.class.getSimpleName();

    //  需要进行替换的原始targetView
    private View mTargetView;

    private ViewGroup mParentView;
    private ViewGroup.LayoutParams mTargetParams;

    private int mTargetViewIndex;

    public LoadingViewHelper(@NonNull View targetView) {
        if (targetView == null) {
            throw new IllegalArgumentException(TAG + " -> target view is null!");
        }
        this.mTargetView = targetView;
    }

    /**
     * 恢复到原始状态
     */
    @Override
    public void restoreView() {
        showLayout(mTargetView, null);
    }

    /**
     * 将targetView替换为当前的view
     *
     * @param view
     */
    @Override
    public void showLayout(View view, View.OnClickListener listener) {
        if (mParentView == null) {
            init();
        }

        if (mParentView.getChildAt(mTargetViewIndex) != view) {
            mParentView.removeViewAt(mTargetViewIndex);
            mParentView.addView(view, mTargetViewIndex, mTargetParams);
            if (listener != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(mTargetView.getContext()).inflate(layoutId, null);
    }

    private void init() {
        mTargetParams = mTargetView.getLayoutParams();
        if (mTargetView.getParent() != null) {
            mParentView = (ViewGroup) mTargetView.getParent();
        } else {
            mParentView = (ViewGroup) mTargetView.getRootView().findViewById(android.R.id.content);
        }

        int count = mParentView.getChildCount();
        for (int i = 0; i < count; i++) {
            if (mTargetView == mParentView.getChildAt(i)) {
                mTargetViewIndex = i;
                break;
            }
        }
    }
}
