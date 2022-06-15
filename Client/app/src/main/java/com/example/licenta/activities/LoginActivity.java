package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.Login;
import proto.generated.LoginServiceGrpc;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews(){
        Button loginButton = findViewById(R.id.loginButton);
        TextView errorMessage = findViewById(R.id.loginErrorMessage);
        EditText eMailInput = findViewById(R.id.email_edit_text);
        EditText passwordInput = findViewById(R.id.password_edit_text);
        Intent mainScreenIntent = new Intent(this, MainScreenActivity.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //opening the channel to the server
                ManagedChannel channel = ManagedChannelBuilder.forAddress("10.0.2.2",8080)
                        .usePlaintext()
                        .build();

                //creating the corresponding stub for the microservice
                LoginServiceGrpc.LoginServiceBlockingStub loginStub = LoginServiceGrpc.newBlockingStub(channel);


                //building the request
                Login.LoginRequest request = Login.LoginRequest.newBuilder()
                        .setEmail(eMailInput.getText().toString())
                        .setPassword(passwordInput.getText().toString())
                        .build();

                //sending it and getting the reply
                Login.LoginReply loginReply =  loginStub.login(request);
                if(!loginReply.getSuccessful())
                {
                    //if something goes wrong, display an error message
                    errorMessage.setVisibility(View.VISIBLE);
                }
                else {
                    //else move to the next part of the application
                    ApplicationController.setAccount(loginReply.getAccount());
                    startActivity(mainScreenIntent);
                    channel.shutdownNow();
                    try {
                        channel.awaitTermination(1, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    finish();
                }

            }
        });
    }
}