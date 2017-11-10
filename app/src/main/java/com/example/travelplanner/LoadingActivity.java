package com.example.travelplanner;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class LoadingActivity extends AppCompatActivity {

    private Handler mHandler;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mHandler = new Handler();
        mHandler.postDelayed( new Runnable() {
            @Override
            public void run() {
                intent = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        },1000); //1초 뒤에 로그인 화면 실행
        finish();


    }
}
