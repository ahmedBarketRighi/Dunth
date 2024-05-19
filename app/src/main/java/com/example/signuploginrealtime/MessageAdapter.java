package com.example.signuploginrealtime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends  RecyclerView.Adapter<MessageAdapter.MyViewHolder>{


    List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);

        MyViewHolder myViewHolder=new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

      Message message=messageList.get(position);
      if (message.getSendby().equals(Message.SENT_BY_ME)){
          holder.leftChatview.setVisibility(View.GONE);
          holder.rightChatView.setVisibility(View.VISIBLE);
          holder.rightTextView.setText(message.getMessage());


      }else {
          holder.rightChatView.setVisibility(View.GONE);
          holder.leftChatview.setVisibility(View.VISIBLE);
          holder.leftTextView.setText(message.getMessage());

      }











    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftChatview,rightChatView;
        TextView leftTextView,rightTextView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

           leftChatview=itemView.findViewById(R.id.left_chat_view);

            rightChatView=itemView.findViewById(R.id.right_chat_view);
            leftTextView=itemView.findViewById(R.id.left_chat_text_view);
            rightTextView=itemView.findViewById(R.id.right_chat_text_view);








        }
    }


}
