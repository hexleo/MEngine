package com.hexleo.mengine.engine.config;

import android.graphics.Color;
import android.text.TextUtils;

import com.hexleo.mengine.engine.config.json.JsonBundleConfig;

/**
 * Created by hexleo on 2017/3/21.
 */

public class MeBundleConfig {
    public String bundleName;
    // 标题栏名称
    public String title;
    // 标题栏颜色
    public int titleColor;
    // 是否懒加载
    public boolean lazyInit;
    // 是否需要刷新功能
    public boolean enableRefresh;
    // 刷新按钮的颜色
    public int refreshColor;
    // 直接使用外部页面（预留接口）
    public String webUrl;

    public MeBundleConfig(JsonBundleConfig bundleConfig) {
        init(bundleConfig);
    }

    public void init(JsonBundleConfig bundleConfig) {
        this.bundleName = bundleConfig.bundleName;
        this.title = bundleConfig.title;
        this.titleColor = getColor(bundleConfig.titleColor, MEngineConfig.getInstance().getGlobalConfig().getTitleColor());
        this.lazyInit = getBoolean(bundleConfig.lazyInit, false);
        this.enableRefresh = getBoolean(bundleConfig.enableRefresh, false);
        this.refreshColor = getColor(bundleConfig.refreshColor, MEngineConfig.getInstance().getGlobalConfig().getRefreshColor());
        this.webUrl = bundleConfig.webUrl;
    }

    private int getColor(String colorStr, int defColor) {
        return TextUtils.isEmpty(colorStr)? defColor : Color.parseColor(colorStr);
    }

    private boolean getBoolean(String boolStr, boolean defBool) {
        return TextUtils.isEmpty(boolStr)? defBool : "true".equals(boolStr);
    }
}
