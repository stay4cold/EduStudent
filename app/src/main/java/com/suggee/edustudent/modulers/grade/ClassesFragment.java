package com.suggee.edustudent.modulers.grade;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.stay4cold.okrecyclerview.OkRecyclerView;
import com.stay4cold.okrecyclerview.state.LoadingState;
import com.stay4cold.okrecyclerview.state.MoreState;
import com.suggee.edustudent.R;
import com.suggee.edustudent.api.ApiClient;
import com.suggee.edustudent.api.ApiException;
import com.suggee.edustudent.base.ui.fragment.BaseLazyFragment;
import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.Classes;
import com.suggee.edustudent.common.AppConfig;
import com.suggee.edustudent.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/1
 * Description:
 */
public class ClassesFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener, OkRecyclerView.OnLoadMoreListener {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.sr)
    SwipeRefreshLayout mSr;

    private List<Classes> mClassLists = new ArrayList<>();

    private ClassesFragmentAdapter mAdapter;

    private OkRecyclerView mOk;

    private int mCurrentPage = 1;

    public static ClassesFragment newInstance() {
        return new ClassesFragment();
    }

    @Override
    protected void lazyLoad() {
        loadFirst(false);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mSr.setOnRefreshListener(this);
        mSr.setColorSchemeResources(R.color.gplus_color_1, R.color.gplus_color_2, R.color.gplus_color_3, R.color.gplus_color_4);
        mAdapter = new ClassesFragmentAdapter();
        mAdapter.setDatas(mClassLists);
        mRv.setAdapter(mAdapter);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        mRv.addItemDecoration(itemDecoration);
        mOk = new OkRecyclerView(mRv);
        mOk.setOnLoadMoreListener(this);
        mOk.getLoadDelegate()
           .getLoadingView(LoadingState.Error)
           .setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   loadFirst(false);
               }
           });
    }

    @Override
    protected void onNetworkConnected(ConnectivityStatus status) {
        Log.e("ministorm", "connect");
    }

    @Override
    protected void onNetworkDisConnected() {
        Log.e("ministorm", "disconnect");

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_classes;
    }

    @Override
    public void onRefresh() {
        loadFirst(true);
    }

    @Override
    public void onLoadMore() {
        loadMore();
    }

    private void loadFirst(final boolean isRefresh) {
        if (!isRefresh) {
            mOk.setLoadState(LoadingState.Loading);
        }
        mCurrentPage = 1;

        addSubscription(getClasses(mCurrentPage).subscribe(
                new Action1<List<Classes>>() {
                    @Override
                    public void call(List<Classes> classes) {
                        mCurrentPage++;
                        mClassLists.clear();
                        mClassLists.addAll(classes);
                        if (isRefresh) {
                            mSr.setRefreshing(false);
                        }
                        if (mClassLists == null) {//没有数据，显示空
                            mOk.setLoadState(LoadingState.Empty);
                        } else if (mClassLists.size() < AppConfig.PER_PAGE) {//有数据，但是不足30条，说明已经全部加载完
                            mOk.setLoadState(LoadingState.Normal);
                            mOk.setMoreState(MoreState.TheEnd);
                        } else {
                            mOk.setLoadState(LoadingState.Normal);
                            mOk.setMoreState(MoreState.Normal);
                        }
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("ministorm", "e = " + throwable);
                        if (isRefresh) {
                            mSr.setRefreshing(false);
                        }
                        mOk.setLoadState(LoadingState.Error);
                    }
                }));
    }

    private void loadMore() {
        addSubscription(getClasses(mCurrentPage).subscribe(new Action1<List<Classes>>() {
            @Override
            public void call(List<Classes> classes) {
                mClassLists.addAll(classes);
                mAdapter.setDatas(mClassLists);
                if (classes == null || classes.size() < AppConfig.PER_PAGE) {
                    mOk.setMoreState(MoreState.TheEnd);
                } else {
                    mCurrentPage++;
                    mOk.setMoreState(MoreState.Normal);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mOk.setMoreState(MoreState.Error);
            }
        }));
    }

    private Observable<List<Classes>> getClasses(int page) {
        return ApiClient.getApiService()
                        .getClasses(page, AppConfig.PER_PAGE)
                        .flatMap(new Func1<BaseResponse<List<Classes>>, Observable<List<Classes>>>() {
                            @Override
                            public Observable<List<Classes>> call(BaseResponse<List<Classes>> listBaseResponse) {
                                if (listBaseResponse.getCode() == 2000) {
                                    return Observable.just(listBaseResponse.getData());
                                } else {
                                    return Observable.error(new ApiException(listBaseResponse));
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }
}
