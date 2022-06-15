package com.example.licenta.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.StringPrepParseException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;
import com.example.licenta.adapters.PostsAdapter;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.Login;
import proto.generated.Posts;
import proto.generated.Posts.PostCategory;
import proto.generated.PostsServiceGrpc;

public class MainScreenActivity extends AppCompatActivity {


    private ImageView lastMenuPressed;

    private RecyclerView postsFeed;

    private Intent searchIntent;
    private Intent postingIntent;

    private PostsAdapter postsAdapter;
    private Vector<Posts.Post> posts = new Vector<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ApplicationController.setPostCategory(Posts.PostCategory.POST_CATEGORY_ALL);
        initializeViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        postsFeed.setAdapter(postsAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        changeMenu();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPause() {
        super.onPause();
        //ApplicationController.setPostCategory(PostCategory.POST_CATEGORY_ALL);

    }

    @Override
    public void onBackPressed() {
        //exit app
        finishAffinity();
        finish();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void changeMenu(){
        posts = new Vector<>();

        refreshPosts(true);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshPosts(boolean isFirstTime)
    {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("10.0.2.2",8080)
                .usePlaintext()
                .build();

        PostsServiceGrpc.PostsServiceBlockingStub postsStub = PostsServiceGrpc.newBlockingStub(channel);

        PostCategory category = ApplicationController.getPostCategory();
        Posts.Campus campus = null;
        int house = 0;
        Posts.PostRequest.Builder builder = Posts.PostRequest.newBuilder()
                .setPostCategory(category);
        if(ApplicationController.getAccount() != null) {
            campus = ApplicationController.getAccount().getCampus();
            house = ApplicationController.getAccount().getHouse();

            builder .setWhichCampus(campus)
                    .setWhichHouse(house);
        }

        if(!isFirstTime && !posts.isEmpty())
            builder
                    .setLastPostFetchedId(posts.firstElement().getPostId());

        Iterator<Posts.Post> reply = postsStub.getPosts(builder.build());

        while(reply.hasNext()){
            posts.add(reply.next());
        }
        postsAdapter = new PostsAdapter(posts);
        postsFeed.setAdapter(postsAdapter);
        postsAdapter.notifyDataSetChanged();
        channel.shutdownNow();
        try {
            channel.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    private void onMenuButtonsClick(View view)
    {
        if(lastMenuPressed.getId() != view.getId()){

            lastMenuPressed.setBackground(getResources().getDrawable(R.drawable.unfocused_item));
            lastMenuPressed = (ImageView) view;
            lastMenuPressed.setBackground(getResources().getDrawable(R.drawable.focused_item));

            switch(lastMenuPressed.getId()) {
                case R.id.allPostsButton:
                {
                    ApplicationController.setPostCategory(PostCategory.POST_CATEGORY_ALL);
                    break;
                }
                case R.id.campusPostsButton:
                {
                    ApplicationController.setPostCategory(PostCategory.POST_CATEGORY_CAMPUS);
                    break;
                }
                case R.id.housePostsButton:
                {
                    ApplicationController.setPostCategory(PostCategory.POST_CATEGORY_HOUSE);
                    break;
                }
            }

            changeMenu();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initializeViews()
    {
        //Buttons for the different feeds
        ImageView allPostsButton = findViewById(R.id.allPostsButton);
        ImageView campusPostsButton = findViewById(R.id.campusPostsButton);
        ImageView housePostsButton = findViewById(R.id.housePostsButton);
        lastMenuPressed = allPostsButton;

        //Functions Buttons
        ImageView searchButton = findViewById(R.id.searchButton);
        ImageView chatButton = findViewById(R.id.chatButton);
        TextView postingBubble = findViewById(R.id.postingEditText);
        ImageView menuButton = findViewById(R.id.menuButton);

        //Profile Picture
        ImageView profilePicture = findViewById(R.id.profilePicture);

        //Posts RecyclerView
        postsFeed = findViewById(R.id.postsFeed);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        postsFeed.setLayoutManager(layoutManager);

        searchIntent = new Intent(this,SearchActivity.class);

        //region OnClickListeners

        allPostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuButtonsClick(view);
            }
        });
        campusPostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuButtonsClick(view);
            }
        });
        housePostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuButtonsClick(view);
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuButtonsClick(view);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(searchIntent);

            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChatsMenuActivity.class);
                startActivity(intent);
            }
        });
        postingIntent = new Intent(this, PostActivity.class);
        postingBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(postingIntent);
            }
        });
        //endregion

        refreshPosts(true);


    }

}