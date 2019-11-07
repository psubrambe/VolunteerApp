package com.mrpanda2.volunteerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowEventFragment extends Fragment {
    private Event mEvent;

    private EditText mNameField;
    private EditText mDateField;
    private EditText mTimeField;
    private EditText mLocationField;
    private Button mCreateButton;
    private DatabaseReference mDatabase;
    private TableLayout tableLayout;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mEvent = new Event();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.show_event_fragment, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("events");
        final TableLayout tableLayout =  v.findViewById(R.id.maintable);
        ValueEventListener valueEventListener = new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<String>();

                //get user typeid
                SharedPreferences sharedPref = ShowEventFragment.this.getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
                String userType = sharedPref.getString(getString(R.string.typeid), "default");



                if (userType.equals("vol")) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        final String dataSnap = ds.getKey();
                        final String date = ds.child("date").getValue(String.class);
                        final String location = ds.child("location").getValue(String.class);
                        final String name = ds.child("name").getValue(String.class);
                        final String time = ds.child("time").getValue(String.class);
                        final String org = ds.child("org").getValue(String.class);
                        Log.d("TAG", date + " / " + location + " / " + name + "/" + time);
                        list.add(time);
                        TableRow row = new TableRow(getActivity());
                        row.setWeightSum(4);
                        TextView tv1 = new TextView(getActivity());
                        tv1.setText(date + " / " + location + " / " + name + "/" + time);
                        tv1.setTextSize(20);
                        tv1.setMaxLines(3);
                        tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                        row.addView(tv1);
                        row.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        row.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                EventFragment secondFragment = new EventFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("date", date);
                                bundle.putString("location", location);
                                bundle.putString("name", name);
                                bundle.putString("time", time);
                                bundle.putString("org", org);
                                bundle.putString("dataSnap", dataSnap);
                                secondFragment.setArguments(bundle);
                                getFragmentManager().beginTransaction().replace(R.id.show_event_fragment_container, secondFragment).addToBackStack(null).commit();
                            }
                        });
                        TableRow row2 = new TableRow(getActivity());
                        row2.setBackgroundColor(Color.BLACK);
                        TextView tv2 = new TextView(getActivity());
                        tv2.setText(" ");
                        tv2.setTextSize(3);
                        tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                        row2.addView(tv2);
                        TableRow row3 = new TableRow(getActivity());
                        row3.setBackgroundColor(Color.BLACK);
                        TextView tv3 = new TextView(getActivity());
                        tv3.setText(" ");
                        tv3.setTextSize(3);
                        tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                        row3.addView(tv3);
                        tableLayout.addView(row3);
                        tableLayout.addView(row);
                        tableLayout.addView(row2);
                    }
                }

                if (userType.equals("org")) {

                    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        final String orgId = ds.child("orgId").getValue(String.class);

                        if (orgId != null && orgId.equals(mUser.getUid())){

                            final String dataSnap = ds.getKey();
                            final String date = ds.child("date").getValue(String.class);
                            final String location = ds.child("location").getValue(String.class);
                            final String name = ds.child("name").getValue(String.class);
                            final String time = ds.child("time").getValue(String.class);
                            final String org = ds.child("org").getValue(String.class);
                            final long attendees = ds.hasChild("attendees") ?  ds.child("attendees").getValue(long.class) :  0;

                            Log.d("TAG", date + " / " + location + " / " + name + "/" + time);
                            list.add(time);
                            TableRow row = new TableRow(getActivity());
                            row.setWeightSum(4);
                            TextView tv1 = new TextView(getActivity());
                            tv1.setText(date + " / " + location + " / " + name + "/" + time);
                            tv1.setTextSize(20);
                            tv1.setMaxLines(3);
                            tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                            row.addView(tv1);
                            row.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            row.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    EditEventFragment secondFragment = new EditEventFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("date", date);
                                    bundle.putString("location", location);
                                    bundle.putString("name", name);
                                    bundle.putString("time", time);
                                    bundle.putString("org", org);
                                    bundle.putLong("attendees", attendees);
                                    bundle.putString("dataSnap", dataSnap);
                                    secondFragment.setArguments(bundle);
                                    getFragmentManager().beginTransaction().replace(R.id.show_event_fragment_container, secondFragment).addToBackStack(null).commit();
                                }
                            });
                            TableRow row2 = new TableRow(getActivity());
                            row2.setBackgroundColor(Color.BLACK);
                            TextView tv2 = new TextView(getActivity());
                            tv2.setText(" ");
                            tv2.setTextSize(3);
                            tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                            row2.addView(tv2);
                            TableRow row3 = new TableRow(getActivity());
                            row3.setBackgroundColor(Color.BLACK);
                            TextView tv3 = new TextView(getActivity());
                            tv3.setText(" ");
                            tv3.setTextSize(3);
                            tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                            row3.addView(tv3);
                            tableLayout.addView(row3);
                            tableLayout.addView(row);
                            tableLayout.addView(row2);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
        return v;
    }

}