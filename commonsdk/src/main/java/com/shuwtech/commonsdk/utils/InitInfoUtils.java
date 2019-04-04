package com.shuwtech.commonsdk.utils;

import android.text.TextUtils;

/**
 * 初始化信息
 */
public class InitInfoUtils {
    //token
    private static final String KEY_TOKEN = "key_token";
    //
    private static final String KEY_IS_FIRST_LOGIN = "is_first_login";
    private static final String KEY_IS_FIRST_OPEN_APP = "key_is_first_open_app";
    private static final String KEY_SALON_ID = "key_salon_id";
    private static final String KEY_PACKAGE_VERSION_NAME = "key_package_version_name";//之前启动的包的版本

    private static String sToken;

    public static String getToken() {
        if (TextUtils.isEmpty(sToken)) {
            sToken = PrefUtils.getString(KEY_TOKEN, null);
        }
        return sToken == null ? "" : sToken;
    }

    public static void setToken(String token) {
        sToken = token;
        PrefUtils.putString(KEY_TOKEN, token);
    }

    public static boolean isFirstLogin() {
        return PrefUtils.getBool(KEY_IS_FIRST_LOGIN, false);
    }

    public static void setIsFirstLogin(boolean isFirstLogin) {
        PrefUtils.putBool(KEY_IS_FIRST_LOGIN, isFirstLogin);
    }

    public static boolean isFirstOpenApp() {
        return PrefUtils.getBool(KEY_IS_FIRST_OPEN_APP, true);
    }

    public static void setIsFirstOpenApp(boolean isFirstOpenApp) {
        PrefUtils.putBool(KEY_IS_FIRST_OPEN_APP, isFirstOpenApp);
    }

    public static void setSalonId(String salonId) {
        PrefUtils.putString(KEY_SALON_ID, salonId);
    }

    public static String getSalonId() {
        return PrefUtils.getString(KEY_SALON_ID, null);
    }

    /**
     * 之前启动的包是否是最新包   不是则要重新登录
     *
     * @return false  不是    true    是
     */
    public static boolean isNewPackage() {
        String versionName = PrefUtils.getString(KEY_PACKAGE_VERSION_NAME, null);
        if (TextUtils.isEmpty(versionName) || !versionName.equals(AppInfoUtils.versionName())) {
            return false;
        }
        return true;
    }

    public static void updateVersionName() {
        PrefUtils.putString(KEY_PACKAGE_VERSION_NAME, AppInfoUtils.versionName());
    }

    public static void clear() {
        setSalonId(null);
        setToken(null);
    }
}
