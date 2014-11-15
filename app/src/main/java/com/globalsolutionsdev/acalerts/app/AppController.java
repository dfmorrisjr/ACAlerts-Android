//package app;
package com.globalsolutionsdev.acalerts.app;
/**
 * Created by dmorris on 10/29/2014.
 * Created the app package by createing it under main then moving to src
 *
 */

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import java.lang.Override;


public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    public static String _username = "";
    public static String _password = "";


    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;

    }
    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T>req){
        req.setTag(TAG);
        getRequestQueue().add(req);

    }

    public void canclePendingRequest(Object tag){
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }

    }

}
