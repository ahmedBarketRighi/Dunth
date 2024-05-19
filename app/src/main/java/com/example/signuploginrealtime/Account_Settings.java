package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account_Settings extends AppCompatActivity {

    Switch switchAppointmentMode;
    Switch switchContactMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");

        String usernameUser = intent.getStringExtra("username");

        String phonneNumber=intent.getStringExtra("phonneNumber");







        switchAppointmentMode = findViewById(R.id.switch_appointementmode);







        // Set an OnCheckedChangeListener to listen for changes in the switch state
        switchAppointmentMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // isChecked is true when the switch is activated (ON), false when deactivated (OFF)
                if (isChecked) {
                    activatemodeappointement(usernameUser,1);
                    Toast.makeText(Account_Settings.this, "appointemnt mode activated", Toast.LENGTH_SHORT).show();
                } else {
                    activatemodeappointement(usernameUser,0);
                    Toast.makeText(Account_Settings.this, "appointemnt mode deactivated", Toast.LENGTH_SHORT).show();
                }
            }
        });


         switchContactMode = findViewById(R.id.switch_contactmode);


        switchContactMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // isChecked is true when the switch is activated (ON), false when deactivated (OFF)
                if (isChecked) {
                    activatemodecontact(usernameUser,1);
                    Toast.makeText(Account_Settings.this, "contact mode  activated", Toast.LENGTH_SHORT).show();
                } else {
                    activatemodecontact(usernameUser,0);
                    Toast.makeText(Account_Settings.this, "contact mode deactivated", Toast.LENGTH_SHORT).show();
                }
            }
        });




        check(usernameUser);



        TextView numbere=findViewById(R.id.numbere);
        numbere.setText(phonneNumber);
        ImageView debrunephoto=findViewById(R.id.debrunephoto);
        TextView kevintext=findViewById(R.id.kevintext);
















        kevintext.setText(nameUser);


        HelpMthodes mh=new HelpMthodes();
        mh.loadProfileImage(debrunephoto,"doctors","personalimageuri",Account_Settings.this,usernameUser);

        AppCompatButton btncomplete_your_info=findViewById(R.id.btncomplete_your_info);

        ImageView retudd=findViewById(R.id.retudd);
        retudd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btncomplete_your_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Account_Settings.this,CompleteYourInformation.class);
                intent.putExtra("username",usernameUser);
                startActivity(intent);
            }
        });




    }


    private void activatemodeappointement(String docotusername,int i){


        DatabaseReference ratingRefdoc = FirebaseDatabase.getInstance().getReference("doctors").child(docotusername);

        ratingRefdoc.child("appointementmode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                ratingRefdoc.child("appointementmode").setValue(i, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void activatemodecontact(String docotusername,int i){


        DatabaseReference ratingRefdoc = FirebaseDatabase.getInstance().getReference("doctors").child(docotusername);

        ratingRefdoc.child("contactmode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                ratingRefdoc.child("contactmode").setValue(i, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


  private void check(String usernamedoc){

      DatabaseReference modereference = FirebaseDatabase.getInstance().getReference("doctors").child(usernamedoc).child("appointementmode");

      modereference.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              if (dataSnapshot.exists()) {
              int    Xappointementmode = dataSnapshot.getValue(Integer.class);
                           if(Xappointementmode==1){
                      switchAppointmentMode.setChecked(true);
                  }

              }
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
          }
      });


      DatabaseReference modecontactreference = FirebaseDatabase.getInstance().getReference("doctors").child(usernamedoc).child("contactmode");

      modecontactreference.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              if (dataSnapshot.exists()) {
                int   Xcontactmode = dataSnapshot.getValue(Integer.class);

                  if(Xcontactmode==1){
                      switchContactMode.setChecked(true);
                  }






              }
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
          }
      });


  }









}