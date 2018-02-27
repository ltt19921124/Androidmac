package com.example.picassotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView targetImageView = (ImageView) findViewById(R.id.ImageView);
    String Url = "http://218.192.170.132/1.jpg";

        Picasso
                .with(this)
                .load(Url)
                .into(targetImageView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
