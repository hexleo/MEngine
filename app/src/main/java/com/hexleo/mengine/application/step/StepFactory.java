package com.hexleo.mengine.application.step;

/**
 * Created by hexleo on 2017/3/20.
 */

public class StepFactory {

    /**
     * 启动步骤，会按照填写顺序运行
     */
    private static final int[] steps = {
            StepConfig.STEP_UNCAUGHTEXCEPTION,
            StepConfig.STEP_THREAD,
            StepConfig.STEP_DB
    };

    public static void doStep() {
        Step step = null;
        for(int s : steps) {
            switch (s) {
                case StepConfig.STEP_UNCAUGHTEXCEPTION:
                    step = UncaughtExceptionStep.create();
                    break;
                case StepConfig.STEP_THREAD:
                    step = ThreadManagerStep.create();
                    break;
                case StepConfig.STEP_DB:
                    step = DBStep.create();
                    break;
            }
            step.doStep();
        }
    }
}
