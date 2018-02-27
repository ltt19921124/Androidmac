package com.example.imoocdemo;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.DataTruncation;

public class DetailActivity extends Activity {

    private TextView nameView,authorView,contentView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Essay e = (Essay) msg.obj;
            nameView.setText(e.getTitle());
            authorView.setText(e.getAuthor());
            contentView.setText(e.getContent());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initData();
    }

    public void initView() {
        nameView = (TextView) findViewById(R.id.name);
        authorView = (TextView) findViewById(R.id.author);
        contentView = (TextView) findViewById(R.id.content);

    }

    public void initData() {
        //HttpUrlConnection
        /**
         * 1,实例化一个url对象
         * 2，获取HtttpUrlConnection对象
         * 3，设置请求连接属性
         * 4，获取响应码，判断是否连接成功
         * 5，读取输入流并解析
         */
        //参数：要访问的接口地址

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.imooc.com/api/teacher?type=3&cid=1");
                    HttpURLConnection coon = (HttpURLConnection)url.openConnection();
                    coon.setRequestMethod("GET");
                    coon.setReadTimeout(6000);
                    //获取响应码
                    if(coon.getResponseCode() == 200) {
                        //获取输入流
                        InputStream in = coon.getInputStream();
                        byte[] b = new byte[1024*512];
                        int len = 0;
                        //建立缓存流，保存所读取的字节数组
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        while ((len = in.read()) > -1) {
                            baos.write(b,0,len);
                        }
                        String msg = baos.toString();
                        Log.e("TAG",msg);
                        //JSON数据解析：
                        JSONObject obj = new JSONObject(msg);
                        int status = obj.getInt("status");
                        String msg2 = obj.getString("msg");
                        Log.e("TAG",status + " " + msg2);

                        Gson gson = new Gson();
                        String data = obj.getString("data");
                        Essay e = gson.fromJson(data,Essay.class);


//                        JSONObject data = obj.getJSONObject("data");

                        /*
                        String title = data.getString("title");
                        String author = data.getString("author");
                        String content = data.getString("content");

                        Log.e("TAG","标题:" + title + ",作者:" + author + ",内容:" + content);
                        */
                        //将操作权交还给主线程
                        Message message = handler.obtainMessage();
//                        Essay e = new Essay(title,author,content);
                        message.obj = e;
                        //触发主线程中Hnadle对象下覆盖了的
                        handler.sendMessage(message);



                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


}
