package com.example.asynctask;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class DownloadAsyncTask extends AsyncTask<String,Integer,Boolean> {

        /**
         * 在另外一个线程中处理事件
         * @param params 入参
         * @return  结果
         */
        @Override
        protected Boolean doInBackground(String... params) {

            return null;
        }
    }
}
