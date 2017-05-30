package com.example.handlerproject;

import android.icu.text.LocaleDisplayNames;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.textVie);

        //创建handler
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                /**
                 *
                 *
                 */
                //处理消息
                Log.d(TAG,"handleMessage:" + msg.what);
                if (msg.what == 1002) {
                    textView.setText("imooc");
                    Log.d(TAG,"handleMessage:" + msg.arg1);
//                    msg.arg2;
                }
            }
        };
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //有可能做耗时操作
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            /**
                             * 通过UI更新
                             */
                            handler.sendEmptyMessage(1001);

                            Message message = Message.obtain();
                            message.what = 1002;
                            message.arg1 = 1003;
                            message.arg2 = 1004;
                            message.obj = MainActivity.this;

                            handler.sendMessage(message);
                            handler.sendMessageAtTime(message, SystemClock.uptimeMillis() + 30000);
                            handler.sendMessageDelayed(message,2000);

                            final Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    int a = 1 + 2 +  3;
                                }
                            };
                            handler.post(runnable);
                            runnable.run();
                            handler.postDelayed(runnable,2000);

                        }
                    }).start();
            }
        });

    }
}
