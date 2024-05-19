package com.example.signuploginrealtime;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class HelperDoctor extends HelperClass{
    String phonneNumber,spec,rating,personalimageuri,description,university,work,experience,address,time,from,to,fromtime,totime,clinicName;
    int member;

    int ratingmember,contactmode,appointementmode;

    public int getRatingmember() {
        return ratingmember;
    }

    public void setRatingmember(int ratingmember) {
        this.ratingmember = ratingmember;
    }

    public int getContactmode() {
        return contactmode;
    }

    public void setContactmode(int contactmode) {
        this.contactmode = contactmode;
    }

    public int getAppointementmode() {
        return appointementmode;
    }

    public void setAppointementmode(int appointementmode) {
        this.appointementmode = appointementmode;
    }

    public HelperDoctor(String name, String email, String username, String password, String uriImage, String phonneNumber, String spec, String rating, String personalimageuri,
                        String description, String university, String work, String experience
            , String address, String time, String from, String to, int member, String fromtime, String totime, String clinicName,
                        int ratingmember, int contactmode, int appointementmode) {
        super(name, email, username, password, uriImage);
        this.phonneNumber = phonneNumber;
        this.spec = spec;
        this.rating=rating;
        this.personalimageuri=personalimageuri;
        this.description=description;
        this.university=university;
        this.work=work;
        this.experience=experience;
        this.address=address;
        this.time=time;
        this.from=from;
        this.to=to;
        this.member=member;
        this.fromtime=fromtime;
        this.totime=totime;
        this.clinicName=clinicName;
        this.ratingmember=ratingmember;
        this.contactmode=contactmode;
        this.appointementmode=appointementmode;

    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromtime() {
        return fromtime;
    }

    public void setFromtime(String fromtime) {
        this.fromtime = fromtime;
    }

    public String getTotime() {
        return totime;
    }

    public void setTotime(String totime) {
        this.totime = totime;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }
    public String getPersonalimageuri() {
        return personalimageuri;
    }

    public void setPersonalimageuri(String personalimageuri) {
        this.personalimageuri = personalimageuri;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPhonneNumber() {
        return phonneNumber;
    }

    public void setPhonneNumber(String phonneNumber) {
        this.phonneNumber = phonneNumber;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }


}
