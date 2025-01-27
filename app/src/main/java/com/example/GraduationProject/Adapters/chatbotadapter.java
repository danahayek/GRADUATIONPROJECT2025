package com.example.GraduationProject.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.GraduationProject.R;
import com.example.GraduationProject.modules.Topics;
import com.example.GraduationProject.modules.chatbotpatient;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class chatbotadapter extends RecyclerView.Adapter<chatbotadapter.ViewHolder> {

    private List<chatbotpatient> chatMessages;

    public chatbotadapter(List<chatbotpatient> chatMessages) {
        this.chatMessages = chatMessages;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbotactivity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder , @SuppressLint("RecyclerView") final int position) {

        chatbotpatient chatMessage = chatMessages.get(position);
        if (chatMessage.isUser()) {
            // User message
            holder.userMessage.setText(chatMessage.getMessage());
            holder.userMessage.setVisibility(View.VISIBLE);
            holder.botMessage.setVisibility(View.GONE);
        } else {
            // Bot message
            holder.botMessage.setText(chatMessage.getMessage());
            holder.botMessage.setVisibility(View.VISIBLE);
            holder.userMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userMessage;
        TextView botMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userMessage = itemView.findViewById(R.id.user_message);
            botMessage = itemView.findViewById(R.id.bot_message);
        }

        @Override
        public void onClick(View view) {

        }
    }
}