package com.shuwtech.commonsdk.ui.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.shuwtech.commonsdk.R;
import com.shuwtech.commonsdk.mediator.router.RequestCode;
import com.shuwtech.commonsdk.utils.Toaster;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * 拍照或者选择相册
 *
 * @author alguojian
 * @date 2018.05.21
 */

public class PhotoCameraSelectDialog extends Dialog implements View.OnClickListener {
    private final RxPermissions permissionHelper;
    private final Activity activity;

    public PhotoCameraSelectDialog(@NonNull Activity activity, int arr) {
        super(activity, R.style.common_Dialog);
        this.activity = activity;
        setContentView(R.layout.common_dialog_photo_camera_select);

        setCanceledOnTouchOutside(false);
        setCancelable(true);
        TextView tvCamera = findViewById(R.id.tvCamera);
        TextView tvPhoto = findViewById(R.id.tvPhoto);
        View view = findViewById(R.id.view);
        tvCamera.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);

        if (1 == arr) {
            tvPhoto.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        } else if (2 == arr) {
            tvCamera.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

        findViewById(R.id.tvCancel).setOnClickListener(this);

        permissionHelper = new RxPermissions(activity);
        setDialog(getContext());
    }

    private void setDialog(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        Window w = getWindow();
        w.setWindowAnimations(R.style.common_DialogBottomToTopAnim);
        w.setGravity(Gravity.BOTTOM);
        ViewGroup.LayoutParams lay = getWindow().getAttributes();
        lay.width = dm.widthPixels;
    }

    @Override
    public void onClick(View view) {
        dismiss();

        int id = view.getId();
        if (R.id.tvCamera == id) {
            checkExternalStorage(RequestCode.REQUEST_TAKE_PHOTO);
        } else if (R.id.tvPhoto == id) {
            checkExternalStorage(RequestCode.REQUEST_CHOOSE_PHOTO);
        }
    }

    private void checkExternalStorage(final int code) {
        permissionHelper.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .filter(it -> {
                if (!it) Toaster.toast("请开启存储权限");
                return it;
            })
            .subscribe(it -> {
                if (code == RequestCode.REQUEST_TAKE_PHOTO) {
                    CameraPhotoUtil.takePhoto(activity, permissionHelper);
                } else {
                    CameraPhotoUtil.openGallery(activity);
                }
            });
    }

}
