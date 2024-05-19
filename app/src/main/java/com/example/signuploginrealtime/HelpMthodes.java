package com.example.signuploginrealtime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HelpMthodes {



    protected String getImageUri(ImageView imageview) {
        if (imageview.getDrawable() != null && imageview.getDrawable() instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
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
    protected void loadProfileImage(ImageView imageview,String path,String ur, Context context,String username) {
        DatabaseReference userRef;


        userRef = FirebaseDatabase.getInstance().getReference(path).child(username);

        /*
        if(i==1) {
             userRef = FirebaseDatabase.getInstance().getReference("users").child(username);
             ur="uriImage";
        }
        else {
            userRef = FirebaseDatabase.getInstance().getReference("doctors").child(username);
            ur="personalimageuri";
        }
*/


        // Assuming nn is the username
        userRef.child(ur).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uri = dataSnapshot.getValue(String.class);
                if (uri != null && !uri.isEmpty()) {
                    // Get a reference to the Firebase Storage location where the image is stored
                    StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(uri);

                    // Create a local instance of Glide
                    RequestManager glide = Glide.with(context);

                    // Download the image from Firebase Storage
                    Task<Uri> firebase = imageRef.getDownloadUrl();

                    // Handle success and failure listeners
                    firebase.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUri) {
                            // Uri of the image is successfully retrieved
                            // Load the image into the ImageView using Glide
                            glide.load(downloadUri)
                                    .into(imageview);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle any errors that may occur while downloading the image
                            Log.e("Firebase", "Failed to download image: " + e.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });




    }


    protected   String generateTimeSlotsString(String startTime, String endTime) {
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
        }
        LocalTime start = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            start = LocalTime.parse(startTime, formatter);
        }
        LocalTime end = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            end = LocalTime.parse(endTime, formatter);
        }

        List<String> timeSlots = new ArrayList<>();
        LocalTime current = start;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            while (current.isBefore(end) || current.equals(end)) {
                timeSlots.add(current.format(formatter));
                current = current.plusHours(1); // Increment by 1 hour
            }
        }

        return String.join(",", timeSlots);
    }

    public String removeHourFromTimes(String hour, String times) {
        // Split the times string into an array of individual time slots
        String[] timeSlots = times.split(",");
        hour = hour.substring(0, 2);
        // Iterate through each time slot
        boolean hourFound = false;
        for (int i = 0; i < timeSlots.length; i++) {
            // Extract the hour part from the current time slot
            String currentHour = timeSlots[i].substring(0, 2); // Get the first two characters (hour)

            // Check if the current hour matches the target hour
            if (currentHour.equals(hour)) {
                // Remove the matching time slot from the array
                timeSlots[i] = null;
                hourFound = true;
                break; // Exit the loop after the first match is found
            }
        }

        if (!hourFound) {
            return "false";
        }
        // Reconstruct the times string without the removed hour
        StringBuilder updatedTimes = new StringBuilder();
        for (String slot : timeSlots) {
            if (slot != null) {
                if (updatedTimes.length() > 0) {
                    updatedTimes.append(",");
                }
                updatedTimes.append(slot);
            }
        }

        // Return the updated times string
        return updatedTimes.toString();
    }
















}
