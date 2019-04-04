package com.shuwtech.commonsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {
    private static SharedPreferences sPreferences;

    public static void init(Context context) {
        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void putString(String key, String value) {
        checkInit();
        sPreferences.edit().putString(key, value).apply();
    }

    public static String getString(String key, String defaultValue) {
        checkInit();
        return sPreferences.getString(key, defaultValue);
    }

    public static void putBool(String key, boolean value) {
        checkInit();
        sPreferences.edit().putBoolean(key, value).apply();
    }

    public static boolean getBool(String key, boolean defalutValue) {
        checkInit();
        return sPreferences.getBoolean(key, defalutValue);
    }

    private static void checkInit() {
        if (sPreferences == null) {
            throw new IllegalStateException("must init SharedPreferences first");
        }
    }
}
