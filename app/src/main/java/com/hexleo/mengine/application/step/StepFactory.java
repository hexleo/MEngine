package com.hexleo.mengine.application.step;

/**
 * Created by hexleo on 2017/3/20.
 */

public class StepFactory {

    /**
     * 启动步骤，会按照填写顺序运行
     */
    private static final int[] steps = {
            StepConfig.STEP_THREAD
    };

    public static void doStep() {
        Step step = null;
        for(int s : steps) {
            switch (s) {
                case StepConfig.STEP_THREAD:
                    step = ThreadManagerStep.create();
                    break;
            }
            step.doStep();
        }
    }
}
