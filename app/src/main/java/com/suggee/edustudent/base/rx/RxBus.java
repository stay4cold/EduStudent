package com.suggee.edustudent.base.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao@howdo.cc | wangchenghao123@126.com
 * Date:    16/6/24
 * Description:
 *              Rxjava类型的EventBus
 */
public class RxBus {
    private static RxBus sInstance = new RxBus();

    private final Subject mSubject;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBus() {
        mSubject = new SerializedSubject<>(PublishSubject.create());
    }

    // 单例RxBus
    public static RxBus getDefault() {
        return sInstance;
    }

    // 提供了一个新的事件
    public void post (Object o) {
        mSubject.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObserverable (Class<T> eventType) {
        return mSubject.ofType(eventType);
    }
}
