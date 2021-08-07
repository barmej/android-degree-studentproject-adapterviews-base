package com.barmej.notesapp.room;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.barmej.notesapp.classes.Note;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NoteConverter {

    @TypeConverter
    public Uri fromStringUri(String value){
        if (value == null){
            return null;
        } else {
            return Uri.parse(value);
        }
    }

    @TypeConverter
    public String toStringUri(Uri uri) {
        return uri.toString();
    }

    @TypeConverter
    public static LiveData<List<Note>> fromString(String value){
        Type listType = new TypeToken<List<Note>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String toString(LiveData<List<Note>> list){
        return new Gson().toJson(list);
    }

}
