package com.mrpanda2.volunteerapp;

import android.widget.DatePicker;

import java.util.Date;
import java.util.UUID;

public class Event {

    private UUID mId;
    //TODO: add specific orgID as foreign key
    private String mName;
    private Date mDate;
    private String mTime;
    private String mLocation;

    public Event(){
        mId = UUID.randomUUID();
    }

    public UUID getId() { return mId; }

    public String getName(){ return mName;}

    public void setName(String name){ mName = name; }

    public Date getDate(){return mDate;}

    public void setDate(Date date){mDate = date;}

    public String getTime(){return mTime;}

    public void setTime(String time){mTime = time;}

    public String getLocation(){return mLocation;}

    public void setLocation(String location){mLocation = location;}

}
