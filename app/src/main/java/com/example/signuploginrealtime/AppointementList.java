package com.example.signuploginrealtime;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class AppointementList extends AppCompatActivity implements SelectListener {

    RecyclerView recyclerView;
    TextView textViewyour;
    ArrayAdapter<String> adapteritems;
    ArrayList<Appointement> list;
    ArrayList<Presc> list2;
    String[] item = {"Appointment","Prescription"};
    AdapterAppointement myadapter;
    AdapterPrescription myadapter2;
    DatabaseReference database;
    Spinner spinnerAppointmentOrPrescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointement_list);

        spinnerAppointmentOrPrescription =findViewById(R.id.spinner_appointment_or_prescription);
        recyclerView = findViewById(R.id.appointement_list);
        textViewyour = findViewById(R.id.textViewyour);

        Intent intent = getIntent();
        String usernameUser = intent.getStringExtra("username");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        list2 = new ArrayList<>();

        myadapter = new AdapterAppointement(this, list, this);
        myadapter2 = new AdapterPrescription(this, list2, this);

        recyclerView.setAdapter(myadapter);

        adapteritems = new ArrayAdapter<>(this, R.layout.dropdown_item, item);

        spinnerAppointmentOrPrescription.setAdapter(adapteritems);




        spinnerAppointmentOrPrescription.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Appointment")) {
                    textViewyour.setText("Appointments");
                    yourappointements(usernameUser);
                } else if (selectedItem.equals("Prescription")) {
                    textViewyour.setText("Prescriptions");
                    yourprescriptions(usernameUser);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Load default appointments
        yourappointements(usernameUser);
    }


    public void yourappointements(String usernameUser) {
        list2.clear(); // Clear the prescription list
        recyclerView.setAdapter(myadapter); // Set the adapter to the appointments adapter

        database = FirebaseDatabase.getInstance().getReference("appointments").child(usernameUser);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("username").getValue(String.class).equals(usernameUser)) {
                        Appointement appointment = dataSnapshot.getValue(Appointement.class);
                        list.add(appointment);
                    }
                }
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void yourprescriptions(String usernameUser) {


        recyclerView.setAdapter(myadapter2); // Set the adapter to the prescriptions adapter

        database = FirebaseDatabase.getInstance().getReference("prescriptions").child(usernameUser);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("username").getValue(String.class).equals(usernameUser)) {
                        Presc prescription = dataSnapshot.getValue(Presc.class);
                        list2.add(prescription);
                    }
                }
                myadapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onItemClicked(Doctor mymodel) {

    }

    @Override
    public void onItemClicked(DossierClass mymodel) {

    }

    @Override
    public void onItemClicked(Presc mymodel) {
       Intent intent=new Intent(AppointementList.this,PrescriptionDetails.class);





        intent.putExtra("clinicname",mymodel.getClinicName());



        Gson gson = new Gson();
        String medicationListJson = gson.toJson(mymodel.getMedications());
        intent.putExtra("medicationListJson", medicationListJson);


        intent.putExtra("fullname",mymodel.getFullname());
        intent.putExtra("doctorname",mymodel.getDoctorname());
        intent.putExtra("date",mymodel.getDate());
        intent.putExtra("signature",mymodel.getSignature());


        startActivity(intent);

    }

    @Override
    public void onItemClicked(Appointement mymodel) {

    }

    @Override
    public void onItemClicked(Contact mymodel) {

    }


}
