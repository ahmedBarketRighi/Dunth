package com.example.signuploginrealtime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPrescription extends RecyclerView.Adapter<AdapterPrescription.MyViewHolder> {

    Context context;
    ArrayList<Presc> list;



    private  SelectListener listener;

    public AdapterPrescription(Context context, ArrayList<Presc> list,SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener=listener;
    }

    @NonNull
    @Override
    public AdapterPrescription.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.prescription_item,parent,false);
        return new AdapterPrescription.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPrescription.MyViewHolder holder, int position) {
        Presc prescription = list.get(position);

        holder.doctornameofprescription.setText("Docor Name: "+prescription.getDoctorname());
        holder.fullnameofprescription.setText("Full Name: "+prescription.getFullname());
        holder.dateofprescription.setText(prescription.getDate());



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(list.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView doctornameofprescription,dateofprescription,fullnameofprescription;

        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctornameofprescription=itemView.findViewById(R.id.doctornmeofprescription);
            dateofprescription=itemView.findViewById(R.id.dateofprescription);
            fullnameofprescription=itemView.findViewById(R.id.fullnameofprescription);


            cardView=itemView.findViewById(R.id.main_containerofprescription);




        }
    }









}
