package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.licenta.ApplicationController;
import com.example.licenta.R;
import com.example.licenta.adapters.SearchAdapter;

import java.util.Iterator;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.generated.SearchAndChat;
import proto.generated.SearchAndChatServiceGrpc;

public class SearchActivity extends AppCompatActivity {

    private Button backButton;
    private EditText searchBar;
    private RecyclerView searchResults;
    private SearchAdapter searchAdapter;

    ManagedChannel channel = ManagedChannelBuilder.forAddress(ApplicationController.
                            getInstance().getResources().getString(R.string.server_ip),
                    ApplicationController.getInstance().getResources().getInteger(R.integer.server_port))
            .usePlaintext()
            .build();

    SearchAndChatServiceGrpc.SearchAndChatServiceBlockingStub stub = SearchAndChatServiceGrpc.newBlockingStub(channel);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeViews();
    }

    private void initializeViews()
    {
        backButton = findViewById(R.id.searchBackButton);
        searchBar = findViewById(R.id.searchBarEditText);
        searchResults = findViewById(R.id.searchResultsRecyclerView);
        searchAdapter = new SearchAdapter();
        searchResults.setLayoutManager(new LinearLayoutManager(ApplicationController.getInstance()));
        searchResults.setAdapter(searchAdapter);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Handler handler = new Handler();
                Runnable searchAccounts = new Runnable() {
                    @Override
                    public void run() {


                        searchAdapter.invalidateResults();
                        SearchAndChat.SearchRequest request = SearchAndChat.SearchRequest.newBuilder()
                                .setText(searchBar.getText().toString())
                                .build();

                        Iterator<SearchAndChat.SearchReply> people = stub.searchForPeople(request);

                        while(people.hasNext()){
                            //add them to the recyler View
                            searchAdapter.addResult(people.next());
                        }

                    }
                };

                handler.post(searchAccounts);
            }
        });

    }
}