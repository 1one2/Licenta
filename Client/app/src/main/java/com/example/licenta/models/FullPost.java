package com.example.licenta.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;

import com.example.licenta.ApplicationController;

import java.io.ByteArrayOutputStream;

import proto.generated.Images;
import proto.generated.Posts;

public class FullPost {

    private Posts.Post post;
    private Bitmap ownProfilePicture;
    private Bitmap posterProfilePicture;
    private Bitmap postedPicture;
    public FullPost(Posts.Post post) {
        this.post = post;

    }

    public Posts.Post getPost() {
        return post;
    }

    public void setPost(Posts.Post post) {
        this.post = post;
    }

    public Bitmap getOwnProfilePicture() {
        return ownProfilePicture;
    }

    public void setOwnProfilePicture(Bitmap ownProfilePicture) {
        this.ownProfilePicture = ownProfilePicture;
    }

    public Bitmap getPosterProfilePicture() {
        return posterProfilePicture;
    }

    public void setPosterProfilePicture(Bitmap posterProfilePicture) {
        this.posterProfilePicture = posterProfilePicture;
    }

    public Bitmap getPostedPicture() {
        return postedPicture;
    }

    public void setPostedPicture(Bitmap postedPicture) {
        this.postedPicture = postedPicture;
    }
}
