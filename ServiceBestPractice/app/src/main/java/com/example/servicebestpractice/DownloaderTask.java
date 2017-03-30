package com.example.servicebestpractice;

import android.os.AsyncTask;
import android.os.ParcelUuid;

import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by Tian Lu on 2017/3/31.
 */

public class DownloaderTask extends AsyncTask<String,Integer,Integer> {
    public static final int TYPE_SUCCESS = 0;

    public static final int TYPE_FAILED = 1;

    public static final int TYPE_PAUSED = 2;

    public static final int TYPE_CANCLED = 3;

    private DownloaderListener listener;

    private

    private boolean isCancled = false;

    private boolean isPaused = false;

    private int lastProgress;

    public DownloaderTask(DownloaderListener listener){
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
    }
}
