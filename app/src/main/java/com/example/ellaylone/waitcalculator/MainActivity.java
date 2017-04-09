package com.example.ellaylone.waitcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final String EMPTY_VIEW = "";
    String startDate = EMPTY_VIEW;
    String endDate = EMPTY_VIEW;

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

    private String calculateDifference() {
        String difference = "36";
        //TODO calculate difference
        return difference;
    }

    private void displayMainTimer (String difference) {
        TextView mainTimer = (TextView) findViewById(R.id.main_timer_text_view);
        mainTimer.setText(difference);
    }

    private void checkDates() {
        if (startDate != EMPTY_VIEW && endDate != EMPTY_VIEW) {
            displayMainTimer(calculateDifference());
        } else {
            displayMainTimer(EMPTY_VIEW);
        }
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
                checkDates();
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
                checkDates();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        endDateEdit.addTextChangedListener(dateTW);
    }
}
