package com.shuwtech.commonsdk.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.shuwtech.commonsdk.BuildConfig;

import java.io.InputStream;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

/**
 * glide module
 * */
@GlideModule
public final class SalonGlideModule extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        final RequestOptions defaultOptions = new RequestOptions();
        defaultOptions.format(PREFER_ARGB_8888);
        defaultOptions.disallowHardwareConfig();
        builder.setDefaultRequestOptions(defaultOptions);
        if (BuildConfig.USE_LOG) builder.setLogLevel(Log.ERROR);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //使用okhttp作为网络下载器
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
