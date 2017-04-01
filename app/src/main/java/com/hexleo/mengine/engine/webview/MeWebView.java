package com.hexleo.mengine.engine.webview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;

/**
 * Created by hexleo on 2017/1/18.
 */

public class MeWebView extends WebView {
    private static final String TAG = "MeWebView";

    private boolean onTop; // 已经滚动到顶部
    private MeWebViewListener listener;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public MeWebView(Context context) {
        super(context);
        onTop = true;
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        boolean top = scrollY == 0;
        if (top != onTop) {
            onTop = top;
            if (listener != null) {
                listener.OnTop(onTop);
            }
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    public void callJs(final String script) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                loadUrl("javascript:" + script);
            }
        });
    }

    public void finishRefresh() {
        if (listener != null) {
            listener.onFinishRefresh();
        }
    }

    public void setListener(MeWebViewListener listener) {
        this.listener = listener;
    }

    public interface MeWebViewListener {
        // WebView初始准备好
        void OnWebViewReady(MeWebView meWebView);
        // 是否在顶部回调
        void OnTop(boolean onTop);
        // 结束刷新回调
        void onFinishRefresh();
    }
}
