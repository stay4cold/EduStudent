package com.suggee.edustudent.modulers.search;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

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

    @BindView(R.id.search_icon)
    AppCompatImageView mSearchIcon;
    @BindView(R.id.search)
    EditText mSearch;
    @BindView(R.id.search_clear)
    AppCompatImageView mSearchClear;
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;

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
        return R.layout.act_search;
    }
}
