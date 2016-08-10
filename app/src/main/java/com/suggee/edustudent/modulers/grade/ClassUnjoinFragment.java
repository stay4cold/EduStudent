package com.suggee.edustudent.modulers.grade;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.suggee.edustudent.R;
import com.suggee.edustudent.api.ApiClient;
import com.suggee.edustudent.base.ui.fragment.BaseFragment;
import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.ClassDetailBean;

import java.util.HashMap;

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
 * Date:    16/8/5
 * Description:
 */
public class ClassUnjoinFragment extends BaseFragment {
    @BindView(R.id.brief)
    TextView mBrief;
    @BindView(R.id.join)
    TextView mJoin;

    private String mBriefContent;
    private int mClassId;

    public static ClassUnjoinFragment newInstance(ClassDetailBean bean) {
        ClassUnjoinFragment unjoinFragment = new ClassUnjoinFragment();
        Bundle bundle = new Bundle();
        bundle.putString("brief", bean.getBrief());
        bundle.putInt("cid", bean.getId());
        unjoinFragment.setArguments(bundle);
        return unjoinFragment;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mBriefContent = getArguments().getString("brief");
        mClassId = getArguments().getInt("cid");
        mBrief.setText(mBriefContent);
    }

    @Override
    protected void onNetworkConnected(ConnectivityStatus status) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_unjoin;
    }

    @OnClick(R.id.join)
    public void join() {
        HashMap<String, Integer> params = new HashMap<>();
        params.put("cid", mClassId);
        addSubscription(ApiClient.getApiService()
                                 .joinClass(params)
                                 .flatMap(new Func1<BaseResponse<String>, Observable<String>>() {
                                     @Override
                                     public Observable<String> call(BaseResponse<String> response) {
                                         return Observable.just(response.getMsg());
                                     }
                                 })
                                 .subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(new Action1<String>() {
                                     @Override
                                     public void call(String s) {
                                             Snackbar.make(getView(), s, Snackbar.LENGTH_SHORT).show();
                                     }
                                 }, new Action1<Throwable>() {
                                     @Override
                                     public void call(Throwable throwable) {
                                         Snackbar.make(getView(), "申请失败，请重新申请！", Snackbar.LENGTH_SHORT)
                                                 .show();
                                     }
                                 }));
    }
}
