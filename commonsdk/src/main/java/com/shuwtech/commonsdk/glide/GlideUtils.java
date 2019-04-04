package com.shuwtech.commonsdk.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.shuwtech.commonsdk.utils.PixelUtils;

/**
 * glide 工具类
 */
public class GlideUtils {

    public static void display(Context context, Object url, ImageView target) {
        display(context, url, 0, 0, false, 0, target);
    }

    public static void displayCrop(Context context, Object url, ImageView target) {
        display(context, url, 0, 0, true, 0, target);
    }

    public static void displayCenter(Context context, Object url, ImageView target) {
        GlideApp.with(context)
            .load(url)
            .fitCenter()
            .dontAnimate()
            .into(target);
    }

    public static void displayGif(Context context, Object url, ImageView target) {
        GlideApp.with(context)
            .asGif()
            .load(url)
            .into(target);
    }

    /**
     * @param cornerSize dp
     */
    public static void display(Context context, Object url, @DrawableRes int placeholder,
                               @DrawableRes int errorHolder, boolean crop, int cornerSize,
                               ImageView target) {
        RequestOptions options = new RequestOptions();
        if (placeholder != 0) options.placeholder(placeholder);
        if (errorHolder != 0) options.error(placeholder);
        if (crop) options.centerCrop();
        if (cornerSize > 0) {
            options.transform(new RoundedCorners(PixelUtils.dip2px(context, cornerSize)));
        }
        GlideApp.with(context)
            .load(url)
            .apply(options)
            .dontAnimate()
            .into(target);
    }

    public static void displayCircle(Context context, Object url, ImageView target) {
        GlideApp.with(context)
            .load(url)
            .circleCrop()
            .dontAnimate()
            .into(target);
    }

    public static void displayCropCorner(Context context, Object url, int cornerSize, ImageView target) {
        display(context, url, null, null, true, cornerSize, target);
    }

    /**
     * @param cornerSize dp
     */
    public static void display(Context context, Object url, Drawable placeholder,
                               Drawable errorHolder, boolean crop, int cornerSize, ImageView target) {
        RequestOptions options = new RequestOptions();
        if (placeholder != null) options.placeholder(placeholder);
        if (errorHolder != null) options.error(placeholder);

        if (crop && cornerSize > 0) {    //圆角剪裁
            options.transforms(new CenterCrop(), new RoundedCorners(PixelUtils.dip2px(context, cornerSize)));
        } else if (crop) {    //只剪裁
            options.centerCrop();
        } else if (cornerSize > 0) {  //只圆角
            options.transform(new RoundedCorners(PixelUtils.dip2px(context, cornerSize)));
        }

        GlideApp.with(context)
            .load(url)
            .apply(options)
            .dontAnimate()
            .into(target);
    }
}
