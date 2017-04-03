package com.hexleo.mengine.engine.config.json;

/**
 * Created by hexleo on 2017/4/3.
 */

public class JsonBundleConfig extends Json{
    public String bundleName;
    public String title;
    public String titleColor;
    public String lazyInit;
    public String enableRefresh;
    public String refreshColor;
    public String webUrl;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("bundleName=" + bundleName);
        sb.append(",title=" + title);
        sb.append(",titleColor=" + titleColor);
        sb.append(",lazyInit=" + lazyInit);
        sb.append(",enableRefresh=" + enableRefresh);
        sb.append(",refreshColor=" + refreshColor);
        sb.append(",webUrl=" + webUrl);
        return sb.toString();
    }
}
