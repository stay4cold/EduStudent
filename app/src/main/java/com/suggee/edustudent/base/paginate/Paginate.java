package com.suggee.edustudent.base.paginate;

import android.support.v7.widget.RecyclerView;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/27
 * Description:
 */
public abstract class Paginate {

    public interface Callbacks {
        //加载更多的时候调用
        void onLoadMore();

        //出现网络错误等error状态时点击回调
        void onRetry();
    }

    public abstract void setFooterState(State state);

    public static RecyclerPaginate with(RecyclerView recyclerView, Callbacks callbacks) {
        return new RecyclerPaginate(recyclerView, callbacks);
    }
}
