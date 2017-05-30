package com.example.handlerproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
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
                download("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
            }
        });
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
                message.what = 1001;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






