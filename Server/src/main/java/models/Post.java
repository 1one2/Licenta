package models;

import proto.generated.Posts;

import java.awt.*;

public class Post {

    private String nameOfPoster;
    private String textOfPost;
    private boolean isItLiked;
    private Image profilePicture;
    private Image postPicture;
    private Posts.PostCategory postCategory;

    //Empty constructor
    public Post() {
    }

    //Constructor without the picture, if the object is created before the pictures, so that they can be added later
    public Post(String nameOfPoster, String textOfPost, boolean isItLiked, Posts.PostCategory postCategory) {
        this.nameOfPoster = nameOfPoster;
        this.textOfPost = textOfPost;
        this.isItLiked = isItLiked;
        this.postCategory = postCategory;
    }

    //Full properties constructor
    public Post(String nameOfPoster, String textOfPost, boolean isItLiked, Image profilePicture, Image postPicture, Posts.PostCategory postCategory) {
        this.nameOfPoster = nameOfPoster;
        this.textOfPost = textOfPost;
        this.isItLiked = isItLiked;
        this.profilePicture = profilePicture;
        this.postPicture = postPicture;
        this.postCategory = postCategory;
    }

    public String getNameOfPoster() {
        return nameOfPoster;
    }

    public void setNameOfPoster(String nameOfPoster) {
        this.nameOfPoster = nameOfPoster;
    }

    public String getTextOfPost() {
        return textOfPost;
    }

    public void setTextOfPost(String textOfPost) {
        this.textOfPost = textOfPost;
    }

    public boolean isItLiked() {
        return isItLiked;
    }

    public void setItLiked(boolean itLiked) {
        isItLiked = itLiked;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Image getPostPicture() {
        return postPicture;
    }

    public void setPostPicture(Image postPicture) {
        this.postPicture = postPicture;
    }

    public Posts.PostCategory getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(Posts.PostCategory postCategory) {
        this.postCategory = postCategory;
    }
}
