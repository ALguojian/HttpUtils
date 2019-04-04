package com.shuwtech.commonsdk.rx;

import android.text.TextUtils;

import com.shuwtech.commonsdk.http.response.BaseResponse;
import com.shuwtech.commonsdk.ui.state.statehelper.IStateHelper;
import com.shuwtech.commonsdk.utils.Toaster;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * rx转换器
 */
public class RxTransfer {

    public static <T extends BaseResponse> Flowable<T> onMain(Flowable<T> request) {
        return request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //切换到主线程
    public static <T> Flowable<T> onMainIO(Flowable<BaseResponse<T>> request) {
        return request.observeOn(AndroidSchedulers.mainThread())
                .filter(it -> {
                    if (it.isNetError()) {
                        Toaster.toast("网络错误，请检查网络设置");
                    }
                    return !it.isNetError();
                })
                .filter(it -> {
                    if (!it.isSuccess() || it.isEmpty()) {
                        Toaster.toast("请求失败");
                    }
                    return it.isSuccess() && !it.isEmpty();
                })
                .map(it -> it.Result);
    }

    //返回类型为空时，切换到主线程
    public static <T> Flowable<BaseResponse<T>> onMainIOWithVoid(Flowable<BaseResponse<T>> request) {
        return request.observeOn(AndroidSchedulers.mainThread())
                .filter(it -> {
                    if (it.isNetError()) {
                        Toaster.toast("网络错误，请检查网络设置");
                    }
                    return !it.isNetError();
                })
                .filter(it -> {
                    if (!it.isSuccess()) {
                        Toaster.toast("请求失败");
                    }
                    return it.isSuccess();
                });
    }

    //自动切换页面状态
    public static <T> Flowable<T> withState(Flowable<BaseResponse<T>> request, boolean showLoading, IStateHelper stateHelper) {
        return request.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(it -> {
                    if (showLoading) stateHelper.showLoading();
                })
                .filter(it -> {
                    if (it.isNetError()) {
                        stateHelper.showError();
                    }
                    return !it.isNetError();
                })
                .filter(it -> {
                    if (!it.isSuccess() || it.isEmpty()) {
                        stateHelper.showEmpty();
                    }
                    return it.isSuccess() && !it.isEmpty();
                })
                .map(it -> it.Result)
                .doOnNext(it -> stateHelper.showContent());
    }

    //返回结果为空时，自动切换页面状态
    public static <T> Flowable<BaseResponse<T>> withStateVoid(Flowable<BaseResponse<T>> request, boolean showLoading, IStateHelper stateHelper) {
        return request.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(it -> {
                    if (showLoading) stateHelper.showLoading();
                })
                .filter(it -> {
                    if (it.isNetError()) {
                        stateHelper.showError();
                    }
                    return !it.isNetError();
                })
                .filter(it -> {
                    if (!it.isSuccess()) {
                        Toaster.toast("请求失败");
                    }
                    return it.isSuccess();
                })
                .doOnNext(it -> stateHelper.showContent());
    }
}
