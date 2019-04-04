package com.shuwtech.commonsdk.ui.dialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuwtech.commonsdk.R;
import com.shuwtech.commonsdk.glide.GlideUtils;
import com.shuwtech.commonsdk.utils.Toaster;

import java.util.List;


/**
 * 微信分享弹框
 * Created by 63062 on 2018/1/4.
 */

public class WXShareDialog extends BottomSheetDialog implements View.OnClickListener {
    private final ImageView ivWXFriend;
    private final ImageView ivWXFriendCircle;
    private WxShareClickListener listener;
    public WXShareDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.common_layout_dialog_wx_share);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        ivWXFriend = (ImageView) findViewById(R.id.ivWXFriend);
        ivWXFriendCircle = (ImageView) findViewById(R.id.ivWXFriendCircle);

        findViewById(R.id.tvCancel).setOnClickListener(this);
        findViewById(R.id.llWXFriend).setOnClickListener(this);
        findViewById(R.id.llWXFriendCircle).setOnClickListener(this);

        GlideUtils.displayCrop(getContext(), R.drawable.common_share_friends_icon, ivWXFriend);
        GlideUtils.displayCrop(getContext(), R.drawable.common_share_friends_circle_icon, ivWXFriendCircle);

//        setDialog();
    }
    public void setWxShareClickListener(WxShareClickListener listener) {
        this.listener = listener;
    }

    public interface WxShareClickListener {
        void shareFriend();

        void shareFriendCircle();
    }    @Override
    public void onClick(View v) {
        dismiss();
        if (v.getId() == R.id.llWXFriend) {

            if (listener != null) {
                listener.shareFriend();
            }
        } else if (v.getId() == R.id.llWXFriendCircle) {

            if (listener != null) {
                listener.shareFriendCircle();
            }
        }
    }




}
