package com.example.licenta;

import android.app.Application;
import android.content.Intent;


import com.example.licenta.activities.CommentActivity;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.Login;
import proto.generated.LoginServiceGrpc;
import proto.generated.Posts;

public class ApplicationController extends Application{

    private static ApplicationController instance;

    private static Login.Account account;

    private static Posts.PostCategory postCategory;

    private static Posts.Post currentCommentatingPost;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
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
