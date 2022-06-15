package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;
import com.example.licenta.adapters.MessageAdapter;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import proto.generated.Messaging;
import proto.generated.MessagingServiceGrpc;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initViews();
    }

    @Override
    protected void onStop() {
        super.onStop();
        channel.shutdownNow();
        try {
            channel.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createStub(){
        channel = ManagedChannelBuilder.forAddress("10.0.2.2",8080)
                .usePlaintext()
                .build();

        stub = MessagingServiceGrpc.newStub(channel);


        toServer = stub.sendReceiveMessage(new StreamObserver<Messaging.MessageFromServer>() {


            @Override
            public void onNext(Messaging.MessageFromServer value) {

                msgAdapter.receiveMessage(value.getMessage());
                Log.e("MY.ERROR:",value.getMessage().getMessageOwnerId() + " : " + value.getMessage() );
                msgAdapter.notifyDataSetChanged();
                //toServer.onNext(value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                //nothing

                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                Log.e("Completed:","Why");
                toServer.onCompleted();
            }
        });
    }
    private void initViews(){
        profilePicture = findViewById(R.id.profilePictureChatMenu);
        name = findViewById(R.id.chatNameTextView);
        Intent intent = getIntent();
        CHAT_ID = intent.getIntExtra("ID",-1);
        name.setText(intent.getStringExtra("NAME"));
        sendButton = findViewById(R.id.sendCommentButton);
        messageEditText = findViewById(R.id.writingBarEditText);
        chat = findViewById(R.id.chat);
        chat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        msgAdapter = new MessageAdapter(getApplicationContext(),messages);
        chat.setAdapter(msgAdapter);

        createStub();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Messaging.Message sentMessage = Messaging.Message.newBuilder()
                        .setChatId(CHAT_ID)
                        .setMessage(messageEditText.getText().toString())
                        .setMessageOwnerId(ApplicationController.getAccount().getId())
                        .build();
                toServer.onNext(sentMessage);
                messageEditText.setText("");

            }
        });
       }

}