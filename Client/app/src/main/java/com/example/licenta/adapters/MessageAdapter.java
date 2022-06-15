package com.example.licenta.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;

import java.util.ArrayList;
import java.util.Vector;

import proto.generated.Messaging;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Vector<Messaging.Message> messages;

    public static final int MESSAGE_TYPE_RECEIVED = 1;
    public static final int MESSAGE_TYPE_SENT = 2;

    public MessageAdapter(Context context, Vector<Messaging.Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    public void receiveMessage(Messaging.Message msg){
        messages.add(msg);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MESSAGE_TYPE_RECEIVED)
            return new MessageReceivedViewHolder(LayoutInflater.from(context).inflate(R.layout.message_received,parent,false));
        return new MessageSentViewHolder(LayoutInflater.from(context).inflate(R.layout.message_send,parent,false));
    }

    @Override
    public int getItemViewType(int position) {
        Messaging.Message message = messages.get(position);
        if(message.getMessageOwnerId() == ApplicationController.getAccount().getId())
            return MESSAGE_TYPE_SENT;
        return MESSAGE_TYPE_RECEIVED;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Messaging.Message message = messages.get(position);
        if(message.getMessageOwnerId() == ApplicationController.getAccount().getId())
            ((MessageSentViewHolder)holder).bind(position);
        else
            ((MessageReceivedViewHolder)holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private class MessageReceivedViewHolder extends RecyclerView.ViewHolder{

        private TextView messageText;
        private View view;
        public MessageReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            messageText = view.findViewById(R.id.message_text_received);
        }

        public void bind(int position){
            Messaging.Message message = messages.get(position);
            messageText.setText(message.getMessage());
        }
    }
    private class MessageSentViewHolder extends RecyclerView.ViewHolder{
        private TextView messageText;
        private View view;
        public MessageSentViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            messageText = view.findViewById(R.id.message_text_send);
        }
        public void bind(int position){
            Messaging.Message message = messages.get(position);
            messageText.setText(message.getMessage());
        }
    }
}
