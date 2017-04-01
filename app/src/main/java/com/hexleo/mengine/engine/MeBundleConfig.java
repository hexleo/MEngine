package com.hexleo.mengine.engine;

/**
 * Created by hexleo on 2017/3/21.
 */

public class MeBundleConfig {
    public String bundleName;
    // 是否懒加载
    public boolean lazyInit;
    // 是否需要刷新功能
    public boolean enableRefresh;
    // 刷新按钮的颜色
    public int refreshColor;

    public MeBundleConfig(String bundleName, boolean lazyInit, boolean enableRefresh, int color) {
        this.bundleName = bundleName;
        this.lazyInit = lazyInit;
        this.enableRefresh = enableRefresh;
        this.refreshColor = color;
    }
}
