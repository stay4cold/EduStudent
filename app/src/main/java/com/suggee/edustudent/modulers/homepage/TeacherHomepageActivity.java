package com.suggee.edustudent.modulers.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.suggee.edustudent.R;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.modulers.login.GradeChooseFragment;

import butterknife.BindView;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/11
 * Description:
 */
public class TeacherHomepageActivity extends BaseActivity {
    @BindView(R.id.ivImage)
    ImageView mIcon;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.vp)
    ViewPager mVp;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        GradeChooseFragment.launchForResult(this, 1000);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_teacher_homepage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case 1000:
                    Log.e("ministorm", data.getStringExtra("grade"));
                    break;
            }
        }
    }
}
