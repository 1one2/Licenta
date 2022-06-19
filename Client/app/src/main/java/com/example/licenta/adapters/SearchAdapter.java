package com.example.licenta.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Looper;
import android.text.Layout;
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
import com.example.licenta.activities.ProfileActivity;

import java.util.Vector;
import android.os.Handler;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.SearchAndChat;
import proto.generated.SearchAndChatServiceGrpc;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchResultViewHolder> {

    private Vector<SearchAndChat.SearchReply> results;

    public SearchAdapter() {
        results = new Vector<>();
    }

    public void invalidateResults(){
        int count = getItemCount();
        results = new Vector<>();
        notifyItemRangeRemoved(0,count);
    }

    public void addResult(SearchAndChat.SearchReply result){
        results.add(result);
        notifyItemInserted(getItemCount());
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_bubble_layout,parent, false);
        SearchResultViewHolder viewHolder = new SearchResultViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {

        final boolean[] doneDownloading = {false};
        SearchAndChat.SearchReply result = results.get(position);
        Runnable getProfilePicture = new Runnable() {
            @Override
            public void run() {
                holder.setProfilePictureBitmap(ApplicationController.downloadAProfilePicture(result.getOtherAccountId()));
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

        holder.bind(result);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder {

        private ImageView profilePicture;
        private TextView name;
        private View view;
        private ImageView startChat;
        private Bitmap profilePictureBitmap;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            profilePicture = view.findViewById(R.id.searchBubbleProfilePicture);
            name = view.findViewById(R.id.searchBubbleName);
            startChat = view.findViewById(R.id.searchStartChat);
        }

        public void bind(SearchAndChat.SearchReply result) {
            name.setText(result.getName());
            startChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Get the chat ID from the server

                    ManagedChannel channel = ManagedChannelBuilder.forAddress(ApplicationController.
                                            getInstance().getResources().getString(R.string.server_ip),
                                    ApplicationController.getInstance().getResources().getInteger(R.integer.server_port))
                            .usePlaintext()
                            .build();

                    Runnable startChat = new Runnable() {
                        @Override
                        public void run() {

                            SearchAndChatServiceGrpc.SearchAndChatServiceBlockingStub stub =
                                    SearchAndChatServiceGrpc.newBlockingStub(channel);

                            SearchAndChat.StartChatRequest startChatRequest = SearchAndChat.StartChatRequest.newBuilder()
                                    .setOwnId(ApplicationController.getAccount().getId())
                                    .setOtherAccountId(result.getOtherAccountId())
                                    .build();
                            SearchAndChat.StartChatReply chat = stub.startNewChat(startChatRequest);

                            //Open up the chat

                            Intent chatIntent = new Intent(ApplicationController.getInstance(), ChatActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            chatIntent.putExtra("ID",chat.getChatId());
                            chatIntent.putExtra("NAME",result.getName());
                            ApplicationController.getInstance().startActivity(chatIntent);

                        }
                    };

                    //Start the chat
                    new Thread(startChat).start();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //start the profile activity
                    Intent profile = new Intent(ApplicationController.getInstance(), ProfileActivity.class);
                    profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    profile.putExtra("ID",result.getOtherAccountId());
                    ApplicationController.getInstance().startActivity(profile);
                }
            });

        }

        public void bindPicture() {
            profilePicture.setImageBitmap(profilePictureBitmap);
        }

        public void setProfilePictureBitmap(Bitmap profilePictureBitmap) {
            this.profilePictureBitmap = profilePictureBitmap;
        }
    }
}
