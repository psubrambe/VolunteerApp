package com.mrpanda2.volunteerapp;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrgAnalysisFragment extends Fragment {
    TextView mDateHeader;
    RecyclerView mRecycler;
    TextView mAllHeader;
    TextView mAllFigures;
    TextView mIndvHeader;
    TextView mIndvFigures;
    Date mStartDate;
    Date mEndDate;
    DatabaseReference mDatabase;
    FirebaseUser mUser;
    ArrayList<String> mEvents;
    ArrayList<String> mVols;
    long totalMins;
    long totalSessions;
    long indvMins;
    long indvSessions;
    public static String selectedVol;
    Button mCalculateIndv;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mStartDate  = OrgAnalysisActivity.mStart;
        mEndDate = OrgAnalysisActivity.mEnd;
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        String id = mUser.getUid();
        mEvents = new ArrayList<>();
        mVols = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d("ORGANALYSIS", "CHECKPOINT");
        mDatabase.child("events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //convert and check date
                    String eventDateString = snapshot.child("date").getValue().toString();
                    Date eventDate = null;
                    try {
                        eventDate = new SimpleDateFormat("MM/dd/yyyy").parse(eventDateString);
                    }
                    catch(ParseException e){
                        e.printStackTrace();
                    }

                    if (snapshot.child("orgId").getValue().equals(mUser.getUid()) && eventDate.compareTo(mStartDate) >= 0 && eventDate.compareTo(mEndDate) <= 0){
                        Log.d("ORGANALYSIS", "Added Event to List");
                        mEvents.add(snapshot.getKey());
                    }
                }
                mDatabase.child("sessions").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            if (mEvents.contains(snapshot.child("eventId").getValue())){
                                Log.d("ORGANALYSIS", "Added session figures");
                                totalSessions ++;
                                long minutes = (long) snapshot.child("duration").getValue();
                                totalMins += minutes;
                                String volId = snapshot.child("volId").getValue().toString();
                                if (!mVols.contains(volId)){
                                    mVols.add(volId);
                                }
                            }
                        }

                        long displayMins = totalMins;
                        long displayHours = totalMins / 60;
                        displayMins = displayMins % 60;

                        String totalFigures = displayHours + " hrs " + displayMins + " mins " + "      " +totalSessions + " sessions";
                        mAllFigures.setText(totalFigures);

                        mRecycler.setLayoutManager(new LinearLayoutManager(OrgAnalysisFragment.this.getContext()));
                        mRecycler.setAdapter(new VolAdapter(mVols, OrgAnalysisFragment.this.getContext()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.org_analysis_fragment, container, false);
        mDateHeader = v.findViewById(R.id.analysis_date_header);
        mDateHeader.setText("Hours donated from " + OrgAnalysisActivity.mStartString.toString() + " to " + OrgAnalysisActivity.mEndString.toString());
        mAllHeader = v.findViewById(R.id.analysis_all_header);
        mAllFigures = v.findViewById(R.id.analysis_all_figures);
        mIndvHeader = v.findViewById(R.id.analysis_indv_header);
        mIndvFigures = v.findViewById(R.id.analysis_indv_figures);
        mRecycler = v.findViewById(R.id.analysis_recycler);
        mCalculateIndv = v.findViewById(R.id.calculate_indv_button);
        mCalculateIndv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedVol != null) {
                    mDatabase.child("sessions").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (snapshot.child("volId").getValue().equals(selectedVol) && mEvents.contains(snapshot.child("eventId").getValue())) {
                                    Log.d("ORGANALYSIS", "Added indv session figures");
                                    indvSessions++;
                                    long minutes = (long) snapshot.child("duration").getValue();
                                    indvMins += minutes;

                                }
                            }

                            long displayMins = indvMins;
                            long displayHours = indvMins / 60;
                            displayMins = displayMins % 60;

                            String totalFigures = displayHours + " hrs " + displayMins + " mins " + "      " + totalSessions + " sessions";
                            mIndvFigures.setText(totalFigures);
                            indvMins = 0;
                            indvSessions = 0;

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(OrgAnalysisFragment.this.getActivity(), "No Volunteer Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        return v;
    }



}
