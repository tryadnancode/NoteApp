package com.example.popup;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Item implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo (name = "Title")
    public String title;
    @ColumnInfo (name = "Description")
    public String description;
    @ColumnInfo (name = "Message")
    public String message;
    int edit, delete;

    public Item(int id, String title, String description, String message) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.message = message;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() { return title;  }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
