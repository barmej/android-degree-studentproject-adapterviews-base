package com.barmej.notesapp.room.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.barmej.notesapp.classes.AllNotes;
import com.barmej.notesapp.classes.PhotoNote;
import com.barmej.notesapp.room.NoteConverter;
import com.barmej.notesapp.classes.Note;

import java.util.List;

@Dao
@TypeConverters(NoteConverter.class)
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM photoNotesTable")
    LiveData<List<PhotoNote>> getAllPhotoNotes();

    @Query("SELECT * FROM checknotestable")
    LiveData<List<PhotoNote>> getAllCheckNotes();


    @Query("SELECT * FROM notesTable")
    LiveData<List<Note>> getAllNotes();


    @Query("DELETE FROM notesTable")
    void deleteAllNotes();
}
