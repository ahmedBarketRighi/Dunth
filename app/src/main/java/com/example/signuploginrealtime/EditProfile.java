package com.example.signuploginrealtime;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {

    EditText editName, editEmail, editUsername, editPassword;
    Button saveButton;
    String nameUser, emailUser, usernameUser, passwordUser,id;
    DatabaseReference reference,reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");
        reference2=FirebaseDatabase.getInstance().getReference("doctors");
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);

        showData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isPasswordChanged() || isEmailChanged()){
                    Toast.makeText(EditProfile.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfile.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    private boolean isNameChanged() {

       if(id.equals("1")) {
           if (!nameUser.equals(editName.getText().toString())) {
               reference.child(usernameUser).child("name").setValue(editName.getText().toString());
               nameUser = editName.getText().toString();
               return true;
           } else {
               return false;
           }
       } else {

           if (!nameUser.equals(editName.getText().toString())) {
               reference2.child(usernameUser).child("name").setValue(editName.getText().toString());
               nameUser = editName.getText().toString();
               return true;
           } else {
               return false;
           }



       }






    }

    private boolean isEmailChanged() {
        if(id.equals("1")) {
        if (!emailUser.equals(editEmail.getText().toString())) {
            reference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else {
            return false;
        }
    } else{
            if (!emailUser.equals(editEmail.getText().toString())) {
                reference2.child(usernameUser).child("email").setValue(editEmail.getText().toString());
                emailUser = editEmail.getText().toString();
                return true;
            } else {
                return false;
            }





        }





    }


    private boolean isPasswordChanged() {
        if(id.equals("1")) {
        if (!passwordUser.equals(editPassword.getText().toString())) {
            reference.child(usernameUser).child("password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            return true;
        } else {
            return false;
        }
    } else {

            if (!passwordUser.equals(editPassword.getText().toString())) {
                reference2.child(usernameUser).child("password").setValue(editPassword.getText().toString());
                passwordUser = editPassword.getText().toString();
                return true;
            } else {
                return false;
            }

        }





    }

    public void showData(){

        Intent intent = getIntent();

        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        usernameUser = intent.getStringExtra("username");
        passwordUser = intent.getStringExtra("password");
        id = intent.getStringExtra("id");


        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editUsername.setText(usernameUser);
        editPassword.setText(passwordUser);
    }
}