package com.mrpanda2.volunteerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ShowEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_event);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.show_event_fragment_container);

        if (fragment == null){
            fragment = new ShowEventFragment();
            fm.beginTransaction()
                    .add(R.id.show_event_fragment_container, fragment)
                    .commit();


        }
    }
}