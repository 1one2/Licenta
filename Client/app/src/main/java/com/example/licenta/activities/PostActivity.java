package com.example.licenta.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;
import com.google.protobuf.ByteString;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.Login;
import proto.generated.Posts;
import proto.generated.PostsServiceGrpc;

public class PostActivity extends AppCompatActivity {

    private Spinner categories;
    private EditText postText;
    private Button postButton;
    private TextView nameOfPoster;
    private ImageView addPhoto;
    private ImageView uploadedPhoto;
    private ActivityResultLauncher<Intent> launcherCapturePhoto;
    private ActivityResultLauncher<Intent> launcherSelectPhoto;
    private Login.Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initializeViews();
    }

    private void initializeViews() {

        launcherCapturePhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                            Intent data = result.getData();
                            Bundle extras = data.getExtras();
                            Bitmap image = (Bitmap)extras.get("data");
                            uploadedPhoto.setImageBitmap(image);
                            uploadedPhoto.setVisibility(View.VISIBLE);
                    }
                });
        launcherSelectPhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        uploadedPhoto.setImageURI(Uri.parse(data.getDataString()));
                        uploadedPhoto.setVisibility(View.VISIBLE);
                    }
                });

        //Category in which the post is gonna be published: Public(ALL), Campus or just House
        categories = findViewById(R.id.postingCategorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);

        //"Your own name"
        nameOfPoster = findViewById(R.id.postingNameOfThePoster);

        nameOfPoster.setText(ApplicationController.getUserNameFromEmail());

        //The input space for the post
        postText = findViewById(R.id.postingTextInput);

        //The "SEND" button
        postButton = findViewById(R.id.sendPostButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManagedChannel channel = ManagedChannelBuilder.forAddress("10.0.2.2",8080)
                        .usePlaintext()
                        .build();

                PostsServiceGrpc.PostsServiceBlockingStub postsStub = PostsServiceGrpc.newBlockingStub(channel);


                Posts.PostCategory current;
                if(((String)categories.getSelectedItem()).equals("All"))
                    current = Posts.PostCategory.POST_CATEGORY_ALL;
                else if(((String)categories.getSelectedItem()).equals("Campus"))
                    current = Posts.PostCategory.POST_CATEGORY_CAMPUS;
                else
                    current = Posts.PostCategory.POST_CATEGORY_HOUSE;
                Posts.LitePost.Builder postBuilder = Posts.LitePost.newBuilder()
                        .setPosterId(account.getId())
                        .setNameOfPoster(nameOfPoster.getText().toString())
                        .setPostText(postText.getText().toString())
                        .setPostCategory(current);

                if(current == Posts.PostCategory.POST_CATEGORY_CAMPUS){

                    postBuilder.setWhichCampus(account.getCampus());
                }
                else if(current == Posts.PostCategory.POST_CATEGORY_HOUSE){
                    postBuilder.setWhichHouse(account.getHouse());
                }

                /* The photo part to be updated in a future part
                if(uploadedPhoto.getVisibility() == View.VISIBLE) {
                    uploadedPhoto.buildDrawingCache();
                    Bitmap bm = uploadedPhoto.getDrawingCache();
                    bm.setConfig(Bitmap.Config.RGB_565);
                    ByteBuffer buffer = ByteBuffer.allocate(1553040);
                    buffer.rewind();
                    bm.copyPixelsToBuffer(buffer);
                    postBuilder
                            .setPostPicture(ByteString.copyFrom(buffer.array()));
                }
                */

                Posts.LitePost request = postBuilder.build();
                Posts.PostingReply reply = postsStub.postIt(request);
                if(!reply.getSuccessful()){
                    // do something if not successful
                }
                channel.shutdownNow();
                try {
                    channel.awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finish();
            }
        });

        //Add a photo button
        addPhoto = findViewById(R.id.postingAddPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        //the space where the photo will be displayed
        uploadedPhoto = findViewById(R.id.postingUploadPhotoView);

    }

    private void selectImage()
    {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
        builder.setTitle("Add photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(options[i].equals("Take Photo")){

                  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  checkPermissionToOpenCamera(intent);
                }
                else if(options[i].equals("Choose from Gallery")){

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    launcherSelectPhoto.launch(intent);

                }
                else {
                    dialogInterface.dismiss();
                }
            }
        });

       builder.show();
    }
    public static final int REQUEST_CAMERA = 111;

    void checkPermissionToOpenCamera(Intent intent){
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(PostActivity.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(PostActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(PostActivity.this,
                        Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(PostActivity.this);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Camera and Gallery permissions are necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(PostActivity.this,
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_CAMERA);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(PostActivity.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CAMERA);
                }
            } else {
                launcherCapturePhoto.launch(intent);
            }
        } else {
            launcherCapturePhoto.launch(intent);
        }
    }
}