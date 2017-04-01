package com.hexleo.mengine.engine.jscore.function;

import com.hexleo.mengine.engine.jscore.function.JsFunction;

/**
 * Created by hexleo on 2017/3/20.
 */

public class JsFunctionInfo {
    public String jsFuncName;
    public Class<? extends JsFunction> jsFuncClass;

    public JsFunctionInfo(String jsFuncName, Class<? extends JsFunction> jsFuncClass) {
        this.jsFuncName = jsFuncName;
        this.jsFuncClass = jsFuncClass;
    }
}
