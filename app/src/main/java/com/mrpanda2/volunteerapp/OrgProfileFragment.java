package com.mrpanda2.volunteerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OrgProfileFragment extends Fragment {
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private TextView mOrgName;
    private Button mNewEventButton;
    private Button showEventButton;
    private Button mSignOutButton;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logiin, container, false);

        mOrgName = v.findViewById(R.id.OrgNameSpace);

        mOrgName.setText(mUser.getDisplayName());

        mNewEventButton = v.findViewById(R.id.newEventButton);
        mNewEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrgProfileFragment.this.getActivity(), NewEventActivity.class);
                startActivity(intent);
            }
        });
        showEventButton = v.findViewById(R.id.Events);
        showEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrgProfileFragment.this.getActivity(), ShowEventActivity.class);
                startActivity(intent);
            }
        });
        mSignOutButton = v.findViewById(R.id.sign_out_button);
        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(OrgProfileFragment.this.getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

}
