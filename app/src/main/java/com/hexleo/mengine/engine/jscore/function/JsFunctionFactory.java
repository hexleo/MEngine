package com.hexleo.mengine.engine.jscore.function;

import com.hexleo.mengine.util.MLog;

/**
 * Created by hexleo on 2017/3/20.
 */

public class JsFunctionFactory {

    private static final String TAG = "JsFunctionFactory";

    public static JsFunction create(JsFunctionInfo func) {
        try {
            return func.jsFuncClass.newInstance();
        } catch (Exception e) {
            MLog.e(TAG , e.getMessage());
        }
        return null;
    }

}
