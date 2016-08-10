package com.suggee.edustudent.base.ui.fragment;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/3
 * Description:
 * lazy load Recyclerview的加载模式下，封装基类Adapter
 */
public abstract class BaseLazyListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> mDatas;

    public void setDatas(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        return mDatas;
    }

    @Override
    public int getItemCount() {
        return getDatas().size();
    }
}
