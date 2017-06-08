package com.hexleo.mengine.application.step;

import com.hexleo.mengine.util.MLog;

/**
 * Created by hexleo on 2017/04/08.
 */

public class UncaughtExceptionStep implements Step {

    public static Step create() {
        return new UncaughtExceptionStep();
    }

    @Override
    public void doStep() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                StringBuilder sb = new StringBuilder("Thread=" + t.getName());
                StackTraceElement[] se = e.getStackTrace();
                if (se != null && se.length > 0) {
                    for (StackTraceElement ste : se) {
                        sb.append("\n" + ste.toString());
                    }
                } else {
                    sb.append("\n" + e.getMessage());
                }
                MLog.e("UncaughtException",  sb.toString());
                MLog.f("UncaughtException",  sb.toString());
            }
        });
    }
}
