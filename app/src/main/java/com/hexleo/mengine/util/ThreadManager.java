package com.hexleo.mengine.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * 线程管理器
 * Created by hexleo on 2017/3/16.
 */

public class ThreadManager {

    private static final String THREAD_NAME = "ThreadManager";

    private static ThreadManager sInstance;

    // 通用HandlerThread
    private HandlerThread mCommonThread;
    private Handler mCommonHandler;
    // app.js的运行线程
    private HandlerThread mAppJsRuntiomThread;
    private Handler mAppJsRuntiom;
    private Handler mMainHandler;

    private ThreadManager() {
        mCommonThread = new HandlerThread("COMMON_THREAD");
        mCommonThread.start();
        mCommonHandler = new Handler(mCommonThread.getLooper());
        mAppJsRuntiomThread = new HandlerThread("APP_JS_RUNTIME");
        mAppJsRuntiomThread.start();
        mAppJsRuntiom = new Handler(mAppJsRuntiomThread.getLooper());
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    public static void init() {
        if (sInstance == null) {
            synchronized (ThreadManager.class) {
                if (sInstance == null) {
                    sInstance = new ThreadManager();
                }
            }
        }
    }

    // TODO 先简单实现
    public static void post(Runnable runnable) {
        new Thread(runnable, THREAD_NAME + runnable.hashCode()).start();
    }

    public static void postDelay(Runnable runnable, long delay) {
        sInstance.mCommonHandler.postDelayed(runnable, delay);
    }

    /**
     * 专门提供给app.js使用的线程
     * @param runnable
     */
    public static void appJsPost(Runnable runnable) {
        sInstance.mAppJsRuntiom.post(runnable);
    }

    /**
     * 抛到主线程进行执行
     * @param runnable
     */
    public static void mainPost(Runnable runnable) {
        sInstance.mMainHandler.post(runnable);
    }

    /**
     * 抛到主线程延迟执行
     * @param runnable
     */
    public static void mainPostDelay(Runnable runnable, long delay) {
        sInstance.mMainHandler.postDelayed(runnable, delay);
    }

}
