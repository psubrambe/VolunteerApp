package com.mrpanda2.volunteerapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PrevSessAdapter extends RecyclerView.Adapter {
    ArrayList<VolunteerSession> sessions;
    Context mContext;
    DatabaseReference mDatabase;

    public PrevSessAdapter(ArrayList<VolunteerSession> sessions, Context context){
        this.sessions = sessions;
        this.mContext = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Adapter", "ViewInflation");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.previous_session_row, parent, false);
        return new ItemHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {


        ((ItemHolder)holder).mEvent.setText(sessions.get(position).getEventName());
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm aa");
        final Timestamp timeIn = Timestamp.valueOf(sessions.get(position).getTimeIn());
        String clockIn = format.format(timeIn);
        ((ItemHolder)holder).mIn.setText(clockIn);
        String clockOut;
        if(sessions.get(position).getTimeOut().equals("TBD")){
            clockOut = "TBD";
        }
        else {
            final Timestamp timeOut = Timestamp.valueOf(sessions.get(position).getTimeOut());
            clockOut = format.format(timeOut);
        }
        ((ItemHolder)holder).mOut.setText(clockOut);
        ((ItemHolder)holder).mDuration.setText(Long.toString(sessions.get(position).getDuration()) + " mins");

    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "Returned Item Count");
        return sessions.size();

    }

    private class ItemHolder extends RecyclerView.ViewHolder{
        TextView mEventHeader;
                TextView mInHeader;
        TextView mOutHeader;
                TextView mDurHeader;
        TextView mEvent;
                TextView mIn;
        TextView mOut;
                TextView mDuration;


        public ItemHolder (View itemView){
            super(itemView);
            mEventHeader = itemView.findViewById(R.id.prev_list_event_header);
            mInHeader = itemView.findViewById(R.id.prev_list_in_header);
            mOutHeader = itemView.findViewById(R.id.prev_list_out_header);
            mDurHeader = itemView.findViewById(R.id.prev_list_duration_header);
            mEvent = itemView.findViewById(R.id.prev_list_event);
            mIn = itemView.findViewById(R.id.prev_list_in);
            mOut = itemView.findViewById(R.id.prev_list_out);
            mDuration = itemView.findViewById(R.id.prev_list_duration);

        }
    }

}