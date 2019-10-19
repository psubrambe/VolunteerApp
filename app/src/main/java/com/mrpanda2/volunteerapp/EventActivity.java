package com.mrpanda2.volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

public class EventActivity extends AppCompatActivity {



    public static final String EXTRA_EVENT_ID = "com.mrpanda2.volunteerapp.event_id";


    public static Intent newIntent(Context packageContext, UUID eventId){
        Intent intent = new Intent(packageContext, EventActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, eventId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.event_fragment_container);

        if (fragment == null){
            fragment = new EventFragment();
            fm.beginTransaction()
                    .add(R.id.event_fragment_container, fragment)
                    .commit();
        }
    }
}
