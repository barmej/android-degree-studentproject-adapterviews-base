package com.barmej.notesapp.room.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.barmej.notesapp.Background.NoteRepository;
import com.barmej.notesapp.classes.CheckNote;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository mNotesRepository;

    private LiveData<List<Note>> mAllNotes;
    private LiveData<List<PhotoNote>> mAllPhotoNotes;
    private LiveData<List<CheckNote>> mAllCheckNotes;
    private MediatorLiveData mAllNotesMerged;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        mNotesRepository = new NoteRepository(application);
        mAllNotes = mNotesRepository.getAllNotes();
        mAllPhotoNotes = mNotesRepository.getAllPhotoNotes();
        mAllNotesMerged = mNotesRepository.getAllNotesMerged();
        mAllCheckNotes = mNotesRepository.getAllCheckNotes();
    }

    public void insert(Note note){
        mNotesRepository.insertNote(note);
    }

    public void deleteAll() {
        mNotesRepository.deleteAllNotes();
    }

    public void deleteNormalNote(Note note){ mNotesRepository.deleteNote(note);}

    public void deletePhotoNote(PhotoNote photoNote){
        mNotesRepository.deletePhotoNote(photoNote);
    }

    public void deleteCheckNote(CheckNote checkNote){
        mNotesRepository.deleteCheckNote(checkNote);
    }


    public void updateNote(Note note){ mNotesRepository.updateNote(note);}

    public void updateCheckNote(CheckNote checkNote){
        mNotesRepository.updateCheckNote(checkNote);
    }

    public void updatePhotoNote(PhotoNote photoNote){
        mNotesRepository.updatePhotoNote(photoNote);
    }

    public LiveData<List<Note>> getAllNotes(){
        return mAllNotes;
    }

    public LiveData<List<CheckNote>> getAllCheckNote(){
        return mAllCheckNotes;
    }

    public MediatorLiveData getAllNotesMerged(){
        return mAllNotesMerged;
    }

    public LiveData<List<PhotoNote>> getAllPhotoNotes(){
        return mAllPhotoNotes;
    }
}