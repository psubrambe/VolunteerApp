package com.mrpanda2.volunteerapp;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class volunteerSignInPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_sign_in);
        Log.d("Fragment Lifecyle", "Working!");
    }
}
