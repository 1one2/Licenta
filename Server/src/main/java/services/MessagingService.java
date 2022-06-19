package services;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import models.ChatSession;
import proto.generated.Messaging;
import proto.generated.MessagingServiceGrpc;

import java.sql.*;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessagingService extends MessagingServiceGrpc.MessagingServiceImplBase {

    //Integer is the chat_id, then a have a Pair of Pairs in which the integer is the owner_id, and the second is the observer for the stream
    private static final HashMap<Integer, ChatSession> observers = new HashMap<>();
    private final static String normalSql = "SELECT * FROM MESSAGES WHERE CHAT_ID = '%s'";


    @Override
    public void getMessagingHistory(Messaging.MessageHistoryRequest request, StreamObserver<Messaging.Message> responseObserver) {
        //The request contains only the chatId
        //Just send the whole chat history for this chat with the help of the $ChatId
        Statement statement = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root", "");

            statement = con
                    .createStatement();


            String sql;
            if (request.getLastMessageId() == 0)
                sql = String.format(normalSql,
                        request.getChatId());
            else sql = String.format(normalSql + " AND MESSAGE_ID > '%s'",
                    request.getChatId(), request.getLastMessageId());


            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String s = resultSet.getString("MESSAGE_TEXT");
                Messaging.Message reply = Messaging.Message.newBuilder()
                        .setMessageId(resultSet.getInt("MESSAGE_ID"))
                        .setChatId(resultSet.getInt("CHAT_ID"))
                        .setMessageOwnerId(resultSet.getInt("OWNER_ID"))
                        .setMessage(resultSet.getString("MESSAGE_TEXT"))
                        .build();

                responseObserver.onNext(reply);
            }
            responseObserver.onCompleted();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public StreamObserver<Messaging.Message> sendReceiveMessage(StreamObserver<Messaging.MessageFromServer> responseObserver) {
        //  return super.sendReceiveMessage(responseObserver);
        //While you are on the inside of the chat the bilateral stream will be a continue one

        final int[] chat_id = new int[1];
        final int[] owner_id = new int[1];

        return new StreamObserver<Messaging.Message>() {
            @Override
            public void onNext(Messaging.Message message) {
                //receiving data from client

                if(message.getMessage().equals("")){
                    if(observers.containsKey(message.getChatId())) {
                        observers.get(message.getChatId()).setOnline(message.getMessageOwnerId(),responseObserver);
                    }
                    else{
                        ChatSession newSession = new ChatSession();
                        newSession.setOnline(message.getMessageOwnerId(),responseObserver);
                        observers.put(message.getChatId(),newSession);
                    }
                }
                else{
                    Statement statement = null;
                    chat_id[0] = message.getChatId();
                    owner_id[0] = message.getMessageOwnerId();
                    try {
                        Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root", "");

                        statement = con
                                .createStatement();


                        String sql = String.format("INSERT INTO MESSAGES (CHAT_ID, OWNER_ID, MESSAGE_TEXT) VALUES ('%s','%s','%s')",
                                message.getChatId(), message.getMessageOwnerId(),message.getMessage());

                        int resultSet = statement.executeUpdate(sql);

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                    observers.get(message.getChatId()).sendMessage(Messaging.MessageFromServer.newBuilder().setMessage(message).build());
                    System.out.println(String.format("Got a message from: '%s' : '%s'", message.getMessageOwnerId(), message.getMessage()));
                }

            }

            @Override
            public void onError(Throwable throwable) {

                if(observers.containsKey(chat_id[0])) {
                    observers.get(chat_id[0]).setOffline(owner_id[0]);
                }
               System.err.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                if(observers.get(chat_id[0]) != null) {
                    observers.get(chat_id[0]).setOffline(owner_id[0]);
                }
            }

        };
    }
}

