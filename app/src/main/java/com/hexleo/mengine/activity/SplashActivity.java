package com.hexleo.mengine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hexleo.mengine.engine.MEngine;
import com.hexleo.mengine.R;
import com.hexleo.mengine.application.BaseApplication;
import com.hexleo.mengine.engine.constant.MeConstant;

/**
 * 闪屏Activity 完成初始化工作才进行跳转
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MEngine.initialize(BaseApplication.getBaseApplication(), new MEngine.InitFinishedCallBack() {
            @Override
            public void onFinish() {
                // WebViewActivity.create(SplashActivity.this, "bundle1", null);
                TabHostActivity.create(SplashActivity.this);
                finish();
            }
        });
    }
}
