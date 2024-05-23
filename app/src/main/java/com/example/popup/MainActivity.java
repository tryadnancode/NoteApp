package com.example.popup;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener, NoteAdapter.AdapterListener {

    private static final int UPDATE_REQUEST_CODE = 1;

    FloatingActionButton btn;
    Button cancelBtn, saveBtn;
    Dialog dialog;
    EditText title, description, message;
    private Dao dao;
    private NoteDatabase noteDatabase;
    private NoteAdapter noteAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.popUpBtn);
        dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);

        noteDatabase = NoteDatabase.getInstance(this);
        dao = noteDatabase.getDao();

        recyclerView = findViewById(R.id.noteRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteAdapter = new NoteAdapter(this, this, this);
        recyclerView.setAdapter(noteAdapter);

        fetchNotes();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNoteDialog();
            }
        });
    }

    private void showAddNoteDialog() {
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        title = dialog.findViewById(R.id.title);
        description = dialog.findViewById(R.id.desc);
        message = dialog.findViewById(R.id.messAge);

        cancelBtn = dialog.findViewById(R.id.cancel);
        saveBtn = dialog.findViewById(R.id.save);

        cancelBtn.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Notes not saved", Toast.LENGTH_SHORT).show();
        });

        saveBtn.setOnClickListener(v -> {
            String title_st = title.getText().toString();
            String desc_st = description.getText().toString();
            String mess_st = message.getText().toString();

            if (title_st.isEmpty()) {
                title.setError("Enter title");
                Toast.makeText(MainActivity.this, "Please enter Title", Toast.LENGTH_SHORT).show();
                return;
            }
            if (desc_st.isEmpty()) {
                description.setError("Enter description");
                Toast.makeText(MainActivity.this, "Please enter Description", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mess_st.isEmpty()) {
                message.setError("Enter message");
                Toast.makeText(MainActivity.this, "Please enter Message", Toast.LENGTH_SHORT).show();
                return;
            }

            Item item = new Item(0, title_st, desc_st, mess_st);
            dao.insert(item);
            fetchNotes();

            title.setText("");
            description.setText("");
            message.setText("");

            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Notes Saved", Toast.LENGTH_SHORT).show();
        });
    }

    private void fetchNotes() {
        List<Item> items = dao.getAllItems();
        noteAdapter.setItems(items);
    }

    @Override
    public void OnDelete(int id, int pos) {
        dao.delete(id);
        noteAdapter.removeItem(pos);
        Toast.makeText(this, "Delete Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnEdit(Item item) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        intent.putExtra("Item", item);
        startActivityForResult(intent, UPDATE_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchNotes();
    }

     //After double click
      @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("description", item.getDescription());
        intent.putExtra("message", item.getMessage());
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
