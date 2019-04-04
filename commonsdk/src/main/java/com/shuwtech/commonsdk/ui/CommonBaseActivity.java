package com.shuwtech.commonsdk.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.shuwtech.commonsdk.R;
import com.shuwtech.commonsdk.ui.widget.CommonToolbar;
import com.shuwtech.commonsdk.utils.StatusBarHelper;

import java.lang.ref.WeakReference;

/**
 * 全局 base activity
 */
public class CommonBaseActivity extends SwipeActivity {

    //标题栏
    protected CommonToolbar toolbar;
    //menifest 配置标题
    private CharSequence label;
    //loading
    private KProgressHUD progressHUD;
    private WeakReference<Activity> mWeakReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeakReference = new WeakReference<>(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        StatusBarHelper.setStatusBarStyle(this);
        setUpToolbar();
        setSwipeBackEnable(true);
        setHomeUpClick();
    }

    //设置toolbar
    private void setUpToolbar() {
        View view = findViewById(R.id.toolbar);
        if (view instanceof CommonToolbar) {
            toolbar = (CommonToolbar) view;
        }
        if (toolbar == null) return;

        try {
            //如果在manifest中配置过label，设置label为标题
            ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(),
                    PackageManager.GET_META_DATA);
            label = activityInfo.loadLabel(getPackageManager());

            setHomeUpEnabled(true);
            setCommonTitle(label);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //设置homeup击事件
    protected void setHomeUpClick() {
        if (toolbar == null) return;
        toolbar.setHomeUpClickListener(v -> {
            onBackPressed();
        });
    }

    //是否显示homeicon图标
    protected void setHomeUpEnabled(boolean enabled) {
        if (toolbar != null) toolbar.setHomeUpEnable(enabled);
    }

    //设置标题
    public void setCommonTitle(CharSequence title) {
        setTitle(""); //disable origin title

        if (title != null && toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    //设置homeup icon
    protected void setHomeUpIcon(@DrawableRes int homeUpIconRes) {
        if (toolbar != null) {
            toolbar.setHomeUpEnable(true);
            toolbar.setHomeUp(homeUpIconRes);
        }
    }

    //设置menu点击事件
    protected void setMenuClick(View.OnClickListener onClickListener) {
        toolbar.setMenuClickListener(onClickListener);
    }

    //显示loading
    public void showProgress(boolean cancel) {
        closeProgress();
        progressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(cancel)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
    }

    //关闭loading
    public void closeProgress() {
        if (progressHUD != null) {
            progressHUD.dismiss();
            progressHUD = null;
        }
    }

    //显示loading，自定义透明度
    public void showProgress(boolean cancel, float dimAmount) {
        closeProgress();
        progressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(cancel)
                .setAnimationSpeed(1)
                .setDimAmount(dimAmount)
                .show();
    }

    //显示loading,带文字说明
    public void showProgress(boolean cancel, String label) {
        closeProgress();
        progressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(cancel)
                .setLabel(label)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
