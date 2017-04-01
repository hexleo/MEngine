package com.hexleo.mengine.engine.exception;

/**
 * Created by hexleo on 2017/3/16.
 */

public class NotWorkThread extends RuntimeException {
    public NotWorkThread() {
        super("Must not in main thread!");
    }
}
