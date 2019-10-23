package com.mrpanda2.volunteerapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VolunteerSignInActivity extends AppCompatActivity {
    private TextView mName;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_sign_in);
        Log.d("Fragment Lifecyle", "Working!");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mName = findViewById(R.id.volunteer_name);
        mName.setText(mUser.getDisplayName());
    }

}
