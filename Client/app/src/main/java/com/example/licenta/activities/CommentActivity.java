package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;
import com.example.licenta.adapters.CommentsAdapter;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.Login;
import proto.generated.Posts;
import proto.generated.PostsServiceGrpc;

public class CommentActivity extends AppCompatActivity {

    private EditText commentEditText;
    private CommentsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initViews();
    }
    private void initViews(){
        commentEditText = findViewById(R.id.commentBarEditText);
        Button sendButton = findViewById(R.id.sendCommentButton);
        RecyclerView commentsView = findViewById(R.id.commentsView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        commentsView.setLayoutManager(linearLayoutManager);
        adapter = new CommentsAdapter(ApplicationController.getCurrentCommentatingPost());
        commentsView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(commentEditText.getText().toString().length() > 0){

                    ManagedChannel channel = ManagedChannelBuilder.forAddress("10.0.2.2",8080)
                            .usePlaintext()
                            .build();
                    PostsServiceGrpc.PostsServiceBlockingStub postsStub = PostsServiceGrpc.newBlockingStub(channel);

                    Posts.LiteCommentary.Builder builder = Posts.LiteCommentary.newBuilder()
                            .setCommentatorId(ApplicationController.getAccount().getId())
                            .setPostId(ApplicationController.getCurrentCommentatingPost().getPostId())
                            .setNameOfCommentator(ApplicationController.getUserNameFromEmail())
                            .setCommentText(commentEditText.getText().toString());

                    Posts.LiteCommentary toSend = builder.build();
                    Posts.PostingReply reply = postsStub/*.withDeadlineAfter(2000, TimeUnit.MILLISECONDS)*/.sendComment(toSend);

                    adapter.postComment(Posts.Commentary.newBuilder()
                            .setLiteCommentary(toSend)
                            //.setCommentatorProfilePicture(ownprofilepicture)
                            .build());
                    adapter.notifyDataSetChanged();
                    channel.shutdownNow();
                    try {
                        channel.awaitTermination(1,TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    commentEditText.setText("");

                }
            }
        });
    }
}