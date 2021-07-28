package com.barmej.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.barmej.notesapp.classes.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NotesRepository mNotesRepository;

    private LiveData<List<Note>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mNotesRepository = new NotesRepository(application);
        mAllNotes = mNotesRepository.getAllNotes();
    }

    public void insert(Note note){
        mNotesRepository.insert(note);
    }

    public void delete(Note note){ mNotesRepository.delete(note);}

    public void update(Note note){ mNotesRepository.update(note);}

    public LiveData<List<Note>> getAllNotes(){
        return mAllNotes;
    }
}
