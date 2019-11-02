package com.mrpanda2.volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class EditProfileInfoFragment extends Fragment {
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private TextView mName;
    private TextView mPassword;
    private TextView mEmail;
    private Button mShowEventButton;
    private Button mUpdateButton;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.edit_profile,container, false);

        mName = v.findViewById(R.id.OrgName);
        mEmail = v.findViewById(R.id.OrgEmail);
        mPassword = v.findViewById(R.id.OrgPassword);
        SharedPreferences sharedPref = EditProfileInfoFragment.this.getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String userType = sharedPref.getString(getString(R.string.typeid), "default");

        if (userType.equals("vol")) {
            mName.setText(mUser.getDisplayName());
            mEmail.setText(mUser.getEmail());
            final String name = mName.getText().toString();

            mUpdateButton = v.findViewById(R.id.UpdateButton);
            mUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.child("volunteers").child(mUser.getUid()).child("name").setValue(name);
                    Toast.makeText(EditProfileInfoFragment.this.getActivity(), "Event Updated", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStackImmediate();
                }
            });
        }
        if (userType.equals("org")) {
            mName.setText(mUser.getDisplayName());
            mEmail.setText(mUser.getEmail());
            final String name = mName.getText().toString();

            mUpdateButton = v.findViewById(R.id.UpdateButton);
            mUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.child("organizations").child(mUser.getUid()).child("name").setValue(name);
                    Toast.makeText(EditProfileInfoFragment.this.getActivity(), "Event Updated", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStackImmediate();
                }
            });
        }
        return v;

    }
}
