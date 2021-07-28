package com.barmej.notesapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.barmej.notesapp.classes.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository {
    private NotesDao mNotesDao;

    private LiveData<List<Note>> getAllNotes;

    public NotesRepository(Application app){
        NotesRoomDb db = NotesRoomDb.getInstance(app);
        mNotesDao = db.notesDao();
        getAllNotes = mNotesDao.getAllNotes();
    }

    //insert
    public void insert(Note note) { new InsertAsyncTask(mNotesDao).execute(note);}

    public void delete(Note note) { new DeleteAsyncTask(mNotesDao).execute(note);}

    public void update(Note note){ new UpdateAsyncTask(mNotesDao).execute(note);}

    public LiveData<List<Note>> getAllNotes(){
        return getAllNotes;
    }

    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void>{

        private NotesDao mNotesDao;

        public InsertAsyncTask(NotesDao notesDao){
            mNotesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNotesDao.insert(notes[0]);

            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Note, Void, Void>{

        public DeleteAsyncTask(NotesDao mNotesDao) {
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNotesDao.delete(notes[0]);

            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Note,Void,Void>{
        public UpdateAsyncTask(NotesDao mNotesDao) {
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNotesDao.update(notes[0]);

            return null;
        }
    }
}
