package com.shuwtech.commonsdk.http;

import android.text.TextUtils;

import com.shuwtech.commonsdk.BuildConfig;
import com.shuwtech.commonsdk.CommonBaseApp;
import com.shuwtech.commonsdk.http.adapter.RxJava2CallAdapterFactory;
import com.shuwtech.commonsdk.http.interceptor.HeadersInterceptor;
import com.shuwtech.commonsdk.http.interceptor.HttpJsonLoggingInterceptor;
import com.shuwtech.commonsdk.http.interceptor.PostBody2JsonInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit service配置
 * */
public class ApiService {

    private final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    private Retrofit mRetrofit;

    public ApiService(String baseUrl) {
        this(baseUrl, null);
    }

    /**
     * @param baseUrl 基址
     * */
    public ApiService(String baseUrl, Interceptor interceptor) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalArgumentException("baseUrl must not be empty!");
        }

        initRetrofit(baseUrl, interceptor);
    }

    private void initRetrofit(String baseUrl, Interceptor interceptor) {
        mRetrofit = new Retrofit.Builder()
            .client(createHttpClient(interceptor))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory)
            .baseUrl(baseUrl)
            .build();
    }

    private OkHttpClient createHttpClient(Interceptor interceptor) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (interceptor != null) {
            httpClient.addInterceptor(interceptor);
        }

        httpClient.addInterceptor(new HeadersInterceptor());
        httpClient.addInterceptor(new PostBody2JsonInterceptor());
        if (BuildConfig.USE_LOG) {
            HttpJsonLoggingInterceptor logInterceptor = new HttpJsonLoggingInterceptor();
            logInterceptor.setLevel(HttpJsonLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logInterceptor);
        }
        return httpClient.build();
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
