package com.example.signuploginrealtime;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
public class AdapterContact extends RecyclerView.Adapter<AdapterContact.MyViewHolder>{
    Context context;
    ArrayList<Contact> list;
int contactmode;
    private  SelectListener listener;

    public AdapterContact(Context context, ArrayList<Contact> list,SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener=listener;
    }
    @NonNull
    @Override
    public AdapterContact.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.contct_doctor_item,parent,false);
        return new AdapterContact.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterContact.MyViewHolder holder, int position) {

        Contact contact = list.get(position);
        holder.doctorName.setText("Dr." + contact.getDoctorName());
        holder.date.setText(contact.getDate());
        holder.time.setText(contact.getTime());
        holder.spec.setText(contact.getSpec());
        holder.confirm.setText(contact.getConfirm());


        if(holder.confirm.getText().equals("canceled")){
            holder.confirm.setTextColor(context.getResources().getColor(R.color.canceled_color));
        }


        DatabaseReference modereference = FirebaseDatabase.getInstance().getReference("doctors").child(contact.getDoctorusername()).child("contactmode");

        modereference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    contactmode = dataSnapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        holder.buttonwatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  contactOnWhatsapp(v,contact);


                
                
                if(contactmode==1){

                Intent intent=new Intent(context,DoctorContactDetails.class);
                intent.putExtra("doctorusername",contact.getDoctorusername());



                v.getContext().startActivity(intent);}else {

                    Toast.makeText(context, "The doctor has stopped the contact service at the present time", Toast.LENGTH_SHORT).show();
                }


            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(list.get(position));
            }
        });



    }


    private void contactOnWhatsapp(View view, Contact contact) {
        String doctorusername = contact.getDoctorusername();

        final String[] phonee = new String[1]; // Change to non-final array
        DatabaseReference doctorsRef = FirebaseDatabase.getInstance().getReference("doctors");

        DatabaseReference doctorQuery = doctorsRef.child(doctorusername);
        doctorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String phone = dataSnapshot.child("phonneNumber").getValue(String.class);

                    Toast.makeText(view.getContext(), "Doctor's Phone Number is = " + phone, Toast.LENGTH_SHORT).show();

                    phonee[0] = copyString(phone); // Assign the copied value

                    if (phonee[0] != null) {
                        String whatsappUser = phonee[0]; // Access the copied value

                        // Now you can use the phone number here
                        String message = "hi i am patient";

                        // Encode the message for URL
                        try {
                            message = URLEncoder.encode(message, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        String url = "https://api.whatsapp.com/send?phone=" + whatsappUser + "&text=" + message;
                        try {
                            PackageManager pm = view.getContext().getPackageManager();
                            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            view.getContext().startActivity(i);
                        } catch (PackageManager.NameNotFoundException e) {
                            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        }
                    } else {
                        System.out.println("Phone number not found for doctor: " + doctorusername);
                    }
                } else {
                    System.out.println("Doctor not found with username: " + doctorusername);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Database Error: " + databaseError.getMessage());
            }
        });
    }



    public static String copyString(String source) {
        // Create a new string with the same value as the source string
        String destination = new String(source);
        return destination;
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView doctorName,date,time,confirm,spec;
        Button buttonwatsapp;

        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName=itemView.findViewById(R.id.docnamecontact);
            date=itemView.findViewById(R.id.tvdatecontact);
            time=itemView.findViewById(R.id.tvtimecontact);
            spec=itemView.findViewById(R.id.tvdocspeccontact);
            confirm=itemView.findViewById(R.id.tvconfirmcontact);
            buttonwatsapp = itemView.findViewById(R.id.button_whatsapp);

            cardView=itemView.findViewById(R.id.main_container3);




        }
    }

}
