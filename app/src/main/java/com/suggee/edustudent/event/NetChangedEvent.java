package com.suggee.edustudent.event;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/24
 * Description:
 *              网络状态更新
 */
public class NetChangedEvent {
    private String a;
    public NetChangedEvent(String a){
        this.a  = a;
    }

    public String getA() {
        return a;
    }
}
