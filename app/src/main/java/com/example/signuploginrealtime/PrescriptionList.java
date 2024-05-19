package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionList extends AppCompatActivity implements SelectListener {

    RecyclerView recyclerView;
    DatabaseReference database;
    AdapterPrescription myadapter;

    ArrayList<Presc> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);


        Intent intent = getIntent();

        String usernameUser = intent.getStringExtra("username");
        recyclerView=findViewById(R.id.prescription_list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        Toast.makeText(this, "here probleme", Toast.LENGTH_SHORT).show();
        myadapter=new AdapterPrescription(this,list,this);

        recyclerView.setAdapter(myadapter);



        database= FirebaseDatabase.getInstance().getReference("prescriptions").child(usernameUser);;
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (dataSnapshot.child("username").getValue(String.class).equals(usernameUser)) {
                        Presc prescription = dataSnapshot.getValue(Presc.class);
                        list.add(prescription);
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


        Intent intent=new Intent(PrescriptionList.this,PrescriptionDetails.class);



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