package com.hexleo.mengine.engine;

import com.hexleo.mengine.engine.config.MeBundleConfig;
import com.hexleo.mengine.engine.webview.MeWebView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MEngineBundle的池子，必须控制池子大小防止WebView与JSCore把内存撑爆
 * Created by hexleo on 2017/1/18.
 */

public class MEnginePool {

    private Map<String, MEngineBundle> mMeBundlePool = new HashMap<>();

    /**
     * 通过配置文件初始化
     * @param bundleList
     */
    public MEnginePool(List<MeBundleConfig> bundleList) {
        for (MeBundleConfig bundleConfig : bundleList) {
            mMeBundlePool.put(bundleConfig.bundleName, MEngineBundle.newInstance(bundleConfig));
        }
    }

    public void getMeWebView(String bundleName, MeWebView.MeWebViewListener listener) {
        if (mMeBundlePool.containsKey(bundleName)) {
            mMeBundlePool.get(bundleName).getWebView(listener);
            return;
        }
        if (listener != null) {
            listener.OnWebViewReady(null);
        }
    }

    public MEngineBundle getBundle(String bundleName) {
        return mMeBundlePool.get(bundleName);
    }
}
