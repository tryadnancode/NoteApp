package com.example.popup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    EditText title, description, message;
    private Button cancelBtn, saveBtn;
    private Item item;
    private NoteDatabase noteDatabase;
    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        noteDatabase = NoteDatabase.getInstance(this);
        dao = noteDatabase.getDao();

        title = findViewById(R.id.edit_title);
        description = findViewById(R.id.edit_desc);
        message = findViewById(R.id.edit_messAge);
        cancelBtn = findViewById(R.id.cancel2);
        saveBtn = findViewById(R.id.save2);

        item = (Item) getIntent().getSerializableExtra("Item");
        title.setText(item.getTitle());
        description.setText(item.getDescription());
        message.setText(item.getMessage());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_st = title.getText().toString();
                String desc_st = description.getText().toString();
                String mess_st = message.getText().toString();

                if (title_st.isEmpty()) {
                    title.setError("Enter title");
                    Toast.makeText(UpdateActivity.this, "Please enter Title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (desc_st.isEmpty()) {
                    description.setError("Enter description");
                    Toast.makeText(UpdateActivity.this, "Please enter Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mess_st.isEmpty()) {
                    message.setError("Enter message");
                    Toast.makeText(UpdateActivity.this, "Please enter Message", Toast.LENGTH_SHORT).show();
                    return;
                }

                item.setTitle(title_st);
                item.setDescription(desc_st);
                item.setMessage(mess_st);
                dao.update(item);

                Toast.makeText(UpdateActivity.this, "Notes Updated Successfully", Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Notes not updated", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
