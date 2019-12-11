package com.example.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AlertDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
    }

    public void stopAlarm(View view) {
        Intent intent = new Intent(this, AlarmsList.class);
        startActivity(intent);
    }
}
