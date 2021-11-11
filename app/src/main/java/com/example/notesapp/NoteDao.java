package com.example.notesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes ORDER BY priority ASC")
    LiveData<List<Note>> getAllNotes();

    @Query("DELETE FROM notes")
    void deleteAllNotes();
}
