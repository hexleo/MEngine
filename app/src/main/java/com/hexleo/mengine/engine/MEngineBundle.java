package com.hexleo.mengine.engine;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.hexleo.mengine.activity.BaseActivity;
import com.hexleo.mengine.application.BaseApplication;
import com.hexleo.mengine.engine.bridge.MeJsBridge;
import com.hexleo.mengine.engine.config.MeBundleConfig;
import com.hexleo.mengine.engine.constant.MeConstant;
import com.hexleo.mengine.engine.jscore.MeJsContextFactory;
import com.hexleo.mengine.engine.jscore.MeJsContext;
import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.engine.webview.MeWebViewFactory;
import com.hexleo.mengine.util.FileHelper;
import com.hexleo.mengine.util.MLog;
import com.hexleo.mengine.util.ThreadManager;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by hexleo on 2017/2/7.
 */

public class MEngineBundle {

    private static final String TAG = "MEngineBundle";

    // 整个App共享全局变量
    private static Map<String, String> sGlobalVar = new Hashtable<>();
    // 引擎通用app.js文件
    private static volatile String sMeCommonJsFileCache;
    // 自定义通用js文件
    private static volatile String sCommonJsFileCache;
    private static volatile boolean sIsCommonInited = false;

    private String mBundleName;
    private MeBundleConfig mConfig;
    private String mJsFileCache;
    private String mIndexHtmlPath;
    private boolean isInit; // bundle是否已经初始化

    private MeJsBridge mJsBridge;
    private MeJsContext mJsContext;
    private MeWebView mWebView;
    private WeakReference<BaseActivity> mActivityRef;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private MEngineBundle(MeBundleConfig config) {
        isInit = false;
        mConfig = config;
        mBundleName = config.bundleName;
        mActivityRef = new WeakReference<>(null);
        mIndexHtmlPath = FileHelper.getIndexPath(mBundleName);
        if (!mConfig.lazyInit) {
            initRuntime(null);
        }
    }

    public static MEngineBundle newInstance(MeBundleConfig config) {
        return new MEngineBundle(config);
    }

    public String getBundleName() {
        return mBundleName;
    }

    public MeBundleConfig getBundleConfig() {
        return mConfig;
    }

    public static void putGlobalVar(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            sGlobalVar.put(key, value);
        }
    }

    public static String getGlobalVar(String key) {
        String value = sGlobalVar.get(key);
        return value == null? "" : value;
    }

    public void setActivity(BaseActivity activity) {
        mActivityRef = new WeakReference<>(activity);
    }

    public BaseActivity getActivity() {
        return mActivityRef.get();
    }

    private void initRuntime(MeWebView.MeWebViewListener listener) {
        initJsBridge();
        initJsContext();
        // JsContext必须要在WebView初始化前完成初始化
        initWebView(listener);
        isInit = true;
    }

    private void initJsBridge() {
        // 创建JsBridge 加载公共类
        mJsBridge = new MeJsBridge(this);
    }

    private void initJsContext() {
        // 加载js文件
        if (TextUtils.isEmpty(mJsFileCache)) {
            mJsFileCache = FileHelper.getAppJs(mBundleName, BaseApplication.getBaseApplication());
        }
        loadCommonAppJs();
        // 创建JsContext
        mJsContext = MeJsContextFactory.getInstance().create();
        // JsBridge加载JsContext
        mJsBridge.initJsContext(mJsContext);
        // 加载引擎通用app.js文件
        mJsContext.runScript(sMeCommonJsFileCache);
        // 加载common文件夹下的文件
        mJsContext.runScript(sCommonJsFileCache);
        // 加载app.js文件
        mJsContext.runScript(mJsFileCache);
    }

    private void loadCommonAppJs() {
        if (sIsCommonInited) {
            return;
        }
        synchronized (MEngineBundle.class) {
            if (sIsCommonInited) {
                return;
            }
            try {
                StringBuilder sb = new StringBuilder();
                // 加载引擎通用app.js文件
                for (int id : MeConstant.ME_COMMON_RES) {
                    sb.append(FileHelper.getResRawFileContext(id, BaseApplication.getBaseApplication()));
                }
                sMeCommonJsFileCache = sb.toString();
                // 加载自定义common js文件
                String[] files = BaseApplication.getBaseApplication().getAssets().list(MeConstant.COMMON_PATH);
                if (files != null && files.length > 0) {
                    sb = new StringBuilder();
                    for (String item : files) {
                        MLog.d(TAG, "commonJs=" + item);
                        sb.append(FileHelper.getAssetFileContext(MeConstant.COMMON_PATH + File.separator + item,
                                BaseApplication.getBaseApplication()));
                    }
                    sCommonJsFileCache = sb.toString();
                }

                sIsCommonInited = true;
            } catch (IOException e) {
                MLog.e(TAG, e.getMessage());
            }
        }
    }

    private void initWebView(final MeWebView.MeWebViewListener listener) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mWebView = MeWebViewFactory.getInstance().create(BaseApplication.getBaseApplication(), mJsBridge);
                // 加载index.html文件  不使用loadData是防止部分机型出现乱码问题
                mWebView.loadUrl(mIndexHtmlPath);
                // 文件加载完成后在加载js组件
                mJsBridge.initWebView(mWebView);
                if (listener != null) {
                    listener.OnWebViewReady(mWebView);
                }
            }
        });
    }

    public void getWebView(final MeWebView.MeWebViewListener listener) {
        if (listener == null) {
            return;
        }
        if (mConfig.lazyInit && !isInit) {
            MLog.d(TAG, "getWebView webview lazy init");
            ThreadManager.post(new Runnable() {
                @Override
                public void run() {
                    initRuntime(listener);
                }
            });
        } else {
            MLog.d(TAG, "getWebView webview is ready");
            listener.OnWebViewReady(mWebView);
        }
    }

    public MeJsBridge getJsBridge() {
        return mJsBridge;
    }

    /**
     * 销毁时回调
     */
    public void destory() {
    }

}
