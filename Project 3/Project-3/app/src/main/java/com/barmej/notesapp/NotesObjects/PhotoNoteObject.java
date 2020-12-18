package com.barmej.notesapp.NotesObjects;

import android.net.Uri;

import com.barmej.notesapp.Notes;

public class PhotoNoteObject extends Notes {
    Uri imageNote;

    public PhotoNoteObject(String noteTextShow, int color, int noteType, Uri imageNote) {
        super(noteTextShow, color, noteType);
        this.imageNote = imageNote;
    }
}
