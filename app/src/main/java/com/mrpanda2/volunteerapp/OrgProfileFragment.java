package com.mrpanda2.volunteerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class OrgProfileFragment extends Fragment {
    private Button mNewEventButton;
    private Button showEventButton;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logiin, container, false);

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

        return v;
    }

}
