package com.example.licenta.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;
import com.example.licenta.activities.ProfileActivity;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.Posts;
import proto.generated.PostsServiceGrpc;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>{

    private Vector<Posts.Commentary> comments = new Vector<>();
    private Posts.Post post;

    public CommentsAdapter(Posts.Post post){
        this.post = post;

        ManagedChannel channel = ManagedChannelBuilder.forAddress(ApplicationController.
                                getInstance().getResources().getString(R.string.server_ip),
                        ApplicationController.getInstance().getResources().getInteger(R.integer.server_port))
                .usePlaintext()
                .build();
        PostsServiceGrpc.PostsServiceBlockingStub postsStub = PostsServiceGrpc.newBlockingStub(channel);

        Posts.CommentariesRequest.Builder builder = Posts.CommentariesRequest.newBuilder()
                .setPostId(post.getPostId());
        Iterator<Posts.Commentary> reply = postsStub.getComments(builder.build());
        while(reply.hasNext()){
            comments.add(reply.next());
        }
        channel.shutdownNow();
        try {
            channel.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    public void postComment(Posts.Commentary commentary){
        comments.add(commentary);
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_layout,parent,false);
        CommentViewHolder commentViewHolder = new CommentViewHolder(view);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        final boolean[] doneDownloading = {false};
        Posts.Commentary commentary = comments.get(position);
        Runnable getProfilePicture = new Runnable() {
            @Override
            public void run() {
                holder.setProfilePictureBitmap(ApplicationController.downloadAProfilePicture(commentary.getLiteCommentary().getCommentatorId()));
                doneDownloading[0] = true;
            }
        };
        Thread downloadPicture = new Thread(getProfilePicture);
        Handler handler = new Handler(Looper.getMainLooper());


        //This thread downloads and bind the pictures to the View
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadPicture.start();

                //This thread sleeps until the pictures are done downloading and then it binds them
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(!doneDownloading[0]){
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                holder.bindPicture();
                            }
                        });
                    }
                }).start();
            }
        }).start();

        holder.bind(commentary);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final ImageView profilePicture;
        private final TextView name;
        private final TextView textComment;
        private Bitmap profilePictureBitmap;


        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;


            profilePicture = view.findViewById(R.id.commentatorProfilePicture);
            name = view.findViewById(R.id.nameOfCommentator);
            textComment = view.findViewById(R.id.commentatedTextView);
        }

        public void bind(Posts.Commentary commentary) {

            name.setText(commentary.getLiteCommentary().getNameOfCommentator());
            textComment.setText(commentary.getLiteCommentary().getCommentText());

            profilePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(ApplicationController.getInstance(), ProfileActivity.class);
                    profile.putExtra("ID",commentary.getLiteCommentary().getCommentatorId());
                    profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ApplicationController.getInstance().startActivity(profile);
                }
            });
        }


        public void setProfilePictureBitmap(Bitmap profilePictureBitmap) {
            this.profilePictureBitmap = profilePictureBitmap;
        }

        public Bitmap getProfilePictureBitmap() {
            return profilePictureBitmap;
        }

        public void bindPicture() {
            profilePicture.setImageBitmap(profilePictureBitmap);
        }
    }


}
