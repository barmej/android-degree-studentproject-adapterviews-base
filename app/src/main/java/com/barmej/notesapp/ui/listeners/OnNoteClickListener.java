package com.barmej.notesapp.ui.listeners;

import com.barmej.notesapp.entity.Note;

public interface OnNoteClickListener {
    void onNoteClick(int position, Note note);
}