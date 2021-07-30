package com.barmej.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.barmej.notesapp.classes.PhotoNote;

import java.util.List;

public class PhotoNoteViewModel extends AndroidViewModel {

    private NoteRepository mNoteRepository;

    private LiveData<List<PhotoNote>> getAllPhotoNotes;

    public PhotoNoteViewModel(@NonNull Application application) {
        super(application);

        mNoteRepository = new NoteRepository(application);
        getAllPhotoNotes = mNoteRepository.getAllPhotoNotes();
    }

    public void deletePhotoNote(PhotoNote photoNote){ mNoteRepository.deletePhotoNote(photoNote);}

    public LiveData<List<PhotoNote>> getAllPhotoNotes(){
        return getAllPhotoNotes;
    }
}
