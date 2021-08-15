package com.barmej.notesapp.room.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.barmej.notesapp.classes.CheckNote;
import com.barmej.notesapp.classes.PhotoNote;

import java.util.List;

@Dao
public interface CheckNoteDao {

    @Insert
    void insert(CheckNote checkNote);

    @Update
    void update(CheckNote checkNote);

    @Delete
    void delete(CheckNote checkNote);

    @Query("SELECT * FROM checknotestable")
    LiveData<List<CheckNote>> getAllCheckNotes();
}
