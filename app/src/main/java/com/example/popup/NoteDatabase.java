package com.example.popup;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities={Item.class},version = 2)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract Dao getDao();

    public static NoteDatabase INSTANCE;
    public static NoteDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,NoteDatabase.class,"NoteDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
