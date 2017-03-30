package com.example.servicebestpractice;

/**
 * Created by Tian Lu on 2017/3/31.
 */

public interface DownloaderListener {
    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCancled();
}
