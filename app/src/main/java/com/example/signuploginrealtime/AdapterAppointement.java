package com.example.signuploginrealtime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class AdapterAppointement extends RecyclerView.Adapter<AdapterAppointement.MyViewHolder>{

    Context context;
    ArrayList<Appointement> list;



    private  SelectListener listener;

    public AdapterAppointement(Context context, ArrayList<Appointement> list,SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener=listener;
    }



    @NonNull
    @Override
    public AdapterAppointement.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.appointement_item,parent,false);
        return new AdapterAppointement.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterAppointement.MyViewHolder holder, int position) {

        Appointement appointement = list.get(position);
        holder.doctorName.setText("Dr." + appointement.getDoctorName());
        holder.date.setText(appointement.getDate());
        holder.time.setText(appointement.getTime());
        holder.spec.setText(appointement.getSpec());
        holder.confirm.setText(appointement.getConfirm());

        if(holder.confirm.getText().equals("canceled")){
            holder.confirm.setTextColor(context.getResources().getColor(R.color.canceled_color));
        }
        if(holder.confirm.getText().equals("confirmed")){
            holder.confirm.setTextColor(context.getResources().getColor(R.color.blue));
        }

        holder.buttonCanceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(appointement); // Pass the current appointment object
            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(list.get(position));
            }
        });



    }


    private void showConfirmationDialog(Appointement appointment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to cancel this appointment?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelAppointment(appointment);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    private void cancelAppointment(Appointement appointment) {
        DatabaseReference appointmentsRef = FirebaseDatabase.getInstance().getReference("appointments").child(appointment.getUsername());

        // Query to find the appointment based on its attributes
        appointmentsRef.orderByChild("indice").equalTo(appointment.getIndice()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Update the confirmation status for the found appointment
                    snapshot.getRef().child("confirm").setValue("canceled");
                }
                //list.remove(appointment);
                list.clear();
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });



        DatabaseReference appointmentsRef2 = FirebaseDatabase.getInstance().getReference("doctorappointments").child(appointment.getDoctorusername());

        appointmentsRef2.orderByChild("indice").equalTo(appointment.getIndice()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Update the confirmation status for the found appointment
                    snapshot.getRef().child("confirm").setValue("canceled");
                }
                //list.remove(appointment);
                list.clear();
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });









    }


   /*
    private void cancelAppointment(Appointement appointment) {
        // Update the confirmation status in the database
        appointment.setConfirm("canceled");

      //  databaseReference.child(appointment.getUsername()).child(appointment.getId()).setValue(appointment);

        Toast.makeText(context, "Appointment id: " + appointment.getUsername(), Toast.LENGTH_SHORT).show();
    }
*/




    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView doctorName,date,time,confirm,spec;
        Button buttonCanceled;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName=itemView.findViewById(R.id.docname);
            date=itemView.findViewById(R.id.tvdate);
            time=itemView.findViewById(R.id.tvtime);
            spec=itemView.findViewById(R.id.tvdocspec);
            confirm=itemView.findViewById(R.id.tvconfirm);
            buttonCanceled = itemView.findViewById(R.id.button_canceled);

            cardView=itemView.findViewById(R.id.main_container2);




        }
    }
}
