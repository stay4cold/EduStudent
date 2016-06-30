package com.suggee.edustudent.base.paginate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    15/11/19
 * Description:
 */
public class WrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Footer {

    private static final int TYPE_LOADING_VIEW = Integer.MIN_VALUE;

    private final RecyclerView.Adapter mAdapter;

    private LoadingFooter mLoadingFooter;

    public WrapperAdapter(Context context, RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        mLoadingFooter = new LoadingFooter(context);
    }

    public void setFooter(LoadingFooter footer) {
        mLoadingFooter = footer;
    }

    @Override
    public void setState(State state) {
        mLoadingFooter.setState(state);
    }

    @Override
    public State getState() {
        return mLoadingFooter.getState();
    }

    @Override
    public void setCallbacks(Paginate.Callbacks callbacks) {
        mLoadingFooter.setCallbacks(callbacks);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING_VIEW) {
            return new FooterViewHolder(mLoadingFooter);
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() - 1) {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    /**
     * StaggeredGridLayoutManager的情况下，处理底部加载更多的view
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams && holder.getLayoutPosition() == mAdapter
                .getItemCount()) {
            ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == mAdapter.getItemCount() ? TYPE_LOADING_VIEW : mAdapter.getItemViewType(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
        mAdapter.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return position == mAdapter.getItemCount() ? RecyclerView.NO_ID : mAdapter.getItemId(position);
    }

    public RecyclerView.Adapter getInnerAdapter() {
        return mAdapter;
    }

    public boolean isFooter(int position) {
        return position == mAdapter.getItemCount();
    }

    public int getRealItemCount() {
        return mAdapter.getItemCount();
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
