package com.barmej.notesapp.Listener;

import com.barmej.notesapp.classes.Note;

public interface ItemClickListener {
    void onClickListener(int position);
    void onItemClick(Note note);
}
