package com.suggee.edustudent.modulers.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.suggee.edustudent.R;
import com.suggee.edustudent.api.ApiClient;
import com.suggee.edustudent.api.ApiException;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.CourseFilterGrade;
import com.suggee.edustudent.bean.School;
import com.suggee.edustudent.bean.User;
import com.suggee.edustudent.modulers.home.HomeActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/11
 * Description:
 */
public class OptimizeDataActivity extends BaseActivity {
    @BindView(R.id.nick)
    TextInputEditText mNick;
    @BindView(R.id.input)
    TextInputLayout mNickInput;
    @BindView(R.id.gender)
    TextView mGender;
    @BindView(R.id.school)
    TextView mSchool;
    @BindView(R.id.grade)
    TextView mGrade;
    @BindView(R.id.save)
    Button mSubmit;

    private CourseFilterGrade mGradeData;
    private int mGenderSelect;//0：未选择，1：男，2：女
    private School mSchoolSelect;//选择的学校

    private BottomSheetDialog mGenderDialog;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("完善资料");
        initGenderDialog();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_optimize_data;
    }

    @OnClick({R.id.gender_container, R.id.school_container, R.id.grade_container, R.id.save})
    public void onClick(View container) {
        switch (container.getId()) {
            case R.id.gender_container:
                mGenderDialog.show();
                break;
            case R.id.school_container:
                SchoolChooseFragment.launchForResult(this, 1001);
                break;
            case R.id.grade_container:
                GradeChooseFragment.launchForResult(this, 1000);
                break;
            case R.id.save:
                submit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    mGradeData = (CourseFilterGrade) data.getSerializableExtra("grade");
                    mGrade.setText(mGradeData.getName());
                    break;
                case 1001:
                    mSchoolSelect = (School) data.getSerializableExtra("school");
                    mSchool.setText(mSchoolSelect.getName());
                    break;
            }
        }
    }

    private void initGenderDialog() {
        mGenderDialog = new BottomSheetDialog(this);
        mGenderDialog.setContentView(R.layout.act_optimize_gender_select);
        TextView boy = ButterKnife.findById(mGenderDialog, R.id.boy);
        TextView girl = ButterKnife.findById(mGenderDialog, R.id.girl);
        TextView cancel = ButterKnife.findById(mGenderDialog, R.id.cancel);
        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGenderSelect = 1;
                mGender.setText("男");
                mGenderDialog.cancel();
            }
        });

        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGenderSelect = 2;
                mGender.setText("女");
                mGenderDialog.cancel();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGenderDialog.cancel();
            }
        });
    }

    private void submit() {
        final HashMap<String, String> user = new HashMap<>();
        user.put("nick_name", mNick.getText().toString());
        user.put("sex", String.valueOf(mGenderSelect));
        if (mSchoolSelect != null) {
            user.put("sid", String.valueOf(mSchoolSelect.getId()));
        }
        if (mGradeData != null) {
            user.put("grade_id", mGradeData.getId());
        }
        addSubscription(ApiClient.getApiService()
                                 .modifyUser(user)
                                 .flatMap(new Func1<BaseResponse<User>, Observable<User>>() {
                                     @Override
                                     public Observable<User> call(BaseResponse<User> userBaseResponse) {
                                         if (userBaseResponse.getCode() == 2000) {
                                             return Observable.just(userBaseResponse.getData());
                                         } else {
                                             return Observable.error(new ApiException(userBaseResponse));
                                         }
                                     }
                                 })
                                 .doOnNext(new Action1<User>() {
                                     @Override
                                     public void call(User user) {
                                         Realm realm = Realm.getDefaultInstance();
                                         realm.beginTransaction();
                                         realm.copyToRealmOrUpdate(user);
                                         realm.commitTransaction();
                                         realm.close();
                                     }
                                 })
                                 .subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(new Subscriber<User>() {
                                     @Override
                                     public void onCompleted() {

                                     }

                                     @Override
                                     public void onError(Throwable e) {
                                         Snackbar.make(mNickInput, "提交失败！", Snackbar.LENGTH_SHORT)
                                                 .show();
                                     }

                                     @Override
                                     public void onNext(User user) {
                                         startActivity(new Intent(OptimizeDataActivity.this, HomeActivity.class));
                                         finish();
                                     }
                                 }));
    }
}
