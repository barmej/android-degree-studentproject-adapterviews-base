package com.barmej.notesapp.room.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.barmej.notesapp.Background.NoteRepository;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository mNotesRepository;

    private LiveData<List<Note>> mAllNotes;
    private LiveData<List<PhotoNote>> mAllPhotoNotes;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        mNotesRepository = new NoteRepository(application);
        mAllNotes = mNotesRepository.getAllNotes();
        mAllPhotoNotes = mNotesRepository.getAllPhotoNotes();
    }

    public void insert(Note note){
        mNotesRepository.insertNote(note);
    }

    public void deleteAll() {
        mNotesRepository.deleteAllNotes();
    }

    public void deleteNormalNote(Note note){ mNotesRepository.deleteNote(note);}


    public void update(Note note){ mNotesRepository.updateNote(note);}

    public LiveData<List<Note>> getAllNotes(){
        return mAllNotes;
    }

    public LiveData<List<PhotoNote>> getAllPhotoNotes(){
        return mAllPhotoNotes;
    }
}
