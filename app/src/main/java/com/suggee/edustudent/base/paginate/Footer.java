package com.suggee.edustudent.base.paginate;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/27
 * Description:
 */
public interface Footer {

    void setState(State state);

    State getState();

    void setCallbacks(Paginate.Callbacks callbacks);
}
