package activitytest.example.com.servicetest4;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MyService.DownloaderBinder mDownloaderBinder;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloaderBinder = (MyService.DownloaderBinder) service;
            mDownloaderBinder.startDownload();
            mDownloaderBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startService = findViewById(R.id.start_service);
        Button stopService = findViewById(R.id.stop_service);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

        Button bindService = findViewById(R.id.start_service);
        Button unbindService = findViewById(R.id.unbind_service);

        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,mConnection,BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                unbindService(mConnection);
                break;
            default:
                break;

        }
    }
}
