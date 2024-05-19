package com.example.signuploginrealtime;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class UserMainPage extends AppCompatActivity  {

    ConstraintLayout profilepage,finddoctorpage,notificationpage,communicationpage,dossierpage,medinfopage;

    private int notificationIndex = -1;
    private List<Notification> notifications =new ArrayList<>();
    FloatingActionButton personalbutton;
    ImageView personalimageview;
    private final int GALLERY_REQ_CODE=100;
    FirebaseDatabase database;
    DatabaseReference databasenotification;
    DatabaseReference reference;

    String nn;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        String emailUser = intent.getStringExtra("email");
        String usernameUser = intent.getStringExtra("username");
        nn=usernameUser;
        String passwordUser = intent.getStringExtra("password");
       // String uri = intent.getStringExtra("uri");
        // scheduleNotification();




        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                scheduleNextAlarm();
            }
        };
        IntentFilter filter = new IntentFilter("com.example.signuploginrealtime.SCHEDULE_NEXT_ALARM");
        registerReceiver(receiver, filter);


        createNotificationChannel();




        databasenotification = FirebaseDatabase.getInstance().getReference("appointments").child(nn);

// 3ayatilha nhki 3la alaram

       doAlarm();
        personalimageview=findViewById(R.id.Personalimageview);


        HelpMthodes mh=new HelpMthodes();
     //   mh.loadProfileImage(personalimageview,"users","uriImage",UserMainPage.this,usernameUser);


     profilepage=findViewById(R.id.profile_page);

     finddoctorpage=findViewById(R.id.find_doctor_page);
        notificationpage=findViewById(R.id.notification);
       communicationpage=findViewById(R.id.communication);
       dossierpage=findViewById(R.id.Dossier_page);
        medinfopage=findViewById(R.id.Med_Info_page);

     personalbutton=findViewById(R.id.floatingActionButton);


     TextView personalname=findViewById(R.id.personalname);
     TextView personalusername=findViewById(R.id.personalusername);

     personalname.setText(nameUser);
        personalusername.setText(usernameUser);



        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");



        personalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iGallery =new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);





            }
        });


     profilepage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {


             Intent intent2 = new Intent(UserMainPage.this, ProfileActivity.class);

             intent2.putExtra("name", nameUser);
             intent2.putExtra("email", emailUser);
             intent2.putExtra("username", usernameUser);
             intent2.putExtra("password", passwordUser);
             intent2.putExtra("id", "1");


             startActivity(intent2);
             overridePendingTransition(R.anim.pop_enter,R.anim.pop_exit);

         }
     });

     finddoctorpage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(UserMainPage.this,DoctorList.class);
             intent.putExtra("username", nn);

             intent.putExtra("name", nameUser);
             intent.putExtra("email", emailUser);

             intent.putExtra("password", passwordUser);
             startActivity(intent);
         }
     });
        notificationpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserMainPage.this,AppointementList.class);
               // intent.putExtra("username", nn);
                intent.putExtra("username", usernameUser);
                startActivity(intent);
            }
        });

        communicationpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserMainPage.this,ContactDoctorList.class);
                // intent.putExtra("username", nn);
                intent.putExtra("username", usernameUser);
                startActivity(intent);
            }
        });
        dossierpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserMainPage.this,Dossier.class);
                // intent.putExtra("username", nn);
                intent.putExtra("username", usernameUser);
                startActivity(intent);
            }
        });

        medinfopage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserMainPage.this,Med_Info.class);
                // intent.putExtra("username", nn);
                intent.putExtra("username", usernameUser);
                startActivity(intent);
            }
        });










    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver when the activity is destroyed
        unregisterReceiver(receiver);
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
            reference.child(nn).child("uriImage").setValue(imageUri);
        }

    }

    private void doAlarm(){



        databasenotification.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Appointement appointment = dataSnapshot.getValue(Appointement.class);
                    // Check if the appointment is confirmed
                    if (appointment.getConfirm().equals("confirmed")) {

                        String time = appointment.getTime();
                        String date = appointment.getDate();
                        notifications.add(new Notification(time, date));
                    }
                }
             // notifications.add(new Notification("03:05","Apr 09 2024"));
             //   notifications.add(new Notification("01:06","Apr 09 2024"));

                Notification.sortNotifications(notifications);
                scheduleNextAlarm();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors here
            }
        });
    }





private void createNotificationChannel(){


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
        channel.setDescription(description);

        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


}


    private long calculateTimeInMillis(int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Check if the desired time is in the past, if so, set it for the next day
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        return calendar.getTimeInMillis();
    }

    public void scheduleNextAlarm() {
        notificationIndex++;
        if (notificationIndex < notifications.size()) {
            Notification notification = notifications.get(notificationIndex);
            setAlarm(notification.getTime(), notification.getDay());

          //  Toast.makeText(UserMainPage.this, " indice="+notificationIndex, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(UserMainPage.this, " indice="+notificationIndex, Toast.LENGTH_SHORT).show();
        }

    }

    public void setAlarm(String time, String day) {
        Toast.makeText(UserMainPage.this, "time " + time, Toast.LENGTH_SHORT).show();

        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);

        // Get the current day of the week
        Calendar calendar = Calendar.getInstance();
        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Parse the provided day to check if it matches the current day
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd yyyy", Locale.ENGLISH);
        Date providedDate;
        try {
            providedDate = dateFormat.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(UserMainPage.this, " parese date failed ", Toast.LENGTH_SHORT).show();
            return; // Return if parsing the date fails

        }
        calendar.setTime(providedDate);
        int providedDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Compare the provided day with the current day of the week
        if (providedDayOfWeek != currentDayOfWeek) {
            // If the provided day is not the same as the current day, do not set the alarm
            Toast.makeText(UserMainPage.this, " day not today", Toast.LENGTH_SHORT).show();
            return;
        }

        if ((hour < currentHour || (hour == currentHour && minute <= currentMinute))) {
            // If the provided time has already passed for today, do not set the alarm
            Toast.makeText(UserMainPage.this, "time has already passed for today", Toast.LENGTH_SHORT).show();

            scheduleNextAlarm();
           // return;
        }
        else{

        // Check if the notification time has already passed for today
        Calendar notifyTime = Calendar.getInstance();

        notifyTime.set(Calendar.HOUR_OF_DAY, hour);
        notifyTime.set(Calendar.MINUTE, minute);
        notifyTime.set(Calendar.SECOND, 0);
        /*
        if (calendar.before(notifyTime)) {
            // If the notification time has already passed for today, do not set the alarm
            Toast.makeText(UserMainPage.this, "before alarm", Toast.LENGTH_SHORT).show();
            return;
        }
       */
        // Show a toast indicating that the alarm is being set
        Toast.makeText(UserMainPage.this, "Setting alarm", Toast.LENGTH_SHORT).show();

        // Create an intent for the broadcast receiver
        Intent intent = new Intent(UserMainPage.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(UserMainPage.this, 0, intent, 0);

        // Calculate the time in milliseconds based on the chosen hour and minute
        long timeInMillis = calculateTimeInMillis(hour, minute);

        // Get an instance of the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Set the alarm to trigger at the desired time
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);}

    }



















}