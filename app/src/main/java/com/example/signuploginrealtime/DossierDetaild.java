package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DossierDetaild extends AppCompatActivity {
    String fullname ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_detaild);
        Intent intent=getIntent();
       // DossierClass dossierClass= (DossierClass) intent.getSerializableExtra("model");

        String username=intent.getStringExtra("username");
        String doctorusername=intent.getStringExtra("doctorusername");

        Toast.makeText(this, "username"+username, Toast.LENGTH_SHORT).show();

        Button add_Prescription=findViewById(R.id.add_Prescription);


        Button show_Prescription=findViewById(R.id.show_Prescription);

        TextView fullnamedos=findViewById(R.id.fullnamedos);
        TextView fathernamedos=findViewById(R.id.fathernamedos);
        TextView mothernamedos=findViewById(R.id.mothernamedos);
        TextView groupdos=findViewById(R.id.groupdos);
        TextView addressedos=findViewById(R.id.addressedos);
        TextView dateofbirthdos=findViewById(R.id.dateofbirthdos);
        TextView genderdos=findViewById(R.id.genderdos);
        TextView heightdos=findViewById(R.id.heightdos);
        TextView weightdos=findViewById(R.id.weightdos);

        TextView chronicdos=findViewById(R.id.chronicdos);
        TextView allergiedos=findViewById(R.id.allergiedos);
        TextView medicationdos=findViewById(R.id.medicationdos);

        ImageView imgdos1=findViewById(R.id.imgdos1);
        ImageView imgdos2=findViewById(R.id.imgdos2);
        ImageView imgdos3=findViewById(R.id.imgdos3);



        show_Prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(DossierDetaild.this,PrescriptionList.class);

                intent1.putExtra("username",username);
                startActivity(intent1);
            }
        });



        add_Prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(DossierDetaild.this,Prescription.class);
                intent1.putExtra("fullname",fullname);
                intent1.putExtra("username",username);

                intent1.putExtra("doctorusername",doctorusername);


                startActivity(intent1);

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("dossiers");


        databaseReference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    fullname=dataSnapshot.child("fullname").getValue(String.class);
                    fullnamedos.setText("Full Name: "+fullname);
                    fathernamedos.setText("Father Name: "+dataSnapshot.child("fathername").getValue(String.class));
                    mothernamedos.setText("Mother Name: "+dataSnapshot.child("mothername").getValue(String.class));
                    groupdos.setText("Group: "+dataSnapshot.child("group").getValue(String.class));
                    addressedos.setText("address:"+dataSnapshot.child("adresse").getValue(String.class));
                    dateofbirthdos.setText("date of birth: "+dataSnapshot.child("dateofbirth").getValue(String.class));
                    genderdos.setText("Gender: "+dataSnapshot.child("gender").getValue(String.class));
                    heightdos.setText("Height: "+dataSnapshot.child("height").getValue(String.class));
                    weightdos.setText("Weight: "+dataSnapshot.child("weight").getValue(String.class));

                    chronicdos.setText("Chronic illnesses  : "+dataSnapshot.child("chronic_illnesses").getValue(String.class));
                    allergiedos.setText("allergies: "+dataSnapshot.child("allergies").getValue(String.class));
                    medicationdos.setText("Medications: "+dataSnapshot.child("medications").getValue(String.class));


                    HelpMthodes mh=new HelpMthodes();

                    mh.loadProfileImage(imgdos1,"dossiers","uri1",DossierDetaild.this,username);

                    mh.loadProfileImage(imgdos2,"dossiers","uri2",DossierDetaild.this,username);

                    mh.loadProfileImage(imgdos3,"dossiers","uri3",DossierDetaild.this,username);



                } else {

                    Toast.makeText(getApplicationContext(), "No dossier found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e("DatabaseError", "Error: " + databaseError.getMessage());
            }
        });





      //  fullnamedos.setText(fullname);
    }
}