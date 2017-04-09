package com.example.ellaylone.waitcalculator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    final int DATABASE_VERSION = 3;
    final String EMPTY_VIEW = "";
    String startDate = EMPTY_VIEW;
    String endDate = EMPTY_VIEW;
    String todayDate = EMPTY_VIEW;
    int difference = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDatePickers();

        setupDb();

        displayMainTimer(difference);
    }


    private String getCurrentDate() {
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        return "" + day + "." + (month + 1) + "." + day + "T" + hour + ":" + minute + ":" + second;
    }

    private void setupDb() {
        DbHelper dbHelper = new DbHelper(MainActivity.this, "WaitCalculator", null, DATABASE_VERSION);
        SQLiteDatabase db;
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }

        // проверка существования записей
        Cursor c = db.query("DATES", null, null, null, null, null, null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            for (int i = 0; i < 2; i++) {
                switch (c.getInt(c.getColumnIndex(c.getColumnName(2)))) {
                    case 1:
                        startDate = c.getString(c.getColumnIndex(c.getColumnName(1)));
                        displayStartDate(startDate);
                        break;
                    case 2:
                        endDate = c.getString(c.getColumnIndex(c.getColumnName(1)));
                        displayEndDate(endDate);
                        break;
                    default:
                        break;
                }
                c.moveToNext();
            }
            difference = calculateDifference(getCurrentDate(), endDate);
            c.close();
        } else {
            ContentValues newValues = new ContentValues();
            newValues.put("DATE_VALUE", "02.04.2017T13:00:00");
            newValues.put("DATE_TYPE", 1);
            db.insert("DATES", null, newValues);
            newValues.clear();
            newValues.put("DATE_VALUE", "14.05.2017T15:55:00");
            newValues.put("DATE_TYPE", 2);
            db.insert("DATES", null, newValues);
        }
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

    private int calculateDifference(String startDate, String endDate) {

        int difference = 0;

        String[] startParts = startDate.split("T");
        String[] endParts = endDate.split("T");

        if (startParts.length != 2 && endParts.length != 2) {
            return difference;
        }

        String[] startDateParts = startParts[0].split("\\.");
        String[] endDateParts = endParts[0].split("\\.");

        String[] startTimeParts = startParts[1].split(":");
        String[] endTimeParts = endParts[1].split(":");

        if ((startDateParts.length != 3 && endDateParts.length != 3) || (startTimeParts.length != 3 && endDateParts.length != 3)) {
            return difference;
        }

        int startYear = Integer.parseInt(startDateParts[2]);
        int endYear = Integer.parseInt(endDateParts[2]);

        int startMonth = Integer.parseInt(startDateParts[1]);
        int endMonth = Integer.parseInt(endDateParts[1]);

        int startDayOfMonth = Integer.parseInt(startDateParts[0]);
        int endDayOfMonth = Integer.parseInt(endDateParts[0]);

        int[] differenceParts = new int[3];
        differenceParts[0] = endYear - startYear;
        differenceParts[1] = endMonth - startMonth;
        differenceParts[2] = endDayOfMonth - startDayOfMonth;

        if (differenceParts[0] < 0 || (differenceParts[0] == 0 && differenceParts[1] < 0) || (differenceParts[0] == 0 && differenceParts[1] == 0 && differenceParts[2] < 0)) {
            return difference;
        }

        int[] daysInMonth = new int[13];
        daysInMonth[1] = 31;
        daysInMonth[2] = 28;
        daysInMonth[3] = 31;
        daysInMonth[4] = 30;
        daysInMonth[5] = 31;
        daysInMonth[6] = 30;
        daysInMonth[7] = 31;
        daysInMonth[8] = 31;
        daysInMonth[9] = 30;
        daysInMonth[10] = 31;
        daysInMonth[11] = 30;
        daysInMonth[12] = 31;

        int totalDayDifference = 0;
        for (int i = 0; i <= differenceParts[1]; i++) {
            int month = startMonth + i;
            if (month > 12) {
                month = 1;
            }
            totalDayDifference += daysInMonth[month];
        }
        totalDayDifference -= startDayOfMonth;
        totalDayDifference -= daysInMonth[endMonth] - endDayOfMonth;

        int startHour = Integer.parseInt(startTimeParts[0]);
        int endHour = Integer.parseInt(endTimeParts[0]);

        int startMinutes = Integer.parseInt(startTimeParts[1]);
        int endMinutes = Integer.parseInt(endTimeParts[1]);

        int startSeconds = Integer.parseInt(startTimeParts[2]);
        int endSeconds = Integer.parseInt(endTimeParts[2]);

        int totalSecondsDifference = endSeconds - startSeconds;
        totalSecondsDifference += endMinutes * 60 - startMinutes * 60;
        totalSecondsDifference += endHour * 60 * 60 - startHour * 60 * 60;

        totalSecondsDifference = totalDayDifference * 24 * 60 * 60 + totalSecondsDifference;

        return totalSecondsDifference;
    }

    private void displayMainTimer(int difference) {
        TextView mainTimer = (TextView) findViewById(R.id.main_timer_text_view);
        if (difference > 0) {
            int fullDays = difference / (60 * 60 * 24);
            difference -= fullDays * (60 * 60 * 24);
            int fullHours = difference / (60 * 60);
            difference -= fullHours * (60 * 60);
            int fullMinutes = difference / (60);
            int fullSeconds = difference - fullMinutes * 60;
            String mainTimerText = "" + fullDays + "d " + String.format("%02d", fullHours) + ":" + String.format("%3d", fullMinutes) + ":" + String.format("%3d", fullSeconds);
            mainTimer.setText(mainTimerText);
        } else {
            mainTimer.setText("");
        }
    }

    private void checkDates() {
        if (endDate != EMPTY_VIEW) {
//            displayMainTimer("" + calculateDifference(startDate, endDate));
        } else {
//            displayMainTimer(EMPTY_VIEW);
        }
    }

    private void displayStartDate(String date) {
        EditText startDateEdit = (EditText) findViewById(R.id.start_date_text_view);
        startDateEdit.setText(date);
    }

    private void displayEndDate(String date) {
        EditText endDateEdit = (EditText) findViewById(R.id.end_date_text_view);
        endDateEdit.setText(date);
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
