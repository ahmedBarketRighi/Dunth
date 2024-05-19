package com.example.signuploginrealtime;




import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;

public class NextSignUpDoctor extends AppCompatActivity {

    private final int GALLERY_REQ_CODE=100;
    ImageView imgGallery;

    TextView loginRedirectText;
    Button signupButton_doctor;
    FirebaseDatabase database;
    DatabaseReference reference;
    EditText phonedoctor;

    private final String[] suggestions = {"Dentist", "Surgeon","Eye care professional", "Custom suggestion"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_next_sign_up_doctor);

        AutoCompleteTextView spec = findViewById(R.id.specdoctor);



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestions);
        spec.setAdapter(adapter);


        phonedoctor=findViewById(R.id.phonedoctor);
         imgGallery =findViewById(R.id.imgCamera);
        Button btnGallery=findViewById(R.id.btnCamera);

        signupButton_doctor=findViewById(R.id.signup_button_doctor);



        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");


        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iGallery =new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);

            }
        });



    signupButton_doctor.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            database = FirebaseDatabase.getInstance();
            reference = database.getReference("doctors");
            String specValue = spec.getText().toString();
            String phoneValue = phonedoctor.getText().toString();
            String imageUri = getImageUri(); // Get image URI from imgGallery
            String rating="0";
            String personalimageuri="";
            String x="";
            int member=0;
            String fromtime="";
            String totime="";

            HelperDoctor helperClass = new HelperDoctor(name, email, username, password, imageUri,phoneValue,specValue,rating,personalimageuri,
                    x,x,x,x,x,x,x,x,member,fromtime,totime,x,0,0,0);
            reference.child(username).setValue(helperClass);

            Toast.makeText(NextSignUpDoctor.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NextSignUpDoctor.this, DoctorMainPage.class);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("username", username);
            intent.putExtra("password",password);
            intent.putExtra("phonneNumber",phoneValue);
            startActivity(intent);
        }
    });









    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== RESULT_OK){
         imgGallery.setImageURI(data.getData());
        }




    }

    private String getImageUri() {
        if (imgGallery.getDrawable() != null && imgGallery.getDrawable() instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) imgGallery.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            // Generate a unique identifier for the image
            String uniqueId = UUID.randomUUID().toString();

            // Specify the base directory and generate the path for the image
            String basePath = "images/"; // You can change this to your desired directory
            String imagePath = basePath + uniqueId + ".jpg";

            // Get a reference to the Firebase Storage location where you want to store the image
            StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(imagePath);

            // Upload image data to Firebase Storage
            UploadTask uploadTask = imageRef.putBytes(imageData);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Image uploaded successfully
                // You can handle any additional logic here
            }).addOnFailureListener(e -> {
                // Handle image upload failure
            });

            return imagePath; // Return the path to the uploaded image
        } else {
            return null; // No image selected
        }
    }
























}