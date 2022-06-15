package services;

import io.grpc.stub.StreamObserver;
import proto.generated.Posts;
import proto.generated.PostsServiceGrpc;

import java.sql.*;

public class PostsService extends PostsServiceGrpc.PostsServiceImplBase {

    private final static String selectSql = "SELECT * FROM posts WHERE  POST_CATEGORY = '%s' ";
    private final static String andPostID = "AND POST_ID > '%s' ";
    private final static String house = "AND WHICH_HOUSE = '%s' ";
    private final static String campus = "AND WHICH_CAMPUS = '%s' ";
    private final static String orderBy = "ORDER BY POST_ID DESC ";
    @Override
    public void getPosts(Posts.PostRequest request, StreamObserver<Posts.Post> responseObserver) {
        //Get posts from database and send them to the client

        Statement statement = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root","");

            statement = con
                    .createStatement();
            String sql;
            String select;
            if(request.getLastPostFetchedId() > 0) {
                select = selectSql + andPostID;

                if(request.getPostCategory() == Posts.PostCategory.POST_CATEGORY_CAMPUS){
                    sql = String.format(select + campus + orderBy,
                            parsePostCategoryToString(request.getPostCategory()),
                            request.getLastPostFetchedId(),
                            parseCampusToString(request.getWhichCampus()));
                }
                else if(request.getPostCategory() == Posts.PostCategory.POST_CATEGORY_HOUSE){
                    sql = String.format(select + house + orderBy,
                            parsePostCategoryToString(request.getPostCategory()),
                            request.getLastPostFetchedId(),
                            request.getWhichHouse());
                }
                else {
                    sql = String.format(select  + orderBy,
                            parsePostCategoryToString(request.getPostCategory()),
                            request.getLastPostFetchedId());
                }
            }
            else {
                select = selectSql;

                if(request.getPostCategory() == Posts.PostCategory.POST_CATEGORY_CAMPUS){
                    sql = String.format(select + campus + orderBy,
                            parsePostCategoryToString(request.getPostCategory()),
                            parseCampusToString(request.getWhichCampus()));
                }
                else if(request.getPostCategory() == Posts.PostCategory.POST_CATEGORY_HOUSE){
                    sql = String.format(select + house + orderBy,
                            parsePostCategoryToString(request.getPostCategory()),
                            request.getWhichHouse());
                }
                else {
                    sql = String.format(select  + orderBy,
                            parsePostCategoryToString(request.getPostCategory()));
                }
            }
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next())
            {
                statement = con.createStatement();
                ResultSet liked = statement.executeQuery(String.format("SELECT liked from Likes WHERE POST_ID = '%s' AND ACCOUNT_ID = '%s'",
                        resultSet.getInt("POST_ID"),resultSet.getInt("POSTER_ID")));
                boolean isLiked = false;
                if(liked.next())
                    isLiked = (liked.getInt("liked") == 1);

                Posts.Post post = Posts.Post.newBuilder()
                        .setPostId(resultSet.getInt("POST_ID"))
                        .setLitePost(Posts.LitePost.newBuilder()
                                    .setPosterId(resultSet.getInt("POSTER_ID"))
                                    .setNameOfPoster(resultSet.getString("NAME_OF_POSTER"))
                                    .setPostText(resultSet.getString("POST_TEXT"))
                                    .setPostCategory(request.getPostCategory())
                                    .setWhichHouse(resultSet.getInt("WHICH_HOUSE"))
                                    .build()
                                    )
                                //.setPosterProfilePicture()

                                    .setLiked(isLiked)
                                    .build();

                responseObserver.onNext(post);
            }

