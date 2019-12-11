
package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

/*
    For demo purposes, the only time that can be edited in the emulator is the alarm for Monday,
    and the only alarm that can be disabled is the alarm for Thursday.
 */

public class AlarmsList extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    List<Alarm> alarmList;
    ListView listView;
    AlarmAdapter adapter;
    View editWindow;
    DialogFragment newFragment;
    private final String CHANNEL_ID = "alarms";
    private static NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        newFragment = new TimePickerFragment();
        //make alarms
        alarmList = new ArrayList<>();
        String[] days = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int[] hours = new int[]     {10, 9, 8, 10, 8, 7, 9};
        int[]minutes = new int[]    {0, 20, 5, 20, 5, 10, 45};
        String[] titles = new String[] {
                "Brunch with Abby at Sweetgreen",
                "CMSC320 at IRB at 10am",
                "Math410 at ARM at 12pm",
                "Office hours at Tawes at 11:30am",
                "Math410 at ARM at 12pm",
                "Work shift at 8:30am",
                "Group Project meeting at IRB at 10:30am", };

        for (int i = 0; i < 7; i++) {
            alarmList.add(new Alarm(days[i], hours[i], minutes[i], titles[i]));
        }

        //set up listView
        listView = findViewById(R.id.alarmListView);

        adapter = new AlarmAdapter(this, R.layout.alarm_list_item, alarmList);
        listView.setAdapter(adapter);

        //set up alarm editor popup window
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

        editWindow = findViewById(R.id.editWindow);
        editWindow.setVisibility(View.INVISIBLE);
        editWindow.setTranslationY(dpHeight);

        // Spinner element
        Spinner spinner = findViewById(R.id.spinner);


        List<String> categories = new ArrayList<>();
        categories.add("Birds");
        categories.add("Chimes");
        categories.add("Ding");
        categories.add("Rise n Shine");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        spinner.setAdapter(dataAdapter);



    }

    public void openAlarmEditor (View view) {
        View editWindow = findViewById(R.id.editWindow);

        Alarm alarm = alarmList.get(1);
        ((TextView) findViewById(R.id.EditTimeView)).setText(alarm.getTime());
        ((TextView) findViewById(R.id.EditDayView)).setText(alarm.getDay());
        ((TextView) findViewById(R.id.EditDescriptionView)).setText(alarm.getDescription());

        editWindow.setVisibility(View.VISIBLE);
        editWindow.animate().translationY(0);

    }

    public void closeAlarmEditor (View view) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

        editWindow.animate().translationY(dpHeight);
        editWindow.setVisibility(View.INVISIBLE);
    }

    public void openTimePicker (View view) {
        //this line is supposed to set the time picker to the alarm's currently set time, but getDialog is returning null
        //((TimePickerDialog) newFragment.getDialog()).updateTime(alarmList.get(1).getHour(),alarmList.get(1).getMinute());
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        alarmList.get(1).setTime(hour, minute);
        ((TextView) findViewById(R.id.EditTimeView)).setText(alarmList.get(1).getTime());

        adapter = new AlarmAdapter(this, R.layout.alarm_list_item, alarmList);
        listView.setAdapter(adapter);

    }

    //Gray out a disabled alarm. For demo purposes, only the Thursday alarm will be disabled
    public void disableAlarm (View view) {
        View alarm = listView.getChildAt(4);
        if (alarm.getAlpha() == 1)
            alarm.setAlpha(0.5f);
        else
            alarm.setAlpha(1);
    }

    public void demoAlarm(View view) {
        Intent intent = new Intent(this, AlertDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(this, AlarmReceiver.class);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        snoozeIntent.setAction("com.example.alarmnotifications.AlarmsList$AlarmReceiver");
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent stopIntent = new Intent(this, AlarmReceiver.class);
        stopIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        stopIntent.setAction("com.example.alarmnotifications.AlarmsList$AlarmReceiver");
        PendingIntent stopPendingIntent =
                PendingIntent.getBroadcast(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Alarm")
                .setContentText("Time to wake up!")
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_launcher, "Snooze",
                        snoozePendingIntent)
                .addAction(R.drawable.ic_launcher, "Stop",
                        stopPendingIntent);

        notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(123, builder.build());
    }

    public static class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            notificationManager.cancel(123);
        }
    }



}

