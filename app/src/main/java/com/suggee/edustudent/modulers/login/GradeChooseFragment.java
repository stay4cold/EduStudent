package com.suggee.edustudent.modulers.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.stay4cold.okrecyclerview.helper.ViewReplaceHelper;
import com.suggee.edustudent.R;
import com.suggee.edustudent.api.ApiClient;
import com.suggee.edustudent.api.ApiException;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.base.ui.activity.FragmentContainerActivity;
import com.suggee.edustudent.base.ui.fragment.BaseFragment;
import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.CourseFilterGrade;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/11
 * Description:
 */
public class GradeChooseFragment extends BaseFragment {


    @BindView(R.id.ev)
    RecyclerView mRv;

    private GradeAdapter mAdapter;

    private ViewReplaceHelper mHelper;

    private View mLoadingView;

    public static void launchForResult(BaseActivity activity, int requestCode) {
        FragmentContainerActivity.launchForResult(activity, GradeChooseFragment.class, null, requestCode);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        getActivity().setTitle("年级选择");

        mHelper = new ViewReplaceHelper(mRv);

        mLoadingView = LayoutInflater.from(getContext()).inflate(R.layout.cm_load_loading, null);

        mRv.setAdapter(mAdapter = new GradeAdapter());
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));

        getGrades();
    }

    @Override
    protected void onNetworkConnected(ConnectivityStatus status) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_grade_choose;
    }

    class GradeAdapter extends RecyclerView.Adapter<GradeHolder> {

        private List<CourseFilterGrade> datas;

        @Override
        public GradeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GradeHolder(LayoutInflater.from(parent.getContext())
                                                 .inflate(R.layout.frag_grade_choose_item, parent, false));
        }

        @Override
        public void onBindViewHolder(GradeHolder holder, final int position) {
            holder.tv.setText(getDatas().get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() != null) {
                        Intent intent = new Intent();
                        intent.putExtra("grade", getDatas().get(position));
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return getDatas().size();
        }

        public void setDatas(List<CourseFilterGrade> datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        public List<CourseFilterGrade> getDatas() {
            if (datas == null) {
                datas = new ArrayList<>();
            }
            return datas;
        }
    }

    static class GradeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv)
        TextView tv;

        public GradeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void getGrades() {
        mHelper.replaceView(mLoadingView);
        addSubscription(ApiClient.getApiService()
                                 .getCourseFilterGrade()
                                 .flatMap(new Func1<BaseResponse<List<CourseFilterGrade>>, Observable<List<CourseFilterGrade>>>() {
                                     @Override
                                     public Observable<List<CourseFilterGrade>> call(BaseResponse<List<CourseFilterGrade>> response) {
                                         if (response.getCode() == 2000) {
                                             return Observable.just(response.getData());
                                         } else {
                                             return Observable.error(new ApiException(response));
                                         }
                                     }
                                 })
                                 .subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(new Subscriber<List<CourseFilterGrade>>() {
                                     @Override
                                     public void onCompleted() {

                                     }

                                     @Override
                                     public void onError(Throwable e) {

                                     }

                                     @Override
                                     public void onNext(List<CourseFilterGrade> courseFilterGrades) {
                                         mAdapter.setDatas(courseFilterGrades);
                                         mHelper.restore();
                                     }
                                 }));
    }
}
