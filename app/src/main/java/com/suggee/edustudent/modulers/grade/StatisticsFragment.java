package com.suggee.edustudent.modulers.grade;

import android.content.Intent;
import android.os.Bundle;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.suggee.edustudent.R;
import com.suggee.edustudent.base.ui.fragment.BaseLazyFragment;
import com.suggee.edustudent.modulers.login.LoginActivity;

import butterknife.OnClick;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/1
 * Description:
 */
public class StatisticsFragment extends BaseLazyFragment {

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
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
        return R.layout.frag_statistics;
    }

    @OnClick(R.id.quit)
    public void quit() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
