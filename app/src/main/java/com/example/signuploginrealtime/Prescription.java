package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Prescription extends AppCompatActivity {
    private EditText numberEditText;
    FirebaseDatabase database;
    DatabaseReference reference;

    String clinicName,doctorname,address;

    private ArrayList<Medication> medications = new ArrayList<>();
    private LinearLayout linearLayoutContainer;

   // ActivityPrescriptionBinding mainbinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mainbinding=ActivityPrescriptionBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_prescription);
      // setContentView(mainbinding.getRoot());

       // Button cleansignature=findViewById(R.id.clearsignature);

/*
        cleansignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainbinding.signatureView.clearCanvas();
            }
        });


*/







        Intent intent=getIntent();


        String username=intent.getStringExtra("username");
        String doctorusername=intent.getStringExtra("doctorusername");
        String fullname=intent.getStringExtra("fullname");

        numberEditText = findViewById(R.id.number_edit_text);
        linearLayoutContainer = findViewById(R.id.linear_layout_container);

        Button save_Prescription=findViewById(R.id.save_Prescription);

        TextView doctornamepresc=findViewById(R.id.doctornamepresc);
        TextView clinicnamepresc=findViewById(R.id.clinicnamepresc);
        TextView fullnamepresc=findViewById(R.id.fullnamepresc);
        fullnamepresc.setText("Full Name: "+fullname);
        TextView date_prescription=findViewById(R.id.date_prescription);

        EditText signaturepresc=findViewById(R.id.signaturepresc);





        date_prescription.setText("Date:"+getTodaysDate());




        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("doctors");


        databaseReference.child(doctorusername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                   doctorname=dataSnapshot.child("name").getValue(String.class);
                    doctornamepresc.setText("Doctor Name: "+doctorname);
                    clinicName=dataSnapshot.child("clinicName").getValue(String.class);

                    address=dataSnapshot.child("address").getValue(String.class);
                    clinicnamepresc.setText(clinicName+","+address);


                } else {

                    Toast.makeText(getApplicationContext(), "Nothing found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e("DatabaseError", "Error: " + databaseError.getMessage());
            }
        });


      // ImageView ivsignature=findViewById(R.id.ivsignature);







        save_Prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveMedications();


             /*  Bitmap signbitmap=mainbinding.signatureView.getSignatureBitmap();
              if(signbitmap !=null) {
                  mainbinding.ivsignature.setImageBitmap(signbitmap);
              }
              *

              */


                HelpMthodes mh=new HelpMthodes();

               // String signature = mh.getImageUri(ivsignature);






                database = FirebaseDatabase.getInstance();
                // database2 = FirebaseDatabase.getInstance();

                reference = database.getReference("prescriptions").child(username);




                // Get the number of appointments for this user
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long numPrescriptions = dataSnapshot.getChildrenCount();
                        String prescriptionId = String.valueOf(numPrescriptions + 1); // Increment the number and convert to string

                        // Create a new reference with the sequential number as the key
                        DatabaseReference newPrescriptionRef = reference.child(prescriptionId);

                        HelperPrescription helperPrescription = new HelperPrescription(
                                prescriptionId,
                                username,
                                clinicName,
                                fullname,
                                doctorname,
                                doctorusername,
                                address,
                                getTodaysDate(),
                                signaturepresc.getText().toString(),
                                medications
                        );
                        newPrescriptionRef.setValue(helperPrescription);
                        Toast.makeText(Prescription.this, "prescription registered successfully: " , Toast.LENGTH_SHORT).show();

                        //  startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



             finish();










            }
        });



        numberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString().trim();
                if (!input.isEmpty()) {
                    int count = Integer.parseInt(input);
                    createLinearLayouts(count);
                } else {
                    linearLayoutContainer.removeAllViews();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    private void createLinearLayouts(int count) {
        linearLayoutContainer.removeAllViews();
        medications.clear();
        for (int i = 0; i < count; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            linearLayout.setLayoutParams(layoutParams);

            TextView textView = new TextView(this);
            textView.setText("Medication " + (i + 1));
            linearLayout.addView(textView);

            EditText editText1 = new EditText(this);
            editText1.setHint("name of the medication");
            linearLayout.addView(editText1);

            EditText editText2 = new EditText(this);
            editText2.setHint("time to take it");
            linearLayout.addView(editText2);

            linearLayoutContainer.addView(linearLayout);
        }
    }


    private void saveMedications() {
        medications.clear();

        for (int i = 0; i < linearLayoutContainer.getChildCount(); i++) {
            View childView = linearLayoutContainer.getChildAt(i);
            if (childView instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) childView;
                EditText editText1 = (EditText) linearLayout.getChildAt(1); // Assuming first child is TextView and second is EditText1
                EditText editText2 = (EditText) linearLayout.getChildAt(2); // Assuming second child is EditText1 and third is EditText2
                String medicationName = editText1.getText().toString();
                String timeToTake = editText2.getText().toString();
                Medication medication = new Medication(medicationName, timeToTake);
                medications.add(medication);
            }
        }

    }


    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());
        return dateFormat.format(cal.getTime());
    }





}