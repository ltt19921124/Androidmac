package com.bignerdranch.android.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class HelloWorldActivity extends AppCompatActivity {

    private static final String TAG = "HelloWorldActiviy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_world_layout);
        Log.d("data","onCreate executed");

    }
}
