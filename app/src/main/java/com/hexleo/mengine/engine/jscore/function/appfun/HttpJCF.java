package com.hexleo.mengine.engine.jscore.function.appfun;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hexleo.mengine.engine.jscore.function.JsContextFunction;
import com.hexleo.mengine.network.HttpListener;
import com.hexleo.mengine.network.HttpManager;
import com.hexleo.mengine.util.MLog;
import com.hexleo.mengine.util.ThreadManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.liquidplayer.webkit.javascriptcore.JSFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * app.js调用http请求
 * Created by hexleo on 2017/3/21.
 */

public class HttpJCF extends JsContextFunction {
    private static final String TAG = "HttpJCF";

    // 发起请求
    private static final String FUNC_REQUEST = "requestNative";

    public static String getFuncName() {
        return "HttpJCF";
    }

    @Override
    protected void initFun() {
        registerFinishRefresh();
    }

    @Override
    public boolean canHandleCall(String action, String data) {
        return false;
    }

    /**
     * 注册请求方法
     */
    private void registerFinishRefresh() {
        JSFunction fun = new JSFunction(mJsBridge.getJsContext(), FUNC_REQUEST) {
            public void requestNative(String method,String url, String param,
                                      String successCallBack, String errorCallBack) {
                final HttpCallBack httpCallBack = new HttpCallBack(successCallBack, errorCallBack);
                if ("GET".equals(method)) {
                    HttpManager.getInstance().requestGet(genGetParams(url,param), httpCallBack);
                } else if ("POST".equals(method)) {
                    HttpManager.getInstance().requestPost(url, genPostParams(param), httpCallBack);
                }
            }
        };
        mJsBridge.getJsContext().property(FUNC_REQUEST, fun);
    }

    private String genGetParams(String url, String paramJson) {
        if (TextUtils.isEmpty(paramJson)) {
            return url;
        }
        StringBuilder urlParam = new StringBuilder(url);
        String tmp;
        if (urlParam.indexOf("?") > 0) {
            tmp = "&";
        } else {
            tmp = "?";
        }
        try {
            JSONObject jsonObject = new JSONObject(paramJson);
            Iterator<String> iterator = jsonObject.keys();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                urlParam.append(tmp + key + "=" + jsonObject.getString(key));
                tmp = "&";
            }
        } catch (JSONException e) {
            MLog.e(TAG, e.getMessage());
        }
        return urlParam.toString();
    }

    private Map<String, String> genPostParams(String paramJson) {
        Map<String, String> params = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(paramJson);
            Iterator<String> iterator = jsonObject.keys();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                params.put(key, jsonObject.getString(key));
            }
        } catch (JSONException e) {
            MLog.e(TAG, e.getMessage());
        }
        return params;
    }

    class HttpCallBack implements HttpListener {

        private String successCallBack;
        private String errorCallBack;

        public HttpCallBack(String successCallBack, String errorCallBack) {
            this.successCallBack = successCallBack;
            this.errorCallBack = errorCallBack;
        }

        @Override
        public void onSuccess(String response) {
            CommonJCF.CommonParamJson paramJson = new CommonJCF.CommonParamJson();
            paramJson.params = new ArrayList<>();
            paramJson.params.add(successCallBack);
            paramJson.params.add(response);
            mJsBridge.callJsContext(CommonJCF.getFuncName(), CommonJCF.ACTION_CALLBACK, new Gson().toJson(paramJson));
        }

        @Override
        public void onError(int errorCode) {
            CommonJCF.CommonParamJson paramJson = new CommonJCF.CommonParamJson();
            paramJson.params = new ArrayList<>();
            paramJson.params.add(errorCallBack);
            paramJson.params.add(errorCode + "");
            mJsBridge.callJsContext(CommonJCF.getFuncName(), CommonJCF.ACTION_CALLBACK, new Gson().toJson(paramJson));
        }
    }

}
