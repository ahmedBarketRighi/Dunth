package com.example.signuploginrealtime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDossier extends RecyclerView.Adapter<AdapterDossier.MyViewHolder>{
    Context context;
    ArrayList<DossierClass> list;
    private  SelectListener listener;

    public AdapterDossier(Context context, ArrayList<DossierClass> list,SelectListener listener) {
        this.context = context;
        this.list = list;

        this.listener=listener;
    }

    @NonNull
    @Override
    public AdapterDossier.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.dossier_item,parent,false);
        return new AdapterDossier.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDossier.MyViewHolder holder, int position) {

        DossierClass doctor=list.get(position);
        holder.name.setText(doctor.getFullname());


       // HelpMthodes hp=new HelpMthodes();
      //  hp.loadProfileImage(holder.img_vv,2,context,doctor.getUsername());


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

        TextView name,username, spec;
        ImageView img_vv;
        RatingBar ratingBar;

        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nompersondossier);
            cardView=itemView.findViewById(R.id.main_container66);

           // img_vv=itemView.findViewById(R.id.img_vv);



        }
    }




}
