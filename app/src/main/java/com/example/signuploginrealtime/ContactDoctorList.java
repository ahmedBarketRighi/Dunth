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



public class ContactDoctorList extends AppCompatActivity implements SelectListener{
    RecyclerView recyclerView;
    DatabaseReference database;
    AdapterContact myadapter;

    ArrayList<Contact> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_doctor_list);

        recyclerView=findViewById(R.id.appointement_doctor_list);

        Intent intent = getIntent();

        String usernameUser = intent.getStringExtra("username");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        myadapter=new AdapterContact(this,list,this);

        recyclerView.setAdapter(myadapter);

        database= FirebaseDatabase.getInstance().getReference("appointments").child(usernameUser);;
        //database = FirebaseDatabase.getInstance();

        //  DatabaseReference reference = database.getReference("appointments").child("aaa");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (dataSnapshot.child("username").getValue(String.class).equals(usernameUser)) {
                        Contact contact = dataSnapshot.getValue(Contact.class);
                        if(contact.getContact().equals("yes")){
                        list.add(contact);}
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

    public void onItemClicked(Contact mymodel) {

        // Toast.makeText(this,mymodel.getName(),Toast.LENGTH_SHORT).show();


        String doctorName,date,time,confirm,spec;

        doctorName = mymodel.getDoctorName();}




}