package com.hexleo.mengine.application.step;

import com.hexleo.mengine.application.BaseApplication;
import com.orm.SugarContext;

/**
 * Created by hexleo on 2017/4/8.
 */

public class DBStep implements Step {

    public static Step create() {
        return new DBStep();
    }

    @Override
    public void doStep() {
        SugarContext.init(BaseApplication.getBaseApplication());
    }
}
