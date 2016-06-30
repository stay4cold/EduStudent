package com.suggee.edustudent.base.paginate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.suggee.edustudent.R;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/27
 * Description:
 */
public class LoadingFooter extends RelativeLayout implements Footer {

    private State mState = State.Normal;

    private Paginate.Callbacks mCallbacks;

    private View mLoadingView;
    private View mErrorView;
    private View mEndView;

    public LoadingFooter(Context context) {
        super(context);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        inflate(context, R.layout.cm_footer, this);

        mLoadingView = findViewById(R.id.footer_loading);
        mErrorView = findViewById(R.id.footer_error);
        mEndView = findViewById(R.id.footer_end);

        setState(State.Normal);
    }

    @Override
    public void setCallbacks(Paginate.Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    @Override
    public State getState() {
        return mState;
    }

    /**
     * 设置状态
     *
     * @param state
     */
    @Override
    public void setState(State state) {
        if (mState == state) {
            return;
        }
        mState = state;

        setOnClickListener(null);

        switch (state) {

            case Normal:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mEndView != null) {
                    mEndView.setVisibility(GONE);
                }

                if (mErrorView != null) {
                    mErrorView.setVisibility(GONE);
                }

                break;
            case Loading:
                if (mEndView != null) {
                    mEndView.setVisibility(GONE);
                }

                if (mErrorView != null) {
                    mErrorView.setVisibility(GONE);
                }

                if (mLoadingView != null) {
                    mLoadingView.setVisibility(VISIBLE);
                }
                if (mCallbacks != null) {
                    mCallbacks.onLoadMore();
                }
                break;
            case TheEnd:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mErrorView != null) {
                    mErrorView.setVisibility(GONE);
                }

                if (mEndView != null) {
                    mEndView.setVisibility(VISIBLE);
                }
                break;
            case Error:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mEndView != null) {
                    mEndView.setVisibility(GONE);
                }

                if (mErrorView != null) {
                    mErrorView.setVisibility(VISIBLE);
                }
                setOnClickListener(mRetryClick);
                break;
            default:

                break;
        }
    }

    private final OnClickListener mRetryClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCallbacks != null) {
                mCallbacks.onRetry();
                setState(State.Loading);
            }
        }
    };
}
