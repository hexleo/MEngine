package com.hexleo.mengine.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hexleo.mengine.engine.MEngine;
import com.hexleo.mengine.R;
import com.hexleo.mengine.engine.config.MEngineConfig;
import com.hexleo.mengine.engine.config.MePageConfig;
import com.hexleo.mengine.engine.constant.MeConstant;
import com.hexleo.mengine.util.FileHelper;
import com.hexleo.mengine.util.ThreadManager;

/**
 * 闪屏Activity 完成初始化工作才进行跳转
 */
public class SplashActivity extends BaseActivity {

    private View root;
    private ImageView icon;

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
                        TabHostActivity.create(SplashActivity.this);
                        finish();
                    }
                });
            }
        });
    }
}
