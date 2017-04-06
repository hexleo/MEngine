package com.hexleo.mengine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.hexleo.mengine.R;
import com.hexleo.mengine.engine.config.MEngineConfig;
import com.hexleo.mengine.engine.config.MePageConfig;
import com.hexleo.mengine.engine.constant.MeConstant;
import com.hexleo.mengine.fragment.MeWebViewFragment;

import java.util.List;

/**
 * Created by hexleo on 2017/4/4.
 */

public class TabHostActivity extends BaseActivity {
    private FragmentTabHost mTabHost;

    public static void create(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, TabHostActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);
        init();
    }

    private void init() {
        mTabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.content);
        initFragment();
        mTabHost.setCurrentTab(0);
    }

    private void initFragment() {
        Bundle bundle;
        List<MePageConfig.NavPage> navList = MEngineConfig.getInstance().getPageConfig().getNavPages();
        for (MePageConfig.NavPage navPage : navList) {
            bundle = new Bundle();
            bundle.putString(MeConstant.INTENT_PARAM_BUNDLE, navPage.bundleName);
            bundle.putString(MeConstant.INTENT_PARAM_DATA, "");
            bundle.putBoolean(MeConstant.INTENT_PARAM_NEED_NAVBAR, false);
            addFragment(navPage.bundleName, MeWebViewFragment.class, navPage.navIcon, navPage.navName, bundle);
        }
    }

    private void addFragment(String tag, Class<? extends Fragment> clz, Drawable icon, String name, Bundle bundle) {
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tag).setIndicator(getTab(icon, name));
        mTabHost.addTab(tabSpec, clz, bundle);
        mTabHost.setCurrentTab(0);
    }

    private View getTab(Drawable icon, String name) {
        View view = getLayoutInflater().inflate(R.layout.tab_host_item, null);
        ImageView iv = (ImageView) view.findViewById(R.id.icon);
        iv.setImageDrawable(icon);
        TextView tv = (TextView) view.findViewById(R.id.nav_name);
        tv.setText(name);
        return view;
    }
}
