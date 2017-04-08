package com.hexleo.mengine.engine.jscore.function.webviewfun;

import com.hexleo.mengine.engine.jscore.function.JsConstant;
import com.hexleo.mengine.engine.jscore.function.JsWebViewFunction;
import com.hexleo.mengine.engine.jscore.function.appfun.CommonJCF;
import com.hexleo.mengine.util.MLog;

/**
 * 给WebView提供接口能力
 * Created by hexleo on 2017/3/20.
 */

public class CommonJWF extends JsWebViewFunction {

    private static final String FUNC_MLOG = "MLog";
    // function name
    public static String FUNC_NAME = "CommonJWF";
    // 向app.js发送数据
    private static final String FUNC_PIPE_TO_APPJS = "sendToApp";
    // 监听app.js传输过来的数据
    public static final String FUNC_PIPE_FRAME_APPJS = "receiveFromApp";

    // 对应执行的动作
    public static final String ACTION_MLOG = "mlog";
    public static final String ACTION_PIPE = "pipe";


    public static final String SCHEME_MLOG = FUNC_NAME + JsConstant.SCHEME_SEPARATOR + ACTION_MLOG + JsConstant.PARAM_SEPARATOR;
    public static final String SCHEME_PIPE = FUNC_NAME + JsConstant.SCHEME_SEPARATOR + ACTION_PIPE + JsConstant.PARAM_SEPARATOR;

    public static String getFuncName() {
        return FUNC_NAME;
    }

    @Override
    protected void initFun() {
        registerMLog();
        registerPipeWrite();
    }

    @Override
    public boolean canHandleCall(String action, String data) {
        boolean canHandle = true;
        switch (action) {
            case ACTION_MLOG:
                MLog.d("WebViewLog", data);
                break;
            case ACTION_PIPE:
                mJsBridge.callJsContext(CommonJCF.getFuncName(), CommonJCF.ACTION_PIPE_READ, data);
                break;
            default:
                canHandle = false;
                break;
        }
        return canHandle;
    }

    private void registerMLog() {
        mJsBridge.getMeWebView().callJs(FUNC_MLOG + " = function(data){console.log(\""+ SCHEME_MLOG +"\" + data);}");
    }

    private void registerPipeWrite() {
        mJsBridge.getMeWebView().callJs(FUNC_PIPE_TO_APPJS + " = function(data){console.log(\"" + SCHEME_PIPE + "\" + data);}");
    }

}
