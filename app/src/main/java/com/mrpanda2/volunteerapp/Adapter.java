package com.mrpanda2.volunteerapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter {
    ArrayList<VolunteerSession> sessions;

    public Adapter(ArrayList<VolunteerSession> items){
        this.sessions = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Adapter", "ViewInflation");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.session_row_item, parent, false);
        return new ItemHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemHolder)holder).mName.setText(sessions.get(position).getVolName());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm aa");
        Timestamp timeIn = Timestamp.valueOf(sessions.get(position).getTimeIn());
        String clockIn = format.format(timeIn);
        ((ItemHolder)holder).mIn.setText(clockIn);
        String clockOut;
        if (!sessions.get(position).getTimeOut().equals("TBD")){
            Timestamp timeOut = Timestamp.valueOf(sessions.get(position).getTimeOut());
            clockOut = format.format(timeOut);
        }
        else{
            clockOut = "TBD";
        }
        ((ItemHolder)holder).mOut.setText(clockOut);
        ((ItemHolder)holder).mDuration.setText(Long.toString(sessions.get(position).getDuration()));
    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "Returned Item Count");
        return sessions.size();

    }

    private class ItemHolder extends RecyclerView.ViewHolder{
        TextView mNameHead;
        TextView mInHead;
        TextView mOutHead;
        TextView mDurationHead;
        TextView mName;
        TextView mIn;
        TextView mOut;
        TextView mDuration;
        Button mButton;


        public ItemHolder (View itemView){
            super(itemView);
            mName = itemView.findViewById(R.id.session_list_name);
            mIn = itemView.findViewById(R.id.session_list_in);
            mOut = itemView.findViewById(R.id.session_list_out);
            mDuration = itemView.findViewById(R.id.session_list_duration);
            mNameHead = itemView.findViewById(R.id.session_list_name_header);
            mInHead = itemView.findViewById(R.id.session_list_in_header);
            mOutHead = itemView.findViewById(R.id.session_list_out_header);
            mDurationHead = itemView.findViewById(R.id.session_list_duration_header);
            mButton = itemView.findViewById(R.id.forceoutbutton);
        }
    }

}
