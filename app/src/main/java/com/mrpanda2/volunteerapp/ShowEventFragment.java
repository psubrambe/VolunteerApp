package com.mrpanda2.volunteerapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String dataSnap = ds.getKey();
                    final String date = ds.child("date").getValue(String.class);
                    final String location = ds.child("location").getValue(String.class);
                    final String name = ds.child("name").getValue(String.class);
                    final String time = ds.child("time").getValue(String.class);
                    Log.d("TAG", date + " / " + location  + " / " + name + "/" + time);
                    list.add(time);
                    TableRow row = new TableRow(getActivity());
                    row.setWeightSum(4);
                    TextView tv1 = new TextView(getActivity());
                    tv1.setText(date + " / " + location  + " / " + name + "/" + time);
                    tv1.setTextSize(20);
                    tv1.setMaxLines(3);
                    tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT,1.0f));
                    row.addView(tv1);
                    row.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v){
                            EditEventFragment secondFragment = new EditEventFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("date", date);
                            bundle.putString("location", location);
                            bundle.putString("name", name);
                            bundle.putString("time", time);
                            bundle.putString("dataSnap", dataSnap);
                            secondFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.show_event_fragment_container, secondFragment).addToBackStack(null).commit();
                        }
                    });

                    tableLayout.addView(row);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
        return v;
    }

}

