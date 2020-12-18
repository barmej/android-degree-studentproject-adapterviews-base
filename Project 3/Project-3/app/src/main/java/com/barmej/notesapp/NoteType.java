package com.barmej.notesapp;

import androidx.cardview.widget.CardView;

public class NoteType {
    int noteColor;
    CardView cardViewType;

    public NoteType(int noteColor, CardView cardViewType) {
        this.noteColor = noteColor;
        this.cardViewType = cardViewType;
    }

    public int getNoteColor() {
        return noteColor;
    }

    public void setNoteColor(int noteColor) {
        this.noteColor = noteColor;
    }

    public CardView getCardViewType() {
        return cardViewType;
    }

    public void setCardViewType(CardView cardViewType) {
        this.cardViewType = cardViewType;
    }
}
