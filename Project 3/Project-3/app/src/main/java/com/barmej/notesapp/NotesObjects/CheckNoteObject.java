package com.barmej.notesapp.NotesObjects;


import android.widget.CheckBox;

import com.barmej.notesapp.Notes;

public class CheckNoteObject extends Notes {
    CheckBox checkBoxNote;

    public CheckNoteObject(String noteTextShow, int color, int noteType, CheckBox checkBoxNote) {
        super(noteTextShow, color, noteType);
        this.checkBoxNote = checkBoxNote;
    }
}
