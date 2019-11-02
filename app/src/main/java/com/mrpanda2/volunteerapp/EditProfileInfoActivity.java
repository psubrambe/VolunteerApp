package com.mrpanda2.volunteerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class EditProfileInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_container);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.edit_profile_container);

        if (fragment == null){
            fragment = new EditProfileInfoFragment();
            fm.beginTransaction()
                    .add(R.id.edit_profile_container, fragment)
                    .commit();

        }
    }
}
