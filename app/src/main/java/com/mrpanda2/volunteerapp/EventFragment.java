package com.mrpanda2.volunteerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class EventFragment extends Fragment {
    private Event mEvent;
    private TextView mName;
    private TextView mDate;
    private TextView mTime;
    private TextView mLocation;
    private Button mDeleteButton;
    private DatabaseReference mDatabase;

    @Override public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID eventId = (UUID) getActivity().getIntent()
                .getSerializableExtra(EventActivity.EXTRA_EVENT_ID);
        //mEvent = //EVENT LIST.get(getActivity()).getEvent(eventId)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);

        mName = (TextView) v.findViewById(R.id.event_name);
        mDate = (TextView) v.findViewById(R.id.event_date);
        mTime = (TextView) v.findViewById(R.id.event_time);
        mLocation = (TextView) v.findViewById(R.id.event_location);

        mName.setText(mEvent.getName());
        mDate.setText(mEvent.getDate());
        mTime.setText(mEvent.getTime());
        mLocation.setText(mEvent.getLocation());

        mDeleteButton = (Button) v.findViewById(R.id.delete_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("events").child(mEvent.getId().toString()).removeValue();
            }
        });

        return v;

    }

}
