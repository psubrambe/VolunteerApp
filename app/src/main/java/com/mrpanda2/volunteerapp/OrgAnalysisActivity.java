package com.mrpanda2.volunteerapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrgAnalysisActivity extends AppCompatActivity {

    public static Date mStart;
    public static Date mEnd;
    private TextView mStartText;
    private  TextView mEndText;
    public static String mStartString;
    public static String mEndString;
    private Button mStartButton;
    private Button mEndButton;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.org_analysis_container);
        mStartText = findViewById(R.id.start_date_text);
        mEndText = findViewById(R.id.end_date_text);
        mStartButton = findViewById(R.id.start_date_button);
        mEndButton = findViewById(R.id.end_date_button);
        mSubmitButton = findViewById(R.id.date_submit_button);

        //Calendar startCal = Calendar.getInstance();

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDate(0);

            }
        });


        //Calendar endCal = Calendar.getInstance();

        mEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDate(1);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStart == null || mEnd == null){
                    Toast.makeText(OrgAnalysisActivity.this, "Select Dates", Toast.LENGTH_SHORT).show();
                }
                else{
                    //launch fragment
                    FragmentManager fm = getSupportFragmentManager();
                    Fragment fragment = fm.findFragmentById(R.id.org_analysis_container);

                    if (fragment == null){
                        fragment = new OrgAnalysisFragment();
                        fm.beginTransaction()
                                .add(R.id.org_analysis_container, fragment)
                                .commit();

                    }
                }
            }
        });


    }

    private void handleDate(final int x) {
        Calendar cal = Calendar.getInstance();
        int YEAR = cal.get(Calendar.YEAR);
        int MONTH = cal.get(Calendar.MONTH);
        int DATE = cal.get(Calendar.DATE);

        DatePickerDialog datePicker = new DatePickerDialog(OrgAnalysisActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                Calendar calFrag = Calendar.getInstance();
                calFrag.set(Calendar.YEAR, year);
                calFrag.set(Calendar.MONTH, month);
                calFrag.set(Calendar.DATE, dayOfMonth);
                month = month + 1;
                if (x == 0){
                    String dateString = month + "/" + dayOfMonth + "/" + year;
                    mStartString = dateString;
                    mStartText.setText(dateString);
                    try {
                        mStart = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
                    }
                    catch (ParseException e){
                        e.printStackTrace();
                    }
                }
                else {
                    String dateString = month + "/" + dayOfMonth + "/" + year;
                    mEndString = dateString;
                    mEndText.setText(dateString);
                    try {
                        mEnd = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
                    }
                    catch (ParseException e){
                        e.printStackTrace();
                    }
                }

            }
        }, YEAR, MONTH, DATE);

        datePicker.show();
    }

}












