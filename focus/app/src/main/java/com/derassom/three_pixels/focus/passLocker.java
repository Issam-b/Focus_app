package com.derassom.three_pixels.focus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class passLocker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i= getIntent();
        setContentView(R.layout.activity_pass_locker);


    }
}
