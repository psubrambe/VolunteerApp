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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class EventFragment extends Fragment {
    private Event mEvent;
    private TextView mName;
    private TextView mDate;
    private TextView mTime;
    private TextView mLocation;
    private TextView mOrg;
    private Button mClockInButton;
    private Button mClockOutButton;
    private VolunteerSession mSession;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    private int sessionActive;

    @Override public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        /*mUser = FirebaseAuth.getInstance().getCurrentUser();
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
                mEvent.setOrg(dataSnapshot.child("organization").getValue().toString());

                mName.setText(mEvent.getName());
                mDate.setText(mEvent.getDate());
                mTime.setText(mEvent.getTime());
                mLocation.setText(mEvent.getLocation());
                mOrg.setText(mEvent.getOrg());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            //nothing
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);

        mName =  v.findViewById(R.id.event_name);
        mDate =  v.findViewById(R.id.event_date);
        mTime =  v.findViewById(R.id.event_time);
        mLocation =  v.findViewById(R.id.event_location);
        mOrg = v.findViewById(R.id.event_org);
        mClockInButton = v.findViewById(R.id.clock_in_button);
        mClockOutButton = v.findViewById(R.id.clock_out_button);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        final Bundle bundle = getArguments();
        mName.setText(String.valueOf(bundle.getString("name")));
        mDate.setText(String.valueOf(bundle.getString("date")));
        mTime.setText(String.valueOf(bundle.getString("time")));
        mLocation.setText(String.valueOf(bundle.getString("location")));
        mOrg.setText(String.valueOf(bundle.getString("org")));


        mClockInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (mSession != null && sessionActive == 1){
                    Toast.makeText(EventFragment.this.getActivity(), "Current session not closed.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //get date and timestamp
                    sessionActive = 1;
                    Date date = new Date();
                    Timestamp timeIn = new Timestamp(date.getTime());
                    //save basic vol and event info
                    mSession = new VolunteerSession();
                    mSession.setEventId(String.valueOf(bundle.getString("dataSnap")));
                    mSession.setEventName(mName.getText().toString());
                    mSession.setVolId(mUser.getUid());
                    mSession.setVolName(mUser.getDisplayName());
                    //get date from calender
                    Calendar cal = Calendar.getInstance();
                    int YEAR = cal.get(Calendar.YEAR);
                    int MONTH = cal.get(Calendar.MONTH);
                    MONTH += 1;
                    int DATE = cal.get(Calendar.DATE);
                    String dateText = MONTH + "/" + DATE + "/" + YEAR;
                    mSession.setDate(dateText);
                    //set time in from timestamp (manipulate for readabliity on request)
                    mSession.setTimeIn(timeIn.toString());
                    //set time out to null
                    mSession.setTimeOut("TBD");
                    //save session item to database
                    mDatabase.child("sessions").child(mSession.getId().toString()).setValue(mSession);
                    Toast.makeText(EventFragment.this.getActivity(), "Clocked In", Toast.LENGTH_SHORT).show();
                }

            }
        });


        mClockOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (mSession == null || mSession.getDuration() != 0 || sessionActive == 0){
                    Toast.makeText(EventFragment.this.getActivity(), "No active session.", Toast.LENGTH_SHORT).show();
                }
                else {
                    sessionActive = 0;
                    Date date = new Date();
                    Timestamp timeOut = new Timestamp(date.getTime());
                    mSession.setTimeOut(timeOut.toString());
                    Timestamp timeIn = Timestamp.valueOf(mSession.getTimeIn());
                    //calculate duration in minutes
                    long milliseconds = timeOut.getTime() - timeIn.getTime();
                    long minutes = milliseconds / 60000;
                    mSession.setDuration(minutes);
                    mDatabase.child("sessions").child(mSession.getId().toString()).child("timeOut").setValue(mSession.getTimeOut());
                    mDatabase.child("sessions").child(mSession.getId().toString()).child("duration").setValue(mSession.getDuration());
                    Toast.makeText(EventFragment.this.getActivity(), "Clocked Out", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }
}
