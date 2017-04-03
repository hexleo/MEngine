package com.hexleo.mengine.engine.config.json;

import java.util.List;

/**
 * 统一配置
 * Created by hexleo on 2017/4/3.
 */

public class JsonConfig extends Json {
    public JsonPageConfig pageConfig;
    public JsonGlobalConfig globalConfig;
    public List<JsonBundleConfig> bundleConfig;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("pageConfig=" + pageConfig.toString());
        sb.append(",globalConfig=" + globalConfig.toString());
        sb.append(",bundleConfig=");
        if (bundleConfig != null && bundleConfig.size() > 0) {
            for (JsonBundleConfig item: bundleConfig) {
                sb.append(item.toString() + ",");
            }
        }
        return sb.toString();
    }
}
