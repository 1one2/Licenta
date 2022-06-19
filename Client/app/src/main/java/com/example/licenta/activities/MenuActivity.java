package com.example.licenta.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import proto.generated.Images;

public class MenuActivity extends AppCompatActivity {

    private TextView name;
    private ImageView profilePicture;
    private TextView uploadPhoto;
    private TextView seeYourProfile;
    private Button logOut;

    private ActivityResultLauncher<Intent> launcherSelectPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initViews();
    }


    private void startProfileActivity(){
        Intent profile = new Intent(ApplicationController.getInstance(),ProfileActivity.class);
        profile.putExtra("ID",ApplicationController.getAccount().getId());
        startActivity(profile);
    }
    private void initViews(){

        name = findViewById(R.id.nameTextViewMenu);
        profilePicture = findViewById(R.id.profilePictureMenu);
        uploadPhoto = findViewById(R.id.menuUploadPhoto);
        seeYourProfile = findViewById(R.id.underNameText);
        logOut = findViewById(R.id.logOutButton);

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

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();

            }
        });

        name.setText(ApplicationController.getUserNameFromEmail());
        seeYourProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProfileActivity();
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProfileActivity();
            }
        });
        profilePicture.setImageBitmap(ApplicationController.getProfilePicture());
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOutIntent = new Intent(ApplicationController.getInstance(), LoginActivity.class);
                finish();
                startActivity(logOutIntent);
            }
        });

    }

    private void selectImage()
    {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("Add photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(options[i].equals("Choose from Gallery")){

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
            if (ActivityCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MenuActivity.this,
                        Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MenuActivity.this);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Camera and Gallery permissions are necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MenuActivity.this,
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_CAMERA);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(MenuActivity.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CAMERA);
                }
            }
        }
    }
}