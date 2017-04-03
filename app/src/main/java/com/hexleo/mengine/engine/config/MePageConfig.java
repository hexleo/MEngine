package com.hexleo.mengine.engine.config;

import android.graphics.drawable.Drawable;

import com.hexleo.mengine.application.BaseApplication;
import com.hexleo.mengine.engine.config.json.JsonPageConfig;
import com.hexleo.mengine.engine.constant.MeConstant;
import com.hexleo.mengine.util.FileHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面配置
 * Created by hexleo on 2017/4/3.
 */

public class MePageConfig {
    // 首页Page
    private List<NavPage> navPages = new ArrayList<>();

    public void init(JsonPageConfig pageConfig) {
        if (pageConfig == null) {
            return;
        }
        if (pageConfig.nav != null && pageConfig.nav.size() > 0) {
            navPages.clear();
            for (JsonPageConfig.JsonNavPageConfig nav : pageConfig.nav) {
                navPages.add(new NavPage(nav.bundleName, nav.navIcon));
            }
        }
    }

    public List<NavPage> getNavPages() {
        return navPages;
    }

    public static class NavPage {
        public String bundleName;
        public Drawable icon;

        public NavPage(String bundleName, String icon) {
            this.bundleName = bundleName;
            this.icon = FileHelper.getAssetResDrawable(MeConstant.RES_PATH + icon, BaseApplication.getBaseApplication());
        }
    }
}
