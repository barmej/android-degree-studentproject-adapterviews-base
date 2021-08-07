package com.barmej.notesapp.classes;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;

public class AllNotes  {
    @Embedded public Note note;

}
