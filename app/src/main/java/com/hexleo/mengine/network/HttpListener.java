package com.hexleo.mengine.network;

/**
 * Created by hexleo on 2017/4/6.
 */

public interface HttpListener {
    void onSuccess(String response);
    void onError(int errorCode);
}
