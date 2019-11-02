package com.mrpanda2.volunteerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class EditEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);
        //launch edit event fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.edit_event_fragment_container);

        if (fragment == null){
            fragment = new EditEventFragment();
            fm.beginTransaction()
                    .add(R.id.edit_event_fragment_container, fragment)
                    .commit();

        }
    }
}
