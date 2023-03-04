package com.barmej.notesapp.entity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;

public class PhotoNote extends Note {

    private Uri photoUri;

    public PhotoNote(int backgroundColor, String text, Uri photoUri) {
        super(backgroundColor, text);
        this.photoUri = photoUri;
    }

    protected PhotoNote(Parcel in) {
        super(in);
        photoUri = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(photoUri, flags);
    }

    public static final Creator<PhotoNote> CREATOR = new Creator<PhotoNote>() {
        @Override
        public PhotoNote createFromParcel(Parcel in) {
            return new PhotoNote(in);
        }

        @Override
        public PhotoNote[] newArray(int size) {
            return new PhotoNote[size];
        }
    };

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

}