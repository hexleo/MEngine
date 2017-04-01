package com.hexleo.mengine.application.step;

import com.hexleo.mengine.util.ThreadManager;

/**
 * Created by hexleo on 2017/3/20.
 */

public class ThreadManagerStep implements Step {

    public static Step create() {
        return new ThreadManagerStep();
    }

    @Override
    public void doStep() {
        ThreadManager.init();
    }
}
