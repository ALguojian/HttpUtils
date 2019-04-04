package com.shuwtech.commonsdk.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shuwtech.commonsdk.model.Salon;

/**
 * account管理类
 * */
public class AccountUtils {

    private static volatile Salon sSalon;
    private static final String KEY_ACCOUNT_JSON = "key_account_json";

    public static void init() {
        readSalon();
    }

    //读取发廊信息
    private static void readSalon() {
        String json = PrefUtils.getString(KEY_ACCOUNT_JSON, null);
        if (!TextUtils.isEmpty(json)) {
            sSalon = new Gson().fromJson(json, Salon.class);
        }
    }

    public static Salon getSalon() {
        return sSalon;
    }

    public static boolean isLogin() {
        return sSalon != null;
    }

    //更新发廊信息
    public static void updateSalon(Salon salon) {
        if (salon == null) return;
        saveSalon(salon);
    }

    //登出清除数据
    public static void logout() {
        saveSalon(null);
    }

    //保存发廊信息到本地
    private static void saveSalon(Salon salon) {
        sSalon = salon;
        String json = new Gson().toJson(salon);
        PrefUtils.putString(KEY_ACCOUNT_JSON, json);
    }

}
