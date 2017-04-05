package com.hexleo.mengine.engine.config;

import android.graphics.Color;
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
    private SplashPage splashPage;

    public void init(JsonPageConfig pageConfig) {
        if (pageConfig == null) {
            return;
        }
        if (pageConfig.nav != null && pageConfig.nav.size() > 0) {
            navPages.clear();
            for (JsonPageConfig.JsonNavPageConfig nav : pageConfig.nav) {
                navPages.add(new NavPage(nav.bundleName, nav.navIcon, nav.navName));
            }
        }
        splashPage = new SplashPage(pageConfig.splash.splashIcon, pageConfig.splash.splashBgColor);
    }

    public List<NavPage> getNavPages() {
        return navPages;
    }

    public SplashPage getSplashPage() {
        return splashPage;
    }

    public static class NavPage {
        public String bundleName;
        public Drawable navIcon;
        public String navName;

        public NavPage(String bundleName, String icon, String navName) {
            this.bundleName = bundleName;
            this.navIcon = FileHelper.getAssetResDrawable(MeConstant.RES_PATH + icon, BaseApplication.getBaseApplication());
            this.navName = navName;
        }
    }

    public static class SplashPage {
        public String splashIcon;
        public int splashBgColor;

        public SplashPage(String splashIcon, String splashBgColor) {
            this.splashIcon = splashIcon;
            this.splashBgColor = Color.parseColor(splashBgColor);
        }
    }
}
