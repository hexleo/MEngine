package com.hexleo.mengine.engine.jscore.function;

import com.hexleo.mengine.engine.bridge.MeJsBridge;
import com.hexleo.mengine.engine.webview.MeWebView;

/**
 * Created by hexleo on 2017/3/13.
 */

public abstract class JsFunction {

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
        String param = "";
        if (params != null && params.length > 0) {
            for (String p : params) {
                param += "\"" + p + "\",";
            }
            param = param.substring(0, param.length()-1); // 删除最后一个逗号
        }
        return funcName + "(" + param + ")";
    }

}
