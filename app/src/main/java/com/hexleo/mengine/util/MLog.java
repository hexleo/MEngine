package com.hexleo.mengine.util;

import android.util.Log;

import com.hexleo.mengine.application.AppSetting;
import com.hexleo.mengine.engine.constant.MeConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by hexleo on 2017/3/13.
 */

public class MLog {

    private static final String TAG_PREFIX = "MLog_";

    public static void i(String tag, String msg) {
        if (AppSetting.isDebug) {
            Log.i(TAG_PREFIX + tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (AppSetting.isDebug) {
            Log.d(TAG_PREFIX + tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (AppSetting.isDebug) {
            Log.e(TAG_PREFIX + tag, msg);
        }
    }

    public static void f(String tag, String msg) {
        OutputStream outputStream;
        byte[] buf = ("TAG:" + tag + " MSG:" + msg).getBytes();
        try {
            outputStream = new FileOutputStream(FileHelper.getExtFilesDir() + File.separator + MeConstant.LOG_FILE);
            outputStream.write(buf, 0 , buf.length);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
