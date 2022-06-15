package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.licenta.R;

public class SearchActivity extends AppCompatActivity {

    private Button backButton;
    private EditText searchBar;
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


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}