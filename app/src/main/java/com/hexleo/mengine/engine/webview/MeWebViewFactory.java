package com.hexleo.mengine.engine.webview;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.hexleo.mengine.engine.bridge.MeJsBridge;


/**
 * WebView生产工厂
 * Created by hexleo on 2017/3/13.
 */

public class MeWebViewFactory {

    private static MeWebViewFactory mInstance;

    private MeWebViewFactory() {

    }

    public static MeWebViewFactory getInstance() {
        if (mInstance == null) {
            synchronized (MeWebViewFactory.class) {
                if (mInstance == null) {
                    mInstance = new MeWebViewFactory();
                }
            }
        }
        return mInstance;
    }


    public MeWebView create(Context context, MeJsBridge jsBridge) {
        MeWebView meWebView = new MeWebView(context);
        meWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initWebViewSetting(meWebView);
        initWebViewClient(meWebView);
        initWebChromeClient(meWebView, jsBridge);
        return meWebView;
    }

    private void initWebViewSetting(MeWebView meWebView) {
        WebSettings webSettings = meWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private void initWebViewClient(MeWebView meWebView) {
        meWebView.setWebViewClient(new MeWebViewClient());
    }

    private void initWebChromeClient(MeWebView meWebView, MeJsBridge jsBridge) {
        meWebView.setWebChromeClient(new MeWebChromeClient(jsBridge));
    }

}
