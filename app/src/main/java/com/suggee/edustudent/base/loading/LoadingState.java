package com.suggee.edustudent.base.loading;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/7/12
 * Description:
 */
public enum LoadingState {

    //数据加载正常，页面正常显示
    Normal,

    //显示正在加载view
    Loading,

    //没有数据可供显示
    Empty,

    //加载出错，或者网络不通等
    Error,
}
