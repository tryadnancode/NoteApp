package com.example.popup;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert
    void insert(Item items);
    @Update
    void update(Item items);
    @Query("delete from Item where id=:id")
    void delete(int id);
    @Query("Select * From Item")
    List<Item> getAllItems();


}
