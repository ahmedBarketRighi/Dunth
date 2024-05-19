package com.example.signuploginrealtime;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Notification {

    String time,day;

    public Notification(String time, String day) {
        this.time = time;
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    public static void sortNotifications(List<Notification> notifications) {
        // Define a custom Comparator for sorting notifications
        Comparator<Notification> comparator = new Comparator<Notification>() {
            @Override
            public int compare(Notification n1, Notification n2) {
                // Compare the days first
                int dayComparison = n1.getDay().compareTo(n2.getDay());
                if (dayComparison != 0) {
                    return dayComparison;
                }
                // If the days are the same, compare the times
                return n1.getTime().compareTo(n2.getTime());
            }
        };
        // Sort the notifications using the custom comparator
        Collections.sort(notifications, comparator);
    }
}
