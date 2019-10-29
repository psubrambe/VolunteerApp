package com.mrpanda2.volunteerapp;

import java.util.UUID;

public class Event {

    private UUID mId;
    //TODO: add specific orgID as foreign key
    private String mName;
    private String mDate;
    private String mTime;
    private String mLocation;
    private String mOrg;
    private String mOrgId;

    public Event(){
        mId = UUID.randomUUID();
    }

    public UUID getId() { return mId; }

    public void setId(UUID id) { mId = id ;}

    public String getName(){ return mName;}

    public void setName(String name){ mName = name; }

    public String getDate(){return mDate;}

    public void setDate(String date){ mDate = date; }

    public String getTime(){return mTime;}

    public void setTime(String time){mTime = time;}

    public String getLocation(){return mLocation;}

    public void setLocation(String location){mLocation = location;}

    public String getOrg(){return mOrg;}

    public void setOrg(String org){mOrg = org;}

    public String getOrgId() {return mOrgId;}

    public void setOrgId(String orgid) {mOrgId = orgid;}

}
