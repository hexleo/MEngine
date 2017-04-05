package com.hexleo.mengine.engine.config;

import android.graphics.drawable.Drawable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hexleo.mengine.application.AppSetting;
import com.hexleo.mengine.application.BaseApplication;
import com.hexleo.mengine.engine.config.json.JsonBundleConfig;
import com.hexleo.mengine.engine.config.json.JsonConfig;
import com.hexleo.mengine.engine.config.json.JsonGlobalConfig;
import com.hexleo.mengine.engine.config.json.JsonPageConfig;
import com.hexleo.mengine.engine.constant.MeConstant;
import com.hexleo.mengine.util.FileHelper;
import com.hexleo.mengine.util.MLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 总体配置
 * Created by hexleo on 2017/4/3.
 */

public class MEngineConfig {
    public static final String TAG = "MEngineConfig";

    private static MEngineConfig sInstance;

    private MeGlobalConfig mGlobalConfig;
    private MePageConfig mPageConfig;
    private List<MeBundleConfig> mBundleConfigs;

    private MEngineConfig() {
        mGlobalConfig = new MeGlobalConfig();
        mPageConfig = new MePageConfig();
        mBundleConfigs = new ArrayList<>();
    }

    public static MEngineConfig getInstance() {
        if (sInstance == null) {
            synchronized (MEngineConfig.class) {
                if (sInstance == null) {
                    sInstance = new MEngineConfig();
                }
            }
        }
        return sInstance;
    }

    public MeGlobalConfig getGlobalConfig() {
        return mGlobalConfig;
    }

    public MePageConfig getPageConfig() {
        return mPageConfig;
    }

    public List<MeBundleConfig> getBundleConfigs() {
        return mBundleConfigs;
    }

    public void parseConfigJson(BaseApplication app) {
        String configJson = FileHelper.getAssetFileContext(MeConstant.CONFIG, app);
        JsonConfig config = new Gson().fromJson(configJson, JsonConfig.class);
        mPageConfig.init(config.pageConfig);
        mGlobalConfig.init(config.globalConfig);
        if (config.bundleConfig != null && config.bundleConfig.size() > 0) {
            for (JsonBundleConfig bundleConfig : config.bundleConfig) {
                mBundleConfigs.add(new MeBundleConfig(bundleConfig));
            }
        }
        MLog.d(TAG, "ConfigInit=" + config.toString());
    }

    public void testGenConfigJson() {
        if (AppSetting.isDebug) {
            JsonConfig jsonConfig = new JsonConfig();
            // TODO pageConfig
            jsonConfig.pageConfig = new JsonPageConfig();
            jsonConfig.pageConfig.nav = new ArrayList<>();
            JsonPageConfig.JsonNavPageConfig nav = new JsonPageConfig.JsonNavPageConfig();
            nav.bundleName = "bundle1";
            nav.navIcon = "icon.png";
            jsonConfig.pageConfig.nav.add(nav);
            nav = new JsonPageConfig.JsonNavPageConfig();
            nav.bundleName = "bundle2";
            nav.navIcon = "icon.png";
            jsonConfig.pageConfig.nav.add(nav);

            // TODO globalConfig
            jsonConfig.globalConfig = new JsonGlobalConfig();
            jsonConfig.globalConfig.titleColor = "#00ff00";
            jsonConfig.globalConfig.refreshColor = "#0000ff";

            // TODO bundleConfig
            jsonConfig.bundleConfig = new ArrayList<>();
            JsonBundleConfig bundle = new JsonBundleConfig();
            bundle.bundleName = "bundle1";
            bundle.title = "bundle1_title";
            bundle.lazyInit = "false";
            bundle.enableRefresh = "false";
            jsonConfig.bundleConfig.add(bundle);
            bundle = new JsonBundleConfig();
            bundle.bundleName = "bundle2";
            bundle.title = "bundle2_title";
            bundle.lazyInit = "false";
            bundle.enableRefresh = "true";
            jsonConfig.bundleConfig.add(bundle);

            String json = new Gson().toJson(jsonConfig);
            MLog.d(TAG, "json=" + json);
        }
    }

}
