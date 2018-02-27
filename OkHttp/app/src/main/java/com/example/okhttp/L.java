package com.example.okhttp;

import android.nfc.Tag;
import android.util.Log;

/**
 * Created by Tian Lu on 2017/5/20.
 */

public class L {
    public static final String TAG = "Imooc_okhttp";
    private static boolean debug = true;
    public static void e (String msg) {
        if (debug) {
            Log.e(TAG,msg);
        }
    }
}
