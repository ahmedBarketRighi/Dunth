package com.example.signuploginrealtime;

public class ScheduleClass {
    String iddate,time,doctorusername,date;

    public ScheduleClass(String iddate, String time, String doctorusername, String date) {
        this.iddate = iddate;
        this.time = time;
        this.doctorusername = doctorusername;
        this.date = date;
    }

    public String getIddate() {
        return iddate;
    }

    public void setIddate(String iddate) {
        this.iddate = iddate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDoctorusername() {
        return doctorusername;
    }

    public void setDoctorusername(String doctorusername) {
        this.doctorusername = doctorusername;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
