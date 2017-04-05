package com.hexleo.mengine.engine;

import android.os.SystemClock;

import com.hexleo.mengine.application.BaseApplication;
import com.hexleo.mengine.engine.config.MEngineConfig;
import com.hexleo.mengine.engine.config.MeBundleConfig;
import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.util.MLog;
import com.hexleo.mengine.util.ThreadManager;

/**
 * Created by hexleo on 2017/1/18.
 */

public class MEngineManager {
    private static final String TAG = "MEngineManager";

    private static final int MAX_START_TIME = 5000; // 最长等待5秒后必须启动
    private static MEngineManager sInstance;

    private final Object mLockObj = new Object();
    private volatile boolean isTimeToStart;
    private MEnginePool mEnginePool;
    private MEngine.InitCallBack mInitCallBack;
    private volatile int mInitWebViewCount; // 启动时需要初始化的网页数量

    private MEngineManager() {

    }

    public static MEngineManager getInstance() {
        if (sInstance == null) {
            synchronized (MEngine.class) {
                if (sInstance == null) {
                    sInstance = new MEngineManager();
                }
            }
        }
        return sInstance;
    }

    // 在线程中初始化
    public void init(MEngine.InitCallBack initCallBack) {
        mInitCallBack = initCallBack;
        // 获取配置文件
        MEngineConfig.getInstance().parseConfigJson(BaseApplication.getBaseApplication());
        if (mInitCallBack != null) {
            mInitCallBack.onConfigReady();
        }
        prepareInitWebViewCallBack();
        mEnginePool = new MEnginePool(MEngineConfig.getInstance().getBundleConfigs());
    }

    public void getWebView(String name, MeWebView.MeWebViewListener listener) {
        mEnginePool.getMeWebView(name, listener);
    }

    public MEngineBundle getBundle(String bundleName) {
        return mEnginePool.getBundle(bundleName);
    }

    private void prepareInitWebViewCallBack() {
        mInitWebViewCount = 0;
        for (MeBundleConfig bundleConfig : MEngineConfig.getInstance().getBundleConfigs()) {
            if (!bundleConfig.lazyInit) {
                mInitWebViewCount++;
            }
        }
        ThreadManager.post(new Runnable() {
            @Override
            public void run() {
                isTimeToStart = false;
                MLog.d(TAG, "mInitWebViewCount=" + mInitWebViewCount);
                synchronized (mLockObj) {
                    while (mInitWebViewCount != 0 && !isTimeToStart) {
                        try {
                            mLockObj.wait();
                            MLog.d(TAG, "mInitWebViewCount=" + mInitWebViewCount);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                if (mInitCallBack != null) {
                    MLog.d(TAG, "onFinish");
                    mInitCallBack.onFinish();
                }
            }
        });
        ThreadManager.postDelay(new Runnable() {
            @Override
            public void run() {
                isTimeToStart = true;
                notifyWebViewReady();
            }
        }, MAX_START_TIME);
    }

    public void notifyWebViewReady() {
        if (mInitWebViewCount > 0) {
            synchronized (mLockObj) {
                mInitWebViewCount--;
                mLockObj.notifyAll();
            }
        }
    }

}
