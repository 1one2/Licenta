package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;
import com.example.licenta.adapters.MessageAdapter;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import proto.generated.Messaging;
import proto.generated.MessagingServiceGrpc;
import proto.generated.SearchAndChat;
import proto.generated.SearchAndChatServiceGrpc;

public class ChatActivity extends AppCompatActivity {


    private int CHAT_ID;

    private ImageView profilePicture;
    private TextView name;
    private Button sendButton;
    private EditText messageEditText;
    private RecyclerView chat;
    private MessageAdapter msgAdapter;

    private Vector<Messaging.Message> messages = new Vector<>();

    private ManagedChannel channel;

    private MessagingServiceGrpc.MessagingServiceStub stub;
    private StreamObserver<Messaging.Message> toServer;
    Handler handler = new Handler();



    int size;
    Runnable runnable  = new Runnable() {
        @Override
        public void run() {
            if(size < msgAdapter.getItemCount()) {
                //msgAdapter.notifyDataSetChanged();
                msgAdapter.notifyItemRangeInserted(size,msgAdapter.getItemCount());
                size = msgAdapter.getItemCount();
                chat.smoothScrollToPosition(size);
            }
            handler.postDelayed(this, 1000);
        }
    };
    Runnable getProfilePicture = new Runnable() {
        @Override
        public void run() {
            SearchAndChatServiceGrpc.SearchAndChatServiceBlockingStub blockingStub = SearchAndChatServiceGrpc.newBlockingStub(channel);
            SearchAndChat.ReplyId replyId = blockingStub.getAccountIdFromChat(SearchAndChat.RequestId.newBuilder()
                            .setChatId(CHAT_ID)
                            .setOwnAccountId(ApplicationController.getAccount().getId())
                            .build());
            Bitmap temp = ApplicationController.downloadAProfilePicture(replyId.getAccountId());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setProfilePicture(temp);
                }
            });
        }
    };

    private void setProfilePicture(Bitmap picture){
        profilePicture.setImageBitmap(picture);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initViews();

        //Load profile Picture Thread
        new Thread(getProfilePicture).start();
        handler.post(runnable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toServer.onCompleted();
        channel.shutdownNow();
        try {
            channel.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getMessages(int lastMessageId){
        MessagingServiceGrpc.MessagingServiceBlockingStub blockingStub = MessagingServiceGrpc.newBlockingStub(channel);
        Iterator<Messaging.Message> history = blockingStub
                .getMessagingHistory(Messaging.MessageHistoryRequest
                        .newBuilder()
                        .setLastMessageId(lastMessageId)
                        .setChatId(CHAT_ID)
                        .build());

        while(history.hasNext()){
            msgAdapter.receiveMessage(history.next());
        }
    }


    private void createStub(){

        //region Connect
        //Creating the channel to the server
        channel = ManagedChannelBuilder.forAddress(ApplicationController.
                                getInstance().getResources().getString(R.string.server_ip),
                        ApplicationController.getInstance().getResources().getInteger(R.integer.server_port))
                .usePlaintext()
                .build();

        //Creating the gRPC stub
        stub = MessagingServiceGrpc.newStub(channel);




        //Creating the stream from the server to the client
        toServer = stub.sendReceiveMessage(new StreamObserver<Messaging.MessageFromServer>() {
            @Override
            public void onNext(Messaging.MessageFromServer value) {
                msgAdapter.receiveMessage(value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                toServer.onCompleted();
            }
        });

        //This ensures the live connection
        Messaging.Message sentMessage = Messaging.Message.newBuilder()
                .setChatId(CHAT_ID)
                .setMessage("")
                .setMessageOwnerId(ApplicationController.getAccount().getId())
                .build();
        toServer.onNext(sentMessage);
        //endregion
        getMessages(0);


    }
    private void initViews(){

        Intent intent = getIntent();
        CHAT_ID = intent.getIntExtra("ID",-1);

        //initializing the graphic elements
        profilePicture = findViewById(R.id.profilePictureChatMenu);

        name = findViewById(R.id.chatNameTextView);
        name.setText(intent.getStringExtra("NAME"));

        messageEditText = findViewById(R.id.writingBarEditText);

        //Everything about the RecyclerView
        chat = findViewById(R.id.chat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        chat.setLayoutManager(linearLayoutManager);
        msgAdapter = new MessageAdapter(getApplicationContext(),messages);
        chat.setAdapter(msgAdapter);

        sendButton = findViewById(R.id.sendCommentButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Sending messages to the server
                Messaging.Message sentMessage = Messaging.Message.newBuilder()
                        .setChatId(CHAT_ID)
                        .setMessage(messageEditText.getText().toString())
                        .setMessageOwnerId(ApplicationController.getAccount().getId())
                        .build();
                toServer.onNext(sentMessage);
                messageEditText.setText("");

            }
        });
        createStub();
       }

}