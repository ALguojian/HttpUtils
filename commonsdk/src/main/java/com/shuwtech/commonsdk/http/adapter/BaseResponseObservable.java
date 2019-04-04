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

import com.shuwtech.commonsdk.http.response.BaseResponse;
import com.shuwtech.commonsdk.mediator.router.RouterUtils;
import com.shuwtech.commonsdk.utils.AccountUtils;
import com.shuwtech.commonsdk.utils.Toaster;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 用于BaseResponse的Observable
 */
final class BaseResponseObservable<T> extends Observable<BaseResponse<T>> {
    private final Observable<Response<BaseResponse<T>>> upstream;

    BaseResponseObservable(Observable<Response<BaseResponse<T>>> upstream) {
        this.upstream = upstream;
    }

    @Override
    protected void subscribeActual(Observer<? super BaseResponse<T>> observer) {
        upstream.safeSubscribe(new BaseResponseObserver<>(observer));
    }

    private static class BaseResponseObserver<R> implements Observer<Response<BaseResponse<R>>> {
        private final Observer<? super BaseResponse<R>> observer;

        BaseResponseObserver(Observer<? super BaseResponse<R>> observer) {
            this.observer = observer;
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            observer.onSubscribe(disposable);
        }

        @Override
        public void onNext(Response<BaseResponse<R>> response) {
            if (response.code() == 401) { //401返回授权失败
                observer.onNext(BaseResponse.authFail());
            } else if (response.code() == 403) {//token过期，要重新登录
                observer.onNext(BaseResponse.authFail());
                Toaster.toast("授权信息已过期，请重新登录！");
                AccountUtils.logout();
                RouterUtils.route2Login();
            } else if (response.code() >= 200 && response.code() < 300) { //2xx 返回服务端内容
                observer.onNext(response.body());
            } else {
                observer.onNext(BaseResponse.netError()); // 返回网络错误
            }
        }

        @Override
        public void onError(Throwable throwable) {
            try {
                if (throwable instanceof IOException
                        || throwable instanceof HttpException) {
                    observer.onNext(BaseResponse.netError());
                } else {
                    observer.onError(throwable);
                }
            } catch (Throwable t) {
                try {
                    observer.onError(t);
                } catch (Throwable inner) {
                    Exceptions.throwIfFatal(inner);
                    RxJavaPlugins.onError(new CompositeException(t, inner));
                }
                return;
            }
            observer.onComplete();
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }
}
