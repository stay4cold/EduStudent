package com.suggee.edustudent.modulers.login;

import android.os.Bundle;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.base.ui.activity.FragmentContainerActivity;
import com.suggee.edustudent.base.ui.fragment.BaseFragment;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/11
 * Description:
 */
public class SchoolChooseFragment extends BaseFragment {

    public static void launchForResult(BaseActivity activity, int requestCode) {
        FragmentContainerActivity.launchForResult(activity, SchoolChooseFragment.class, null, requestCode);
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
        return 0;
    }
}
