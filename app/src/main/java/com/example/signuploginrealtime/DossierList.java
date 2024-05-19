package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DossierList extends AppCompatActivity  implements SelectListener{
    RecyclerView recyclerView;

    AdapterDossier myadapter;
    String doctorusername;
    List<String> uniqueUsernamesList;

    ArrayList<DossierClass> listAllDossier;
    ArrayList<Appointement> listappointement=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_list);


        Intent intent=getIntent();



         doctorusername=intent.getStringExtra("doctorusername");




        recyclerView=findViewById(R.id.dossier_list);




        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getappointements();
        listAllDossier=new ArrayList<>();


        myadapter=new AdapterDossier(this,listAllDossier,this);

        recyclerView.setAdapter(myadapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dossierReference = database.getReference("dossiers");



        for (String username : uniqueUsernamesList) {
            dossierReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DossierClass dossier = snapshot.getValue(DossierClass.class);
                        listAllDossier.add(dossier);
                    }

                    myadapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle onCancelled
                }
            });
        }

















    }

    @Override
    public void onItemClicked(Doctor mymodel) {

    }

    @Override
    public void onItemClicked(DossierClass mymodel) {
        Intent intent = new Intent(this, DossierDetaild.class);
     //   intent.putExtra("model", (Serializable) mymodel);
        intent.putExtra("doctorusername",doctorusername);
       intent.putExtra("username",mymodel.getUsername());
        startActivity(intent);

    }


    private  void getappointements(){

        DatabaseReference database= FirebaseDatabase.getInstance().getReference("doctorappointments").child(doctorusername);;

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (dataSnapshot.child("doctorusername").getValue(String.class).equals(doctorusername)) {
                        Appointement appointment = dataSnapshot.getValue(Appointement.class);
                        listappointement.add(appointment);
                    }

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Set<String> uniqueUsernames = new HashSet<>();


        for (Appointement appointment : listappointement) {

            if (appointment.getMode().equals("Follow Up")) {

                String username = appointment.getUsername();

                if (!uniqueUsernames.contains(username)) {

                    uniqueUsernames.add(username);
                }
            }
        }





         uniqueUsernamesList = new ArrayList<>(uniqueUsernames);











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