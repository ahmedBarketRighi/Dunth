package com.example.signuploginrealtime;

import android.os.Parcel;
import android.os.Parcelable;

public class Medication{

    private String nameofmedication;
    private String timetotakeit;

    public String getNameofmedication() {
        return nameofmedication;
    }
    public Medication() {

    }
    public void setNameofmedication(String nameofmedication) {
        this.nameofmedication = nameofmedication;
    }

    public String getTimetotakeit() {
        return timetotakeit;
    }

    public void setTimetotakeit(String timetotakeit) {
        this.timetotakeit = timetotakeit;
    }

    public Medication(String nameofmedication, String timetotakeit) {
        this.nameofmedication = nameofmedication;
        this.timetotakeit = timetotakeit;
    }




}
