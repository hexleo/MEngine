package com.hexleo.mengine.engine.bridge;

import android.text.TextUtils;

import com.hexleo.mengine.application.AppSetting;
import com.hexleo.mengine.engine.MEngineBundle;
import com.hexleo.mengine.engine.jscore.MeJsContext;
import com.hexleo.mengine.engine.jscore.function.JsConstant;
import com.hexleo.mengine.engine.jscore.function.JsFunction;
import com.hexleo.mengine.engine.jscore.function.JsFunctionConfig;
import com.hexleo.mengine.engine.jscore.function.JsFunctionFactory;
import com.hexleo.mengine.engine.jscore.function.JsFunctionInfo;
import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.util.MLog;

import java.util.HashMap;
import java.util.Map;

/**
 * JsCore与WebView通信的桥梁
 * Created by hexleo on 2017/3/13.
 */

public class MeJsBridge {
    private static final String TAG = "MeJsBridge";

    private MeJsContext mJsContext;
    private MeWebView mMeWebView;
    private MEngineBundle mMeBundle; // 与其相关联的bundle
    private Map<String, JsFunction> mJsContextFunc;
    private Map<String, JsFunction> mJsWebViewFunc;

    public MeJsBridge(MEngineBundle meBundle) {
        mMeBundle = meBundle;
        mJsContextFunc = new HashMap<>();
        mJsWebViewFunc = new HashMap<>();
    }

    public void initJsContext(MeJsContext jsContext) {
        mJsContext = jsContext;
        createJsFunction(mJsContextFunc, JsFunctionConfig.jsContextFuncArray);
    }

    /**
     * WebView需要异步初始化
     * @param webView
     */
    public void initWebView(MeWebView webView) {
        mMeWebView = webView;
        createJsFunction(mJsWebViewFunc, JsFunctionConfig.jsWebViewFuncArray);
    }

    public MeJsContext getJsContext() {
        return mJsContext;
    }

    public MeWebView getMeWebView() {
        return mMeWebView;
    }

    public MEngineBundle getMeBundle() {
        return mMeBundle;
    }

    private void createJsFunction(Map<String, JsFunction> funcMap, JsFunctionInfo[] infos) {
        JsFunction jsFunction;
        for (JsFunctionInfo info : infos) {
            jsFunction = JsFunctionFactory.create(info);
            jsFunction.init(this);
            funcMap.put(info.jsFuncName, jsFunction);
        }
    }

    /**
     * 处理WebView的页面请求
     * 格式：function://action?[data]
     * function 方法名称
     * action 方法执行的动作
     * data具体的数据
     * @param msg
     */
    public void handleWebViewRequest(String msg) {
        if (AppSetting.isDebug) {
            MLog.d(TAG, "handleWebViewRequest " + msg);
        }
        if (TextUtils.isEmpty(msg)) {
            MLog.e(TAG, "message is empty");
            return;
        }
        int index = msg.indexOf(JsConstant.SCHEME_SEPARATOR);
        if (index <= 0) {
            MLog.e(TAG, "func name length error");
            return;
        }
        String funcName = msg.substring(0, index);
        String paramBody = msg.substring(index + JsConstant.SCHEME_SEPARATOR.length());
        JsFunction jsFunction = mJsWebViewFunc.get(funcName);
        if (jsFunction == null) {
            return;
        }

        index = paramBody.indexOf(JsConstant.PARAM_SEPARATOR);
        if (index <= 0) {
            MLog.e(TAG, "param body length error");
            return;
        }
        String action = paramBody.substring(0, index);
        String data = paramBody.substring(index + JsConstant.PARAM_SEPARATOR.length());
        MLog.d(TAG, "func=" + funcName + " action=" + action);

        jsFunction.canHandleCall(action, data);

    }

    public void callJsContext(String funcName, String action, String data) {
        if (AppSetting.isDebug) {
            MLog.d(TAG, "callJsContext funcName=" + funcName + " action=" + action);
        }
        JsFunction jsFunction = mJsContextFunc.get(funcName);
        if (jsFunction != null) {
            jsFunction.canHandleCall(action, data);
        }
    }

}
