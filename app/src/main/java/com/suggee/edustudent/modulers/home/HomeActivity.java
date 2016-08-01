package com.suggee.edustudent.modulers.home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.suggee.edustudent.R;
import com.suggee.edustudent.base.ui.activity.BaseActivity;
import com.suggee.edustudent.modulers.course.CourseContainerFragment;
import com.suggee.edustudent.modulers.grade.GradeContainerFragment;
import com.suggee.edustudent.modulers.profile.ProfileFragment;
import com.suggee.edustudent.widgets.HackViewPager;

import butterknife.BindView;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/7/27
 * Description:
 */
public class HomeActivity extends BaseActivity {

    @BindView(R.id.hack_vp)
    HackViewPager mHackVp;

    private BottomBar mBottomBar;


    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        configVp(mHackVp);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.home_bottombar_menu_three_items);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.menu_grade:
                        mHackVp.setCurrentItem(0, false);
                        break;
                    case R.id.menu_course:
                        mHackVp.setCurrentItem(1, false);
                        break;
                    case R.id.menu_person:
                        mHackVp.setCurrentItem(2, false);
                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_home;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }

    private void configVp(ViewPager vp) {
        vp.setOffscreenPageLimit(3);
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return GradeContainerFragment.newInstance();
                    case 1:
                        return CourseContainerFragment.newInstance();
                    case 2:
                        return ProfileFragment.newInstance();
                    default:
                        throw new IllegalArgumentException(TAG + ": Adapter max position is 3 and current position is " + position);
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }
}
