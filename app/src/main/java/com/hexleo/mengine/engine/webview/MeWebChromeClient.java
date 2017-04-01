package com.hexleo.mengine.engine.webview;

import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

import com.hexleo.mengine.engine.bridge.MeJsBridge;
import com.hexleo.mengine.util.MLog;

/**
 * Created by hexleo on 2017/3/13.
 */

public class MeWebChromeClient extends WebChromeClient {
    private static final String TAG = "MeWebChromeClient";

    private MeJsBridge mJsBridge;

    public MeWebChromeClient(MeJsBridge jsBridge) {
        this.mJsBridge = jsBridge;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        mJsBridge.handleWebViewRequest(consoleMessage.message());
        return true;
    }
}
