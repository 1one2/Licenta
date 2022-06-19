package com.example.licenta;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;


import androidx.annotation.RequiresApi;

import com.example.licenta.activities.CommentActivity;
import com.google.protobuf.ByteString;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import proto.generated.ImageServiceGrpc;
import proto.generated.Images;
import proto.generated.Login;
import proto.generated.LoginServiceGrpc;
import proto.generated.MessagingServiceGrpc;
import proto.generated.Posts;

public class ApplicationController extends Application{

    private static ApplicationController instance;

    private static Login.Account account;

    private static Posts.PostCategory postCategory;

    private static Posts.Post currentCommentatingPost;
    private static Bitmap profilePicture;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static void downloadProfilePicture(){
        Images.ImageInfo imageInfo = Images.ImageInfo.newBuilder()
                .setImageUsage(Images.ImageUsage.PROFILE_PICTURE)
                .setImageType(".jpg")
                .setAccountId(ApplicationController.getAccount().getId())
                .build();
        ByteArrayOutputStream stream = ApplicationController.downloadImage(imageInfo);
        profilePicture = BitmapFactory.decodeByteArray(stream.toByteArray(),0, stream.size());

    }
    public static Bitmap downloadAProfilePicture(int account_id) {

        Images.ImageInfo imageInfo = Images.ImageInfo.newBuilder()
                .setImageUsage(Images.ImageUsage.PROFILE_PICTURE)
                .setImageType(".jpg")
                .setAccountId(account_id)
                .build();
        ByteArrayOutputStream stream = ApplicationController.downloadImage(imageInfo);

        Bitmap bm =  BitmapFactory.decodeByteArray(stream.toByteArray(),0, stream.size());
        return bm;
    }
    public static ByteArrayOutputStream downloadImage(Images.ImageInfo imageInfo) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        CountDownLatch latch = new CountDownLatch(1);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(
                                instance.getResources().getString(R.string.server_ip),
                        instance.getResources().getInteger(R.integer.server_port))
                .usePlaintext()
                .build();

        //Creating the gRPC stub
        ImageServiceGrpc.ImageServiceStub stub = ImageServiceGrpc.newStub(channel);

        final boolean[] ok = {false};
        stub.downloadImage(imageInfo, new StreamObserver<Images.ImageTransfer>() {

            @Override
            public void onNext(Images.ImageTransfer value) {
                try {
                    stream.write(value.getChunkData().toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                    onError(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
               // Bitmap temp = BitmapFactory.decodeByteArray(stream.toByteArray(),0,stream.size());
                channel.shutdownNow();
                ok[0] = true;
                while(!channel.isTerminated()) {
                    try {
                        channel.awaitTermination(50, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        while(!ok[0]) {
            try {
                latch.await(1, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return stream;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void uploadImage(String imagePath, Images.ImageUsage imageUsage, String imageType, int post_id) throws FileNotFoundException {

        //Creating the channel to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ApplicationController.
                        getInstance().getResources().getString(R.string.server_ip),
                        ApplicationController.getInstance().getResources().getInteger(R.integer.server_port))
                .usePlaintext()
                .build();

        //Creating the gRPC stub
        ImageServiceGrpc.ImageServiceStub stub = ImageServiceGrpc.newStub(channel);
        StreamObserver<Images.ImageTransfer> requestStreamObserver = stub.uploadImage(new StreamObserver<Images.Empty>() {

            @Override
            public void onNext(Images.Empty value) {

                Log.e("onNext:", "HERE");
            }

            @Override
            public void onError(Throwable t) {
                Log.e("onError:", "HERE");
            }

            @Override
            public void onCompleted() {
                Log.e("onCompleted:", "HERE");
            }
        });




        Uri uri = Uri.parse(imagePath);

        InputStream fileInputStream = instance.getContentResolver().openInputStream(uri);
        int account_id = account.getId();
        Images.ImageInfo.Builder builder = Images.ImageInfo.newBuilder()
                .setImageUsage(imageUsage)
                .setAccountId(account_id)
                .setImageType(imageType);
        if (imageUsage == Images.ImageUsage.POST_PICTURE)
            builder.setPostId(post_id);
        Images.ImageTransfer request = Images.ImageTransfer.newBuilder()
                .setImageInfo(builder).build();
        try {
            requestStreamObserver.onNext(request);
            byte[] buffer = new byte[1024];
            while (true) {
                int n = fileInputStream.read(buffer);
                if (n <= 0)
                    break;

                request = Images.ImageTransfer.newBuilder()
                        .setChunkData(ByteString.copyFrom(buffer, 0, n))
                        .build();
                requestStreamObserver.onNext(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        requestStreamObserver.onCompleted();

    }

    public static Bitmap getProfilePicture() {
        return profilePicture;
    }

    public static void setPostCategory(Posts.PostCategory postCategory) {
        ApplicationController.postCategory = postCategory;
    }


    public static void setAccount(Login.Account account) {
        ApplicationController.account = account;
    }

    public static String getUserNameFromEmail(){
        String email = account.getEmail();
        return email.substring(0,email.indexOf(".")) + " " + email.substring(email.indexOf(".")+1,email.indexOf("@"));
    }

    public static void setCurrentCommentatingPost(Posts.Post currentCommentatingPost) {
        ApplicationController.currentCommentatingPost = currentCommentatingPost;
    }

    public static void openCommentActivity()
    {

        Intent commentIntent = new Intent(ApplicationController.getInstance(), CommentActivity.class);
        commentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        instance.startActivity(commentIntent);
    }
    public static ApplicationController getInstance() {
        return instance;
    }

    public static Posts.PostCategory getPostCategory() {
        return postCategory;
    }

    public static Login.Account getAccount() {
        return account;
    }

    public static Posts.Post getCurrentCommentatingPost() {
        return currentCommentatingPost;
    }

}
