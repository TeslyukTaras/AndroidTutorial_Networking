package com.teslyuk.android.androidtutorial_volley_retrofit.data.source.volley_api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.StackOverflowAnswers;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.StackOverflowQuestions;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.StackOverflowTags;
import com.teslyuk.android.androidtutorial_volley_retrofit.utils.Logger;

import java.lang.reflect.Type;
import java.util.HashMap;

public class WebRequest {

    private static final String TAG = "NetworkUtil";

    public static final String API_URL = "https://api.stackexchange.com";

    private RequestQueue mQueue;

    public interface ResponseListener<T> {

        void onResponseSuccess(T response);

        void onResponseFail(String errorMessage);

    }

    public WebRequest(Context activity) {
        mQueue = Volley.newRequestQueue(activity);
    }

    private <T> Response.Listener generateSuccessListener(final ResponseListener listener) {
        Response.Listener<T> callback = new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                if (listener != null) {
                    listener.onResponseSuccess(response);
                } else {
                    Logger.e(TAG, "failed to set successfully response ");
                }
            }
        };
        return callback;
    }

    private Response.ErrorListener generateFailListener(final ResponseListener listener) {
        Response.ErrorListener callback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg = "general error";
                if (listener != null) {
                    listener.onResponseFail(msg);
                } else {
                    Logger.e(TAG, "failed to set error response");
                }
            }
        };
        return callback;
    }

    private void logRequest() {

    }

    private <T> void post(String url, Class<T> targetClass, ResponseListener listener) {
        logRequest();
        GsonRequest request = new GsonRequest(Request.Method.POST, url, generateSuccessListener(listener), generateFailListener(listener), targetClass);
        mQueue.add(request);
    }

    private <T> void post(String url, HashMap<String, String> params, Class<T> targetClass, final ResponseListener listener) {
        GsonRequest request = new GsonRequest(Request.Method.POST, url, generateSuccessListener(listener), generateFailListener(listener), targetClass);
        request.setParams(params);
        mQueue.add(request);
    }

    private <T> void post(String url, HashMap<String, String> params, HashMap<String, String> headers, Class<T> targetClass, ResponseListener listener) {
        GsonRequest request = new GsonRequest(Request.Method.POST, url, generateSuccessListener(listener), generateFailListener(listener), targetClass);
        request.setParams(params);
        request.setHeaders(headers);
        mQueue.add(request);
    }

    private <T> void post(String url, HashMap<String, String> params, HashMap<String, String> headers, String body, Class<T> targetClass, ResponseListener<T> listener) {
        GsonRequest request = new GsonRequest(Request.Method.POST, url, generateSuccessListener(listener), generateFailListener(listener), targetClass);
        request.setParams(params);
        request.setBody(body);
        request.setHeaders(headers);
        mQueue.add(request);
    }

    private <T> void get(String url, Class<T> targetClass, ResponseListener<T> listener) {
        GsonRequest request = new GsonRequest(Request.Method.GET, url, generateSuccessListener(listener), generateFailListener(listener), targetClass);
        mQueue.add(request);
    }

    private <T> void get(String url, Type targetClass, ResponseListener<T> listener) {
        GsonRequest request = new GsonRequest(Request.Method.GET, url, generateSuccessListener(listener), generateFailListener(listener), targetClass);
        mQueue.add(request);
    }

    private <T> void get(String url, HashMap<String, String> params, Class<T> targetClass, ResponseListener listener) {
        GsonRequest request = new GsonRequest(Request.Method.GET, url + generateGETParams(params), generateSuccessListener(listener), generateFailListener(listener), targetClass);
        request.setParams(params);
        mQueue.add(request);
    }

    private <T> void get(String url, HashMap<String, String> params, HashMap<String, String> headers, Class<T> targetClass, ResponseListener listener) {
        GsonRequest request = new GsonRequest(Request.Method.GET, url + generateGETParams(params), generateSuccessListener(listener), generateFailListener(listener), targetClass);
        request.setParams(params);
        request.setHeaders(headers);
        mQueue.add(request);
    }

    private <T> void get(String url, HashMap<String, String> params, HashMap<String, String> headers, String body, Class<T> targetClass, ResponseListener listener) {
        GsonRequest request = new GsonRequest(Request.Method.GET, url + generateGETParams(params), generateSuccessListener(listener), generateFailListener(listener), targetClass);
        request.setBody(body);
        request.setHeaders(headers);
        if (mQueue == null) Logger.e(TAG, "mQueue==null");
        if (request == null) Logger.e(TAG, "request==null");
        mQueue.add(request);
    }

    private String generateGETParams(HashMap<String, String> params) {
        StringBuilder requestParams = new StringBuilder();

        if (params != null) {
            requestParams.append("?");
            for (String key : params.keySet()) {
                requestParams.append(key);
                requestParams.append("=");
                requestParams.append(params.get(key));
                requestParams.append("&");
            }
            requestParams.deleteCharAt(requestParams.length() - 1);
        }
        return requestParams.toString();
    }

    public void getQuestionByTag(String tag, ResponseListener listener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("order", "desc");
        params.put("sort", "creation");
        params.put("site", "stackoverflow");
        params.put("tagged", tag);

        get((API_URL) + "/2.2/questions", params, StackOverflowQuestions.class, listener);
    }


    public void getPopularTags(ResponseListener listener) {
        get((API_URL) + "/2.2/tags?order=desc&sort=popular&site=stackoverflow", StackOverflowTags.class, listener);
    }

    public void getAnswers(ResponseListener listener) {
        get((API_URL) + "/2.2/answers?order=desc&sort=activity&site=stackoverflow", StackOverflowAnswers.class, listener);
    }
}