package com.hexleo.mengine.engine;

import android.app.Application;

import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.fragment.MeWebViewFragment;
import com.hexleo.mengine.util.ThreadManager;

/**
 * Created by hexleo on 2017/1/18.
 */

public class MEngine {
    public static MEngine sInstance;
    private MEngineManager mMEngineManager;

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


    public static void initialize(final Application app, final InitFinishedCallBack callBack) {
        ThreadManager.post(new Runnable() {
            @Override
            public void run() {
                getInstance().init(app);
                callBack.onFinish();
            }
        });
    }

    private void init(Application app) {
        mMEngineManager = new MEngineManager(app);
    }

    public void getWebView(String bundleName, MeWebView.MeWebViewListener listener) {
        mMEngineManager.getWebView(bundleName, listener);
    }

    public MEngineBundle getBundle(String bundleName) {
        return mMEngineManager.getBundle(bundleName);
    }


    public interface InitFinishedCallBack {
        void onFinish();
    }

}
