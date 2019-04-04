/*
 * Copyright (C) 2016 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shuwtech.commonsdk.http.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.shuwtech.commonsdk.BuildConfig;
import com.shuwtech.commonsdk.http.response.BaseResponse;
import com.shuwtech.commonsdk.mediator.event.TagEvent;
import com.shuwtech.commonsdk.rx.RxBus;

import java.lang.reflect.Type;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;

/**
 * 转换BaseResponse的adapter
 * */
final class BaseResponseCallAdapter<R> implements CallAdapter<BaseResponse<R>, Object> {
    private final Type responseType;
    private final @Nullable
    Scheduler scheduler;
    private final boolean isFlowable;
    private final boolean isAsync;
    private final boolean isCompletable;

    BaseResponseCallAdapter(Type responseType, @Nullable Scheduler scheduler, boolean isAsync,
                            boolean isFlowable, boolean isCompletable) {
        this.responseType = responseType;
        this.scheduler = scheduler;
        this.isFlowable = isFlowable;
        this.isAsync = isAsync;
        this.isCompletable = isCompletable;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call<BaseResponse<R>> call) {
        Observable<Response<BaseResponse<R>>> responseObservable = isAsync
            ? new CallEnqueueObservable<>(call)
            : new CallExecuteObservable<>(call);

        Observable<BaseResponse<R>> observable = new BaseResponseObservable<>(responseObservable);

        if (scheduler != null) {
            observable = observable.subscribeOn(scheduler);
        }

        //检测网络错误，授权失败
        if (isFlowable) {
            return observable.toFlowable(BackpressureStrategy.LATEST)
                .doOnError(this::checkNetError)
                .onErrorResumeNext(e -> {
                    return Flowable.just(BaseResponse.empty());
                })
                .filter(it -> {
                    if (it.isAuthFail()) {
                        RxBus.INSTANCE().send(TagEvent.EVENT_AUTH_FAIL);
                    }
                    return !it.isAuthFail();
                })
                .subscribeOn(Schedulers.io());
        }

        if (isCompletable) {
            return observable.ignoreElements();
        }

        return RxJavaPlugins.onAssembly(observable);
    }

    protected void checkNetError(Throwable throwable) {
        if (BuildConfig.USE_LOG) {
            Log.d("OkHttp", String.format("retrofit adapt error: %s", throwable.getMessage()));
        }
    }
}
