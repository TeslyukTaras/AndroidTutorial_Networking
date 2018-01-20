package com.teslyuk.android.androidtutorial_volley_retrofit.utils;

import android.util.Log;

/**
 * Created by taras on 14.02.16.
 */
public class Logger {

    public static final boolean DEBUG = true;

    public static void i(String text, String message) {
        if (DEBUG) {
            Log.i(text, message);
        }
    }

    public static void w(String text, String message) {
        if (DEBUG) {
            Log.w(text, message);
        }
    }

    public static void e(String text, String message) {
        if (DEBUG) {
            Log.e(text, message);
        }
    }

    public static void d(String text, String message) {
        if (DEBUG) {
            Log.d(text, message);
        }
    }
}
