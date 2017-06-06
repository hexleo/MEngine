package com.hexleo.mengine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.hexleo.mengine.R;
import com.hexleo.mengine.engine.MEngine;
import com.hexleo.mengine.engine.MEngineBundle;
import com.hexleo.mengine.engine.constant.MeConstant;
import com.hexleo.mengine.fragment.MeWebViewFragment;

/**
 * Created by hexleo on 2017/1/18.
 */

public class WebViewActivity extends BaseActivity {

    public static void create(Context context, String bundleName, String param) {
        create(context, bundleName, true, param);
    }

    public static void create(Context context, String bundleName, boolean needNavBar, String param) {
        if (context != null && MEngine.getInstance().getBundle(bundleName) != null) {
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(MeConstant.INTENT_PARAM_BUNDLE, bundleName);
            intent.putExtra(MeConstant.INTENT_PARAM_DATA, param);
            intent.putExtra(MeConstant.INTENT_PARAM_NEED_NAVBAR, needNavBar);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        if (savedInstanceState == null) {
            String bundleName = getIntent().getStringExtra(MeConstant.INTENT_PARAM_BUNDLE);
            MEngineBundle meBundle = MEngine.getInstance().getBundle(bundleName);
            if (meBundle != null) {
                MeWebViewFragment fragment = meBundle.getFragment();
                fragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
            }
        }
    }

}
