package com.teslyuk.android.androidtutorial_volley_retrofit.data.source.volley_api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.teslyuk.android.androidtutorial_volley_retrofit.utils.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by taras on 14.02.16.
 */
public class GsonRequest<T> extends Request<T> {

    private static final String TAG = GsonRequest.class.getSimpleName();

    private final Response.Listener<T> mListener;

    private String mBody;
    private Map<String, String> mParams;
    private Map<String, String> mHeaders;
    private final Class<T> clazz;
    private final Type type;


    public GsonRequest(int method, String url, Response.Listener<T> respListener, Response.ErrorListener listener, Class<T> targetClass) {
        super(method, url, listener);
        mListener = respListener;
        clazz = targetClass;
        type = null;
    }

    public GsonRequest(int method, String url, Response.Listener<T> respListener, Response.ErrorListener listener, Type objectType) {
        super(method, url, listener);
        mListener = respListener;
        type = objectType;
        clazz = null;
    }

    public void addHeader(String key, String value) {
        if (mHeaders == null) mHeaders = new HashMap<>();
    }

    public void addParam(String key, String value) {
        if (mParams == null) mParams = new HashMap<>();
    }

    public void setHeaders(HashMap<String, String> headers) {
        if (headers == null) {
            Logger.w(TAG, "headers is empty ");
        } else {
            for (String key : headers.keySet()) {
                Logger.i(TAG, "header " + key + " " + headers.get(key));
            }
        }
        mHeaders = headers;
    }

    public void setParams(HashMap<String, String> params) {
        if (params == null) {
            Logger.w(TAG, "params is empty ");
        } else {
            for (String key : params.keySet()) {
                Logger.i(TAG, "param " + key + " " + params.get(key));
            }
        }
        mParams = params;
    }

    public void setBody(String body) {
        Logger.w(TAG, body == null ? "string body is empty " : body);
        mBody = body;
    }

    public void setBody(JSONObject body) {
        if (body != null) {
            mBody = body.toString();
        } else {
            Logger.w(TAG, "json body is empty");
        }
    }

    public void setBody(JSONArray body) {
        if (body != null) {
            mBody = body.toString();
        } else {
            Logger.w(TAG, "json array body is empty");
        }
    }

    public Map<String, String> getHeaders() {
        if (mHeaders == null) mHeaders = new HashMap<>();
        return mHeaders;
    }

    public Map<String, String> getParams() {
        if (mParams == null) mParams = new HashMap<>();
        return mParams;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mBody != null) return mBody.getBytes();
        return super.getBody();
    }

    public void setParsingRule() {

    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Logger.i(TAG, "response url : " + getUrl());

        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Logger.i(TAG, "response received : " + parsed);
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }

        if (parsed != null && !parsed.equals("")) {
            Gson gsonParser = new Gson();
            T obj;
            if (clazz != null)
                obj = gsonParser.fromJson(parsed, clazz);
            else {
                obj = gsonParser.fromJson(parsed, type);
            }
            if (obj != null) {
                if (clazz != null)
                    Logger.i(TAG, "response parsed successfully : " + clazz);
                else Logger.i(TAG, "response parsed successfully : " + type);
                return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
            }
        }
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
