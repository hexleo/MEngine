package com.hexleo.mengine.engine.config.json;

import java.util.List;

/**
 * Created by hexleo on 2017/4/3.
 */

public class JsonPageConfig extends Json{

    public List<JsonNavPageConfig> nav;
    public JsonSplashPageConfig splash;

    public static class JsonNavPageConfig extends Json {
        public String bundleName;
        public String navIcon;
        public String navName;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("bundleName=" + bundleName);
            sb.append(",navIcon=" + navIcon);
            sb.append(",navName=" + navName);
            return sb.toString();
        }
    }

    public static class JsonSplashPageConfig extends Json {
        public String splashIcon;
        public String splashBgColor;
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("splashIcon=" + splashIcon);
            sb.append(",splashBgColor=" + splashBgColor);
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("nav=");
        if (nav != null && nav.size() > 0) {
            for (JsonNavPageConfig navPage: nav) {
                sb.append(navPage.toString() + ",");
            }
        }
        return sb.toString();
    }
}
