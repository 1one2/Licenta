package com.example.licenta.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;

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

        ManagedChannel channel = ManagedChannelBuilder.forAddress("10.0.2.2",8080)
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
        moveAndAddComments();
    }

    private void moveAndAddComments(){
        Posts.Commentary temp = comments.lastElement();

        for(int i = comments.size()-1; i > 0; i--){
            comments.insertElementAt(comments.elementAt(i-1),1);
        }
        comments.insertElementAt(temp,0);
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

        Posts.Commentary commentary = comments.get(position);
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

            //profilePicture.setImageBitmap(commentary.getCommentatorProfilePicture());
        }
    }


}
