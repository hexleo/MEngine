package com.hexleo.mengine.application;

import android.app.Application;

import com.hexleo.mengine.application.step.StepFactory;
import com.orm.SugarContext;

/**
 * Created by hexleo on 2017/1/18.
 */

public class BaseApplication extends Application {

    private static BaseApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public void init() {
        mApp = this;
        StepFactory.doStep();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    public static BaseApplication getBaseApplication() {
        return mApp;
    }
}
