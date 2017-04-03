package com.hexleo.mengine.engine.config;

import android.graphics.Color;

import com.hexleo.mengine.engine.config.json.JsonGlobalConfig;

/**
 * 全局配置
 * Created by hexleo on 2017/4/3.
 */

public class MeGlobalConfig {
    // 默认颜色
    private int titleColor = 0xff00ff00;
    private int refreshColor = 0xff00ff00;
    public void init(JsonGlobalConfig globalConfig) {
        if (globalConfig == null) {
            return;
        }
        this.titleColor = Color.parseColor(globalConfig.titleColor);
        this.refreshColor = Color.parseColor(globalConfig.refreshColor);
    }

    public int getTitleColor() {
        return titleColor;
    }

    public int getRefreshColor() {
        return refreshColor;
    }
}
