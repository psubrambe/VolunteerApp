package com.mrpanda2.volunteerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        mEvent = new Event();
        final UUID eventId = (UUID) getActivity().getIntent()
                .getSerializableExtra(EventActivity.EXTRA_EVENT_ID);
        mEvent.setId(eventId);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference event = mDatabase.child("events").child(eventId.toString());

        event.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Tag", "ACCESSED");

                mEvent.setName(dataSnapshot.child("name").getValue().toString());
                mEvent.setDate(dataSnapshot.child("date").getValue().toString());
                mEvent.setTime(dataSnapshot.child("time").getValue().toString());
                mEvent.setLocation(dataSnapshot.child("location").getValue().toString());

                mName.setText(mEvent.getName());
                mDate.setText(mEvent.getDate());
                mTime.setText(mEvent.getTime());
                mLocation.setText(mEvent.getLocation());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            //nothing
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);

        mName =  v.findViewById(R.id.event_name);
        mDate =  v.findViewById(R.id.event_date);
        mTime =  v.findViewById(R.id.event_time);
        mLocation =  v.findViewById(R.id.event_location);

        mDeleteButton =  v.findViewById(R.id.delete_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("events").child(mEvent.getId().toString()).removeValue();
                Toast.makeText(EventFragment.this.getActivity(), "Event Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EventFragment.this.getActivity(), organizationSignInPage.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
