package com.hexleo.mengine.engine.constant;

import com.hexleo.mengine.R;

import java.io.File;

/**
 * 常量定义
 * Created by hexleo on 2017/1/18.
 */

public class MeConstant {
    public static final String ASSET_FILE_URI_PREFIX = "file:///android_asset/";
    public static final String CONFIG = "config.json";
    public static final String APP_JS = "app.js";
    public static final String INDEX_HTML = "index.html";
    public static final String RES_PATH = "res" + File.separator; // 资源文件位置
    public static final String COMMON_PATH = "common"; // 通用js文件 加载app.js前都会统一加载
    public static final String LOG_FILE = "app.log";
    public static final int[] ME_COMMON_RES = {R.raw.http_request}; // 引擎通用app.js文件

    public static final String INTENT_PARAM_BUNDLE = "param_bundle"; // 需要调整的bundleName
    public static final String INTENT_PARAM_DATA = "param_data"; // 跳转后为页面传递的数据
    public static final String INTENT_PARAM_NEED_NAVBAR = "nav_bar"; // 是否需要导航栏
}
