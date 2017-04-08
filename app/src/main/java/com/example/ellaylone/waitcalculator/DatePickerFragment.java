package com.example.ellaylone.waitcalculator;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {

    int editDateResourceId;
    EditText mEdit;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public void setEditDateResourceId(int editDateResourceId) {
        this.editDateResourceId = editDateResourceId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        return new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                populateSetDate(year, month + 1, dayOfMonth);
            }
        }, year, month, day);
    }

    public void populateSetDate(int year, int month, int day) {
        mEdit = (EditText) getActivity().findViewById(editDateResourceId);
        mEdit.setText(day + "." + month + "." + year);
    }
}
