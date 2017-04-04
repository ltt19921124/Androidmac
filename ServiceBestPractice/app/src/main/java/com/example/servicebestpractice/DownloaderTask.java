package com.example.servicebestpractice;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.ParcelUuid;
import android.sax.StartElementListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tian Lu on 2017/3/31.
 */

public class DownloaderTask extends AsyncTask<String,Integer,Integer> {
    public static final int TYPE_SUCCESS = 0;

    public static final int TYPE_FAILED = 1;

    public static final int TYPE_PAUSED = 2;

    public static final int TYPE_CANCLED = 3;

    private DownloaderListener listener;
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
        File file = null;
        try{
            long downloadedLength = 0;//记录已下载的文件长度
            String downloaderUrl = params[0];
            String fileName = downloaderUrl.substring(downloaderUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .getPath();
            file = new File(directory + fileName);
            if (file.exists()) {
                downloadedLength = file.length();
            }
            long contentLength = getContentLength(downloaderUrl);
            if (contentLength == 0){
                return TYPE_FAILED;
            }else if (contentLength == downloadedLength){
                // 已下载字节和文件总字节相等，说明已经下载完了
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Response request = new Response.Builder()
                    .addHeader("RANGE","bytes=" + downloadedLength + "-")
                    .url(downloaderUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downloadedLength);
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1){
                    if (isCancled) {
                        return TYPE_CANCLED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        savedFile.write(b,0,len);
                        int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                        publishProgress(progress);
                     }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (savedFile != null){
                    file.delete();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCLED:
                listener.onCancled();
            default:
                break;
        }
    }
    public void pauseDownload() {
        isPaused = true;
    }
    public void cancelDownload() {
        isCancled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }
}
