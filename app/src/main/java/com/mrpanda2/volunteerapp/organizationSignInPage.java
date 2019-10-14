package com.mrpanda2.volunteerapp;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class organizationSignInPage extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_logiin);
        Log.d("Fragment Lifecyle", "Working!");
    }
}
