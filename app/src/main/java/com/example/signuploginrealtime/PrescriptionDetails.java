package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDetails extends AppCompatActivity {
    private  List<Medication> medications = new ArrayList<>();
    private LinearLayout linearLayoutContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_details);
        Intent intent=getIntent();


        linearLayoutContainer = findViewById(R.id.linear_layout_container12);



        TextView doctornamepresc=findViewById(R.id.doctornamepresc12);

        TextView clinicnamepresc=findViewById(R.id.clinicname12);
        TextView fullnamepresc=findViewById(R.id.fullnamepresc12);

        TextView date_prescription=findViewById(R.id.date_prescription12);

        TextView signaturepresc=findViewById(R.id.signaturepresc12);


        fullnamepresc.setText("Full Name: "+intent.getStringExtra("fullname"));
        doctornamepresc.setText("Doctor Name: "+intent.getStringExtra("doctorname"));

        clinicnamepresc.setText(intent.getStringExtra("clinicname"));
        date_prescription.setText("Date: "+intent.getStringExtra("date"));
        signaturepresc.setText("Signature: "+intent.getStringExtra("signature"));

        String medicationListJson = intent.getStringExtra("medicationListJson");

        Type type = new TypeToken<List<Medication>>(){}.getType();
        medications = new Gson().fromJson(medicationListJson, type);


        createLinearLayouts();



    }
    private void createLinearLayouts() {
        linearLayoutContainer.removeAllViews(); // Clear existing views

         int ind=1;
        // Assuming medications is the list of Medication objects
        for (Medication medication : medications) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            linearLayout.setLayoutParams(layoutParams);

            TextView medicationTitle = new TextView(this);
            medicationTitle.setTextSize(25);

            medicationTitle.setText("medication "+String.valueOf(ind));
            linearLayout.addView(medicationTitle);
            ind++;
            TextView medicationName = new TextView(this);
            medicationName.setTextSize(18);
            medicationName.setText("Name: " + medication.getNameofmedication());
            linearLayout.addView(medicationName);

            TextView medicationTime = new TextView(this);
            medicationTime.setTextSize(18);
            medicationTime.setText("Time: " + medication.getTimetotakeit());
            linearLayout.addView(medicationTime);

            linearLayoutContainer.addView(linearLayout);
        }
    }

}