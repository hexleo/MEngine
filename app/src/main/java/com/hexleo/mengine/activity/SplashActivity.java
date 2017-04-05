package com.hexleo.mengine.activity;

import android.os.Bundle;

import com.hexleo.mengine.engine.MEngine;
import com.hexleo.mengine.R;

/**
 * 闪屏Activity 完成初始化工作才进行跳转
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MEngine.initialize(new MEngine.InitCallBack() {
            @Override
            public void onConfigReady() {

            }

            @Override
            public void onFinish() {
                TabHostActivity.create(SplashActivity.this);
                finish();
            }
        });
    }
}
