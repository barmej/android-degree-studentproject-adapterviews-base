package com.barmej.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.barmej.notesapp.classes.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository mNotesRepository;

    private LiveData<List<Note>> mAllNotes;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        mNotesRepository = new NoteRepository(application);
        mAllNotes = mNotesRepository.getAllNotes();
    }

    public void insert(Note note){
        mNotesRepository.insertNote(note);
    }

    public void deleteNormalNote(Note note){ mNotesRepository.deleteNote(note);}

    public LiveData<List<Note>> getAllAllNotes(){
        return mNotesRepository.getEntities();
    }

    public void update(Note note){ mNotesRepository.updateNote(note);}

    public LiveData<List<Note>> getAllNotes(){
        return mAllNotes;
    }
}
