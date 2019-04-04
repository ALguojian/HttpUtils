package com.shuwtech.commonsdk.ui.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.shuwtech.commonsdk.CommonBaseApp;
import com.shuwtech.commonsdk.mediator.router.RequestCode;
import com.shuwtech.commonsdk.utils.Toaster;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.UUID;

/**
 * 相册，照片工具类
 * */
public class CameraPhotoUtil {

    private static String takePhotoPath = null;

    //拍照
    public static void takePhoto(final Activity activity, RxPermissions permissionHelper) {
        if (activity == null || permissionHelper == null) {
            return;
        }

        permissionHelper.request(Manifest.permission.CAMERA)
            .filter(it -> {
                if (!it) Toaster.toast("请开起相机权限");
                return it;
            })
            .subscribe(it -> {
                File takePhotoFile = new File(CommonBaseApp.getApp().getExternalCacheDir().getAbsolutePath(), UUID.randomUUID() + ".jpg");
                takePhotoPath = takePhotoFile.getAbsolutePath();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uri;
                if (Build.VERSION.SDK_INT >= 24) {
                    uri = FileProvider.getUriForFile(activity, "com.shuwtech.salon.fileprovider", takePhotoFile);
                } else {
                    uri = Uri.fromFile(takePhotoFile);
                }
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                activity.startActivityForResult(intent, RequestCode.REQUEST_TAKE_PHOTO);
            });
    }

    //打开系统相册
    public static void openGallery(Activity activity) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, RequestCode.REQUEST_CHOOSE_PHOTO);
    }

    public static File getTakePhotoFile() {
        if (TextUtils.isEmpty(takePhotoPath)) {
            return null;
        }
        return new File(takePhotoPath);
    }

    public static String getPhotoFile(Context context, Uri uri) {
        if (context == null || uri == null) {
            return null;
        }
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }
}