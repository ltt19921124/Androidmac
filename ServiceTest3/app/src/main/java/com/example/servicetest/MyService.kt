package com.example.servicetest

import android.app.DownloadManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.annotation.IntDef
import android.util.Log

class MyService : Service() {

    private val mBinder = DownloadBinder()

    internal inner class DownloadBinder : Binder() {

        fun startDownload() {
            Log.d("MyService", "startDownload executed")
        }

        val progress: Int
            get() {
                Log.d("MyService", "getProgress executed")
                return 0
            }

        fun onBind(intent: Intent): IBinder {
            return mBinder
        }

    }

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyServie", "oncreate executed")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand executed")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService", "onDestroy executed")
    }
}
