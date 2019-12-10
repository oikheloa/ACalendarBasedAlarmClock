package com.example.alarmclock;

import java.sql.Time;

public class Alarm {
    private String day, title, time, suffix;
    private int hour;
    private int minute;


    public Alarm(String day, int hour, int minute, String title) {
        this.day = day;
        this.title = title;
        setHour(hour);
        setMinute(minute);
    }

    public void setHour(int hour) {
        suffix = hour>12 ? "PM" : "AM";
        hour = hour>12 ? hour - 12 : hour;
        this.hour = hour;

    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setTime(int hour, int minute) {
        setHour(hour);
        setMinute(minute);

        this.time = hour + ":" + minute + " " + suffix;
    }

    public String getTime() {
        String h = (hour < 10) ? "0" + hour : hour + "";
        String m = (minute < 10) ? "0" + minute : minute + "";
        this.time = h + ":" + m + " " + suffix;
        return time;
    }

    public String getDay() {
        return day;
    }

    public String getDescription() {
        return title;
    }
}
