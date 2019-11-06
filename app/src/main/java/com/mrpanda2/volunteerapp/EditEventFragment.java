package com.mrpanda2.volunteerapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditEventFragment extends Fragment {
    private Event mEvent;
    private EditText mNameField;
    private EditText mDateField;
    private EditText mTimeField;
    private EditText mLocationField;
    private EditText mOrgField;
    private Button mUpdateButton;
    private Button mDeleteButton;
    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    private String mEventId;
    private ArrayList<VolunteerSession> sessions;
    private TextView mAttendees;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mEvent = new Event();
        Bundle bundle = getArguments();
        //final String dataSnap = String.valueOf(bundle.getString("dataSnap"));
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.edit_event_fragment, container, false);
        mNameField = v.findViewById(R.id.new_event_name);
        mDateField = v.findViewById(R.id.new_event_date);
        mTimeField = v.findViewById(R.id.new_event_time);
        mLocationField = v.findViewById(R.id.new_event_location);
        mOrgField = v.findViewById(R.id.new_event_org);
        mUpdateButton = v.findViewById(R.id.update_event_button);
        mDeleteButton = v.findViewById(R.id.delete_event_button);
        mRecyclerView = v.findViewById(R.id.org_session_list);
        mAttendees = v.findViewById(R.id.event_attendees);
        mRecyclerView.setHasFixedSize(true);
        sessions = new ArrayList<VolunteerSession>();
        Bundle bundle = getArguments();
        mNameField.setText(String.valueOf(bundle.getString("name")));
        mDateField.setText(String.valueOf(bundle.getString("date")));
        mTimeField.setText(String.valueOf(bundle.getString("time")));
        mLocationField.setText(String.valueOf(bundle.getString("location")));
        mOrgField.setText(String.valueOf(bundle.getString("org")));
        String attendLine = (bundle.getLong("attendees")) + " volunteers plan to attend.";
        mAttendees.setText(attendLine);


        mEventId = String.valueOf(bundle.getString("dataSnap"));

        final String dataSnap = String.valueOf(bundle.getString("dataSnap"));

        mUpdateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = mNameField.getText().toString();
                String date = mDateField.getText().toString();
                String time = mTimeField.getText().toString();
                String location = mLocationField.getText().toString();
                String org = mOrgField.getText().toString();
                mDatabase.child("events").child(dataSnap).child("name").setValue(name);
                mDatabase.child("events").child(dataSnap).child("date").setValue(date);
                mDatabase.child("events").child(dataSnap).child("time").setValue(time);
                mDatabase.child("events").child(dataSnap).child("location").setValue(location);
                mDatabase.child("events").child(dataSnap).child("org").setValue(org);
                Toast.makeText(EditEventFragment.this.getActivity(), "Event Updated", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mDatabase.child("events").child(dataSnap).removeValue();
                Toast.makeText(EditEventFragment.this.getActivity(), "Event Deleted", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();
            }
        });


        //populate sessions array with matching sessions
        mDatabase.child("sessions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("eventId").getValue().equals(dataSnap)) {
                        VolunteerSession volSess = new VolunteerSession();
                        volSess.setIdString(snapshot.getKey());
                        volSess.setVolName(snapshot.child("volName").getValue().toString());
                        volSess.setTimeIn(snapshot.child("timeIn").getValue().toString());
                        volSess.setTimeOut(snapshot.child("timeOut").getValue().toString());

                        if (snapshot.child("active").exists()) {
                            long status = (long) snapshot.child("active").getValue();
                            volSess.setClosedStatus(status);
                        }

                        long duration = (long) snapshot.child("duration").getValue();
                        volSess.setDuration(duration);
                        sessions.add(volSess);
                        Log.d("TAG", "Added session to list");
                    }
                }
                mRecyclerView.setLayoutManager(new LinearLayoutManager(EditEventFragment.this.getContext()));
                mRecyclerView.setAdapter(new Adapter(sessions, EditEventFragment.this.getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

}
