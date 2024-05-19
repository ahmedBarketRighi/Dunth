package com.example.signuploginrealtime;

import static com.example.signuploginrealtime.AdapterContact.copyString;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DoctorContactDetails extends AppCompatActivity {

    ImageView personalimageview;

    TextView nametexview,descriptiondatails,special;

    static  int PERMISSION_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_contact_details);

        ImageView watsappiv=findViewById(R.id.contactwatsapp);
        ImageView phoneiv=findViewById(R.id.phoneiv);
        ImageView emailiv=findViewById(R.id.emailiv);

        ImageView returnflash=findViewById(R.id.returnflash);

        personalimageview=findViewById(R.id.personalimageview);
        nametexview=findViewById(R.id.nametexview);
        descriptiondatails=findViewById(R.id.tvdescription);
        special=findViewById(R.id.special);


        returnflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        Intent intent=getIntent();
      String doctorusername=  intent.getStringExtra("doctorusername");


      loadinfos(doctorusername);





        watsappiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactOnWhatsapp(v,doctorusername);
            }
        });

        phoneiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callphone(doctorusername);
            }
        });
        emailiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail("consultation online","hi doctor",doctorusername);
            }
        });











    }

    private void loadinfos(String doctorusername) {

        DatabaseReference doctorsRef = FirebaseDatabase.getInstance().getReference("doctors");

        DatabaseReference doctorQuery = doctorsRef.child(doctorusername);


        doctorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String personalimageuri = dataSnapshot.child("personalimageuri").getValue(String.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    String spec = dataSnapshot.child("spec").getValue(String.class);
                    String name = dataSnapshot.child("name").getValue(String.class);

                   if (name!=null && !name.isEmpty()){
                       nametexview.setText(name);
                   }
                    if (spec!=null && !spec.isEmpty()){
                        special.setText(spec);
                    }
                    if (description!=null && !description.isEmpty()){
                        descriptiondatails.setText(description);
                    }
                    if (personalimageuri!=null && !personalimageuri.isEmpty()){

                        HelpMthodes mh=new HelpMthodes();
                    mh.loadProfileImage(personalimageview,"doctors","personalimageuri",DoctorContactDetails.this,doctorusername);
                    }








                } else {
                  //  Toast.makeText(DoctorContactDetails.this, "phone not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void contactOnWhatsapp(View view, String  doctorusername) {


        final String[] phonee = new String[1]; // Change to non-final array
        DatabaseReference doctorsRef = FirebaseDatabase.getInstance().getReference("doctors");

        DatabaseReference doctorQuery = doctorsRef.child(doctorusername);
        doctorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String phone = dataSnapshot.child("phonneNumber").getValue(String.class);

                    Toast.makeText(view.getContext(), "Doctor's Phone Number is = " + phone, Toast.LENGTH_SHORT).show();

                    phonee[0] = copyString(phone); // Assign the copied value

                    if (phonee[0] != null) {
                        String whatsappUser = phonee[0]; // Access the copied value

                        // Now you can use the phone number here
                        String message = "hi i am patient";

                        // Encode the message for URL
                        try {
                            message = URLEncoder.encode(message, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        String url = "https://api.whatsapp.com/send?phone=" + whatsappUser + "&text=" + message;
                        try {
                            PackageManager pm = view.getContext().getPackageManager();
                            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            view.getContext().startActivity(i);
                        } catch (PackageManager.NameNotFoundException e) {
                            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        }
                    } else {
                        System.out.println("Phone number not found for doctor: " + doctorusername);
                    }
                } else {
                    System.out.println("Doctor not found with username: " + doctorusername);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Database Error: " + databaseError.getMessage());
            }
        });
    }


    public  void sendEmail(String subject,String content,String doctorusername){


        DatabaseReference doctorsRef = FirebaseDatabase.getInstance().getReference("doctors");

        DatabaseReference doctorQuery = doctorsRef.child(doctorusername);


        doctorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String to_email = dataSnapshot.child("email").getValue(String.class);
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL,new String[]{to_email});
                    intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                    intent.putExtra(Intent.EXTRA_TEXT,content);
                    intent.setType("message/rfc822");
                    startActivity(Intent.createChooser(intent,"choose email:"));


                } else {
                    Toast.makeText(DoctorContactDetails.this, "email not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


private void callphone(String doctorusername){

   if(ContextCompat.checkSelfPermission(DoctorContactDetails.this, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){

       ActivityCompat.requestPermissions(DoctorContactDetails.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
   }

    DatabaseReference doctorsRef = FirebaseDatabase.getInstance().getReference("doctors");

    DatabaseReference doctorQuery = doctorsRef.child(doctorusername);


    doctorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                String phone = dataSnapshot.child("phonneNumber").getValue(String.class);
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+phone));
                startActivity(intent);

            } else {
                Toast.makeText(DoctorContactDetails.this, "phone not found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });



}














}