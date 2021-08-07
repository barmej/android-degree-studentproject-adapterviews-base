package com.barmej.notesapp.room.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.barmej.notesapp.Background.NoteRepository;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;

public class AddNewNormalNoteViewModel extends AndroidViewModel {

    private NoteRepository mNotesRepository;

    public AddNewNormalNoteViewModel(@NonNull Application application){
        super(application);

        mNotesRepository = new NoteRepository(application);
    }

    public void insert(Note note){
        mNotesRepository.insertNote(note);
    }
    public void insertPhotoNote(PhotoNote photoNote){
        mNotesRepository.insertPhotoNote(photoNote);
    }

}
