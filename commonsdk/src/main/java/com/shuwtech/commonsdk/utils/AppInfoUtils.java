package com.shuwtech.commonsdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 获取appinfo工具类
 * */
public class AppInfoUtils {
    private static PackageInfo sPackageInfo;

    public static void init(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            sPackageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static PackageInfo getPackageInfo() {
        return sPackageInfo;
    }

    //获取版本号
    public static String versionName() {
        if(sPackageInfo == null) return "";
        return sPackageInfo.versionName;
    }

    //获取versioncode
    public static int versionCode() {
        if(sPackageInfo == null) return -1;
        return sPackageInfo.versionCode;
    }

    //获取包名
    public static String pkgName() {
        if(sPackageInfo == null) return null;
        return sPackageInfo.packageName;
    }
}
