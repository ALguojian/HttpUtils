package com.shuwtech.commonsdk;

import android.app.Application;
import android.content.res.Resources;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shuwtech.commonsdk.utils.AccountUtils;
import com.shuwtech.commonsdk.utils.AppInfoUtils;
import com.shuwtech.commonsdk.utils.BuglyUtils;
import com.shuwtech.commonsdk.utils.Downloader;
import com.shuwtech.commonsdk.utils.PrefUtils;
import com.shuwtech.commonsdk.utils.RomUtils;
import com.shuwtech.commonsdk.utils.Toaster;

public class CommonBaseApp extends Application {

    private static CommonBaseApp sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;

        RomUtils.init();
        PrefUtils.init(this);
        AppInfoUtils.init(this);
        Toaster.init(this);
        AccountUtils.init();
        initARouter();
    }

    private void initARouter() {
        ARouter.init(this);
        if (BuildConfig.USE_LOG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
    }

    public static CommonBaseApp getApp() {
        return sAppContext;
    }

    public static Resources getRes() {
        return sAppContext.getResources();
    }
}