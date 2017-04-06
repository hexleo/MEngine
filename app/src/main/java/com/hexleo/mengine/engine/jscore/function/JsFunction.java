package com.hexleo.mengine.engine.jscore.function;

import com.hexleo.mengine.engine.bridge.MeJsBridge;
import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.util.MLog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hexleo on 2017/3/13.
 */

public abstract class JsFunction {
    public static final String TAG = "JsFunction";

    protected MeJsBridge mJsBridge;

    protected abstract void initFun();

    /**
     * 处理协议调用
     * @param action
     * @param data
     * @return true 接受处理 false 不处理
     */
    public abstract boolean canHandleCall(String action, String data);


    public void init(MeJsBridge jsBridge) {
        mJsBridge = jsBridge;
        initFun();
    }

    public String genJsCall(String funcName, String... params) {
        List<String> list = null;
        if (params != null && params.length > 0) {
            list = new ArrayList<>();
            for (String p : params) {
                list.add(p);
            }
        }
        return genJsCall(funcName, list);
    }

    public String genJsCall(String funcName, List<String> params) {
        String param = "";
        if (params != null && params.size() > 0) {
            for (String p : params) {
                try {
                    p = URLEncoder.encode(p, "UTF-8")
                            .replaceAll("\\+", "%20")
                            .replaceAll("%0A", "%20");
                } catch (UnsupportedEncodingException e) {
                    MLog.e(TAG, e.getMessage());
                }
                param += "\'" + p + "\',";
            }
            param = param.substring(0, param.length()-1); // 删除最后一个逗号
        }
        return funcName + "(" + param + ")";
    }

}
