package com.example.gridviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tian Lu on 2017/6/1.
 */

public class ExampleActivity extends Activity{
    private GridView gridView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        gridView = (GridView) findViewById(R.id.gridview);
        List<String> stringList = new ArrayList<String>();
        for (int i = 0;i < 9;i++) {
            stringList.add("慕课网" + i);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stringList);
        gridView.setAdapter(arrayAdapter);
    }
}
