package com.teslyuk.android.androidtutorial_volley_retrofit;

import android.app.Application;

import com.teslyuk.android.androidtutorial_volley_retrofit.data.source.volley_api.WebRequest;

/**
 * Created by taras on 14.02.16.
 */
public class AndroidTutorialApplication extends Application {

    private static WebRequest mRequest;
    private static AndroidTutorialApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static WebRequest getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequest == null) {
            mRequest = new WebRequest(instance);
        }
        return mRequest;
    }
}
