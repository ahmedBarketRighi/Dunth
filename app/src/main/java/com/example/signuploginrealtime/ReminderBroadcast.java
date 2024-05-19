package com.example.signuploginrealtime;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = generateRandomId();

        Toast.makeText(context, "Notification is received now", Toast.LENGTH_SHORT).show();

        // Build the sound URI
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "/raw/notif2.mp3");

        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Notification")
                .setContentText("you have appointement now")
                .setSound(soundUri) // Set the notification sound
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Set up audio attributes for the notification sound (for devices targeting Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            // Create the notification channel
            NotificationChannel notificationChannel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setSound(soundUri, audioAttributes); // Set the sound with audio attributes
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationId, mbuilder.build());

        // Broadcast intent to schedule next alarm
        Intent broadcastIntent = new Intent("com.example.signuploginrealtime.SCHEDULE_NEXT_ALARM");
        context.sendBroadcast(broadcastIntent);
    }

    // Method to generate a random notification ID
    private int generateRandomId() {
        Random random = new Random();
        return random.nextInt(10000); // You can adjust the range as needed
    }
}
