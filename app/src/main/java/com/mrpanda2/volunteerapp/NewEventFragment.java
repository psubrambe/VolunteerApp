package com.mrpanda2.volunteerapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewEventFragment extends Fragment {
    private Event mEvent;
    private EditText mNameField;
    private EditText mDateField;
    private EditText mTimeField;
    private EditText mLocationField;
    private Button mCreateButton;
    private DatabaseReference mDatabase;

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
        mDateField = (EditText) v.findViewById(R.id.new_event_date);
        mDateField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                //blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after){
                mEvent.setDate(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s){
                //blank
            }
        });

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
                Log.d("Tag", "Database write attempt.");
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("events").child(mEvent.getId().toString()).setValue(mEvent);
                Toast.makeText(NewEventFragment.this.getActivity(), "Event Created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewEventFragment.this.getActivity(), organizationSignInPage.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
