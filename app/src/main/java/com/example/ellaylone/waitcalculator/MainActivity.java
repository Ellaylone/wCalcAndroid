package com.example.ellaylone.waitcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Date startDate;
    Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setStartDate();
        setEndDate();
    }

    public void setStartDate() {
        long startUnixTime = new Long("1491585275653");
        startDate = new Date(startUnixTime);

        TextView startDateTextView = (TextView) findViewById(R.id.start_date_text_view);
        startDateTextView.setText(startDate.toString());
    }

    public void setEndDate() {
        long startUnixTime = new Long("1491585475061");
        endDate = new Date(startUnixTime);

        TextView startDateTextView = (TextView) findViewById(R.id.end_date_text_view);
        startDateTextView.setText(endDate.toString());
    }
}
