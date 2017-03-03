package com.example.tr210demo.utils;

import java.io.IOException;
import java.io.StreamCorruptedException;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtils {

    private final static String KEY_CONTINUOUS_SCAN_MODE = "continuous_scan_mode";

    private static SharedPreferenceUtils mSharedPreferenceUtils;

    private static SharedPreferences msp;

    public static synchronized void initSharedPreference(Context context) {
        if (mSharedPreferenceUtils == null) {
            mSharedPreferenceUtils = new SharedPreferenceUtils(context);
        }
    }

    public static synchronized SharedPreferenceUtils getInstance() {
        return mSharedPreferenceUtils;
    }

    private SharedPreferenceUtils(Context context) {
        msp = context.getSharedPreferences("SharedPreUtil",
                Context.MODE_PRIVATE | Context.MODE_APPEND);
    }

    private SharedPreferences getSharedPref() {
        return msp;
    }

    private void putInt(String key, int value) {
        Editor editor = msp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private static int getInt(String key) {
        return msp.getInt(key, 0);
    }

    private static void putString(String key, String value) {
        Editor editor = msp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return msp.getString(key, null);
    }


    public void putScanmode(boolean value) {
        Editor editor = msp.edit();
        editor.putBoolean(KEY_CONTINUOUS_SCAN_MODE, value);
        editor.commit();
    }

    public int getScanmode() {
        return msp.getInt(KEY_CONTINUOUS_SCAN_MODE, 0);
    }

}