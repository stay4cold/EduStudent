package com.suggee.edustudent.modulers.course;

import android.os.Bundle;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.suggee.edustudent.R;
import com.suggee.edustudent.base.ui.fragment.BaseLazyFragment;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/1
 * Description:
 */
public class CourseContainerFragment extends BaseLazyFragment {

    public static CourseContainerFragment newInstance() {
        return new CourseContainerFragment();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

    }

    @Override
    protected void onNetworkConnected(ConnectivityStatus status) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_course_container;
    }
}
