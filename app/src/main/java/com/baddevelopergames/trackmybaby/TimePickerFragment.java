package com.baddevelopergames.trackmybaby;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public static void show(AppCompatActivity activity, TimePickerCallbacks callbacks) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.register(callbacks);
        timePickerFragment.show(activity.getSupportFragmentManager(), "timePicker");
    }

    private TimePickerCallbacks callbacks;

    private void register(TimePickerCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (callbacks != null) {
            callbacks.onTimePicked(hourOfDay, minute);
        }
    }

    public interface TimePickerCallbacks {
        void onTimePicked(int hourOfDay, int minute);
    }
}