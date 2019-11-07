package com.mrpanda2.volunteerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ViewVolunteerAttendanceActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_volunteer_data);
        //launch edit event fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.view_event_fragment_container);

        if (fragment == null){
            fragment = new ViewVolunteerAttendanceInfoFragment();
            fm.beginTransaction()
                    .add(R.id.view_event_fragment_container, fragment)
                    .commit();

        }
}
}
