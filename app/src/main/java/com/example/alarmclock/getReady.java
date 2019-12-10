package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class getReady extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ready);

        System.out.println("getReady onCreate");
    }

    public void jumpToAlarmsList(View view){

        Intent intent = new Intent(getApplicationContext(), AlarmsList.class);
        startActivity(intent);

        System.out.println("getReady jumpToAlarmsList");
    }
}

