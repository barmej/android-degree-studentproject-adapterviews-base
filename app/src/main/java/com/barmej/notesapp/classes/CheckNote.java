package com.barmej.notesapp.classes;

import android.widget.CheckBox;

import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "CheckNotesTable")
public class CheckNote extends Note implements Serializable {

    private boolean checkBox;

    public CheckNote(int noteColor, String note, boolean checkBox) {
        super(noteColor,note);
        this.checkBox = checkBox;
    }

    public CheckNote(boolean checkBox){
        this.checkBox = checkBox;
    }

    public boolean getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }
}
