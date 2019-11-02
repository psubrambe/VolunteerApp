package com.mrpanda2.volunteerapp;

import android.content.ClipData;
import android.content.Context;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VolAdapter extends RecyclerView.Adapter {
    ArrayList<String> vols;
    Context mContext;
    DatabaseReference mDatabase;

    public VolAdapter(ArrayList<String> vols, Context context){
        this.vols = vols;
        this.mContext = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Adapter", "ViewInflation");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.volunteer_row_item, parent, false);
        return new ItemHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        /*((ItemHolder)holder).mName.setText(sessions.get(position).getVolName());
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm aa");
        final Timestamp timeIn = Timestamp.valueOf(sessions.get(position).getTimeIn());
        String clockIn = format.format(timeIn);
        ((ItemHolder)holder).mIn.setText(clockIn);
        final String clockOut;
        if (!sessions.get(position).getTimeOut().equals("TBD")){
            Timestamp timeOut = Timestamp.valueOf(sessions.get(position).getTimeOut());
            clockOut = format.format(timeOut);
        }
        else{
            clockOut = "TBD";
        }
        ((ItemHolder)holder).mOut.setText(clockOut);
        ((ItemHolder)holder).mDuration.setText(Long.toString(sessions.get(position).getDuration()));
        ((ItemHolder)holder).mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessions.get(position).getActive() == 1){
                    Toast.makeText(mContext, "Already clocked out.", Toast.LENGTH_SHORT).show();
                }
                else {

                    Date date = new Date();
                    final Timestamp timeOut = new Timestamp(date.getTime());
                    String timeOutStringFormatted = format.format(timeOut);
                    sessions.get(position).setTimeOut(timeOutStringFormatted);
                    ((ItemHolder)holder).mOut.setText(timeOutStringFormatted);

                    long milliseconds = timeOut.getTime() - timeIn.getTime();
                    final long minutes = milliseconds / 60000;
                    sessions.get(position).setDuration(minutes);
                    ((ItemHolder)holder).mDuration.setText(Long.toString(minutes));

                    sessions.get(position).closeSession();

                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("sessions").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                if (snapshot.getKey().equals(sessions.get(position).getmIdString())){
                                    Log.d("Adapter", "Modified Session");
                                    mDatabase.child("sessions").child(snapshot.getKey()).child("timeOut").setValue(timeOut.toString());
                                    mDatabase.child("sessions").child(snapshot.getKey()).child("duration").setValue(minutes);
                                    mDatabase.child("sessions").child(snapshot.getKey()).child("active").setValue(sessions.get(position).getActive());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
            }
        });*/
        mDatabase.child("volunteers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.getKey().equals(vols.get(position))){
                        ((ItemHolder)holder).mVolName.setText(snapshot.child("name").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ((ItemHolder)holder).mVolSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgAnalysisFragment.selectedVol = vols.get(position);
                Toast.makeText(mContext, "Selected", Toast.LENGTH_SHORT).show();
            }
        });





    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "Returned Item Count");
        return vols.size();

    }

    private class ItemHolder extends RecyclerView.ViewHolder{
        TextView mVolName;
        Button mVolSelector;


        public ItemHolder (View itemView){
            super(itemView);
            mVolName = itemView.findViewById(R.id.volunteer_recycler_name);
            mVolSelector = itemView.findViewById(R.id.select_vol_button);
        }
    }

}
