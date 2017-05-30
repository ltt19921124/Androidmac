package com.example.imoocdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    public void initView() {
        findViewById(R.id.header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DetailActivity.class));
            }
        });
    }

    public void initData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.imooc.com/api/teacher?type=2");

                    HttpURLConnection coon = (HttpURLConnection)url.openConnection();
                    coon.setRequestMethod("GET");
                    coon.setReadTimeout(6000);

                    if (coon.getResponseCode()  == 200) {
                        InputStream in = coon.getInputStream();
                        byte[] b = new byte[1024*512];
                        int len = 0;
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        while ((len = in.read(b)) > -1) {
                            baos.write(b,0,len);
                        }
                        String result = baos.toString();
                        Log.e("TAG",result);
                        String data = new JSONObject(result).getString("data");
                        //Gson
                        //1，解析普通的json对象
                        //2，解析json数组
                        Gson gson = new Gson();
                        //参数一：.
                        //参数二：type对象
                        ArrayList<Outline> outlines = gson.fromJson(data,new TypeToken<ArrayList<Outline>>(){}.getType());

                        for (int i=0;i < outlines.size();i++) {
                            Outline o = outlines.get(i);
                            Log.e("TAG","id:" + o.getId() + "标题:" + o.getName());
                        }
                        /*
                        JSONObject jo = new JSONObject(result);
                        JSONArray ary = jo.getJSONArray("data");
                        for (int i = 0;i < ary.length();i++) {
                            JSONObject obj = ary.getJSONObject(i);
                            String s = obj.getString("name");
                        }
                        */
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
