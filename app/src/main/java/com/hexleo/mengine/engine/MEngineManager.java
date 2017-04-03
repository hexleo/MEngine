package com.hexleo.mengine.engine;

import android.app.Application;

import com.hexleo.mengine.application.BaseApplication;
import com.hexleo.mengine.engine.config.MEngineConfig;
import com.hexleo.mengine.engine.config.MeBundleConfig;
import com.hexleo.mengine.engine.webview.MeWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hexleowang on 2017/1/18.
 */

public class MEngineManager {
    private MEnginePool mEnginePool;

    // 在线程中初始化
    public MEngineManager(BaseApplication app) {
        // 获取配置文件
        MEngineConfig.getInstance().paseConfigJson(app);
        mEnginePool = new MEnginePool(MEngineConfig.getInstance().getBundleConfigs());
    }

    public void getWebView(String name, MeWebView.MeWebViewListener listener) {
        mEnginePool.getMeWebView(name, listener);
    }

    public MEngineBundle getBundle(String bundleName) {
        return mEnginePool.getBundle(bundleName);
    }

}
