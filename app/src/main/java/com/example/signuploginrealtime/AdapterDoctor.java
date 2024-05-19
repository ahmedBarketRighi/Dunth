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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterDoctor extends RecyclerView.Adapter<AdapterDoctor.MyViewHolder> {

   Context context;
   ArrayList<Doctor> list;
   private  SelectListener listener;


    public AdapterDoctor(Context context, ArrayList<Doctor> list,SelectListener listener) {
        this.context = context;
        this.list = list;

        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.doctor_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

     Doctor doctor=list.get(position);
     holder.name.setText(doctor.getName());
     holder.username.setText(doctor.getUsername());
     holder.spec.setText(doctor.getSpec());
     holder.ratingBar.setRating(Float.valueOf(doctor.getRating()));


     HelpMthodes hp=new HelpMthodes();
     hp.loadProfileImage(holder.img_vv,"doctors","personalimageuri",context,doctor.getUsername());


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
           name=itemView.findViewById(R.id.tvname);
            username=itemView.findViewById(R.id.tvusername);
            spec =itemView.findViewById(R.id.tvspec);
            cardView=itemView.findViewById(R.id.main_container);
            ratingBar=itemView.findViewById(R.id.tvrating);
            img_vv=itemView.findViewById(R.id.img_vv);



        }
    }




}
