package com.shuwtech.commonsdk.utils;

import android.content.Context;

import com.shuwtech.commonsdk.BuildConfig;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 腾讯bugly utils
 * */
public class BuglyUtils {
    private static final String BUGLY_ID = BuildConfig.IS_DEVELOP_ENV ? "95eb69b339" : "b6a3f3c640";

    public static void initBugly(Context context) {
        CrashReport.initCrashReport(context, BUGLY_ID, false);
    }

    //提交关键exception
    public static void submitError(String tag, String msg) {
        BuglyLog.e(tag, msg);
    }
}
