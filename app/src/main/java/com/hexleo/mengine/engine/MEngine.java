package com.hexleo.mengine.engine;

import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.util.ThreadManager;

/**
 * Created by hexleo on 2017/1/18.
 */

public class MEngine {
    public static MEngine sInstance;

    private MEngine() {
    }

    public static MEngine getInstance() {
        if (sInstance == null) {
            synchronized (MEngine.class) {
                if (sInstance == null) {
                    sInstance = new MEngine();
                }
            }
        }
        return sInstance;
    }


    public static void initialize(final InitCallBack callBack) {
        ThreadManager.post(new Runnable() {
            @Override
            public void run() {
                getInstance().init(callBack);
            }
        });
    }

    private void init(InitCallBack initCallBack) {
        MEngineManager.getInstance().init(initCallBack);
    }

    public void getWebView(String bundleName, MeWebView.MeWebViewListener listener) {
        MEngineManager.getInstance().getWebView(bundleName, listener);
    }

    public MEngineBundle getBundle(String bundleName) {
        return MEngineManager.getInstance().getBundle(bundleName);
    }


    public interface InitCallBack {
        void onConfigReady();
        void onFinish();
    }

}
