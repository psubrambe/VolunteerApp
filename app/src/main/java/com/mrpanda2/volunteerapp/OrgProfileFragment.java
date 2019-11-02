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
    private TextView mOrgName;
    private Button mNewEventButton;
    private Button showEventButton;
    private Button mCalculateButton;
    private Button mMapButton;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
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
        mCalculateButton = v.findViewById(R.id.calculate_button);
        mCalculateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(OrgProfileFragment.this.getActivity(), OrgAnalysisActivity.class);
                startActivity(intent);
            }
        });
        mMapButton = v.findViewById(R.id.MapB);
        mMapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(OrgProfileFragment.this.getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
