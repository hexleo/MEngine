package com.hexleo.mengine.engine.jscore.function.appfun;

import com.hexleo.mengine.engine.jscore.function.JsContextFunction;
import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.util.MLog;
import com.hexleo.mengine.util.ThreadManager;

import org.liquidplayer.webkit.javascriptcore.JSFunction;

/**
 * 刷新控件app.js回调
 * Created by hexleo on 2017/3/21.
 */

public class RefreshJCF extends JsContextFunction {
    private static final String TAG = "RefreshJCE";

    // 开始刷新
    private static final String FUNC_ON_REFRESH = "onRefresh";
    // 结束刷新
    private static final String FUNC_FINISH_REFRESH = "finishRefresh";

    public static final String ACTION_ON_REFRESH = "actionOnRefresh";

    public static String getFuncName() {
        return "RefreshJCF";
    }

    @Override
    protected void initFun() {
        registerFinishRefresh();
    }

    @Override
    public boolean canHandleCall(String action, String data) {
        boolean canHandle = true;
        switch (action) {
            case ACTION_ON_REFRESH:
                actionOnRefresh();
                break;
            default:
                canHandle = false;
                break;
        }
        return canHandle;
    }

    /**
     * 通知app.js 刷新开始
     */
    private void actionOnRefresh() {
        ThreadManager.appJsPost(new Runnable() {
            @Override
            public void run() {
                MLog.d(TAG, "actionOnRefresh");
                mJsBridge.getJsContext().runScript(genJsCall(FUNC_ON_REFRESH));
            }
        });
    }

    /**
     * 关闭刷新效果
     */
    private void registerFinishRefresh() {
        JSFunction fun = new JSFunction(mJsBridge.getJsContext(), FUNC_FINISH_REFRESH) {
            public void finishRefresh() {
                ThreadManager.mainPost(new Runnable() {
                    @Override
                    public void run() {
                        MLog.d(TAG, "finishRefresh");
                        MeWebView webView = mJsBridge.getMeWebView();
                        if (webView != null) {
                            webView.finishRefresh();
                        }
                    }
                });
            }
        };
        mJsBridge.getJsContext().property(FUNC_FINISH_REFRESH, fun);
    }

}
