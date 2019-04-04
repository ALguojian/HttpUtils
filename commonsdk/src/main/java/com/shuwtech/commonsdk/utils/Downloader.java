package com.shuwtech.commonsdk.utils;

import android.content.Context;
import android.os.Environment;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.core.connection.DownloadOkHttp3Connection;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 下载类
 * */
public class Downloader {
    private static final String DOWN_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    private static final String IMG_DIR = DOWN_DIR + "/sw_salon/";

    public static void init(Context context) {
        //更改超时时间100s
        OkDownload.Builder builder = new OkDownload.Builder(context)
            .connectionFactory(url -> {
                DownloadOkHttp3Connection.Factory factory = new DownloadOkHttp3Connection.Factory();
                OkHttpClient.Builder builder1 = factory.builder();
                builder1.readTimeout(100, TimeUnit.SECONDS);
                factory.setBuilder(builder1);
                return factory.create(url);
            });
        OkDownload.setSingletonInstance(builder.build());
    }

    //下载图片
    public static DownloadTask downloadImage(String url, String fileName, boolean single, DownloadTaskListener listener) {
        DownloadTask task = new DownloadTask.Builder(url, new File(IMG_DIR))
            .setFilename(fileName)
            .setConnectionCount(single ? 1 : 3)
            .setMinIntervalMillisCallbackProcess(300)
            .setPassIfAlreadyCompleted(false)
            .build();
        task.enqueue(listener);
        return task;
    }
}
