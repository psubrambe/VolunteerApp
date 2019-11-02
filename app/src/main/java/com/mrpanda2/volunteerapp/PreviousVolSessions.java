package com.mrpanda2.volunteerapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PreviousVolSessions extends AppCompatActivity {

    RecyclerView mRecycler;
    DatabaseReference mDatabase;
    FirebaseUser mUser;
    ArrayList<VolunteerSession> mSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previous_sessions_activity);
        mSessions = new ArrayList<>();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("sessions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.child("volId").getValue().equals(mUser.getUid())){
                        Log.d("PREVIOUS", "Added session");
                        VolunteerSession sess = new VolunteerSession();
                        sess.setEventName(snapshot.child("eventName").getValue().toString());
                        String inTime = snapshot.child("timeIn").getValue().toString();
                        String outTime = snapshot.child("timeOut").getValue().toString();
                        long duration = (long) snapshot.child("duration").getValue();
                        sess.setDuration(duration);
                        sess.setTimeIn(inTime);
                        sess.setTimeOut(outTime);
                        mSessions.add(sess);
                    }
                }
                mRecycler = findViewById(R.id.previous_recycler);
                mRecycler.setLayoutManager(new LinearLayoutManager(PreviousVolSessions.this));
                mRecycler.setAdapter(new PrevSessAdapter(mSessions, PreviousVolSessions.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
