package com.barmej.notesapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.room.Dao;

import com.barmej.notesapp.Daos.NoteDao;
import com.barmej.notesapp.Daos.PhotoNoteDao;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository {
    private NoteDao mNoteDao;
    private PhotoNoteDao mPhotoNoteDao;

    private LiveData<List<Note>> getAllNotes;
    private LiveData<List<PhotoNote>> getAllPhotoNotes;

    public NoteRepository(Application app){
        NotesRoomDb db = NotesRoomDb.getInstance(app);
        mNoteDao = db.noteDao();
        mPhotoNoteDao = db.photoNoteDao();
        getAllNotes = mNoteDao.getAllNotes();
        getAllPhotoNotes = mPhotoNoteDao.getAllPhotoNotes();
    }

    public LiveData<List<Note>> getEntities() {
        return mergeDataSources(
                mNoteDao.getAllNotes(),
                mPhotoNoteDao.getAllPhotoNotes());
    }

    private static LiveData<List<Note>> mergeDataSources(LiveData... sources) {
        MediatorLiveData<List<Note>> mergedSources = new MediatorLiveData();
        for (LiveData source : sources) {
            mergedSources.addSource(source, mergedSources::setValue);
        }

        return mergedSources;
    }

    //insert
    public void insertNote(Note note) { new InsertAsyncTaskNote(mNoteDao).execute(note);}

    public void deleteNote(Note note) { new DeleteAsyncTaskNote(mNoteDao).execute(note);}

    public void updateNote(Note note){ new UpdateAsyncTaskNote(mNoteDao).execute(note);}

    public LiveData<List<Note>> getAllNotes(){
        return getAllNotes;
    }

    public void insertPhotoNote(PhotoNote photoNote) {
        new InsertAsyncTaskPhotoNote(mPhotoNoteDao).execute(photoNote);}

        public void deletePhotoNote(PhotoNote photoNote){
        new DeleteAsyncTaskPhotoNote(mPhotoNoteDao).execute(photoNote);}

    public LiveData<List<PhotoNote>> getAllPhotoNotes() {
        return getAllPhotoNotes;
    }

    public void updatePhotoNote(PhotoNote photoNote){ new UpdateAsyncTaskPhotoNote(mPhotoNoteDao).execute(photoNote);}

    private static class InsertAsyncTaskNote extends AsyncTask<Note, Void, Void>{

        private NoteDao mNotesDao;

        public InsertAsyncTaskNote(NoteDao notesDao){
            mNotesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNotesDao.insert(notes[0]);

            return null;
        }
    }

    private class DeleteAsyncTaskNote extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;

        public DeleteAsyncTaskNote(NoteDao mNotesDao) {
            this.noteDao = mNotesDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);

            return null;
        }
    }

    private class UpdateAsyncTaskNote extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        public UpdateAsyncTaskNote(NoteDao mNoteDao) {
            this.noteDao = mNoteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);

            return null;
        }
    }

    private static class InsertAsyncTaskPhotoNote extends AsyncTask<PhotoNote, Void, Void>{

        private PhotoNoteDao mPhotoNoteDao;

        public InsertAsyncTaskPhotoNote(PhotoNoteDao mPhotoNoteDao) {
            this.mPhotoNoteDao = mPhotoNoteDao;
        }

        @Override
        protected Void doInBackground(PhotoNote... photoNotes) {
            mPhotoNoteDao.insert(photoNotes[0]);
            return null;
        }
    }

    private class DeleteAsyncTaskPhotoNote extends AsyncTask<PhotoNote, Void, Void>{

        private PhotoNoteDao mPhotoNoteDao;

        public DeleteAsyncTaskPhotoNote(PhotoNoteDao photoNoteDao) {
            this.mPhotoNoteDao = photoNoteDao;
        }

        @Override
        protected Void doInBackground(PhotoNote... photoNotes) {
            mPhotoNoteDao.delete(photoNotes[0]);
            return null;
        }
    }

    private class UpdateAsyncTaskPhotoNote extends AsyncTask<PhotoNote, Void, Void>{

        private PhotoNoteDao mPhotoNoteDao;

        public UpdateAsyncTaskPhotoNote(PhotoNoteDao photoNoteDao) {
            this.mPhotoNoteDao = photoNoteDao;
        }

        @Override
        protected Void doInBackground(PhotoNote... photoNotes) {
            mPhotoNoteDao.update(photoNotes[0]);
            return null;
        }
    }


}
