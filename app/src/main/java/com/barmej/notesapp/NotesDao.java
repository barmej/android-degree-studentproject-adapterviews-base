package com.barmej.notesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.barmej.notesapp.classes.Note;

import java.util.ArrayList;
import java.util.List;

@Dao
@TypeConverters(NoteConverter.class)
public interface NotesDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notesTable")
    LiveData<List<Note>> getAllNotes();
}
