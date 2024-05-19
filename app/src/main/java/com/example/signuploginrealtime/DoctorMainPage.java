package com.example.signuploginrealtime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class DoctorMainPage extends AppCompatActivity {
    private final int GALLERY_REQ_CODE=101;
    ImageView personalimageview;
    FirebaseDatabase database;
    DatabaseReference reference;
    String nn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main_page);
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        String emailUser = intent.getStringExtra("email");
        String usernameUser = intent.getStringExtra("username");
        nn=usernameUser;
        String passwordUser = intent.getStringExtra("password");

        String phonneNumber=intent.getStringExtra("phonneNumber");


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("doctors");

        TextView tvname=findViewById(R.id.textView_name);
        TextView tvemail=findViewById(R.id.textView2_email);
        tvname.setText(nameUser);
        tvemail.setText(emailUser);


        FloatingActionButton addphoto=findViewById(R.id.floatingAct);
         personalimageview =findViewById(R.id.personal_image_view_doctor);

        AppCompatButton logout_doctor=findViewById(R.id.logout_doctor);
        AppCompatButton button_personal_information=findViewById(R.id.button_personal_information);
        AppCompatButton button_account_settings=findViewById(R.id.button_account_settings);
        AppCompatButton buttonmyappointement=findViewById(R.id.buttonmyappointement);



        AppCompatButton buttondossier=findViewById(R.id.buttondossier);


        HelpMthodes mh=new HelpMthodes();
        mh.loadProfileImage(personalimageview,"doctors","personalimageuri",DoctorMainPage.this,usernameUser);



        buttondossier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent1=new Intent(DoctorMainPage.this,DossierList.class);
                intent1.putExtra("doctorusername",usernameUser);

                startActivity(intent1);
            }
        });










        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iGallery =new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);
                Toast.makeText(DoctorMainPage.this, nn, Toast.LENGTH_SHORT).show();


            }
        });

        button_account_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DoctorMainPage.this,Account_Settings.class);
                intent.putExtra("username", usernameUser);

                intent.putExtra("name", nameUser);
                intent.putExtra("phonneNumber",phonneNumber);




                overridePendingTransition(R.anim.pop_enter,R.anim.pop_exit);
                startActivity(intent);
            }
        });


        buttonmyappointement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DoctorMainPage.this,AppointementDoctorList.class);
                overridePendingTransition(R.anim.pop_enter,R.anim.pop_exit);
                intent.putExtra("username", usernameUser);
                startActivity(intent);
            }
        });






        logout_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        button_personal_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(DoctorMainPage.this, ProfileActivity.class);

                intent2.putExtra("name", nameUser);
                intent2.putExtra("email", emailUser);
                intent2.putExtra("username", usernameUser);
                intent2.putExtra("password", passwordUser);
                intent2.putExtra("id", "2");

                startActivity(intent2);
                overridePendingTransition(R.anim.pop_enter,R.anim.pop_exit);
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== RESULT_OK){
            personalimageview.setImageURI(data.getData());
            personalimageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        HelpMthodes mh=new HelpMthodes();

        String imageUri = mh.getImageUri(personalimageview);
        if (imageUri != null) {
            reference.child(nn).child("personalimageuri").setValue(imageUri);
        }

    }






}