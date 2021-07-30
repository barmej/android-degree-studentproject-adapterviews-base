package com.barmej.notesapp.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.barmej.notesapp.NoteConverter;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;

import java.util.List;

@Dao
@TypeConverters(NoteConverter.class)
public interface PhotoNoteDao {

    @Insert
    void insert(PhotoNote photoNote);

    @Update
    void update(PhotoNote photoNote);

    @Delete
    void delete(PhotoNote photoNote);

    @Query("SELECT * FROM photoNotesTable")
    LiveData<List<Note>> getAllPhotoNotes();
}
