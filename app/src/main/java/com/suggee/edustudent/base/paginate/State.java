package com.suggee.edustudent.base.paginate;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/24
 * Description:
 *              RecyclerView的各种加载状态
 */
public enum State {
    //正常状态
    Normal,

    //加载完成，没有更多数据
    TheEnd,

    //加载中
    Loading,

    //网络异常或者其他异常情况，导致数据没有更长加载
    Error
}
