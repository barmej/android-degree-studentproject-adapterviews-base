package com.barmej.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.barmej.notesapp.classes.CheckNote;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;

public class AddNewNormalNoteViewModel extends AndroidViewModel {

    private NotesRepository mNotesRepository;

    public AddNewNormalNoteViewModel(@NonNull Application application){
        super(application);

        mNotesRepository = new NotesRepository(application);
    }

    public void insert(Note note){
        mNotesRepository.insert(note);
    }


}
