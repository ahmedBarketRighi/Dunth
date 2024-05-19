package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpDoctor extends AppCompatActivity {
    EditText signupName, signupUsername, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button next_doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_doctor);

        signupName = findViewById(R.id.signup_name_doctor);

        signupEmail = findViewById(R.id.signup_email_doctor);
        signupUsername = findViewById(R.id.signup_username_doctor);
        signupPassword = findViewById(R.id.signup_password_doctor);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        next_doctor = findViewById(R.id.next_button);

      next_doctor.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(SignUpDoctor.this, NextSignUpDoctor.class);
              intent.putExtra("name", signupName.getText().toString());
              intent.putExtra("email", signupEmail.getText().toString());
              intent.putExtra("username", signupUsername.getText().toString());
              intent.putExtra("password", signupPassword.getText().toString());
              startActivity(intent);





          }
      });



    }
}