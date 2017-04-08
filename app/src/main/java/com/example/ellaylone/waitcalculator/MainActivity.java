package com.example.ellaylone.waitcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String startDate;
    String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDatePickers();
    }

    public void selectDate(View view, int editDateResourceId) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setEditDateResourceId(editDateResourceId);
        datePickerFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    private void setupDatePickers() {
        setupStartDatePicker();
        setupEndDatePicker();
    }

    private void setupStartDatePicker() {
        EditText startDateEdit = (EditText) findViewById(R.id.start_date_text_view);
        startDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v, R.id.start_date_text_view);
            }
        });

        TextWatcher dateTW = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startDate = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        startDateEdit.addTextChangedListener(dateTW);
    }

    private void setupEndDatePicker() {
        EditText endDateEdit = (EditText) findViewById(R.id.end_date_text_view);
        endDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v, R.id.end_date_text_view);
            }
        });

        TextWatcher dateTW = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                endDate = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        endDateEdit.addTextChangedListener(dateTW);
    }
}
