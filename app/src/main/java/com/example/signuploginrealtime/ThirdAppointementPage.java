package com.example.signuploginrealtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class ThirdAppointementPage extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button datebutton,confirmbutton;
int member;
    TimePicker timePicker;
    String usernameUser;
    String time,times;
    FirebaseDatabase database,database2;
    DatabaseReference reference,reference2,reference3;

    String appointementtype;
    String appointementmode;
    String doctorUsername;
    TextView validtimes,validdays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_appointement_page);




        datebutton = findViewById(R.id.datepickerbutton);
        datebutton.setText(getTodaysDate());



        validtimes=findViewById(R.id.validtimes);

        validdays=findViewById(R.id.validdays);




        RadioGroup radioGroup = findViewById(R.id.radioGroup1);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if(radioButton != null) {
                    appointementtype = radioButton.getText().toString();

                }
            }
        });

        RadioGroup radioGroup2 = findViewById(R.id.radioGroup2);

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if(radioButton != null) {
                    appointementmode = radioButton.getText().toString();

                }
            }
        });








        Intent intent2 = getIntent();

        usernameUser = intent2.getStringExtra("username");



         doctorUsername = intent2.getStringExtra("DOCTOR_Username");
        String doctorName = intent2.getStringExtra("DOCTOR_NAME");
        String spec=intent2.getStringExtra("spec");
        String firstnamepatient = intent2.getStringExtra("fnpatient");
        String lastnamepatient = intent2.getStringExtra("lspatient");
        String emailpatient = intent2.getStringExtra("empatient");
        String phonepatient = intent2.getStringExtra("phpatient");
        String sexpatient = intent2.getStringExtra("sexpatient");

        String nameUser = intent2.getStringExtra("name");
        String emailUser = intent2.getStringExtra("email");
        String passwordUser = intent2.getStringExtra("password");

        String fromto = intent2.getStringExtra("fromto");
        validdays.setText("From/to: "+fromto);


        String confirm="waiting";
        times = intent2.getStringExtra("times");

        schudulethetime();
        initDatePicker();

     //   addToFirebase();

        //validtimes.setText(times);
       // Toast.makeText(this, "times"+times, Toast.LENGTH_SHORT).show();


        Toast.makeText(ThirdAppointementPage.this, "user name is : " + usernameUser, Toast.LENGTH_SHORT).show();

        confirmbutton=findViewById(R.id.confirm);
        timePicker = findViewById(R.id.timepicker);

        // Set up a listener to detect when the time is changed
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // Get the hour and minute selected by the user
                time = formatTime(hourOfDay, minute);

                Toast.makeText(ThirdAppointementPage.this, "Selected Time: " + time, Toast.LENGTH_SHORT).show();
            }
        });



    /*    datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  schudulethetime();
            }
        });*/





        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HelpMthodes hp=new HelpMthodes();

                String remplcetimes=validtimes.getText().toString();
                remplcetimes= hp.removeHourFromTimes(time,remplcetimes);

                if(remplcetimes.equals("false")){

                    Toast.makeText(ThirdAppointementPage.this, "choose valide time", Toast.LENGTH_SHORT).show();
                }

                else {

                    if (!appointementmode.isEmpty() &&
                            !appointementtype.isEmpty() &&
                            !datebutton.getText().toString().isEmpty()){

                    Intent intent = new Intent(ThirdAppointementPage.this, UserMainPage.class);

                    intent.putExtra("username", usernameUser);

                    intent.putExtra("name", nameUser);
                    intent.putExtra("email", emailUser);

                    intent.putExtra("password", passwordUser);


                    database = FirebaseDatabase.getInstance();
                    // database2 = FirebaseDatabase.getInstance();

                    reference = database.getReference("appointments").child(usernameUser);
                    reference2 = database.getReference("doctorappointments").child(doctorUsername);

                    int indice=generateRandomId();

                    // Get the number of appointments for this user
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long numAppointments = dataSnapshot.getChildrenCount();
                            String appointmentId = String.valueOf(numAppointments + 1); // Increment the number and convert to string

                            // Create a new reference with the sequential number as the key
                            DatabaseReference newAppointmentRef = reference.child(appointmentId);

                            HelperAppointement appointementClass = new HelperAppointement(appointmentId, usernameUser, firstnamepatient, lastnamepatient, emailpatient, phonepatient, sexpatient, doctorUsername, time, datebutton.getText().toString(), appointementtype, appointementmode, doctorName, spec, confirm,indice,"no");

                            newAppointmentRef.setValue(appointementClass);
                            Toast.makeText(ThirdAppointementPage.this, "Appointment registered successfully: " + time, Toast.LENGTH_SHORT).show();

                            //  startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long numAppointments = dataSnapshot.getChildrenCount();
                            String appointmentId = String.valueOf(numAppointments + 1); // Increment the number and convert to string


                            DatabaseReference newAppointmentRef = reference2.child(appointmentId);

                            HelperAppointement appointementClass = new HelperAppointement(appointmentId, usernameUser, firstnamepatient, lastnamepatient, emailpatient, phonepatient, sexpatient, doctorUsername, time, datebutton.getText().toString(), appointementtype, appointementmode, doctorName, spec, confirm,indice,"no");

                            newAppointmentRef.setValue(appointementClass);
                            Toast.makeText(ThirdAppointementPage.this, "Appointment registered successfully: " + time, Toast.LENGTH_SHORT).show();

                            //  startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle errors here
                        }
                    });


                    addscheduletodoctor(remplcetimes);

                    addmember();


                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
                    finish();}else {
                        Toast.makeText(ThirdAppointementPage.this, "Make sure that you have filled all required information.", Toast.LENGTH_SHORT).show();


                    }



                }




            }
        });




    }



    private void addmember(){
        DatabaseReference modereference = FirebaseDatabase.getInstance().getReference("doctors").child(doctorUsername).child("member");

        modereference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    member = dataSnapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Toast.makeText(this, "member"+member, Toast.LENGTH_SHORT).show();
     //   member=member+1;


        Toast.makeText(this, "member"+member, Toast.LENGTH_SHORT).show();
        DatabaseReference ratingRefdoc = FirebaseDatabase.getInstance().getReference("doctors").child(doctorUsername);





        ratingRefdoc.child("member").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                ratingRefdoc.child("member").setValue(member+1, new DatabaseReference.CompletionListener() {
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
















    private String formatTime(int hour, int minute) {
        String hourString = hour < 10 ? "0" + hour : String.valueOf(hour);
        String minuteString = minute < 10 ? "0" + minute : String.valueOf(minute);
        return hourString + ":" + minuteString;
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                datebutton.setText(date);

                schudulethetime();

            }

        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";

        //default should never happen
        return "JAN";
    }


    private void addscheduletodoctor(String time) {


        reference3 = database.getReference("schudeledoctor").child(doctorUsername);



        // Get the number of appointments for this user
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Id_date = datebutton.getText().toString(); // Increment the number and convert to string

                // Create a new reference with the sequential number as the key
                DatabaseReference newAppointmentRef = reference3.child(Id_date);

                ScheduleClass appointementClass = new ScheduleClass(Id_date,time,doctorUsername, Id_date);

                newAppointmentRef.setValue(appointementClass);

                Toast.makeText(ThirdAppointementPage.this, "doctor schudule successfully: " + time, Toast.LENGTH_SHORT).show();

                //  startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }







    private void schudulethetime() {

        String date_id = datebutton.getText().toString();
        // Assume doctorUsername and date_id are already defined

        DatabaseReference doctorScheduleRef = FirebaseDatabase.getInstance().getReference("schudeledoctor").child(doctorUsername).child(date_id);

        doctorScheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String time = dataSnapshot.child("time").getValue(String.class);
                    Toast.makeText(ThirdAppointementPage.this, "time"+time, Toast.LENGTH_SHORT).show();
                    validtimes.setText(time);
                    // Use the retrieved time as needed
                } else {
                    validtimes.setText(times);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });



    }




    private int generateRandomId() {
        Random random = new Random();
        return random.nextInt(10000); // You can adjust the range as needed
    }
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

}