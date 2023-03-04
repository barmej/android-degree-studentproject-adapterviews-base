package com.barmej.notesapp.entity;

import android.os.Parcel;

public class TextNote extends Note {

    public TextNote(int backgroundColor, String text) {
        super(backgroundColor, text);
    }

    public TextNote(Parcel in) {
        super(in);
    }

    public static final Creator<TextNote> CREATOR = new Creator<TextNote>() {
        @Override
        public TextNote createFromParcel(Parcel in) {
            return new TextNote(in);
        }

        @Override
        public TextNote[] newArray(int size) {
            return new TextNote[size];
        }
    };

}
