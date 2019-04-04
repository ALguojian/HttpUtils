package com.shuwtech.commonsdk.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * 全局 base fragment
 * */
public class CommonBaseFragment extends StateFragment {
    private KProgressHUD kProgressHUD;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showProgress(boolean cancel) {
        closeProgress();
        kProgressHUD = KProgressHUD.create(getContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(cancel)
            .setAnimationSpeed(1)
            .setDimAmount(0.5f)
            .show();
    }

    public void showProgress(boolean cancel, float dimAmount) {
        closeProgress();
        kProgressHUD = KProgressHUD.create(getContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(cancel)
            .setAnimationSpeed(1)
            .setDimAmount(dimAmount)
            .show();
    }

    public void showProgress(boolean cancel, String label) {
        closeProgress();
        kProgressHUD = KProgressHUD.create(getContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(cancel)
            .setLabel(label)
            .setAnimationSpeed(1)
            .setDimAmount(0.5f)
            .show();
    }

    public void closeProgress() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
            kProgressHUD = null;
        }
    }
}
