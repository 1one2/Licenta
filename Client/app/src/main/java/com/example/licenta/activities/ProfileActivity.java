package com.example.licenta.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;
import com.example.licenta.adapters.PostsAdapter;
import com.example.licenta.models.FullPost;

import java.io.FileNotFoundException;
import java.util.Iterator;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.Images;
import proto.generated.Posts;
import proto.generated.PostsServiceGrpc;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private TextView name;
    private RecyclerView profilePosts;
    private PostsAdapter postsAdapter;
    private Handler handler = new Handler();
    private boolean isNameSet = false;
    private Button backButton;
    private TextView searchBar;
    private ActivityResultLauncher<Intent> launcherSelectPhoto;

    ManagedChannel channel = ManagedChannelBuilder.forAddress(ApplicationController.
                            getInstance().getResources().getString(R.string.server_ip),
                    ApplicationController.getInstance().getResources().getInteger(R.integer.server_port))
            .usePlaintext()
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();

    }
    private void initViews(){

        profilePicture = findViewById(R.id.profilePH);
        name = findViewById(R.id.profileName);
        profilePosts = findViewById(R.id.profilePostsRecyclerView);
        profilePosts.setNestedScrollingEnabled(false);
        postsAdapter = new PostsAdapter();

        profilePosts.setLayoutManager(new LinearLayoutManager(ApplicationController.getInstance()));
        profilePosts.setAdapter(postsAdapter);
        backButton = findViewById(R.id.profileBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        searchBar = findViewById(R.id.profileBarEditText);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(ApplicationController.getInstance(),SearchActivity.class);
                searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ApplicationController.getInstance().startActivity(searchIntent);
            }
        });

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID",0);

        if(id == ApplicationController.getAccount().getId()) {
            profilePicture.setImageBitmap(ApplicationController.getProfilePicture());
            ImageView uploadProfilePicture = findViewById(R.id.profileUploadPhoto);
            uploadProfilePicture.setVisibility(View.VISIBLE);
            launcherSelectPhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            Intent data = result.getData();
                            String imagePath = data.getDataString();
                            try {
                                ApplicationController.uploadImage(imagePath, Images.ImageUsage.PROFILE_PICTURE,".jpg",0);
                                ApplicationController.downloadProfilePicture();
                                profilePicture.setImageBitmap(ApplicationController.getProfilePicture());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            uploadProfilePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectImage();
                }
            });

        }
        else {
            Runnable getProfilePicture = new Runnable() {
                @Override
                public void run() {
                    profilePicture.setImageBitmap(ApplicationController.downloadAProfilePicture(id));
                }
            };

            handler.post(getProfilePicture);
        }

        Runnable getPosts = new Runnable() {
            @Override
            public void run() {

                PostsServiceGrpc.PostsServiceBlockingStub stub = PostsServiceGrpc.newBlockingStub(channel);

                Posts.PostsRequestByAccountId request = Posts.PostsRequestByAccountId.newBuilder()
                        .setMyAccountId(ApplicationController.getAccount().getId())
                        .setAccountId(id)
                        .build();

                Iterator<Posts.Post> posts = stub.getPostsById(request);

                while(posts.hasNext()){
                    Posts.Post post = posts.next();
                    if(!isNameSet)
                    {
                        name.setText(post.getLitePost().getNameOfPoster());
                        isNameSet = true;
                    }
                    postsAdapter.addPost(new FullPost(post));
                }
            }
        };
        handler.post(getPosts);



    }
    private void selectImage() {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (options[i].equals("Choose from Gallery")) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    launcherSelectPhoto.launch(intent);

                } else {
                    dialogInterface.dismiss();
                }
            }
        });

        builder.show();
    }
}