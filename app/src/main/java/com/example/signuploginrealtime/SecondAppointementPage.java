package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SecondAppointementPage extends AppCompatActivity {

    Button completeregister;
    String gender="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_appointement_page);

        completeregister=findViewById(R.id.complete_register);

        TextInputLayout textInputLayout = findViewById(R.id.firstnamepatientlayout);
        TextInputEditText firstnamepatient = textInputLayout.findViewById(R.id.firstnameofpatient);
        TextInputLayout textInputLayout2 = findViewById(R.id.lastnamelayout);
        TextInputEditText lastnamepatient = textInputLayout2.findViewById(R.id.lastnameofpatient);
        TextInputLayout textInputLayout3 = findViewById(R.id.emailofpatientlayout);
        TextInputEditText emailpatient = textInputLayout3.findViewById(R.id.emailofpatient);
        TextInputLayout textInputLayout4 = findViewById(R.id.phoneofpatientlayout);
        TextInputEditText phonepatient = textInputLayout4.findViewById(R.id.phoneofpatient);


        RadioGroup radioGroupgrnder = findViewById(R.id.radioGroupgender1);

        radioGroupgrnder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if(radioButton != null) {
                    gender = radioButton.getText().toString();

                }
            }
        });

        completeregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondAppointementPage.this,ThirdAppointementPage.class);
                Intent intent2 = getIntent();

                String usernameUser = intent2.getStringExtra("username");
                String usernameDoctor = intent2.getStringExtra("DOCTOR_Username");
                String doctorName = intent2.getStringExtra("DOCTOR_NAME");
                String spec=intent2.getStringExtra("spec");

                String nameUser = intent2.getStringExtra("name");
                String emailUser = intent2.getStringExtra("email");
                String passwordUser = intent2.getStringExtra("password");

                String times = intent2.getStringExtra("times");

                String fromto = intent2.getStringExtra("fromto");

                intent.putExtra("fromto",fromto);
                intent.putExtra("times", times);
                intent.putExtra("username", usernameUser);

                intent.putExtra("name", nameUser);
                intent.putExtra("email", emailUser);

                intent.putExtra("password", passwordUser);

                intent.putExtra("DOCTOR_Username", usernameDoctor);
                intent.putExtra("DOCTOR_NAME", doctorName);
                intent.putExtra("spec", spec);


                if (!firstnamepatient.getText().toString().isEmpty() &&
                        !lastnamepatient.getText().toString().isEmpty() &&
                        !gender.isEmpty()&&
                        !emailpatient.getText().toString().isEmpty() &&
                        !phonepatient.getText().toString().isEmpty()) {
                    intent.putExtra("fnpatient", firstnamepatient.getText().toString());
                    intent.putExtra("lspatient", lastnamepatient.getText().toString());
                    intent.putExtra("empatient", emailpatient.getText().toString());
                    intent.putExtra("phpatient", phonepatient.getText().toString());
                    intent.putExtra("sexpatient", gender);


                    startActivity(intent);

                }

                else {
                    Toast.makeText(SecondAppointementPage.this, "Make sure that you have filled all required information.", Toast.LENGTH_SHORT).show();


                }

            }
        });
    }


}