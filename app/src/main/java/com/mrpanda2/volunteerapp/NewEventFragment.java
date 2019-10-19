package com.mrpanda2.volunteerapp;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.Date;

public class NewEventFragment extends Fragment {
    private Event mEvent;
    private EditText mNameField;
    private DatePicker mDateField;
    private Date mDate;
    private EditText mTimeField;
    private EditText mLocationField;
    private Button mCreateButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mEvent = new Event();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_new_event, container, false);

        //Event name
        mNameField = (EditText) v.findViewById(R.id.new_event_name);
        mNameField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                //blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after){
                mEvent.setName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s){
                //blank
            }
        });

        //DATE
        mDateField = (DatePicker) v.findViewById(R.id.new_event_date);


        //Event Time
        mTimeField = (EditText) v.findViewById(R.id.new_event_time);
        mTimeField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                //blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after){
                mEvent.setTime(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s){
                //blank
            }
        });

        //Event Location
        mLocationField = (EditText) v.findViewById(R.id.new_event_location);
        mLocationField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                //blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after){
                mEvent.setLocation(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s){
                //blank
            }
        });

        mCreateButton = (Button) v.findViewById(R.id.new_event_create_button);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //writes event data to firebase
            }
        });

        return v;
    }

}
