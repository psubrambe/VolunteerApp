package com.mrpanda2.volunteerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class NewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.new_event_fragment_container);

        if (fragment == null){
            fragment = new NewEventFragment();
            fm.beginTransaction()
                    .add(R.id.new_event_fragment_container, fragment)
                    .commit();
        }
    }
}
