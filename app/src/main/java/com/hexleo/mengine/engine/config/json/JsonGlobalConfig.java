package com.hexleo.mengine.engine.config.json;

/**
 * Created by hexleo on 2017/4/3.
 */

public class JsonGlobalConfig extends Json{
    public String titleColor;
    public String refreshColor;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("titleColor=" + titleColor);
        sb.append(",refreshColor=" + refreshColor);
        return sb.toString();
    }
}
