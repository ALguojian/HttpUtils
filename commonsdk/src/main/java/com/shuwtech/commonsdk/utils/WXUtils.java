package com.shuwtech.commonsdk.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shuwtech.commonsdk.BuildConfig;
import com.shuwtech.commonsdk.glide.GlideApp;
import com.shuwtech.commonsdk.mediator.router.Router;
import com.shuwtech.commonsdk.model.WxShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXUtils {

    private static final String WX_APP_ID = BuildConfig.IS_DEVELOP_ENV
        ? "wx4540ab20be6f4bd7" : "wx1da38dfd3a20dbf2";
    private static final String WX_MINA_ID = BuildConfig.IS_DEVELOP_ENV
        ? "gh_41a52d0bf642" : "gh_a13dd8f7fed9";

    private static IWXAPI sIWXAPI;

    public static void registerWx(Context context) {
        sIWXAPI = WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
        sIWXAPI.registerApp(WX_APP_ID);
    }

    public static boolean isWXInstalled() {
        return sIWXAPI.isWXAppInstalled();
    }

    public static IWXAPI getIWXAPI() {
        return sIWXAPI;
    }

    //flag ：分享类型 0 好友 1 朋友圈
    public static void shareUrl(final Context context, final int flag, WxShare wxShare) {
        if (!isWXInstalled()) {
            Toaster.toast("您还未安装微信客户端");
            return;
        }

        if (wxShare == null || TextUtils.isEmpty(wxShare.link)) {
            Toaster.toast("分享链接不存在");
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = wxShare.link;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        if (!TextUtils.isEmpty(wxShare.title)) {
            msg.title = wxShare.title;
        } else {
            msg.title = wxShare.desc;
        }
        msg.description = wxShare.desc;

        if (TextUtils.isEmpty(wxShare.imgUrl)) {
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            sIWXAPI.sendReq(req);
        } else {
            GlideApp.with(context)
                .asBitmap()
                .load(wxShare.imgUrl)
                .override(100, 100)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        msg.setThumbImage(ImageUtils.compressWXImage(resource));
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = String.valueOf(System.currentTimeMillis());
                        req.message = msg;
                        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                        sIWXAPI.sendReq(req);
                    }
                });
        }
    }

    //flag ：分享类型 0 好友 1 朋友圈
    public static void shareImage(final Context context, final int flag, final String title, final String imgUrl) {
        if (!isWXInstalled()) {
            Toaster.toast("您还未安装微信客户端");
            return;
        }

        if (TextUtils.isEmpty(imgUrl)) {
            Toaster.toast("图片地址不存在");
            return;
        }

        GlideApp.with(context)
            .asBitmap()
            .load(imgUrl)
            .into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    WXImageObject imageObject = new WXImageObject(resource);
                    WXMediaMessage msg = new WXMediaMessage();
                    msg.title = title;
                    msg.mediaObject = imageObject;
                    Bitmap thumb = Bitmap.createScaledBitmap(resource, 100, 100, true);
                    msg.setThumbImage(ImageUtils.compressWXImage(thumb));
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = msg;
                    req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                    sIWXAPI.sendReq(req);
                }
            });
    }

    public static void shareSalonPageMina(Context context, String title, String desc, String imgUrl, String salonId) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = String.format(Router.PATH.PATH_SALON_PAGE, salonId); // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = BuildConfig.IS_DEVELOP_ENV
            ? WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW
            : WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = WX_MINA_ID;// 小程序原始id
        miniProgramObj.path = String.format("pages/details/index?id=%s", salonId);//小程序页面路径
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = desc;               // 小程序消息desc

        if (TextUtils.isEmpty(imgUrl)) {
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
            sIWXAPI.sendReq(req);
        } else {
            GlideApp.with(context)
                .asBitmap()
                .load(imgUrl)
                .override(100, 100)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        msg.setThumbImage(ImageUtils.compressWXImage(resource));
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = String.valueOf(System.currentTimeMillis());
                        req.message = msg;
                        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
                        sIWXAPI.sendReq(req);
                    }
                });
        }

    }

    /**
     * 开启微信
     */
    public static void jump2WX(Context context){
        if (!isWXInstalled()) {
            Toaster.toast("您还未安装微信客户端");
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }
}
