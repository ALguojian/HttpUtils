package com.shuwtech.commonsdk.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shuwtech.commonsdk.R;

public class Toaster {
    private static Context sContext;

    public static void init(Context context) {
        sContext = context;
    }

    public static void toast(@StringRes int toastRes) {
        checkInit();
        toast(sContext.getResources().getString(toastRes));
    }

    public static void toast(String toast) {
        checkInit();
        Toast result = new Toast(sContext);
        TextView v = (TextView) View.inflate(sContext, R.layout.common_layout_toast, null);
        v.setText(toast);
        result.setView(v);
        result.setDuration(Toast.LENGTH_SHORT);
        result.setGravity(Gravity.CENTER, 0, 0);
        result.show();
    }

    public static void toast(@StringRes int toastRes, @DrawableRes int iconRes) {
        checkInit();
        toast(sContext.getResources().getString(toastRes), iconRes);
    }

    public static void toast(String toast, @DrawableRes int iconRes) {
        checkInit();
        Toast result = new Toast(sContext);
        TextView v = (TextView) View.inflate(sContext, R.layout.common_layout_toast, null);
        v.setText(toast);
        Drawable drawable = sContext.getResources().getDrawable(iconRes);
        drawable.setBounds(0, 0, PixelUtils.dip2px(sContext, 36),
            PixelUtils.dip2px(sContext, 36));
        v.setCompoundDrawables(null, drawable, null, null);
        v.setCompoundDrawablePadding(PixelUtils.dip2px(sContext, 10));
        result.setView(v);
        result.setDuration(Toast.LENGTH_SHORT);
        result.setGravity(Gravity.CENTER, 0, 0);
        result.show();
    }

    public static void checkInit() {
        if (sContext == null) throw new IllegalStateException("toaster hasn't not init.");
    }
}

