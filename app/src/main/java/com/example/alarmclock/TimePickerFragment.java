package com.example.alarmclock;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;


import androidx.fragment.app.DialogFragment;

 public class TimePickerFragment extends DialogFragment
         {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        int hour = 9;
        int minute = 20;

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

}
