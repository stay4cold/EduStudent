package com.suggee.edustudent.base.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.stay4cold.okrecyclerview.OkRecyclerView;
import com.stay4cold.okrecyclerview.state.LoadingState;
import com.stay4cold.okrecyclerview.state.MoreState;
import com.suggee.edustudent.R;
import com.suggee.edustudent.common.AppConfig;
import com.suggee.edustudent.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/3
 * Description:
 * lazy load模式下封装Recyclerview类型的Fragment，抽出功能中的公共部分
 */
public abstract class BaseLazyListFragment<T> extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener, OkRecyclerView.OnLoadMoreListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.sr)
    SwipeRefreshLayout mSr;

    protected List<T> mDatas = new ArrayList<>();

    protected BaseLazyListAdapter mAdapter;

    protected OkRecyclerView mOk;

    protected int mCurrentPage = 1;

    @Override
    protected void lazyLoad() {
        mOk.setLoadState(LoadingState.Loading);
        addSubscription(loadFirstData().subscribe(new Action1<List<T>>() {
            @Override
            public void call(List<T> ts) {
                mCurrentPage++;
                mDatas.clear();
                mDatas.addAll(ts);

                mAdapter.setDatas(mDatas);

                if (ts == null) {//没有数据，显示空
                    mOk.setLoadState(LoadingState.Empty);
                } else if (ts.size() < AppConfig.PER_PAGE) {//有数据，但是不足30条，说明已经全部加载完
                    mOk.setLoadState(LoadingState.Normal);
                    mOk.setMoreState(MoreState.TheEnd);
                } else {
                    mOk.setLoadState(LoadingState.Normal);
                    mOk.setMoreState(MoreState.Normal);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mOk.setLoadState(LoadingState.Error);
            }
        }));
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        configAdapter();
        configRv();
        configOkRv();
    }

    @Override
    public void onLoadMore() {
        addSubscription(loadMoreData().subscribe(new Action1<List<T>>() {
            @Override
            public void call(List<T> data) {
                mDatas.addAll(data);
                mAdapter.setDatas(mDatas);
                if (data == null || data.size() < AppConfig.PER_PAGE) {
                    mOk.setMoreState(MoreState.TheEnd);
                } else {
                    mCurrentPage++;
                    mOk.setMoreState(MoreState.Normal);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "loadmore e = " + throwable);
                mOk.setMoreState(MoreState.Error);
            }
        }));
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        addSubscription(loadRefreshData().subscribe(new Action1<List<T>>() {
            @Override
            public void call(List<T> ts) {
                mCurrentPage++;
                mDatas.clear();
                mDatas.addAll(ts);

                mAdapter.setDatas(mDatas);

                mSr.setRefreshing(false);

                if (ts == null) {//没有数据，显示空
                    mOk.setLoadState(LoadingState.Empty);
                } else if (ts.size() < AppConfig.PER_PAGE) {//有数据，但是不足30条，说明已经全部加载完
                    mOk.setLoadState(LoadingState.Normal);
                    mOk.setMoreState(MoreState.TheEnd);
                } else {
                    mOk.setLoadState(LoadingState.Normal);
                    mOk.setMoreState(MoreState.Normal);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mSr.setRefreshing(false);
                mOk.setLoadState(LoadingState.Error);
            }
        }));
    }

    protected void configRv() {
        mSr.setOnRefreshListener(this);
        mSr.setColorSchemeResources(R.color.gplus_color_1, R.color.gplus_color_2, R.color.gplus_color_3, R.color.gplus_color_4);

        mRv.setAdapter(mAdapter);
        mRv.setLayoutManager(getLayoutManager());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        mRv.addItemDecoration(itemDecoration);
    }

    protected void configAdapter() {
        mAdapter = createAdapter();
        mAdapter.setDatas(mDatas);
    }

    protected void configOkRv() {
        mOk = new OkRecyclerView(mRv);
        mOk.setOnLoadMoreListener(this);
        mOk.getLoadDelegate()
           .getLoadingView(LoadingState.Error)
           .setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   lazyLoad();
               }
           });

        mOk.getFooterDelegate()
           .getMoreStateView(MoreState.Error)
           .setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mOk.setMoreState(MoreState.Loading);
               }
           });
    }

    //提供默认的LayoutManager，有需要的时候可以复写
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    //实例化Adapter
    protected abstract BaseLazyListAdapter createAdapter();

    //”加载更多“时使用的Observable
    protected abstract Observable<List<T>> loadMoreData();

    //”首次进入界面”时加载数据的Observable
    protected abstract Observable<List<T>> loadFirstData();

    //获取“下拉刷新“时使用的Observable，注意和 {loadFirstData()} 区分
    protected abstract Observable<List<T>> loadRefreshData();
}
