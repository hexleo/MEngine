package com.hexleo.mengine.engine.webview;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hexleo.mengine.engine.MEngineManager;

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

    /**
     * 页面加载完成
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        MEngineManager.getInstance().notifyWebViewReady();
        super.onPageFinished(view, url);
    }
}
