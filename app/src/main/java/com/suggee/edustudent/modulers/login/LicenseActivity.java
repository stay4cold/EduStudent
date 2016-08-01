package com.suggee.edustudent.modulers.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.suggee.edustudent.R;
import com.suggee.edustudent.base.ui.activity.BaseActivity;

import butterknife.BindView;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/7/29
 * Description:
 * 注册协议
 */
public class LicenseActivity extends BaseActivity {

    @BindView(R.id.common_toolbar)
    Toolbar commonToolbar;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.wb_view)
    WebView wbView;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        progress.setProgress(50);
        wbView.loadUrl("https://github.com/akexorcist/Android-RoundCornerProgressBar");
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_license;
    }
}
