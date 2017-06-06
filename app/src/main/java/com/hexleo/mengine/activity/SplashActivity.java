package com.hexleo.mengine.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hexleo.mengine.engine.MEngine;
import com.hexleo.mengine.R;
import com.hexleo.mengine.engine.MEngineBundle;
import com.hexleo.mengine.engine.config.MEngineConfig;
import com.hexleo.mengine.engine.config.MeBundleConfig;
import com.hexleo.mengine.engine.config.MePageConfig;
import com.hexleo.mengine.engine.constant.MeConstant;
import com.hexleo.mengine.util.FileHelper;
import com.hexleo.mengine.util.ThreadManager;

import java.util.List;

/**
 * 闪屏Activity 完成初始化工作才进行跳转
 */
public class SplashActivity extends BaseActivity {

    private View root;
    private ImageView icon;
    private FrameLayout preInitWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MEngine.getInstance().isInited()) {
            TabHostActivity.create(SplashActivity.this);
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        root = findViewById(R.id.root);
        icon = (ImageView) findViewById(R.id.icon);
        preInitWebView = (FrameLayout) findViewById(R.id.splash_content);
        // 初始化逻辑应该转移到Application中，这里只是暂时的
        MEngine.initialize(new MEngine.InitCallBack() {
            @Override
            public void onConfigReady() {
                MePageConfig.SplashPage splashPage = MEngineConfig.getInstance().getPageConfig().getSplashPage();
                final Drawable iconDrawable = FileHelper.getAssetResDrawable(MeConstant.RES_PATH + splashPage.splashIcon, SplashActivity.this);
                final int bgColor = splashPage.splashBgColor;
                ThreadManager.mainPost(new Runnable() {
                    @Override
                    public void run() {
                        root.setBackgroundColor(bgColor);
                        icon.setImageDrawable(iconDrawable);
                    }
                });
            }

            @Override
            public void onFinish() {
                ThreadManager.mainPost(new Runnable() {
                    @Override
                    public void run() {
                        final List<MePageConfig.NavPage> navs =  MEngineConfig.getInstance().getPageConfig().getNavPages();
                        if (navs == null) {
                            return;
                        }
                        List<MeBundleConfig> listBundle = MEngineConfig.getInstance().getBundleConfigs();
                        for (MeBundleConfig config : listBundle) {
                            MEngineBundle meBundle = MEngine.getInstance().getBundle(config.bundleName);
                            if (meBundle != null && !config.lazyInit) {
                                preInitWebView.addView(meBundle.getWebView());
                            }
                        }
                        preInitWebView.invalidate();
                        // 通过发送消息的方式为WebView初始化展示预留时间
                        ThreadManager.mainPostDelay(new Runnable() {
                            @Override
                            public void run() {
                                preInitWebView.removeAllViews();
                                if (navs.size() == 1) {
                                    WebViewActivity.create(SplashActivity.this, navs.get(0).bundleName, false, "");
                                } else {
                                    TabHostActivity.create(SplashActivity.this);
                                }
                                finish();
                            }
                        }, 1000); // 留 1s 的缓冲绘制时间
                    }
                });
            }
        });
    }
}
