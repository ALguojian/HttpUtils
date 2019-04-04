package com.shuwtech.commonsdk.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.shuwtech.commonsdk.R;

public class CommonBaseDialog extends Dialog {

    public CommonBaseDialog(@NonNull Context context) {
        super(new ContextThemeWrapper(context, R.style.common_Dialog));
    }

    public CommonBaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public CommonBaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(new ContextThemeWrapper(context, R.style.common_Dialog), cancelable, cancelListener);
    }

    protected void setWindowStyle(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        w.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        w.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams lp = getWindow().getAttributes();
        lp.width = width * 3 / 4;
        w.setLayout(lp.width, lp.height);
        w.setWindowAnimations(R.style.common_DialogAnimation);
    }
}