            responseObserver.onCompleted();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void likeIt(Posts.SendLike request, StreamObserver<Posts.PostingReply> responseObserver) {

        Statement statement;
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root","");

            statement = con
                    .createStatement();

            String verifyExistenceSql = String.format("Select * From Likes Where POST_ID = '%s' AND ACCOUNT_ID = '%s'",
                    request.getPostId(),
                    request.getAccountId());
            ResultSet resultSet = statement.executeQuery(verifyExistenceSql);
            if(resultSet.next()){
                String sql = String.format("Update Likes Set Liked = '%s' Where POST_ID = '%s' AND ACCOUNT_ID = '%s'",
                        parseBooleanToInt(request.getLiked()),
                        request.getPostId(),
                        request.getAccountId());
                statement = con.createStatement();
                statement.executeUpdate(sql);
            }
            else {
                String sql = String.format("INSERT INTO Likes VALUES('%s', '%s', '%s')",
                        request.getPostId(),
                        request.getAccountId(),
                        parseBooleanToInt(request.getLiked()));
                statement = con.createStatement();
                statement.executeUpdate(sql);
            }
            responseObserver.onNext(Posts.PostingReply.newBuilder().setSuccessful(true).build());
            responseObserver.onCompleted();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            responseObserver.onNext(Posts.PostingReply.newBuilder().setSuccessful(false).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void sendComment(Posts.LiteCommentary request, StreamObserver<Posts.PostingReply> responseObserver) {

        Statement statement;
        try{
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root","");
            statement = con.createStatement();
            String sql = String.format("INSERT INTO COMMENT (COMMENTATOR_ID, POST_ID, NAME_OF_COMMENTATOR, COMMENT_TEXT) VALUES ('%s','%s','%s','%s')",
                    request.getCommentatorId(),
                    request.getPostId(),
                    request.getNameOfCommentator(),
                    request.getCommentText()
                    );

            int resultSet = statement.executeUpdate(sql);

            responseObserver.onNext(Posts.PostingReply.newBuilder().setSuccessful(true).build());
            responseObserver.onCompleted();

        } catch (SQLException e) {

            System.err.println(e);
            responseObserver.onNext(Posts.PostingReply.newBuilder().setSuccessful(true).build());
        }
    }

    @Override
    public void getComments(Posts.CommentariesRequest request, StreamObserver<Posts.Commentary> responseObserver) {

        Statement statement;
        try{
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root","");
            statement = con.createStatement();
            String sql = String.format("SELECT * FROM COMMENT WHERE POST_ID = '%s' ORDER BY COMMENT_ID DESC",
                    request.getPostId());

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Posts.Commentary.Builder replyBuilder = Posts.Commentary.newBuilder()
                        .setCommentId(resultSet.getInt("COMMENT_ID"))
                        .setLiteCommentary(Posts.LiteCommentary.newBuilder()
                                .setCommentatorId(resultSet.getInt("COMMENTATOR_ID"))
                                .setPostId(resultSet.getInt("POST_ID"))
                                .setNameOfCommentator(resultSet.getString("NAME_OF_COMMENTATOR"))
                                .setCommentText(resultSet.getString("COMMENT_TEXT"))
                                .build())
                        //.setCommentatorProfilePicture()
                        ;
                responseObserver.onNext(replyBuilder.build());
            }
            responseObserver.onCompleted();


        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void postIt(Posts.LitePost request, StreamObserver<Posts.PostingReply> responseObserver) {
        //Get the post from the client and store it in the database
        Statement statement = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root","");

            statement = con
                    .createStatement();

            String sql = String.format("INSERT INTO posts (POSTER_ID,NAME_OF_POSTER,POST_TEXT,POST_CATEGORY,WHICH_HOUSE,WHICH_CAMPUS)"
                    + "VALUES ('%s','%s','%s','%s','%s','%s');",
                    request.getPosterId(),
                    request.getNameOfPoster(),
                    request.getPostText(),
                    parsePostCategoryToString(request.getPostCategory()),
                    request.getWhichHouse(),
                    parseCampusToString(request.getWhichCampus()));

            int resultSet = statement.executeUpdate(sql);
            responseObserver.onNext(Posts.PostingReply.newBuilder().setSuccessful(true).build());
            responseObserver.onCompleted();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            responseObserver.onNext(Posts.PostingReply.newBuilder().setSuccessful(false).build());
            responseObserver.onCompleted();
        }
    }

    private int parseBooleanToInt(boolean bool){
        if(bool)
            return 1;
        return 0;
    }
    private String parsePostCategoryToString(Posts.PostCategory postCategory){
        if(postCategory == Posts.PostCategory.POST_CATEGORY_ALL)
            return "ALL";
        if(postCategory == Posts.PostCategory.POST_CATEGORY_CAMPUS)
            return "CAMPUS";
        return "HOUSE";
    }
    private String parseCampusToString(Posts.Campus campus){
        if(campus == Posts.Campus.CAMPUS_MEMO)
            return "MEMO";
        return "COLINA";
    }
}
