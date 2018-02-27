package com.example.handlerproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Tian Lu on 2017/5/30.
 */

public class DownloadAvtivity extends Activity{

    public static final int DOWNLOAD_MESSAGE_CODE = 100001;
    public static final int DOWNLOAD_MESSAGE_FAIL_CODE = 100002;
    public static final String APPURL = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
    Handler mhandler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        /**
         * 主线程
         * 点击按钮
         * 发起下载
         * 开启子线程下载
         * 下载完后通知主线程
         * 在主线程更新进度条
         *
         */
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download(APPURL);
                    }
                }).start();
            }
        });

        mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case DOWNLOAD_MESSAGE_CODE:
                        progressBar.setProgress((Integer) msg.obj);
                        break;
                    case DOWNLOAD_MESSAGE_FAIL_CODE:

                }
            }
        };
    }

    private void download(String appurl) {
        try {
            URL url = new URL(appurl);
            URLConnection urlConnection = url.openConnection();

            InputStream inputStream = urlConnection.getInputStream();
            /*

             */
            int contentLength = urlConnection.getContentLength();
            String downloadFolderName = Environment.getExternalStorageDirectory() +
                    File.separator + "imooc" + File.separator;
            File file = new File(downloadFolderName);
            if (!file.exists()) {
                file.mkdir();
            }
            String fileName = downloadFolderName + "imooc.apk";
            File apkFile = new File(fileName);

            if (apkFile.exists()) {
                apkFile.delete();
            }

            int downloadSize = 0;
            byte[] bytes = new byte[1024];

            int length = 0;

            OutputStream outputStream = new FileOutputStream(fileName);
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes,0,length);
                downloadSize += length;
                /**
                 *
                 */
                Message message = Message.obtain();
                message.obj = downloadSize * 100 / contentLength;
                message.what = DOWNLOAD_MESSAGE_CODE;
                mhandler.sendMessage(message);

            }
            inputStream.close();
            outputStream.close();

        } catch (MalformedURLException e) {
            notifyDownloadFailed();
            e.printStackTrace();
        } catch (IOException e) {
            notifyDownloadFailed();
            e.printStackTrace();
        }
    }
    private void notifyDownloadFailed() {
        Message message = Message.obtain();
        message.what = DOWNLOAD_MESSAGE_FAIL_CODE;
        mhandler.sendMessage(message);
    }
}






