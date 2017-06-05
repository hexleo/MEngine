package com.hexleo.mengine.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexleo.mengine.R;
import com.hexleo.mengine.activity.BaseActivity;
import com.hexleo.mengine.activity.WebViewActivity;
import com.hexleo.mengine.engine.MEngine;
import com.hexleo.mengine.engine.MEngineBundle;
import com.hexleo.mengine.engine.config.MEngineConfig;
import com.hexleo.mengine.engine.constant.MeConstant;
import com.hexleo.mengine.engine.jscore.function.appfun.CommonJCF;
import com.hexleo.mengine.engine.jscore.function.appfun.RefreshJCF;
import com.hexleo.mengine.engine.webview.MeWebView;
import com.hexleo.mengine.util.MLog;
import com.hexleo.mengine.util.ThreadManager;
import com.hexleo.mengine.view.MeRefreshView;
import com.hexleo.mengine.widget.NavBarView;


/**
 * Created by hexleo on 2017/2/6.
 */

public class MeWebViewFragment extends BaseFragment implements MeWebView.MeWebViewListener, SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "MeWebViewFragment";

    private boolean enableRefresh;
    private boolean isPageFromWebSite; // 是否为外部页面
    private String mInitParam; // 初始化时传入的参数
    private View mRootView;
    private MeWebView mWebView;
    private MEngineBundle mMeBundle;
    private ViewGroup mViewContent;
    private MeRefreshView mRefreshLayout;
    private NavBarView mNavBarView;
    private boolean isNeedNavBar;

    public MeWebViewFragment() {
        mInitParam = "";
        // 防止转屏后fragment重新创建, onDestroy需要被onDetach取代
        setRetainInstance(true);
    }

    public void setMeBundle(MEngineBundle meBundle) {
        mMeBundle = meBundle;
    }

    public void setInitParam(String initParam) {
        this.mInitParam = initParam;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        if (mMeBundle != null) {
            mMeBundle.startUsing();
        }
        if (savedInstanceState == null && mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_webview, container, false);
            initView(mRootView);
        } else if (mRootView != null && mMeBundle != null) {
            // 已经初始化过，只需要重设参数即可
            mMeBundle.getJsBridge().callJsContext(CommonJCF.getFuncName(), CommonJCF.ACTION_INIT, mInitParam);
        }
        return mRootView;
    }

    private void initData() {
        Bundle bundle = getArguments();
        String bundleName = bundle.getString(MeConstant.INTENT_PARAM_BUNDLE);
        String param = bundle.getString(MeConstant.INTENT_PARAM_DATA);
        param = param == null ? "" : param;
        isNeedNavBar = bundle.getBoolean(MeConstant.INTENT_PARAM_NEED_NAVBAR, true);
        setInitParam(param);
        if (mMeBundle != null) {
            mMeBundle.setActivity((BaseActivity) getActivity());
        } else { // 兼容TabHost创建方式
            setMeBundle(MEngine.getInstance().getBundle(bundleName));
        }
        if (mMeBundle != null) {
            mMeBundle.setActivity((BaseActivity) getActivity());
        }
        MLog.d(TAG, "bundleName=" + bundleName);
    }

    private void initView(View view) {
        mNavBarView = (NavBarView) view.findViewById(R.id.header_nav);
        mNavBarView.setBackgroundColor(mMeBundle.getBundleConfig().titleColor);
        mNavBarView.setTitle(mMeBundle.getBundleConfig().title);
        mNavBarView.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        if (!isNeedNavBar) {
            mNavBarView.setVisibility(View.GONE);
        }
        mViewContent = (ViewGroup) view.findViewById(R.id.content);
        mRefreshLayout = (MeRefreshView) view.findViewById(R.id.refresh_layout);
        enableRefresh = mMeBundle.getBundleConfig().enableRefresh;
        isPageFromWebSite = !TextUtils.isEmpty(mMeBundle.getBundleConfig().webUrl);
        mRefreshLayout.setEnabled(enableRefresh || isPageFromWebSite);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(mMeBundle.getBundleConfig().refreshColor);
        MEngine.getInstance().getWebView(mMeBundle.getBundleName(), this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mMeBundle != null) {
            mMeBundle.destroy();
        }
        if (enableRefresh) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void OnWebViewReady(MeWebView meWebView) {
        mWebView = meWebView;
        mWebView.setListener(this);
        mMeBundle.getJsBridge().callJsContext(CommonJCF.getFuncName(), CommonJCF.ACTION_INIT, mInitParam);
        ThreadManager.mainPost(new Runnable() {
            @Override
            public void run() {
                if (mViewContent.getChildCount() == 0) {
                    mViewContent.addView(mWebView);
                }
            }
        });
    }

    @Override
    public void OnTop(boolean onTop) {
        mRefreshLayout.setCanScroll(enableRefresh && onTop);
    }

    @Override
    public void onRefresh() {
        if (isPageFromWebSite) {
            final MeWebView webView = mMeBundle.getWebView();
            if (webView != null) {
                ThreadManager.mainPost(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(mMeBundle.getBundleConfig().webUrl);
                        // 假等待关闭
                        ThreadManager.postDelay(new Runnable() {
                            @Override
                            public void run() {
                                mRefreshLayout.setRefreshing(false);
                            }
                        }, 2000);
                    }
                });
            }
        } else {
            mMeBundle.getJsBridge().callJsContext(RefreshJCF.getFuncName(), RefreshJCF.ACTION_ON_REFRESH, null);
        }
    }

    @Override
    public void onFinishRefresh() {
        if (enableRefresh) {
            mRefreshLayout.setRefreshing(false);
        }
    }
}
