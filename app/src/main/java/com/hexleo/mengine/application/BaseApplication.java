package com.hexleo.mengine.application;

import android.app.Application;

import com.hexleo.mengine.application.step.StepFactory;

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

    public static BaseApplication getBaseApplication() {
        return mApp;
    }
}
