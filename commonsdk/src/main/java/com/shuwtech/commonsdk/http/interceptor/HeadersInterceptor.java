package com.shuwtech.commonsdk.http.interceptor;

import com.shuwtech.commonsdk.utils.AppInfoUtils;
import com.shuwtech.commonsdk.utils.InitInfoUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求头拦截器，加入app-version,platform,token等
 * */
public class HeadersInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("App-Version", AppInfoUtils.versionName());
        builder.addHeader("Platform", "android");
        builder.addHeader("Token", InitInfoUtils.getToken());
        return chain.proceed(builder.build());
    }
}
