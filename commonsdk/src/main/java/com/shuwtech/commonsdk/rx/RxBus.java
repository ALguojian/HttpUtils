package com.shuwtech.commonsdk.rx;

import android.support.annotation.NonNull;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 *rx实现的bus总线
 * */
@SuppressWarnings("unused")
public class RxBus {
    private final FlowableProcessor<Object> BUS = PublishProcessor.create().toSerialized();

    private static volatile RxBus sDefaultInstance;

    private RxBus() {
    }

    public static RxBus INSTANCE() {
        if (sDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (sDefaultInstance == null) {
                    sDefaultInstance = new RxBus();
                }
            }
        }
        return sDefaultInstance;
    }

    //发送事件
    public void send(Object o) {
        BUS.onNext(o);
    }

    public Flowable<Object> toFlowable() {
        return BUS;
    }

    public boolean hasSubscribers() {
        return BUS.hasSubscribers();
    }

    //过滤指定事件类型
    public <T> Flowable<T> toFlowable(@NonNull Class<T> classToSubscribe) {
        return BUS.filter(classToSubscribe::isInstance)
            .cast(classToSubscribe);
    }
}