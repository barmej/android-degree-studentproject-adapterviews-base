package com.barmej.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.barmej.notesapp.classes.Note;

public class EditNormalNoteViewModel extends AndroidViewModel {

    private NotesRepository mNotesRepository;

    public EditNormalNoteViewModel(@NonNull Application application) {
        super(application);

        mNotesRepository = new NotesRepository(application);
    }

    public void update(Note note){
        mNotesRepository.update(note);
    }
}
