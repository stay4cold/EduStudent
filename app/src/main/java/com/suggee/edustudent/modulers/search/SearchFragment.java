package com.suggee.edustudent.modulers.search;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.suggee.edustudent.R;
import com.suggee.edustudent.base.ui.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/1
 * Description:
 */
public class SearchFragment extends BaseFragment {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_view)
    SearchView mSearchView;

    public static SearchFragment newInstance() {
        return new SearchFragment();
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
        return R.layout.frag_search;
    }

}
