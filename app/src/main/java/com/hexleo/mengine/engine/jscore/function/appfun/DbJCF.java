package com.hexleo.mengine.engine.jscore.function.appfun;

import com.google.gson.Gson;
import com.hexleo.mengine.activity.WebViewActivity;
import com.hexleo.mengine.db.DBManager;
import com.hexleo.mengine.engine.MEngineBundle;
import com.hexleo.mengine.engine.config.json.Json;
import com.hexleo.mengine.engine.jscore.function.JsContextFunction;
import com.hexleo.mengine.engine.jscore.function.webviewfun.CommonJWF;
import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.util.MLog;
import com.hexleo.mengine.util.ThreadManager;

import org.liquidplayer.webkit.javascriptcore.JSFunction;

import java.util.List;

/**
 * app.js db相关模块
 * Created by hexleo on 2017/3/13.
 */

public class DbJCF extends JsContextFunction {
    private static final String TAG = "DbJCF";

    // KeyValue db读取与写入
    private static final String FUNC_KV_WRITE = "keyValueWrite";
    private static final String FUNC_KV_READ = "keyValueRead";


    public static String getFuncName() {
        return "DbJCF";
    }

    @Override
    protected void initFun() {
        registerKeyValueDb();
    }

    @Override
    public boolean canHandleCall(String action, String data) {
        return false;
    }


    private void registerKeyValueDb() {
        JSFunction fun = new JSFunction(mJsBridge.getJsContext(), FUNC_KV_WRITE) {
            public void keyValueWrite(String key, String value) {
                DBManager.getInstance().put(key, value);
            }
        };
        mJsBridge.getJsContext().property(FUNC_KV_WRITE, fun);
        fun = new JSFunction(mJsBridge.getJsContext(), FUNC_KV_READ) {
            public String keyValueRead(String key) {
                return DBManager.getInstance().get(key);
            }
        };
        mJsBridge.getJsContext().property(FUNC_KV_READ, fun);
    }

}
