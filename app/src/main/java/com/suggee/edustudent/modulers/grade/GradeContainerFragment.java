package com.suggee.edustudent.modulers.grade;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.suggee.edustudent.R;
import com.suggee.edustudent.base.ui.fragment.BaseLazyFragment;

import butterknife.BindView;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/1
 * Description:
 */
public class GradeContainerFragment extends BaseLazyFragment {

    public static GradeContainerFragment newInstance() {
        return new GradeContainerFragment();
    }

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.vp)
    ViewPager mVp;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mVp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return StatisticsFragment.newInstance();
                    case 1:
                        return ClassesFragment.newInstance();
                    default:
                        throw new IllegalArgumentException(TAG + ": adapter's max count is 2 and current position is " + position);
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "我的统计";
                    case 1:
                        return "我的班级";
                    default:
                        return "";
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        mTab.setupWithViewPager(mVp);
    }

    @Override
    protected void onNetworkConnected(ConnectivityStatus status) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_grade_container;
    }
}
