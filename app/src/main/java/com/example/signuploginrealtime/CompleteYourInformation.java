package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CompleteYourInformation extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    TextView city,countery,address;
    String add;
    Button getlocation,save_infos;

     DatabaseReference reference;
  private  final static int REQUEST_CODE=100;
    private static final int REQUEST_MAP_LOCATION = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_your_information);


        Intent intent = getIntent();


        String usernameUser = intent.getStringExtra("username");

        reference= FirebaseDatabase.getInstance().getReference("doctors");


        ImageView returnfrominfos=findViewById(R.id.returnfrominfos);


        getlocation=findViewById(R.id.get_location);
        save_infos=findViewById(R.id.save_infos);

        city=findViewById(R.id.citylocation);
        countery=findViewById(R.id.counterylocation);
        address=findViewById(R.id.addresslocation);


        TextInputLayout textInputLayout = findViewById(R.id.desc_layout);
        TextInputEditText description = textInputLayout.findViewById(R.id.desc);

        TextInputLayout textInputLayout1 = findViewById(R.id.university_layout);
        TextInputEditText university = textInputLayout1.findViewById(R.id.university);

        TextInputLayout textInputLayout2 = findViewById(R.id.work_layout);
        TextInputEditText work = textInputLayout2.findViewById(R.id.work);

        TextInputLayout textInputLayout3 = findViewById(R.id.experience_layout);
        TextInputEditText experience = textInputLayout3.findViewById(R.id.experience);


        TextInputLayout textInputLayout4 = findViewById(R.id.clinic_layout);
        TextInputEditText clinicName = textInputLayout4.findViewById(R.id.clinicname);

        TextInputEditText fromtime=findViewById(R.id.fromtime);

        TextInputEditText totime=findViewById(R.id.totime);
        TextInputEditText fromday=findViewById(R.id.fromday);
        TextInputEditText todayy=findViewById(R.id.todayy);
  


        save_infos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(usernameUser).child("description").setValue(description.getText().toString());
                reference.child(usernameUser).child("university").setValue(university.getText().toString());
                reference.child(usernameUser).child("work").setValue(work.getText().toString());
                reference.child(usernameUser).child("experience").setValue(experience.getText().toString());
                reference.child(usernameUser).child("address").setValue(add);

                HelpMthodes hp=new HelpMthodes();
                String t=hp.generateTimeSlotsString(fromtime.getText().toString(),totime.getText().toString());



                reference.child(usernameUser).child("fromtime").setValue(fromtime.getText().toString());
                reference.child(usernameUser).child("totime").setValue(totime.getText().toString());

                reference.child(usernameUser).child("time").setValue(t);


                reference.child(usernameUser).child("from").setValue(fromday.getText().toString());
                reference.child(usernameUser).child("to").setValue(todayy.getText().toString());

                Toast.makeText(CompleteYourInformation.this, "save sucess", Toast.LENGTH_SHORT).show();
                finish();



            }
        });










        returnfrominfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




      fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
      getlocation.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              getLastLocation();
              getLastLocation2();
          }


      });

    }



    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            launchMapIntent();
        } else {
            askPermission();
        }
    }

    private void getLastLocation2() {

    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                    if(location!=null){
                        Geocoder geocoder=new Geocoder(CompleteYourInformation.this, Locale.getDefault());
                        List<Address> addresses= null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                           // lagitude.setText("latitude:"+addresses.get(0).getLatitude());
                           // langitude.setText("langitude:"+addresses.get(0).getLongitude());
                            address.setText("-addrese: "+addresses.get(0).getAddressLine(0));
                            add=addresses.get(0).getAddressLine(0);
                            city.setText("-city: "+addresses.get(0).getLocality());
                            countery.setText("-country: "+addresses.get(0).getCountryName());

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }



                    }
                    }
                });
    }else {

    askPermission();

    }


    }

    private void askPermission() {
        ActivityCompat.requestPermissions(CompleteYourInformation.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }


    private void launchMapIntent() {
        Uri uri = Uri.parse("geo:0,0?q="); // Google Maps URI
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivityForResult(mapIntent, REQUEST_MAP_LOCATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MAP_LOCATION) {
            if (resultCode == RESULT_OK) {
                double latitude = data.getDoubleExtra("latitude", 0.0);
                double longitude = data.getDoubleExtra("longitude", 0.0);
                getAddressFromLocation(latitude, longitude);
            }
        }
    }

    private void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                Address addresse = addresses.get(0);
                // Update UI with selected location
                address.setText("- Address: " + addresse.getAddressLine(0));
                city.setText("- City: " + addresse.getLocality());
                countery.setText("- Country: " + addresse.getCountryName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else {
                Toast.makeText(this, "required permission", Toast.LENGTH_SHORT).show();

            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}