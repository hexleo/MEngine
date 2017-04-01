package com.hexleo.mengine.engine.jscore;

/**
 * Created by hexleo on 2017/3/13.
 */

public class MeJsContextFactory {

    private static final String TAG = "MeJsContextFactory";

    private static MeJsContextFactory mInstance;

    private MeJsContextFactory() {
    }

    public static MeJsContextFactory getInstance() {
        if (mInstance == null) {
            synchronized (MeJsContextFactory.class) {
                if (mInstance == null) {
                    mInstance = new MeJsContextFactory();
                }
            }
        }
        return mInstance;
    }

    public MeJsContext create() {
        return new MeJsContext();
    }

}
