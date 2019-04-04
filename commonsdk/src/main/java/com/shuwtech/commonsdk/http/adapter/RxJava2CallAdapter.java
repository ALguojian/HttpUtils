package com.shuwtech.commonsdk.http.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.shuwtech.commonsdk.BuildConfig;

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

final class RxJava2CallAdapter<R> implements CallAdapter<R, Object> {
    private final Type responseType;
    private final @Nullable
    Scheduler scheduler;
    private final boolean isAsync;
    private final boolean isResult;
    private final boolean isBody;
    private final boolean isFlowable;
    private final boolean isSingle;
    private final boolean isMaybe;
    private final boolean isCompletable;

    RxJava2CallAdapter(Type responseType, @Nullable Scheduler scheduler, boolean isAsync,
                       boolean isResult, boolean isBody, boolean isFlowable, boolean isSingle, boolean isMaybe,
                       boolean isCompletable) {
        this.responseType = responseType;
        this.scheduler = scheduler;
        this.isAsync = isAsync;
        this.isResult = isResult;
        this.isBody = isBody;
        this.isFlowable = isFlowable;
        this.isSingle = isSingle;
        this.isMaybe = isMaybe;
        this.isCompletable = isCompletable;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call<R> call) {
        Observable<Response<R>> responseObservable = isAsync
            ? new com.shuwtech.commonsdk.http.adapter.CallEnqueueObservable<>(call)
            : new com.shuwtech.commonsdk.http.adapter.CallExecuteObservable<>(call);

        Observable<?> observable;
        if (isResult) {
            observable = new com.shuwtech.commonsdk.http.adapter.ResultObservable<>(responseObservable);
        } else if (isBody) {
            observable = new com.shuwtech.commonsdk.http.adapter.BodyObservable<>(responseObservable);
        } else {
            observable = responseObservable;
        }

        if (scheduler != null) {
            observable = observable.subscribeOn(scheduler);
        }

        if (isFlowable) {
            //todo check error
            return observable.toFlowable(BackpressureStrategy.LATEST)
                .doOnError(this::checkNetError)
                .onErrorResumeNext(Flowable.empty())
                .subscribeOn(Schedulers.io());
        }

        if (isSingle) {
            return observable.singleOrError();
        }
        if (isMaybe) {
            return observable.singleElement();
        }
        if (isCompletable) {
            return observable.ignoreElements();
        }
        return RxJavaPlugins.onAssembly(observable);
    }

    protected void checkNetError(Throwable throwable) {
        if (BuildConfig.USE_LOG) {
            Log.d("OkHttp---", String.format("retrofit adapt error: %s", throwable.getMessage()));
        }
    }
}
