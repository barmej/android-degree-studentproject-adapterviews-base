package com.barmej.notesapp.classes;

import androidx.cardview.widget.CardView;

import java.io.Serializable;

public class Note implements Serializable {
    protected int color;
    protected String note;

    public Note(int noteColor, String note) {
        this.color = noteColor;
        this.note = note;
    }

    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note = note;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Note() {
    }
}
