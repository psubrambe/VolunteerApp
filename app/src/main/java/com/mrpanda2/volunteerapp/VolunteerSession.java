package com.mrpanda2.volunteerapp;

import java.util.UUID;

public class VolunteerSession {

    private UUID mId;
    private String mVolId;
    private String mVolName;
    private String mEventId;
    private String mEventName;
    private String mTimeIn;
    private String mTimeOut;
    private String mDate;
    private long mDuration;

    public VolunteerSession() {mId = UUID.randomUUID();}

    public UUID getId(){return mId;}

    public void setId(UUID id){mId = id;}

    public String getVolId(){return mVolId;}

    public void setVolId(String id){mVolId = id;}

    public String getVolName(){return mVolName;}

    public void setVolName(String name){mVolName = name;}

    public String getEventId(){return mEventId;}

    public void setEventId(String id){mEventId = id;}

    public String getEventName(){return mEventName;}

    public void setEventName(String name){mEventName = name;}

    public String getTimeIn(){return mTimeIn;}

    public void setTimeIn(String time){mTimeIn = time;}

    public String getTimeOut(){return mTimeOut;}

    public void setTimeOut(String time){mTimeOut = time;}

    public String getDate(){return mDate;}

    public void setDate(String date){mDate = date;}

    public long getDuration(){return mDuration;}

    public void setDuration(long mins){
        mDuration = mins;
    }

}
