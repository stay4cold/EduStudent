package com.suggee.edustudent.modulers.search;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.suggee.edustudent.R;
import com.suggee.edustudent.api.ApiClient;
import com.suggee.edustudent.api.ApiException;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.SearchResult;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/1
 * Description:
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.search)
    EditText mSearch;
    @BindView(R.id.search_clear)
    AppCompatImageView mSearchClear;
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv)
    RecyclerView mRv;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        search();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_search;
    }

    @OnClick(R.id.search_clear)
    public void clear() {
        mSearch.setText("");
        mSearchClear.setVisibility(View.GONE);
    }

    private void search() {
        RxTextView.textChanges(mSearch)
                  .filter(new Func1<CharSequence, Boolean>() {
                      @Override
                      public Boolean call(CharSequence charSequence) {
                          if (charSequence.length() > 0) {
                              mSearchClear.setVisibility(View.VISIBLE);
                              return true;
                          } else {
                              mSearchClear.setVisibility(View.GONE);
                              return false;
                          }
                      }
                  })
                  .subscribeOn(AndroidSchedulers.mainThread())
                  .debounce(200, TimeUnit.MILLISECONDS)
                  .observeOn(Schedulers.io())
                  .switchMap(new Func1<CharSequence, Observable<BaseResponse<SearchResult>>>() {
                      @Override
                      public Observable<BaseResponse<SearchResult>> call(CharSequence charSequence) {
                          return ApiClient.getApiService()
                                          .getSearchResult(charSequence
                                                  .toString());
                      }
                  })
                  .flatMap(new Func1<BaseResponse<SearchResult>, Observable<SearchResult>>() {
                      @Override
                      public Observable<SearchResult> call(BaseResponse<SearchResult> response) {
                          if (response.getCode() == 2000) {
                              return Observable.just(response
                                      .getData());
                          } else {
                              return Observable.error(new ApiException(response));
                          }
                      }
                  })
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Subscriber<SearchResult>() {
                      @Override
                      public void onCompleted() {
                          Log.e("ministorm", "com");
                      }

                      @Override
                      public void onError(Throwable e) {
                          Log.e("ministorm", "err = " + e);

                      }

                      @Override
                      public void onNext(SearchResult searchResult) {
                          Log.e("ministorm", "nex = " + searchResult);
                      }
                  });
    }

    @OnClick(R.id.tv)
    public void retry() {
        search();
    }
}
