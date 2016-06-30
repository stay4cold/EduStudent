package com.suggee.edustudent.base.paginate;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    15/11/19
 * Description:
 */
public final class RecyclerPaginate extends Paginate {

    private final RecyclerView mRecyclerView;
    private final Callbacks mCallbacks;
    private WrapperAdapter mWrapperAdapter;
    private GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

    RecyclerPaginate(RecyclerView recyclerView, Callbacks callbacks) {
        this(recyclerView, callbacks, null);
    }

    RecyclerPaginate(RecyclerView recyclerView, Callbacks callbacks, LoadingFooter footer) {
        mRecyclerView = recyclerView;
        mCallbacks = callbacks;
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        mWrapperAdapter = new WrapperAdapter(recyclerView.getContext(), adapter);
        if (footer != null) {
            mWrapperAdapter.setFooter(footer);
        }
        mWrapperAdapter.setCallbacks(mCallbacks);
        adapter.registerAdapterDataObserver(mDataObserver);
        mRecyclerView.setAdapter(mWrapperAdapter);

        /**
         * GridLayoutManager的情况下，处理底部加载更多的view
         */
        if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            mSpanSizeLookup = new GridSpanSizeLookup(mRecyclerView);
            ((GridLayoutManager) mRecyclerView.getLayoutManager()).setSpanSizeLookup(mSpanSizeLookup);
        }
    }

    @Override
    public void setFooterState(State state) {
        mWrapperAdapter.setState(state);
        mWrapperAdapter.notifyDataSetChanged();
    }

    private void setFooterView(LoadingFooter footer) {
        mWrapperAdapter.setFooter(footer);
    }

    private final RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            mWrapperAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapperAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapperAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapperAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        /**
         * 最后一个可见的item的位置
         */
        private int mLastVisiblePosition;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

            if (lm instanceof GridLayoutManager) {
                mLastVisiblePosition = ((GridLayoutManager) lm).findLastVisibleItemPosition();
            } else if (lm instanceof StaggeredGridLayoutManager) {
                int[] positions = new int[((StaggeredGridLayoutManager) lm).getSpanCount()];
                positions = ((StaggeredGridLayoutManager) lm).findLastVisibleItemPositions(positions);
                mLastVisiblePosition = findMax(positions);
            } else if (lm instanceof LinearLayoutManager) {
                mLastVisiblePosition = ((LinearLayoutManager) lm).findLastVisibleItemPosition();
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            if (visibleItemCount > 0 && mLastVisiblePosition >= totalItemCount - 1 && mWrapperAdapter
                    .getState() == State.Normal) {
                mWrapperAdapter.setState(State.Loading);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        /**
         * 取数组中最大值
         *
         * @param lastPositions
         * @return
         */
        private int findMax(int[] lastPositions) {
            int max = lastPositions[0];
            for (int value : lastPositions) {
                if (value > max) {
                    max = value;
                }
            }

            return max;
        }
    };
}
