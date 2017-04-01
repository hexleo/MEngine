package com.hexleo.mengine.util;

import android.util.Log;

import com.hexleo.mengine.application.AppSetting;

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

}
