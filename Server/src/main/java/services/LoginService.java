package services;

import io.grpc.stub.StreamObserver;
import proto.generated.Login;
import proto.generated.LoginServiceGrpc;
import proto.generated.Posts;

import java.sql.*;
import java.util.Locale;

public class LoginService extends LoginServiceGrpc.LoginServiceImplBase {
    @Override
    public void login(Login.LoginRequest request, StreamObserver<Login.LoginReply> responseObserver) {
        System.out.println("Somebody is trying to log in");
        Login.LoginReply.Builder response = Login.LoginReply.newBuilder();
        Statement statement = null;
            try {
                Connection con = DriverManager.getConnection("jdbc:h2:~/Licenta", "root","");

                statement = con
                        .createStatement();

                String sql = String.format("SELECT id, complex, house FROM accounts WHERE accounts.email = '%s' AND accounts.password = '%s'",
                        request.getEmail(),request.getPassword());

                ResultSet resultSet = statement.executeQuery(sql);
                if(resultSet.next())
                {
                    Login.Account account = Login.Account.newBuilder()
                            .setId(resultSet.getInt("id"))
                            .setEmail(request.getEmail())
                            .setPassword(request.getPassword())
                            .setCampus(parseStringToCampus(resultSet.getString("complex")))
                            .setHouse(resultSet.getInt("house"))
                            .build();

                    response.setSuccessful(true);
                    response.setAccount(account);
                }
                else
                    response.setSuccessful(false);

                responseObserver.onNext(response.build());
                responseObserver.onCompleted();

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
