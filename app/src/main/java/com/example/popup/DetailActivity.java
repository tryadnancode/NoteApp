package com.example.popup;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView titleTextView = findViewById(R.id.title);
        TextView descriptionTextView = findViewById(R.id.desc);
        TextView messageTextView = findViewById(R.id.messAge);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String message = getIntent().getStringExtra("message");

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        messageTextView.setText(message);
    }
}