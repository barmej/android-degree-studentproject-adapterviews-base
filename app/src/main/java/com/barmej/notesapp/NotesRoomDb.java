package com.barmej.notesapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.barmej.notesapp.Daos.NotesDao;
import com.barmej.notesapp.classes.CheckNote;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;

@Database(entities = {Note.class, PhotoNote.class, CheckNote.class}, version = 1)
@TypeConverters(NoteConverter.class)
public abstract class NotesRoomDb extends RoomDatabase {
    private static NotesRoomDb instance;
    private static final String DATABASE_NAME = "notes-database";
    public abstract NotesDao notesDao();

    public static synchronized NotesRoomDb getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NotesRoomDb.class , DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
