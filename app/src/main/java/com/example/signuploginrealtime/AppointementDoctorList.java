package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppointementDoctorList extends AppCompatActivity implements SelectListener{
    RecyclerView recyclerView;

    DatabaseReference database;

    AdapterAppointementDoctor myadapter;

    ArrayList<Appointement> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointement_doctor_list);

        recyclerView=findViewById(R.id.appointement_doctor_list);

        Intent intent = getIntent();

        String usernameUser = intent.getStringExtra("username");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        myadapter=new AdapterAppointementDoctor(this,list,this);


        recyclerView.setAdapter(myadapter);

        database= FirebaseDatabase.getInstance().getReference("doctorappointments").child(usernameUser);;
        //database = FirebaseDatabase.getInstance();

        //  DatabaseReference reference = database.getReference("appointments").child("aaa");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (dataSnapshot.child("doctorusername").getValue(String.class).equals(usernameUser)) {
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

    @Override
    public void onItemClicked(Doctor mymodel) {

    }

    @Override
    public void onItemClicked(DossierClass mymodel) {

    }

    @Override
    public void onItemClicked(Presc mymodel) {

    }

    @Override
    public void onItemClicked(Appointement mymodel) {

    }

    @Override
    public void onItemClicked(Contact mymodel) {

    }
}