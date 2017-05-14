package com.hexleo.mengine.engine.jscore.function.appfun;

import com.google.gson.Gson;
import com.hexleo.mengine.activity.WebViewActivity;
import com.hexleo.mengine.engine.MEngineBundle;
import com.hexleo.mengine.engine.MEngineManager;
import com.hexleo.mengine.engine.config.json.Json;
import com.hexleo.mengine.engine.jscore.function.JsContextFunction;
import com.hexleo.mengine.engine.jscore.function.webviewfun.CommonJWF;
import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.util.MLog;
import com.hexleo.mengine.util.ThreadManager;

import org.liquidplayer.webkit.javascriptcore.JSFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * app.js中可以调用的公共方法
 * Created by hexleo on 2017/3/13.
 */

public class CommonJCF extends JsContextFunction {
    private static final String TAG = "CommonJCF";

    // app.js 打印log日志
    private static final String FUNC_MLOG = "MLog";
    // app.js 被启动时调用并传入参数（页面跳转时传入参数）
    private static final String FUNC_INIT = "init";
    // app.js 页面退出时调用
    private static final String FUNC_FINISH = "finish";
    // 全局内存变量读取与写入
    private static final String FUNC_G_VAR_WRITE = "globalVarWrite";
    private static final String FUNC_G_VAR_READ = "globalVarRead";
    // 管道写  app.js主动调用向WebView写入信息
    private static final String FUNC_PIPE_WRITE = "sendToWebView";
    // 管道读  app.js注册实现此方法，读取WevView的返回
    private static final String FUNC_PIPE_READ = "receiveFromWebView";
    // 跳转新的页面
    private static final String FUNC_JUMP_TO = "jumpTo";
    // 回调统一调用接口
    private static final String FUNC_CALLBACK_LISTENER = "callBackListener";

    // 被调用 启动被调用
    public static final String ACTION_INIT = "init";
    // 被调用 页面结束时调用
    public static final String ACTION_FINISH = "finish";

    // 被调用 管道度
    public static final String ACTION_PIPE_READ = "pipe";
    // 被调用 回调
    public static final String ACTION_CALLBACK = "callback";


    public static String getFuncName() {
        return "CommonJCF";
    }

    @Override
    protected void initFun() {
        initJsContextFun();
    }

    @Override
    public boolean canHandleCall(String action, String data) {
        boolean canHandle = true;
        switch (action) {
            case ACTION_INIT:
                actionInitApp(data);
                break;
            case ACTION_FINISH:
                actionFinishApp();
                break;
            case ACTION_PIPE_READ:
                actionPipeRead(data);
                break;
            case ACTION_CALLBACK:
                actionCallBack(data);
                break;
            default:
                canHandle = false;
                break;
        }
        return canHandle;
    }

    /**
     * 初始化app.js相关方法
     */
    private void initJsContextFun() {
        registerMLog();
        registerPipeWrite();
        registerJumpTo();
        registerGlobalVar();
    }
    /**
     * app.js 启动时传入的数据
     * @param param
     */
    public void actionInitApp(final String param) {
        ThreadManager.appJsPost(new Runnable() {
            @Override
            public void run() {
                mJsBridge.getJsContext().runScript(genJsCall(FUNC_INIT, param));
            }
        });
    }

    /**
     * app.js 页面关闭时调用
     */
    public void actionFinishApp() {
        ThreadManager.appJsPost(new Runnable() {
            @Override
            public void run() {
                mJsBridge.getJsContext().runScript(genJsCall(FUNC_FINISH));
            }
        });
    }

    private void registerMLog() {
        JSFunction fun = new JSFunction(mJsBridge.getJsContext(), FUNC_MLOG) {
            public void MLog(String data) {
                MLog.d(TAG, data);
            }
        };
        mJsBridge.getJsContext().property(FUNC_MLOG, fun);
    }

    /**
     * app.js向WebView读取管道写入数据
     */
    private void registerPipeWrite() {
        JSFunction fun = new JSFunction(mJsBridge.getJsContext(), FUNC_PIPE_WRITE) {
            public void sendToWebView(String data) {
                MLog.d(TAG, FUNC_PIPE_WRITE);
                MeWebView meWebView = mJsBridge.getMeWebView();
                if (meWebView != null) {
                    meWebView.callJs(genJsCall(CommonJWF.FUNC_PIPE_FRAME_APPJS,data));
                }
            }
        };
        mJsBridge.getJsContext().property(FUNC_PIPE_WRITE, fun);
    }

    private void registerJumpTo() {
        JSFunction fun = new JSFunction(mJsBridge.getJsContext(), FUNC_JUMP_TO) {
            public void jumpTo(String bundleName, String data) {
                MLog.d(TAG, FUNC_JUMP_TO + " " + bundleName);
                MEngineBundle bundle = MEngineManager.getInstance().getBundle(bundleName);
                if (bundle != null && !bundle.isUsed()) {
                    WebViewActivity.create(mJsBridge.getMeBundle().getActivity(), bundleName, data);
                } else {
                    MLog.d(TAG, FUNC_JUMP_TO + "bundle is null OR bundle is used NAME=" + bundleName);
                }
            }
        };
        mJsBridge.getJsContext().property(FUNC_JUMP_TO, fun);
    }

    private void registerGlobalVar() {
        JSFunction fun = new JSFunction(mJsBridge.getJsContext(), FUNC_G_VAR_WRITE) {
            public void globalVarWrite(String key, String value) {
                MEngineBundle.putGlobalVar(key, value);
            }
        };
        mJsBridge.getJsContext().property(FUNC_G_VAR_WRITE, fun);
        fun = new JSFunction(mJsBridge.getJsContext(), FUNC_G_VAR_READ) {
            public String globalVarRead(String key) {
                return MEngineBundle.getGlobalVar(key);
            }
        };
        mJsBridge.getJsContext().property(FUNC_G_VAR_READ, fun);
    }

    /**
     * WebView传输过来的数据
     * @param data
     */
    public void actionPipeRead(final String data) {
        ThreadManager.appJsPost(new Runnable() {
            @Override
            public void run() {
                mJsBridge.getJsContext().runScript(genJsCall(FUNC_PIPE_READ, data));
            }
        });
    }

    /**
     * app.js的回调调用
     * @param data
     */
    public void actionCallBack(final String data) {
        ThreadManager.appJsPost(new Runnable() {
            @Override
            public void run() {
                CommonParamJson paramJson = new Gson().fromJson(data, CommonParamJson.class);
                mJsBridge.getJsContext().runScript(genJsCall(FUNC_CALLBACK_LISTENER, paramJson.params));
            }
        });
    }

    public static class CommonParamJson extends Json {
        List<String> params;
    }

}
