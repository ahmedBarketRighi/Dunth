package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorList extends AppCompatActivity implements SelectListener {

    RecyclerView recyclerView;
    DatabaseReference database;
    AdapterDoctor myadapter;

    ArrayList<Doctor> list;
    ArrayList<Doctor> filteredList;
    private SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
      recyclerView=findViewById(R.id.doctor_list);
      database= FirebaseDatabase.getInstance().getReference("doctors");


      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));

      list=new ArrayList<>();
      filteredList=new ArrayList<>();
      myadapter=new AdapterDoctor(this,list,this);

      recyclerView.setAdapter(myadapter);



        searchView = findViewById(R.id.searchview);



        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false); // Expand the SearchView
            }
        });

        // Set an OnQueryTextListener to handle text changes in the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Call the method to update the filtered list based on the new text
                filterDoctors(newText);

                return true;
            }
        });

        // Set an OnClickListener for the SearchView to ensure filter when clicked
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchView.getQuery().toString();
                filterDoctors(searchText);
            }
        });















     database.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {

         for(DataSnapshot dataSnapshot: snapshot.getChildren()){

       Doctor doctor=dataSnapshot.getValue(Doctor.class);
       list.add(doctor);

         }

         myadapter.notifyDataSetChanged();





         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });



    }




    private void filterDoctors(String query) {
        filteredList.clear(); // Clear the previous filtered list
        query = query.toLowerCase().trim(); // Convert query to lowercase and trim

        // If the query is empty, add all doctors to the filtered list
        if (query.isEmpty()) {
            filteredList.addAll(list);
        } else {
            // Loop through the list and add doctors whose specialties start with the query
            for (Doctor doctor : list) {
                if (doctor.getSpec().toLowerCase().startsWith(query)) {
                    filteredList.add(doctor);
                }
            }
        }
        myadapter = new AdapterDoctor(this, filteredList, this);
        recyclerView.setAdapter(null);
        recyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged(); // Notify adapter of data change
    }












    @Override
    public void onItemClicked(Doctor mymodel) {

       // Toast.makeText(this,mymodel.getName(),Toast.LENGTH_SHORT).show();
        String doctorName = mymodel.getName();
        String doctorusername=mymodel.getUsername();
        String spec=mymodel.getSpec();





       // Toast.makeText(DoctorList.this, "You rated: " + mymodel.getRating(), Toast.LENGTH_SHORT).show();
      //  Toast.makeText(DoctorList.this, "doctor name : " + doctorusername, Toast.LENGTH_SHORT).show();

        // Create an Intent to start the new activity
        Intent intent = new Intent(this, FirstAppointementPage.class);
        Intent intent2 = getIntent();

        String usernameUser = intent2.getStringExtra("username");
        String nameUser = intent2.getStringExtra("name");
        String emailUser = intent2.getStringExtra("email");
        String passwordUser = intent2.getStringExtra("password");


        intent.putExtra("fromto", mymodel.getFrom()+"/"+mymodel.getTo());
        intent.putExtra("rate", mymodel.getRating());

        intent.putExtra("username", usernameUser);

        intent.putExtra("name", nameUser);
        intent.putExtra("email", emailUser);

        intent.putExtra("password", passwordUser);

        // Put the name as an extra in the Intent
        intent.putExtra("DOCTOR_NAME", doctorName);
        intent.putExtra("spec", spec);

        intent.putExtra("DOCTOR_Username", doctorusername);

        // Start the new activity
        startActivityForResult(intent, 1);


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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check if the result is from FirstAppointementPage and if it indicates a rating update
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Refresh the doctor list
            // For example, you can call your method to fetch doctors from the database again
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Doctor doctor = dataSnapshot.getValue(Doctor.class);
                        list.add(doctor);
                    }
                    myadapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle errors
                }
            });
        }
    }



}