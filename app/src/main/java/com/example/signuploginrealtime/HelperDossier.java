package com.example.signuploginrealtime;

public class HelperDossier {

   String username,group,fullname,fathername,mothername,adresse,dateofbirth,height,weight,gender,chronic_illnesses,allergies,medications,uri1,uri2,uri3;

   public HelperDossier(String username,String group,String fullname, String fathername, String mothername, String adresse, String dateofbirth, String height, String weight, String gender, String chronic_illnesses, String allergies, String medications, String uri1, String uri2, String uri3) {
      this.username=username;
      this.group=group;
      this.fullname = fullname;

      this.fathername = fathername;
      this.mothername = mothername;
      this.adresse = adresse;
      this.dateofbirth = dateofbirth;
      this.height = height;
      this.weight = weight;
      this.gender = gender;
      this.chronic_illnesses = chronic_illnesses;
      this.allergies = allergies;
      this.medications = medications;
      this.uri1 = uri1;
      this.uri2 = uri2;
      this.uri3 = uri3;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getGroup() {
      return group;
   }

   public void setGroup(String group) {
      this.group = group;
   }

   public String getFullname() {
      return fullname;
   }

   public void setFullname(String fullname) {
      this.fullname = fullname;
   }

   public String getFathername() {
      return fathername;
   }

   public void setFathername(String fathername) {
      this.fathername = fathername;
   }

   public String getMothername() {
      return mothername;
   }

   public void setMothername(String mothername) {
      this.mothername = mothername;
   }

   public String getAdresse() {
      return adresse;
   }

   public void setAdresse(String adresse) {
      this.adresse = adresse;
   }

   public String getDateofbirth() {
      return dateofbirth;
   }

   public void setDateofbirth(String dateofbirth) {
      this.dateofbirth = dateofbirth;
   }

   public String getHeight() {
      return height;
   }

   public void setHeight(String height) {
      this.height = height;
   }

   public String getWeight() {
      return weight;
   }

   public void setWeight(String weight) {
      this.weight = weight;
   }

   public String getGender() {
      return gender;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }

   public String getChronic_illnesses() {
      return chronic_illnesses;
   }

   public void setChronic_illnesses(String chronic_illnesses) {
      this.chronic_illnesses = chronic_illnesses;
   }

   public String getAllergies() {
      return allergies;
   }

   public void setAllergies(String allergies) {
      this.allergies = allergies;
   }

   public String getMedications() {
      return medications;
   }

   public void setMedications(String medications) {
      this.medications = medications;
   }

   public String getUri1() {
      return uri1;
   }

   public void setUri1(String uri1) {
      this.uri1 = uri1;
   }

   public String getUri2() {
      return uri2;
   }

   public void setUri2(String uri2) {
      this.uri2 = uri2;
   }

   public String getUri3() {
      return uri3;
   }

   public void setUri3(String uri3) {
      this.uri3 = uri3;
   }
}
