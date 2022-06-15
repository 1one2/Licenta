package com.example.licenta.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;
import com.example.licenta.activities.CommentActivity;

import org.w3c.dom.Text;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.Posts;
import proto.generated.PostsServiceGrpc;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private Vector<Posts.Post> posts;

    public PostsAdapter(Vector<Posts.Post> posts) {

        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_layout,parent,false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Posts.Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        private View view;

        private ImageView posterProfilePicture;
        private TextView posterName;
        private TextView postText;
        private ImageView postedPicture;

        private ImageView likeButton;
        private TextView likeText;
        private LinearLayout likeAction;

        private LinearLayout commentButton;
        private ImageView ownProfilePicture;
        private TextView commentBar;

        private boolean isItLiked;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            //find views...

            posterProfilePicture = view.findViewById(R.id.postedProfilePicture);
            posterName = view.findViewById(R.id.postedNameOfThePoster);
            postText = view.findViewById(R.id.postedTextView);
            postedPicture = view.findViewById(R.id.postedPictureImageView);

            likeButton = view.findViewById(R.id.postedLikeActionImage);
            likeText = view.findViewById(R.id.postedLikeActionText);
            likeAction = view.findViewById(R.id.postedLikeAction);

            commentButton = view.findViewById(R.id.postedCommentAction);
            ownProfilePicture = view.findViewById(R.id.postedBottomCommentActionBarProfilePicture);
            commentBar = view.findViewById(R.id.postedWriteAComment);
        }

        public void bind(Posts.Post post) {
            //set on click listener etc
            //posterProfilePicture.setImageResource(post.getProfilePicture())
            isItLiked = post.getLiked();
            if(isItLiked) {
                likeButton.setImageResource(R.drawable.like_vector_pressed);
                likeText.setTextColor(ApplicationController.getInstance().getResources().getColor(R.color.action));
            }
            else {
                likeButton.setImageResource(R.drawable.like_vector_unpressed);
                likeText.setTextColor(ApplicationController.getInstance().getResources().getColor(R.color.white));
            }
            posterName.setText(post.getLitePost().getNameOfPoster());

            postText.setText(post.getLitePost().getPostText());

            //postedPicture.setImageBitmap(post.getPostPicture());

            View.OnClickListener likeClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ManagedChannel channel = ManagedChannelBuilder.forAddress("10.0.2.2",8080)
                            .usePlaintext()
                            .build();

                    PostsServiceGrpc.PostsServiceBlockingStub postsStub = PostsServiceGrpc.newBlockingStub(channel);

                    Posts.SendLike.Builder builder = Posts.SendLike.newBuilder()
                            .setAccountId(post.getLitePost().getPosterId())
                            .setPostId(post.getPostId());

                    if(isItLiked)
                    {
                        isItLiked = false;
                        likeButton.setImageResource(R.drawable.like_vector_unpressed);
                        likeText.setTextColor(ApplicationController.getInstance().getResources().getColor(R.color.white));

                    }
                    else {
                        isItLiked = true;
                        likeButton.setImageResource(R.drawable.like_vector_pressed);
                        likeText.setTextColor(ApplicationController.getInstance().getResources().getColor(R.color.action));
                    }
                    builder.setLiked(isItLiked);
                    //update post database

                    Posts.PostingReply reply = postsStub.likeIt(builder.build());
                    channel.shutdownNow();
                    try {
                        channel.awaitTermination(1, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            };
            likeAction.setOnClickListener(likeClickListener);

            View.OnClickListener commentClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApplicationController.setCurrentCommentatingPost(post);
                    ApplicationController.openCommentActivity();
                }
            };
            commentBar.setOnClickListener(commentClickListener);
            commentButton.setOnClickListener(commentClickListener);

        }
    }
}
