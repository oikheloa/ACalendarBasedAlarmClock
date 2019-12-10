package com.example.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    Context mCtx;
    int resource;
    List<Alarm> alarmList;
    public AlarmAdapter(Context mCtx, int resource, List<Alarm> alarmList) {
        super(mCtx, resource, alarmList);

        this.mCtx = mCtx;
        this.resource = resource;
        this.alarmList = alarmList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(resource, null);

        TextView description = view.findViewById(R.id.descriptionView);
        TextView day = view.findViewById(R.id.dayView);
        TextView time = view.findViewById(R.id.timeView);

        day.setText(alarmList.get(position).getDay());
        description.setText(alarmList.get(position).getDescription());
        time.setText(alarmList.get(position).getTime());


        return view;
    }



}
