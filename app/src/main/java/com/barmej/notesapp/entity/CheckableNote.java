package com.barmej.notesapp.entity;

import android.os.Parcel;

public class CheckableNote extends Note {

    private boolean isChecked;

    public CheckableNote(int backgroundColor, String text, boolean isChecked) {
        super(backgroundColor, text);
        this.isChecked = isChecked;
    }

    protected CheckableNote(Parcel in) {
        super(in);
        isChecked = in.readInt() == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(isChecked ? 1 : 0);
    }

    public static final Creator<CheckableNote> CREATOR = new Creator<CheckableNote>() {
        @Override
        public CheckableNote createFromParcel(Parcel in) {
            return new CheckableNote(in);
        }

        @Override
        public CheckableNote[] newArray(int size) {
            return new CheckableNote[size];
        }
    };

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }

}
