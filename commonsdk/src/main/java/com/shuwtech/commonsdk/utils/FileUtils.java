package com.shuwtech.commonsdk.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.shuwtech.commonsdk.CommonBaseApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static String savePhoto(byte[] data, String fileName) throws IOException {

        File cacheDir = new File(CommonBaseApp.getApp().getCacheDir().getAbsolutePath() + "/image");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        File file = new File(cacheDir, fileName);
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, null);
        out.write(ImageUtils.compressImage(bitmap, 200));
        out.flush();
        out.close();
        bitmap.recycle();
        return file.getAbsolutePath();
    }

    public static String savePhoto(Bitmap bitmap, String fileName) throws IOException {

        File cacheDir = new File(CommonBaseApp.getApp().getCacheDir().getAbsolutePath() + "/image");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        File file = new File(cacheDir, fileName);
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        out.write(ImageUtils.compressImage(bitmap, 200));
        out.flush();
        out.close();
        bitmap.recycle();
        return file.getAbsolutePath();
    }

    public static String savePhoto(String path, String fileName) throws IOException {

        File cacheDir = new File(CommonBaseApp.getApp().getCacheDir().getAbsolutePath() + "/image");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        File file = new File(cacheDir, fileName);
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        Bitmap bitmap = BitmapFactory.decodeFile(path, null);
        if(bitmap == null) return null;
        out.write(ImageUtils.compressImage(bitmap, 200));
        out.flush();
        out.close();
        bitmap.recycle();
        return file.getAbsolutePath();
    }

    public static String getExternalStorageCachePath() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/salon/cache");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
