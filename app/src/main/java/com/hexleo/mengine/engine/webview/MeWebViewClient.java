package com.hexleo.mengine.engine.webview;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by hexleo on 2017/3/13.
 */

public class MeWebViewClient extends WebViewClient {
    /**
     * html内请求资源
     * @param view
     * @param request
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }
}
