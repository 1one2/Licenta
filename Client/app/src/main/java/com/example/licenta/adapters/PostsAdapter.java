package com.example.licenta.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
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
import com.example.licenta.activities.ProfileActivity;
import com.example.licenta.models.FullPost;


import java.io.ByteArrayOutputStream;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.Images;
import proto.generated.Posts;
import proto.generated.PostsServiceGrpc;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private Vector<FullPost> posts;

    public PostsAdapter() {

        this.posts = new Vector<>();
    }

    public void invalidatePosts(){
        int count = getItemCount();
        posts = new Vector<>();
        notifyItemRangeRemoved(0,count);
    }
    public void addPost(FullPost newPost){
    posts.add(newPost);
    notifyItemInserted(getItemCount());
    }

    public int getLastPostID(){
        return posts.firstElement().getPost().getPostId();
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


        FullPost post = posts.get(position);

        boolean[] doneDownloading = {false,false};

        //Post picture
        Runnable getPostedPicture = new Runnable() {
            @Override
            public void run() {
                if(post.getPost().getLitePost().getHasPhoto()) {
                    Images.ImageInfo imageInfo = Images.ImageInfo.newBuilder()
                            .setImageUsage(Images.ImageUsage.POST_PICTURE)
                            .setImageType(".jpg")
                            .setAccountId(post.getPost().getLitePost().getPosterId())
                            .setPostId(post.getPost().getPostId()).build();

                    ByteArrayOutputStream stream = ApplicationController.downloadImage(imageInfo);
                    Bitmap temp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                    post.setPostedPicture(temp);

                }
                doneDownloading[0] = true;
            }
        };
        Runnable getProfilePicture = new Runnable() {
            @Override
            public void run() {

                if(ApplicationController.getAccount().getId() == post.getPost().getLitePost().getPosterId()){
                    post.setPosterProfilePicture(ApplicationController.getProfilePicture());
                }
                else {
                    Bitmap temp = ApplicationController.downloadAProfilePicture(post.getPost().getLitePost().getPosterId());
                    post.setPosterProfilePicture(temp);
                }
                doneDownloading[1] = true;

            }
        };
        Runnable downloadPictures = new Runnable() {
            @Override
            public void run() {
                new Thread(getPostedPicture).start();
                new Thread(getProfilePicture).start();

            }
        };

        post.setOwnProfilePicture(ApplicationController.getProfilePicture());

        Handler handler = new Handler(Looper.getMainLooper());
        //This thread downloads and bind the pictures to the View
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Thread(downloadPictures).start();

                //This thread sleeps until the pictures are done downloading and then it binds them

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(!(doneDownloading[0] && doneDownloading[1])) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //Log.e("Still Running: ", post.getPost().getLitePost().getPostText());
                            //notifyItemChanged(posts.indexOf(post));
                        }
                        handler.post(new Runnable() {
                                         @Override
                                         public void run() {
                                             holder.bindPictures(post);
                                         }
                                     });
                    }
                }).start();

            }
        }).start();

        try {
            holder.bind(post);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        private Bitmap profilePictureBitmap;
        private Bitmap postedPictureBitmap;
        private Bitmap posterProfilePictureBitmap;


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


        public void bindPictures(FullPost post){

            if(post.getPost().getLitePost().getHasPhoto()){
                postedPicture.setImageBitmap(post.getPostedPicture());
                postedPicture.setVisibility(View.VISIBLE);
            }

            posterProfilePicture.setImageBitmap(post.getPosterProfilePicture());


            ownProfilePicture.setImageBitmap(post.getOwnProfilePicture());

        }

        public void bind(FullPost post) throws InterruptedException {
            //set on click listener etc
            //posterProfilePicture.setImageResource(post.getProfilePicture())
            isItLiked = post.getPost().getLiked();
            if(isItLiked) {
                likeButton.setImageResource(R.drawable.like_vector_pressed);
                likeText.setTextColor(ApplicationController.getInstance().getResources().getColor(R.color.action));
            }
            else {
                likeButton.setImageResource(R.drawable.like_vector_unpressed);
                likeText.setTextColor(ApplicationController.getInstance().getResources().getColor(R.color.white));
            }
            posterName.setText(post.getPost().getLitePost().getNameOfPoster());

            postText.setText(post.getPost().getLitePost().getPostText());

            //postedPicture.setImageBitmap(post.getPostPicture());

            View.OnClickListener likeClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ManagedChannel channel = ManagedChannelBuilder.forAddress(ApplicationController.
                                    getInstance().getResources().getString(R.string.server_ip),
                                    ApplicationController.getInstance().getResources().getInteger(R.integer.server_port))
                            .usePlaintext()
                            .build();

                    PostsServiceGrpc.PostsServiceBlockingStub postsStub = PostsServiceGrpc.newBlockingStub(channel);

                    Posts.SendLike.Builder builder = Posts.SendLike.newBuilder()
                            .setAccountId(post.getPost().getLitePost().getPosterId())
                            .setPostId(post.getPost().getPostId());

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
                    ApplicationController.setCurrentCommentatingPost(post.getPost());
                    ApplicationController.openCommentActivity();
                }
            };
            commentBar.setOnClickListener(commentClickListener);
            commentButton.setOnClickListener(commentClickListener);



            posterProfilePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(ApplicationController.getInstance(), ProfileActivity.class);
                    profile.putExtra("ID",post.getPost().getLitePost().getPosterId());
                    profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ApplicationController.getInstance().startActivity(profile);
                }
            });

            ownProfilePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(ApplicationController.getInstance(), ProfileActivity.class);
                    profile.putExtra("ID",ApplicationController.getAccount().getId());
                    profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ApplicationController.getInstance().startActivity(profile);
                }
            });


        }
    }
}
