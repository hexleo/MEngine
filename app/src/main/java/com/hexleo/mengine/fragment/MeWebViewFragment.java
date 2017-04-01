package com.hexleo.mengine.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexleo.mengine.R;
import com.hexleo.mengine.engine.MEngine;
import com.hexleo.mengine.engine.MEngineBundle;
import com.hexleo.mengine.engine.jscore.function.appfun.CommonJCF;
import com.hexleo.mengine.engine.jscore.function.appfun.RefreshJCF;
import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.view.MeRefreshView;


/**
 * Created by hexleo on 2017/2/6.
 */

public class MeWebViewFragment extends BaseFragment implements MeWebView.MeWebViewListener, SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "MeWebViewFragment";

    private boolean enableRefresh;
    private String mInitParam; // 初始化时传入的参数
    private View mRootView;
    private MeWebView mWebView;
    private MEngineBundle mMeBundle;
    private ViewGroup mViewContent;
    private MeRefreshView mRefreshLayout;
    private Handler mHandler;

    public MeWebViewFragment() {
        mInitParam = "";
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void setMeBundle(MEngineBundle meBundle) {
        mMeBundle = meBundle;
    }

    public void setInitParam(String initParam) {
        this.mInitParam = initParam;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止转屏后fragment重新创建
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mRootView = inflater.inflate(R.layout.fragment_webview, container, false);
            initView(mRootView);
        }
        return mRootView;
    }

    private void initView(View view) {
        mViewContent = (ViewGroup) view.findViewById(R.id.content);
        mRefreshLayout = (MeRefreshView) view.findViewById(R.id.refresh_layout);
        enableRefresh = mMeBundle.getBundleConfig().enableRefresh;
        mRefreshLayout.setEnabled(enableRefresh);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(mMeBundle.getBundleConfig().refreshColor);
        MEngine.getInstance().getWebView(mMeBundle.getBundleName(), this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewContent.removeAllViews();
        if (mMeBundle != null) {
            mMeBundle.destory();
        }
    }

    @Override
    public void OnWebViewReady(MeWebView meWebView) {
        mWebView = meWebView;
        mWebView.setListener(this);
        mMeBundle.getJsBridge().callJsContext(CommonJCF.getFuncName(), CommonJCF.ACTION_INIT, mInitParam);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mViewContent.addView(mWebView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        });
    }

    @Override
    public void OnTop(boolean onTop) {
        mRefreshLayout.setCanScroll(enableRefresh && onTop);
    }

    @Override
    public void onRefresh() {
        mMeBundle.getJsBridge().callJsContext(RefreshJCF.getFuncName(), RefreshJCF.ACTION_ON_REFRESH, null);
    }

    @Override
    public void onFinishRefresh() {
        if (enableRefresh) {
            mRefreshLayout.setRefreshing(false);
        }
    }
}
