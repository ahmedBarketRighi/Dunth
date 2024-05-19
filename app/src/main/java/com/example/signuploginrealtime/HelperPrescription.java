package com.example.signuploginrealtime;

import java.util.List;

public class HelperPrescription {

   String id,username, clinicName, fullname,doctorname,doctorusername,addresse,date,signature;
   List<Medication> medications;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HelperPrescription(String id, String username, String clininName, String fullname, String doctorname, String doctorusername, String addresse, String date, String signature, List<Medication> medications) {
        this.id=id;
        this.username = username;
        this.clinicName = clininName;
        this.fullname = fullname;
        this.doctorname = doctorname;
        this.doctorusername = doctorusername;
      
        this.addresse = addresse;
        this.date = date;
        this.signature = signature;
        this.medications = medications;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getDoctorusername() {
        return doctorusername;
    }

    public void setDoctorusername(String doctorusername) {
        this.doctorusername = doctorusername;
    }



    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }
}
