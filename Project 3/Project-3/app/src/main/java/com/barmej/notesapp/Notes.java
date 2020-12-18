package com.barmej.notesapp;

public class Notes {
    private String noteTextShow;
    private int color;
    private int noteType;



    public Notes(String noteTextShow , int color , int noteType ) {
        this.noteTextShow = noteTextShow;
        this.color = color;
        this.noteType = noteType;

    }

    public String getNoteTextShow() {
        return noteTextShow;
    }

    public void setNoteTextShow(String noteTextShow) {
        this.noteTextShow = noteTextShow;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getNoteType() {
        return noteType;
    }

    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }
}
