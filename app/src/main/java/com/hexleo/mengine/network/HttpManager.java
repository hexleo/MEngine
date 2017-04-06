package com.hexleo.mengine.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hexleo.mengine.application.BaseApplication;

import java.util.Map;

/**
 * Created by hexleo on 2017/4/6.
 */

public class HttpManager {
    private static final String TAG = "HttpManager";

    public static final int GET = 1;
    public static final int POST = 2;

    private static HttpManager sInstance;
    private RequestQueue mReqQueue;


    private HttpManager() {
        mReqQueue = Volley.newRequestQueue(BaseApplication.getBaseApplication());
    }

    public static HttpManager getInstance() {
        if (sInstance == null) {
            synchronized (HttpManager.class) {
                if (sInstance == null) {
                    sInstance = new HttpManager();
                }
            }
        }
        return sInstance;
    }

    public void requestGet(String url, HttpListener listener) {
        request(GET, url, null, listener);
    }

    public void requestPost(String url, Map<String, String> params, HttpListener listener) {
        request(POST, url, params, listener);
    }

    public void request(int method, String url, final Map<String, String > params, final HttpListener listener) {
        StringRequest req = null;
        Response.Listener<String> success = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        };
        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.networkResponse.statusCode);
            }
        };

        if (method == GET) {
            req = new StringRequest(Request.Method.GET, url, success, error);
        } else if (method == POST) {
            req = new StringRequest(Request.Method.POST, url, success, error) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params;
                }
            };
        }
        if (req != null) {
            mReqQueue.add(req);
        }
    }

}
