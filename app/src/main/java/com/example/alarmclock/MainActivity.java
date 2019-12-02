package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alarmclock.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button googleButton = (Button) findViewById(R.id.button);
//        googleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,Pop.class));
//            }
//        });
    }

    public void jumpToNext(View view){

        EditText textbox = findViewById(R.id.editText);

        String _string = "I am from the first Activity";
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("mydata", _string);
        startActivity(intent);

    }
}
