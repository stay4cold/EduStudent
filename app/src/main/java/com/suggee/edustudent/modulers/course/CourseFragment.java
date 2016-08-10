package com.suggee.edustudent.modulers.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.rock.android.tagselector.views.TagSelectView;
import com.stay4cold.okrecyclerview.OkRecyclerView;
import com.stay4cold.okrecyclerview.state.LoadingState;
import com.stay4cold.okrecyclerview.state.MoreState;
import com.suggee.edustudent.R;
import com.suggee.edustudent.api.ApiClient;
import com.suggee.edustudent.api.ApiException;
import com.suggee.edustudent.base.ui.fragment.BaseLazyFragment;
import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.Course;
import com.suggee.edustudent.bean.CourseFilterGrade;
import com.suggee.edustudent.bean.CourseFilterSubject;
import com.suggee.edustudent.common.AppConfig;
import com.suggee.edustudent.modulers.search.SearchActivity;
import com.suggee.edustudent.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
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
public class CourseFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener, OkRecyclerView.OnLoadMoreListener {

    @BindView(R.id.location_tv)
    TextView mLocationTv;
    @BindView(R.id.location)
    LinearLayout mLocation;
    @BindView(R.id.search_icon)
    AppCompatImageView mSearchIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.sr)
    SwipeRefreshLayout mSr;
    @BindView(R.id.select_view)
    TagSelectView mSelectView;

    private List<Course> mCourseLists = new ArrayList<>();

    private CourseAdapter mAdapter;

    private OkRecyclerView mOk;

    private int mCurrentPage = 1;

    private CourseRequestFilter mRequestFilter = new CourseRequestFilter();

    private List<CourseFilterGrade> mGradeFilter = new ArrayList<>();

    private List<CourseFilterSubject> mSubjectFilter = new ArrayList<>();

    public static CourseFragment newInstance() {
        return new CourseFragment();
    }

    @Override
    protected void lazyLoad() {
        loadFirst(false);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mSr.setOnRefreshListener(this);
        mSr.setColorSchemeResources(R.color.gplus_color_1, R.color.gplus_color_2, R.color.gplus_color_3, R.color.gplus_color_4);
        mAdapter = new CourseAdapter();
        mAdapter.setDatas(mCourseLists);
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

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_course;
    }

    @Override
    public void onLoadMore() {
        loadMore();
    }

    @Override
    public void onRefresh() {
        loadFirst(true);
    }

    private void loadFirst(final boolean isRefresh) {
        if (!isRefresh) {
            mOk.setLoadState(LoadingState.Loading);
        }

        mCurrentPage = 1;

        mRequestFilter.setPage(String.valueOf(mCurrentPage));

        addSubscription(getCourse(mRequestFilter).subscribe(new Action1<List<Course>>() {
            @Override
            public void call(List<Course> courses) {
                mCurrentPage++;

                mCourseLists.clear();
                mCourseLists.addAll(courses);

                if (isRefresh) {
                    mSr.setRefreshing(false);
                }

                if (mCourseLists == null) {//没有数据，显示空
                    mOk.setLoadState(LoadingState.Empty);
                } else if (mCourseLists.size() < AppConfig.PER_PAGE) {//有数据，但是不足30条，说明已经全部加载完
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
                if (isRefresh) {
                    mSr.setRefreshing(false);
                }
                mOk.setLoadState(LoadingState.Error);
            }
        }));

    }

    private void loadMore() {
        mRequestFilter.setPage(String.valueOf(mCurrentPage));
        addSubscription(getCourse(mRequestFilter).subscribe(new Action1<List<Course>>() {
            @Override
            public void call(List<Course> courses) {
                mCourseLists.addAll(courses);
                mAdapter.setDatas(mCourseLists);
                if (courses == null || courses.size() < AppConfig.PER_PAGE) {
                    mOk.setMoreState(MoreState.TheEnd);
                } else {
                    mCurrentPage++;
                    mOk.setMoreState(MoreState.Normal);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("ministorm", "more e = " + throwable);
                mOk.setMoreState(MoreState.Error);
            }
        }));
    }

    private Observable<List<Course>> getCourse(Map<String, String> options) {
        return ApiClient.getApiService()
                        .getCourse(options)
                        .flatMap(new Func1<BaseResponse<List<Course>>, Observable<List<Course>>>() {
                            @Override
                            public Observable<List<Course>> call(BaseResponse<List<Course>> listBaseResponse) {
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

    @OnClick(R.id.search)
    public void search() {
        startActivity(new Intent(getContext(), SearchActivity.class));
    }
}
