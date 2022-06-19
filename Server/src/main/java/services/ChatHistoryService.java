package services;

import io.grpc.stub.StreamObserver;
import proto.generated.ChatHistoryServiceGrpc;
import proto.generated.ChatMenu;
import proto.generated.Login;

import java.sql.*;

public class ChatHistoryService extends ChatHistoryServiceGrpc.ChatHistoryServiceImplBase {
    @Override
    public void getChatHistory(ChatMenu.ChatHistoryRequest request, StreamObserver<ChatMenu.ChatHistoryReply> responseObserver) {
        //the request contains the userId
        //use that to get the whole chat history

        Statement statement = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root","");

            statement = con
                    .createStatement();

            String sql = String.format("SELECT * FROM CHATS WHERE FIRST_ID = '%s' OR SECOND_ID = '%s'",
                    request.getUserId(),request.getUserId());

            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next())
            {

                //the first ID is the client, so we need to query on the second
                statement = con.createStatement();
                if(resultSet.getInt("FIRST_ID") == request.getUserId()){
                    sql = String.format("SELECT EMAIL FROM ACCOUNTS WHERE ID = '%s'",
                            resultSet.getInt("SECOND_ID"));
                }
                //The second ID is the client, so we need to query on the first
                else {
                    sql = String.format("SELECT EMAIL FROM ACCOUNTS WHERE ID = '%s'",
                            resultSet.getInt("FIRST_ID"));
                }
                ResultSet nameResult = statement.executeQuery(sql);

                nameResult.next();
                int otherId;
                if(resultSet.getInt("FIRST_ID") == request.getUserId())
                    otherId = resultSet.getInt(("SECOND_ID"));
                else
                    otherId = resultSet.getInt(("FIRST_ID"));
                String email = nameResult.getString("EMAIL");
                String name = email.substring(0,email.indexOf(".")) + " " + email.substring(email.indexOf(".")+1,email.indexOf("@"));
                ChatMenu.ChatHistoryReply reply = ChatMenu.ChatHistoryReply.newBuilder()
                        .setChatId(resultSet.getInt("CHAT_ID"))
                        .setName(name)
                        .setAccountId(otherId)
                        .build();

                responseObserver.onNext(reply);
            }
            responseObserver.onCompleted();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
