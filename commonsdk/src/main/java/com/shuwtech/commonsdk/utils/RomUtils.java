package com.shuwtech.commonsdk.utils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RomUtils {
    public enum Rom {
        MIUI, EMUI, FLYME, OPPO, SMARTISAN, VIVO, UNKNOW
    }

    private static final String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
    private static final String KEY_VERSION_EMUI = "ro.build.version.emui";
    private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";
    private static final String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final String KEY_VERSION_VIVO = "ro.vivo.os.version";

    private static Rom sRom;

    public static void init() {
        if (checkVersion(KEY_VERSION_MIUI)) {
            sRom = Rom.MIUI;
        } else if (checkVersion(KEY_VERSION_EMUI)) {
            sRom = Rom.EMUI;
        } else if (checkVersion(KEY_VERSION_OPPO)) {
            sRom = Rom.OPPO;
        } else if (checkVersion(KEY_VERSION_SMARTISAN)) {
            sRom = Rom.SMARTISAN;
        } else if (checkVersion(KEY_VERSION_VIVO)) {
            sRom = Rom.VIVO;
        } else {
            sRom = Rom.UNKNOW;
        }
    }

    public static Rom getRom() {
        if (sRom == null) {
            sRom = Rom.UNKNOW;
        }

        return sRom;
    }

    private static boolean checkVersion(String key) {
        String line = null;
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + key);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return !TextUtils.isEmpty(line);
        }
    }
}
