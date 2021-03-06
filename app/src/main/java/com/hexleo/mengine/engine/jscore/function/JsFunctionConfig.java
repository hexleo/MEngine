package com.hexleo.mengine.engine.jscore.function;

import com.hexleo.mengine.engine.jscore.function.appfun.CommonJCF;
import com.hexleo.mengine.engine.jscore.function.appfun.DbJCF;
import com.hexleo.mengine.engine.jscore.function.appfun.HttpJCF;
import com.hexleo.mengine.engine.jscore.function.appfun.RefreshJCF;
import com.hexleo.mengine.engine.jscore.function.webviewfun.CommonJWF;

/**
 * Created by hexleo on 2017/3/16.
 */

public class JsFunctionConfig {

    public static final JsFunctionInfo[] jsContextFuncArray = {
            new JsFunctionInfo(CommonJCF.getFuncName(), CommonJCF.class),
            new JsFunctionInfo(RefreshJCF.getFuncName(), RefreshJCF.class),
            new JsFunctionInfo(HttpJCF.getFuncName(), HttpJCF.class),
            new JsFunctionInfo(DbJCF.getFuncName(), DbJCF.class)
    };

    public static final JsFunctionInfo[] jsWebViewFuncArray = {
            new JsFunctionInfo(CommonJWF.getFuncName(), CommonJWF.class)
    };

}
