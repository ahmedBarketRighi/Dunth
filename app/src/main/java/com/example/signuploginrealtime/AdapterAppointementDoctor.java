package com.example.signuploginrealtime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class AdapterAppointementDoctor extends RecyclerView.Adapter<AdapterAppointementDoctor.MyViewHolder>{

    Context context;
    ArrayList<Appointement> list;



    private  SelectListener listener;

    public AdapterAppointementDoctor(Context context, ArrayList<Appointement> list,SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener=listener;
    }



    @NonNull
    @Override
    public AdapterAppointementDoctor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.doctor_appointement_item,parent,false);
        return new AdapterAppointementDoctor.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterAppointementDoctor.MyViewHolder holder, int position) {

        Appointement appointement = list.get(position);
        holder.patientname.setText(appointement.getFirstname()+" "+appointement.getLastname());
        holder.date.setText(appointement.getDate());
        holder.time.setText(appointement.getTime());
        holder.mode.setText(appointement.getMode());
        holder.confirm.setText(appointement.getConfirm());


        HelpMthodes hp=new HelpMthodes();
        hp.loadProfileImage(holder.img_bb,"users","uriImage",context,appointement.getUsername());

        if(holder.confirm.getText().equals("canceled")){
            holder.confirm.setTextColor(context.getResources().getColor(R.color.canceled_color));
        }
        if(holder.confirm.getText().equals("confirmed")){
            holder.confirm.setTextColor(context.getResources().getColor(R.color.blue));
        }

        holder.buttonCanceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(appointement,1); // Pass the current appointment object
            }
        });


        holder.confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(appointement,2);
            }
        });


        holder.contactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(appointement,3);
            }
        });




        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(list.get(position));
            }
        });



    }


    private void showConfirmationDialog(Appointement appointment,int ind) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(ind==1) {
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to cancel this appointment?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cancelAppointment(appointment);
                }
            });

        }
        if(ind==2) {
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to confirm this appointment?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirmAppointment(appointment);
                }
            });

        }
        if(ind==3){
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to make access to patient to contact you ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirmContact(appointment);
                }
            });
        }




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
        DatabaseReference appointmentsRef = FirebaseDatabase.getInstance().getReference("doctorappointments").child(appointment.getDoctorusername());

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

        DatabaseReference appointmentsRef2 = FirebaseDatabase.getInstance().getReference("appointments").child(appointment.getUsername());

        appointmentsRef2.orderByChild("indice").equalTo(appointment.getIndice()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Update the confirmation status for the found appointment
                    snapshot.getRef().child("confirm").setValue("canceled");
                }
                //list.remove(appointment);
               // list.clear();
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });







    }




    private void confirmAppointment(Appointement appointment) {
        DatabaseReference appointmentsRef = FirebaseDatabase.getInstance().getReference("doctorappointments").child(appointment.getDoctorusername());

        appointmentsRef.orderByChild("indice").equalTo(appointment.getIndice()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Update the confirmation status for the found appointment
                    snapshot.getRef().child("confirm").setValue("confirmed");
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

        DatabaseReference appointmentsRef2 = FirebaseDatabase.getInstance().getReference("appointments").child(appointment.getUsername());

        appointmentsRef2.orderByChild("indice").equalTo(appointment.getIndice()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Update the confirmation status for the found appointment
                    snapshot.getRef().child("confirm").setValue("confirmed");
                }
                //list.remove(appointment);
                // list.clear();
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });


    }




    private void confirmContact(Appointement appointment) {
        DatabaseReference appointmentsRef = FirebaseDatabase.getInstance().getReference("doctorappointments").child(appointment.getDoctorusername());

        appointmentsRef.orderByChild("indice").equalTo(appointment.getIndice()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Update the confirmation status for the found appointment
                    snapshot.getRef().child("contact").setValue("yes");
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

        DatabaseReference appointmentsRef2 = FirebaseDatabase.getInstance().getReference("appointments").child(appointment.getUsername());

        appointmentsRef2.orderByChild("indice").equalTo(appointment.getIndice()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Update the confirmation status for the found appointment
                    snapshot.getRef().child("contact").setValue("yes");
                }
                //list.remove(appointment);
                // list.clear();
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

        TextView patientname,date,time,confirm,mode;
        Button buttonCanceled,confirmbtn,contactbtn;
        ImageView img_bb;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patientname=itemView.findViewById(R.id.docname5);
            date=itemView.findViewById(R.id.tvdate5);
            time=itemView.findViewById(R.id.tvtime5);
            mode=itemView.findViewById(R.id.tvmode);
            confirm=itemView.findViewById(R.id.tvconfirm5);
            img_bb=itemView.findViewById(R.id.img_bb);
            buttonCanceled = itemView.findViewById(R.id.button_canceled5);
            confirmbtn = itemView.findViewById(R.id.confirmbtn);
            contactbtn = itemView.findViewById(R.id.contactbtn);



            cardView=itemView.findViewById(R.id.main_container5);




        }
    }
}
