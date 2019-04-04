package com.shuwtech.commonsdk.http.response;

import java.io.Serializable;

/**
 * BaseResponse类
 * T  返回result
 * */
@SuppressWarnings("unused")
public class BaseResponse<T> implements Serializable {

    public String Code;
    public T Result;
    public String Tips;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "Code='" + Code + '\'' +
                ", Result=" + Result +
                ", Tips='" + Tips + '\'' +
                ", isNetError=" + isNetError +
                '}';
    }

    /**
     * 网络错误
     */
    private boolean isNetError;


    public static <R> BaseResponse<R> netError() {
        BaseResponse<R> response = new BaseResponse<>();
        response.isNetError = true;
        return response;
    }

    /**
     * 用户被封号
     * @param <R>
     * @return
     */
    public static <R> BaseResponse<R> authFail() {
        BaseResponse<R> response = new BaseResponse<>();
        response.Code = "100";
        return response;
    }

    public static <R> BaseResponse<R> empty() {
        BaseResponse<R> response = new BaseResponse<>();
        response.Result = null;
        return response;
    }

    public boolean isSuccess() {
        return "1".equals(Code) ;
    }

    public boolean isEmpty() {
        return Result == null;
    }

    public boolean isNetError() {
        return isNetError;
    }

    public boolean isAuthFail() {
        return "100".equals(Code);
    }}
