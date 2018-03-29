package activitytest.example.com.servicebestpractice2;

import android.os.AsyncTask;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by lutian on 2018/3/6.
 */

public class DownloadTask extends AsyncTask<String,Integer,Integer> {
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCLED = 3;


    private DownloadListener listener;

    private boolean isCancled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {

        InputStream is = null;
        RandomAccessFile saveFile = null;
        File file = null;
        try {
            long downloadedLength = 0;
            String downloadUr
        }

        return null;
    }
}
