package com.barmej.notesapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Superclass for all notes types, this class in abstract so it can't be instantiated
 */
public abstract class Note implements Parcelable {


    private int backgroundColor;
    private String text;

    public Note() {

    }

    public Note(int backgroundColor, String text) {
        this.backgroundColor = backgroundColor;
        this.text = text;
    }

    protected Note(Parcel in) {
        backgroundColor = in.readInt();
        text = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(backgroundColor);
        dest.writeString(text);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}
