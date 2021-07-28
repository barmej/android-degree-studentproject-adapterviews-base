package com.barmej.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.barmej.notesapp.classes.Note;

public class AddNewNoteViewModel extends AndroidViewModel {

    private NotesRepository mNotesRepository;

    public AddNewNoteViewModel(@NonNull Application application){
        super(application);

        mNotesRepository = new NotesRepository(application);
    }

    public void insert(Note note){
        mNotesRepository.insert(note);
    }

}
