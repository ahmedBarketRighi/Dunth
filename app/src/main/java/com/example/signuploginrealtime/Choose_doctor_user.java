package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Choose_doctor_user extends AppCompatActivity {
    ImageView doctorimage,sickimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor_user);
        doctorimage=findViewById(R.id.doctor_image);
        sickimage=findViewById(R.id.sick_image);

        doctorimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Choose_doctor_user.this,SignUpDoctor.class);
                startActivity(intent);
            }
        });

        sickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Choose_doctor_user.this,SignUpActivity.class);
                startActivity(intent);
            }
        });



    }
}