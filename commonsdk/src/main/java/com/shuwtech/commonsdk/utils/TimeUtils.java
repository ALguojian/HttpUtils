package com.shuwtech.commonsdk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String formatYMDHMS(long millis) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(millis));
    }


    public static String formatLeftTime(long leftSecond) {
        long hour = leftSecond / 3600;
        long min = leftSecond % 3600 / 60;
        long second = leftSecond % 60;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", hour)).append(":");
        sb.append(String.format("%02d", min)).append(":");
        sb.append(String.format("%02d", second));
        return sb.toString();
    }
}
