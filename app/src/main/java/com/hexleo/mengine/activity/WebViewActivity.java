package com.hexleo.mengine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hexleo.mengine.R;
import com.hexleo.mengine.engine.MEngine;
import com.hexleo.mengine.engine.MEngineBundle;
import com.hexleo.mengine.engine.constant.MeConstant;
import com.hexleo.mengine.fragment.MeWebViewFragment;

/**
 * Created by hexleo on 2017/1/18.
 */

public class WebViewActivity extends Activity {

    public static void create(Context context, String bundleName, String param) {
        if (context != null && MEngine.getInstance().getBundle(bundleName) != null) {
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(MeConstant.INTENT_PARAM_BUNDLE, bundleName);
            intent.putExtra(MeConstant.INTENT_PARAM_DATA, param);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Bundle bundle = getIntent().getExtras();
        String bundleName = bundle.getString(MeConstant.INTENT_PARAM_BUNDLE);
        String param = bundle.getString(MeConstant.INTENT_PARAM_DATA);
        param = param == null ? "" : param;
        MEngineBundle meBundle = MEngine.getInstance().getBundle(bundleName);
        meBundle.setActivity(this);
        if (savedInstanceState == null && meBundle != null) {
            MeWebViewFragment fragment = new MeWebViewFragment();
            fragment.setMeBundle(meBundle);
            fragment.setInitParam(param);
            getFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
        }
    }

}
