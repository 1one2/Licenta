package services;

import io.grpc.stub.StreamObserver;
import proto.generated.*;

import java.sql.*;
import java.util.Locale;

public class SearchAndChatService extends SearchAndChatServiceGrpc.SearchAndChatServiceImplBase {

    @Override
    public void getAccountInfo(SearchAndChat.AccountRequest request, StreamObserver<Login.Account> responseObserver) {
        Statement statement = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root", "");

            statement = con
                    .createStatement();

            String[] name = request.getName().split(" ");
            String sql = "SELECT * FROM ACCOUNTS WHERE EMAIL LIKE ''%s'.'%s'@%";
            String  query = String.format(sql,name[0],name[1]);


            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                Login.Account account = Login.Account.newBuilder()
                        .setId(resultSet.getInt("id"))
                        .setEmail(resultSet.getString("EMAIL"))
                        .setCampus(parseStringToCampus(resultSet.getString("complex")))
                        .setHouse(resultSet.getInt("house"))
                        .build();

                responseObserver.onNext(account);
            }

            responseObserver.onCompleted();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void getAccountIdFromChat(SearchAndChat.RequestId request, StreamObserver<SearchAndChat.ReplyId> responseObserver) {
        Statement statement = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root", "");

            statement = con
                    .createStatement();


            String sql = String.format("SELECT * FROM CHATS WHERE CHAT_ID = '%s'",
                        request.getChatId());

            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                int id;
                if(resultSet.getInt("FIRST_ID") == request.getOwnAccountId())
                    id = resultSet.getInt("SECOND_ID");
                else
                    id = resultSet.getInt("FIRST_ID");
                SearchAndChat.ReplyId replyId = SearchAndChat.ReplyId.newBuilder()
                        .setAccountId(id)
                        .build();
                responseObserver.onNext(replyId);
            }

            responseObserver.onCompleted();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void searchForPeople(SearchAndChat.SearchRequest request, StreamObserver<SearchAndChat.SearchReply> responseObserver) {
        Statement statement = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root", "");

            statement = con
                    .createStatement();


            String sql = "SELECT * FROM ACCOUNTS WHERE EMAIL LIKE '%" + request.getText() + "%'";


            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String email = resultSet.getString("EMAIL");
                String name =  email.substring(0,email.indexOf(".")) + " " + email.substring(email.indexOf(".")+1,email.indexOf("@"));

                SearchAndChat.SearchReply reply = SearchAndChat.SearchReply.newBuilder()
                        .setName(name)
                        .setOtherAccountId(resultSet.getInt("ID"))
                        .build();
                responseObserver.onNext(reply);
                System.out.println(String.format("Sent: '%s'",reply.getName()));
            }
            responseObserver.onCompleted();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void startNewChat(SearchAndChat.StartChatRequest request, StreamObserver<SearchAndChat.StartChatReply> responseObserver) {
        Statement statement = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root", "");

            statement = con
                    .createStatement();


            String sql = String.format("SELECT * FROM CHATS WHERE (FIRST_ID = '%s' AND SECOND_ID = '%s') OR (FIRST_ID = '%s' AND SECOND_ID = '%s')",
                    request.getOwnId(),request.getOtherAccountId(),request.getOtherAccountId(),request.getOwnId());


            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                SearchAndChat.StartChatReply reply = SearchAndChat.StartChatReply.newBuilder()
                        .setChatId(resultSet.getInt("CHAT_ID"))
                        .build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
            }
            else {
                sql = String.format("INSERT INTO CHATS (FIRST_ID,SECOND_ID) VALUES ('%s','%s')",
                        request.getOwnId(),request.getOtherAccountId());
                statement = con.createStatement();
                statement.executeUpdate(sql);

                sql = String.format("SELECT * FROM CHATS WHERE FIRST_ID = '%s' AND SECOND_ID = '%s'",
                request.getOwnId(),request.getOtherAccountId());
                statement = con.createStatement();
                resultSet = statement.executeQuery(sql);
                resultSet.next();
                responseObserver.onNext(SearchAndChat.StartChatReply.newBuilder().setChatId(resultSet.getInt("CHAT_ID")).build());
                responseObserver.onCompleted();

            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private Posts.Campus parseStringToCampus(String name){
        if(name.toLowerCase(Locale.ROOT).equals("memo"))
            return Posts.Campus.CAMPUS_MEMO;
        return  Posts.Campus.CAMPUS_COLINA;
    }
}
