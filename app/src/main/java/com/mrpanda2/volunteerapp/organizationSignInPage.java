package com.mrpanda2.volunteerapp;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class organizationSignInPage extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_profile);
        Log.d("Fragment Lifecyle", "Working!");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.org_profile_fragment_container);

        if (fragment == null){
            fragment = new OrgProfileFragment();
            fm.beginTransaction()
                    .add(R.id.org_profile_fragment_container, fragment)
                    .commit();
        }
    }
}
