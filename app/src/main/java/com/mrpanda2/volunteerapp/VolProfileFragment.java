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

public class VolProfileFragment extends Fragment {

    private FirebaseUser mUser;
    private TextView mVolName;
    private Button mShowEventButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.volunteer_sign_in, container, false);

        mVolName = v.findViewById(R.id.volunteer_name);
        mVolName.setText(mUser.getDisplayName());

        mShowEventButton = v.findViewById(R.id.vol_events_button);
        mShowEventButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(VolProfileFragment.this.getActivity(), ShowEventActivity.class);
                startActivity(intent);
            }
        });

        return v;

    }


}
