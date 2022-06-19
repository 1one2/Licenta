package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;
import com.example.licenta.adapters.ChatsAdapter;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.ChatHistoryServiceGrpc;
import proto.generated.ChatMenu;

public class ChatsMenuActivity extends AppCompatActivity {

    private RecyclerView chatsView;
    private ChatsAdapter adapter;
    private Vector<ChatMenu.ChatHistoryReply> chats;
    private ImageView profilePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_menu);
        initView();
    }

    private void initView(){

        chats = new Vector<>();
        chatsView = findViewById(R.id.chatsRecyclerView);
        //chats.add(ChatMenu.ChatHistoryReply.newBuilder().setName("Test").build());
        adapter = new ChatsAdapter(chats);
        chatsView.setLayoutManager(new LinearLayoutManager(ApplicationController.getInstance()));
        chatsView.setAdapter(adapter);
        profilePicture = findViewById(R.id.profilePictureChatsMenu);
        profilePicture.setImageBitmap(ApplicationController.getProfilePicture());

        refreshChatHistory();

    }


    private void refreshChatHistory(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ApplicationController.
                                getInstance().getResources().getString(R.string.server_ip),
                        ApplicationController.getInstance().getResources().getInteger(R.integer.server_port))
                .usePlaintext()
                .build();

        ChatHistoryServiceGrpc.ChatHistoryServiceBlockingStub chatsStub = ChatHistoryServiceGrpc.newBlockingStub(channel);

        ChatMenu.ChatHistoryRequest request = ChatMenu.ChatHistoryRequest.newBuilder()
                .setUserId(ApplicationController.getAccount().getId())
                .build();

        Iterator<ChatMenu.ChatHistoryReply> reply = chatsStub.getChatHistory(request);

        while(reply.hasNext()){
            chats.add(reply.next());
        }
        adapter.notifyDataSetChanged();


            channel.shutdownNow();
            try {
                channel.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}