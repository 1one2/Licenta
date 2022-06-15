package com.example.licenta.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;
import com.example.licenta.activities.ChatActivity;

import java.util.Vector;

import proto.generated.ChatMenu;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

    private Vector<ChatMenu.ChatHistoryReply> chats;

    public ChatsAdapter(Vector<ChatMenu.ChatHistoryReply> chats) {
        this.chats = chats;
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_bubble_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {

        ChatMenu.ChatHistoryReply bubble = chats.get(position);
        holder.bind(bubble);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ChatsViewHolder extends RecyclerView.ViewHolder {

        private ImageView profilePicture;
        private TextView name;
        private View view;

        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            profilePicture = view.findViewById(R.id.chatBubbleProfilePicture);
            name = view.findViewById(R.id.chatBubbleName);
        }

        public void bind(ChatMenu.ChatHistoryReply bubble){
            name.setText(bubble.getName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Open up the chat
                    Intent chatIntent = new Intent(ApplicationController.getInstance(), ChatActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    chatIntent.putExtra("ID",bubble.getChatId());
                    chatIntent.putExtra("NAME",bubble.getName());
                    ApplicationController.getInstance().startActivity(chatIntent);
                }
            });
        }
    }
}
