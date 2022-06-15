package services;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import proto.generated.Messaging;
import proto.generated.MessagingServiceGrpc;

import java.sql.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessagingService extends MessagingServiceGrpc.MessagingServiceImplBase {

    //Integer is the chat_id, then a have a Pair of Pairs in which the integer is the owner_id, and the second is the observer for the stream
    private static final Set<StreamObserver<Messaging.MessageFromServer>> observers = ConcurrentHashMap.newKeySet();
    private final static String normalSql = "SELECT * FROM MESSAGES WHERE CHAT_ID = '%s'";

    //owner_id, observer
    //Pair<Integer,StreamObserver<Messaging.Message>>

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


        ServerCallStreamObserver<Messaging.MessageFromServer> observer = (ServerCallStreamObserver<Messaging.MessageFromServer>) responseObserver;
        observer.setOnCancelHandler(() -> {
            System.out.println("Stream cancelled."); // never invoked
        });
        new Thread(() -> {
            while (!observer.isCancelled()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.err.println("isCancelled switched to true"); // this message eventually gets logged
        }).start();
        observers.add(observer);

        return new StreamObserver<Messaging.Message>() {
            @Override
            public void onNext(Messaging.Message message) {
                //receiving data from client

                System.out.println(String.format("Got a message from: '%s' : '%s'", message.getMessageOwnerId(), message.getMessage()));
                for(StreamObserver<Messaging.MessageFromServer> observer : observers){
                    observer.onNext(Messaging.MessageFromServer.newBuilder().setMessage(message).build());
                   //observer.onCompleted();
                }
            }

            @Override
            public void onError(Throwable throwable) {

                observers.remove(responseObserver);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                observers.remove(responseObserver);
            }

        };
    }
}

