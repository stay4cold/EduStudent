package com.suggee.edustudent.base.paginate;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/24
 * Description:
 *              用于处理Gridlayout的底部视图，比如“加载更多”等view
 */
public class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private static int mSpanSize = 1;

    private final WrapperAdapter mAdapter;
    private final GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

    public GridSpanSizeLookup(RecyclerView recyclerView) {
        mAdapter = (WrapperAdapter) recyclerView.getAdapter();

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            mSpanSizeLookup = (((GridLayoutManager) recyclerView.getLayoutManager())).getSpanSizeLookup();
            mSpanSize = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
        } else {
            throw new IllegalStateException("RecyclerView's layoutmanager is need to be GridLayoutManager");
        }
    }

    @Override
    public int getSpanSize(int position) {
        if (mAdapter.isFooter(position)) {
            return mSpanSize;
        } else {
            return mSpanSizeLookup.getSpanSize(position);
        }
    }
}
