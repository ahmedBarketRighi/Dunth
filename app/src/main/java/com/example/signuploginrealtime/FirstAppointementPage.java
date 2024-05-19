package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstAppointementPage extends AppCompatActivity {

    TextView namedoctor;
    TextView specdoctor;
    Button bookappointement;
    String usernameDoctor,times;
    DatabaseReference reference;
    float finalRatingValue=0;
    int ratingmember,appointementmode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_appointement_page);

        Intent intent = getIntent();
        String doctorName = intent.getStringExtra("DOCTOR_NAME");
        String spec=intent.getStringExtra("spec");
        String rate=intent.getStringExtra("rate");
        usernameDoctor = intent.getStringExtra("DOCTOR_Username");
        TextView expertv=findViewById(R.id.textViewexper);
        TextView tvmemeber=findViewById(R.id.textViewmemeber);

        TextView tvdays=findViewById(R.id.textView77);

        TextView tvtime=findViewById(R.id.textView55);
        TextView tvdescription=findViewById(R.id.tvdescription);







        reference = FirebaseDatabase.getInstance().getReference("doctors");

        reference.child(usernameDoctor).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                expertv.setText(dataSnapshot.child("experience").getValue(String.class)+" YEARS");

                tvmemeber.setText(String.valueOf(dataSnapshot.child("member").getValue(Integer.class))+"+");

                tvdays.setText("from/to : "+dataSnapshot.child("from").getValue(String.class)+" / "+dataSnapshot.child("to").getValue(String.class));
                tvtime.setText(dataSnapshot.child("fromtime").getValue(String.class)+"-"+dataSnapshot.child("totime").getValue(String.class));

                tvdescription.setText(dataSnapshot.child("description").getValue(String.class));

                times=dataSnapshot.child("time").getValue(String.class);
              //  String doctorUsername = dataSnapshot.child("username").getValue(String.class);
              //  String doctorSpec = dataSnapshot.child("spec").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });







        ImageView iv=findViewById(R.id.imagvb);
        HelpMthodes hp=new HelpMthodes();
        hp.loadProfileImage(iv,"doctors","personalimageuri",FirstAppointementPage.this,usernameDoctor);


        specdoctor=findViewById(R.id.spectv);
        specdoctor.setText(spec);
        namedoctor=findViewById(R.id.doctor_name_appointement);
        namedoctor.setText("Dr."+doctorName);
        TextView tvratedoc=findViewById(R.id.tvrate);
        tvratedoc.setText(rate);

        bookappointement=findViewById(R.id.book_appointement);

      Button  ratingButton = findViewById(R.id.rating_button);

        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ratingRefdoc = FirebaseDatabase.getInstance().getReference("doctors").child(usernameDoctor).child("ratingmember");

                ratingRefdoc.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            ratingmember = dataSnapshot.getValue(Integer.class);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


               // Toast.makeText(FirstAppointementPage.this, "rating member"+ratingmember, Toast.LENGTH_SHORT).show();


                showRatingDialog(ratingmember);
            }
        });


        DatabaseReference modereference = FirebaseDatabase.getInstance().getReference("doctors").child(usernameDoctor).child("appointementmode");

        modereference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    appointementmode = dataSnapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bookappointement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(appointementmode==1){

                Intent intent=new Intent(FirstAppointementPage.this,SecondAppointementPage.class);
                Intent intent2 = getIntent();

                String usernameUser = intent2.getStringExtra("username");

                String nameUser = intent2.getStringExtra("name");
                String emailUser = intent2.getStringExtra("email");
                String passwordUser = intent2.getStringExtra("password");

                String fromto = intent2.getStringExtra("fromto");

                intent.putExtra("fromto",fromto);
                intent.putExtra("name", nameUser);
                intent.putExtra("email", emailUser);

                intent.putExtra("password", passwordUser);

                intent.putExtra("times", times);


                intent.putExtra("username", usernameUser);
                intent.putExtra("DOCTOR_Username", usernameDoctor);
                intent.putExtra("DOCTOR_NAME", doctorName);
                intent.putExtra("spec", spec);


                startActivity(intent);}else {

                    Toast.makeText(FirstAppointementPage.this, "The doctor has stopped the reservation service at the present time", Toast.LENGTH_SHORT).show();
                }

            }
        });
      //  Toast.makeText(FirstAppointementPage.this, "rating member"+appointementmode, Toast.LENGTH_SHORT).show();



    }

    private void showRatingDialog(int ratingmember2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rating, null);
        builder.setView(dialogView);

        RatingBar ratingBar = dialogView.findViewById(R.id.ratingbar); // Corrected the ratingBar initialization

        builder.setPositiveButton("OK", (dialog, which) -> {
            float ratingValue = ratingBar.getRating();
            Toast.makeText(FirstAppointementPage.this, "doc username: " + usernameDoctor, Toast.LENGTH_SHORT).show();

            DatabaseReference ratingRefdoc = FirebaseDatabase.getInstance().getReference("doctors").child(usernameDoctor);


            //updating member of doctor in firebase


            ratingRefdoc.child("ratingmember").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    int updatingratingmemeber=ratingmember2+1;
                    ratingRefdoc.child("ratingmember").setValue(updatingratingmemeber, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





               // Updating  rating in firebase

            ratingRefdoc.child("rating").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String rating = dataSnapshot.getValue(String.class);
                    float currentRating = Float.valueOf(rating);

               /*      float normalizedRatingValue = ratingValue+currentRating ;

                    normalizedRatingValue=normalizedRatingValue/(ratingmember2+1);

                    float updatedRating = currentRating + normalizedRatingValue;
                   */

                  float updatedRating=((currentRating*ratingmember2)+ratingValue)/(ratingmember2+1);

                    ratingRefdoc.child("rating").setValue(String.format("%.1f", updatedRating), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error != null) {
                                // Handle error

                                Log.e("Firebase", "Failed to update rating: " + error.getMessage());

                            } else {

                                Log.d("Firebase", "Rating updated successfully");

                            }
                        }
                    });

                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors here
                }
            });










            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }














}