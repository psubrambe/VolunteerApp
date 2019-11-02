package com.mrpanda2.volunteerapp;

import android.content.Context;
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

import java.util.ArrayList;

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
