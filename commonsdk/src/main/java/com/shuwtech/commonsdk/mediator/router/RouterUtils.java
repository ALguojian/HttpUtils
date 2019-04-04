package com.shuwtech.commonsdk.mediator.router;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

public class RouterUtils {

    private static String sModuleMainRoute;

    public static void init(String mainRoute) {
        sModuleMainRoute = mainRoute;
    }

    public static String getModuleMainRoute() {
        return sModuleMainRoute;
    }



    //跳转登录页
    public static void route2Login() {

    }

    public static Fragment getFragment(String path) {
        return (Fragment) ARouter.getInstance().build(path).navigation();
    }

    public static void routeTo(String path, Context context) {
        ARouter.getInstance().build(path).navigation(context);
    }

    public static void routeTo(String path, Bundle bundle, Context context) {
        ARouter.getInstance().build(path)
            .withBundle(Extra.EXTRA_BUNDLE, bundle)
            .navigation(context);
    }

    public static void routeTo(String path, @AnimRes int enterAnim,
                               @AnimRes int ExitAnim, Context context) {
        ARouter.getInstance().build(path)
            .withTransition(enterAnim, ExitAnim)
            .navigation(context);
    }


    //跳转到web页面
    public static void routeToWeb(String title, String url, Context context) {
        ARouter.getInstance().build(Router.PATH.PATH_APP_WEB)
            .withString(Extra.EXTRA_TITLE, title)
            .withString(Extra.EXTRA_URL, url)
            .navigation(context);
    }

}
