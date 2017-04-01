package com.hexleo.mengine.engine;

import android.app.Application;

import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.fragment.MeWebViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hexleowang on 2017/1/18.
 */

public class MEngineManager {
    private MEnginePool mEnginePool;

    // 在线程中初始化
    public MEngineManager(Application app) {

        // 获取配置文件
        List<MeBundleConfig> list = new ArrayList<>();
        list.add(new MeBundleConfig("bundle1", false, false, 0xff0000ff));
        list.add(new MeBundleConfig("bundle2", false, true, 0xff0000ff));
        mEnginePool = new MEnginePool(list);
    }

    public void getWebView(String name, MeWebView.MeWebViewListener listener) {
        mEnginePool.getMeWebView(name, listener);
    }

    public MEngineBundle getBundle(String bundleName) {
        return mEnginePool.getBundle(bundleName);
    }

}
