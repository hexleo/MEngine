package com.hexleo.mengine.engine.jscore;

import android.os.Looper;

import com.hexleo.mengine.engine.exception.NotWorkThread;
import com.hexleo.mengine.util.MLog;

import org.liquidplayer.webkit.javascriptcore.JSContext;

/**
 * Created by hexleo on 2017/3/16.
 */

public class MeJsContext extends JSContext {

    public void runScript(String script) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new NotWorkThread();
        }
        try {
            evaluateScript(script);
        } catch (Throwable e) {
            MLog.e("MeJsContext" , "runScript exception\n" + e.toString());
        }
    }
}
