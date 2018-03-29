package activitytest.example.com.servicebestpractice2;

/**
 * Created by lutian on 2018/3/6.
 */

public interface DownloadListener {

    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPause();
    void onCancled();


}
