package com.example.licenta.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
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

        final boolean[] doneDownloading = {false};
        ChatMenu.ChatHistoryReply bubble = chats.get(position);
        Runnable getProfilePicture = new Runnable() {
            @Override
            public void run() {
                holder.setProfilePictureBitmap(ApplicationController.downloadAProfilePicture(bubble.getAccountId()));
                doneDownloading[0] = true;
            }
        };
        Thread downloadPicture = new Thread(getProfilePicture);
        Handler handler = new Handler(Looper.getMainLooper());


        //This thread downloads and bind the pictures to the View
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadPicture.start();

                //This thread sleeps until the pictures are done downloading and then it binds them
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(!doneDownloading[0]){
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                holder.bindPicture();
                            }
                        });
                    }
                }).start();
            }
        }).start();
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
        private Bitmap profilePictureBitmap;


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

        public void setProfilePictureBitmap(Bitmap profilePictureBitmap) {
            this.profilePictureBitmap = profilePictureBitmap;
        }

        public void bindPicture() {
            profilePicture.setImageBitmap(profilePictureBitmap);
        }
    }
}
