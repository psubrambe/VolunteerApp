package com.mrpanda2.volunteerapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class NewEventFragment extends Fragment {
    private Event mEvent;
    private EditText mNameField;
    private TextView mDateField;
    private Button mDateButton;
    private TextView mTimeField;
    private Button mTimeButton;
    private EditText mLocationField;
    private Button mCreateButton;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mEvent = new Event();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_new_event, container, false);

        mEvent.setOrg(mUser.getDisplayName());
        mEvent.setOrgId(mUser.getUid());

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

        mDateField = v.findViewById(R.id.new_event_date);
        mDateButton = v.findViewById(R.id.new_event_date_button);
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                handleDate();
            }
        });
        mTimeField = v.findViewById(R.id.new_event_time);
        mTimeButton = v.findViewById(R.id.new_event_time_button);
        mTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                handleTime();
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

                if (mEvent.getDate() == null || mEvent.getName() == null || mEvent.getLocation() == null || mEvent.getTime() == null){
                    Toast.makeText(NewEventFragment.this.getActivity(), "Field is empty.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("Tag", "Database write attempt.");
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("events").child(mEvent.getId().toString()).setValue(mEvent);
                    Toast.makeText(NewEventFragment.this.getActivity(), "Event Created", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(NewEventFragment.this.getActivity(), organizationSignInPage.class);
                startActivity(intent);*/

                    //Intent intent = EventActivity.newIntent(NewEventFragment.this.getActivity(), mEvent.getId());
                    EditEventFragment createdFragment = new EditEventFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("date", mEvent.getDate());
                    bundle.putString("location", mEvent.getLocation());
                    bundle.putString("name", mEvent.getName());
                    bundle.putString("time", mEvent.getTime());
                    bundle.putString("org", mEvent.getOrg());
                    bundle.putLong("attendees", mEvent.getAttendees());
                    bundle.putString("dataSnap", mEvent.getId().toString());
                    createdFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.new_event_fragment_container, createdFragment).addToBackStack(null).commit();
                    //Intent intent = new Intent(NewEventFragment.this.getActivity(), createdFragment.getClass());
                    //startActivity(intent);
                }
            }
        });

        return v;
    }

    private void handleDate() {
        Calendar cal = Calendar.getInstance();
        int YEAR = cal.get(Calendar.YEAR);
        int MONTH = cal.get(Calendar.MONTH);
        int DATE = cal.get(Calendar.DATE);

        DatePickerDialog datePicker = new DatePickerDialog(NewEventFragment.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                Calendar calFrag = Calendar.getInstance();
                calFrag.set(Calendar.YEAR, year);
                calFrag.set(Calendar.MONTH, month);
                calFrag.set(Calendar.DATE, dayOfMonth);
                month = month + 1;
                String dateText = month + "/" + dayOfMonth + "/" + year;

                mDateField.setText(dateText);
                mEvent.setDate(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePicker.show();
    }

    private void handleTime(){
        Calendar cal = Calendar.getInstance();
        int HOUR = cal.get(Calendar.HOUR);
        int MINUTE = cal.get(Calendar.MINUTE);
        boolean is24Hour = DateFormat.is24HourFormat(NewEventFragment.this.getActivity());

        TimePickerDialog timePicker = new TimePickerDialog(NewEventFragment.this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                Calendar calFrag = Calendar.getInstance();
                calFrag.set(Calendar.HOUR, hour);
                calFrag.set(Calendar.MINUTE, minute);
                String timeText = DateFormat.format("h:mm a", calFrag).toString();

                mTimeField.setText(timeText);
                mEvent.setTime(timeText);
            }
        }, HOUR, MINUTE, is24Hour);


        timePicker.show();

    }


}
