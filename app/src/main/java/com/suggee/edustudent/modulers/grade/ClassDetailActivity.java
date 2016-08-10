package com.suggee.edustudent.modulers.grade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.suggee.edustudent.R;
import com.suggee.edustudent.api.ApiClient;
import com.suggee.edustudent.api.ApiException;
import com.suggee.edustudent.base.loading.LoadingViewHelper;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.ClassDetailBean;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/5
 * Description:
 */
public class ClassDetailActivity extends BaseActivity {

    @BindView(R.id.stu_num)
    TextView mStuNum;
    @BindView(R.id.course_num)
    TextView mCourseNum;
    @BindView(R.id.hw_num)
    TextView mHwNum;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.ivImage)
    ImageView mLogo;
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    ViewGroup mContent;

    public static void launch(Context context, int classId) {
        Intent intent = new Intent(context, ClassDetailActivity.class);
        intent.putExtra("classId", 6);
        context.startActivity(intent);
    }

    private LoadingViewHelper mHelper;
    private int mClassId;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mHelper = new LoadingViewHelper(getLoadingTargetView());
        mClassId = getIntent().getIntExtra("classId", 0);
        getClassData(String.valueOf(mClassId));
    }

    @Override
    protected View getLoadingTargetView() {
        return mContent;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_class_detail;
    }

    private void getClassData(String classId) {
        showLoading(true);
        addSubscription(ApiClient.getApiService()
                                 .getClassDetail(classId)
                                 .flatMap(new Func1<BaseResponse<ClassDetailBean>, Observable<ClassDetailBean>>() {
                                     @Override
                                     public Observable<ClassDetailBean> call(BaseResponse<ClassDetailBean> response) {
                                         if (response.getCode() == 2000) {
                                             return Observable.just(response.getData());
                                         } else {
                                             return Observable.error(new ApiException(response));
                                         }
                                     }
                                 })
                                 .subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(new Subscriber<ClassDetailBean>() {
                                     @Override
                                     public void onCompleted() {
                                         Log.e("ministorm", "complete");

                                     }

                                     @Override
                                     public void onError(Throwable e) {
                                         Log.e("ministorm", "error" + e);
                                         showError();
                                     }

                                     @Override
                                     public void onNext(ClassDetailBean classDetailBean) {
                                         Log.e("ministorm", "next");

                                         setData(classDetailBean);
                                         showLoading(false);
                                     }
                                 }));
    }

    private void setData(ClassDetailBean bean) {
        if (bean == null) {
            showError();
            return;
        } else {
            Glide.with(this).load(bean.getLogo()).crossFade().into(mLogo);
            mTitle.setText(bean.getTitle());
            mStuNum.setText(bean.getStudentNum()+"名");
            mCourseNum.setText(bean.getCourseCount() + "节");
            mHwNum.setText(bean.getHomeworkCount() + "篇");
        }

        if (bean.getStatus() != 1) {
            showJoin();
        } else {
            showUnjoin(bean);
        }
    }

    private void showLoading(boolean show) {
        if (show) {
            mHelper.showLayout(getLayoutInflater().inflate(R.layout.cm_load_loading, null), null);
        } else {
            mHelper.restoreView();
        }
    }

    private void showError() {
        mHelper.showLayout(R.layout.cm_load_error, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getClassData(String.valueOf(mClassId));
            }
        });
    }

    private void showJoin() {

    }

    private void showUnjoin(ClassDetailBean bean) {
        ClassUnjoinFragment unjoinFragment = ClassUnjoinFragment.newInstance(bean);
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.container, unjoinFragment)
                                   .commit();
    }
}
