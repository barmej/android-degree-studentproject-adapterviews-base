package com.barmej.notesapp.Background;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.barmej.notesapp.room.Daos.NoteDao;
import com.barmej.notesapp.room.Daos.PhotoNoteDao;
import com.barmej.notesapp.room.NotesRoomDb;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;

import java.util.List;

public class NoteRepository {
    private NoteDao mNoteDao;
    private PhotoNoteDao mPhotoNoteDao;
    private LiveData<List<Note>> getAllNotes;
    private LiveData<List<PhotoNote>> getAllPhotoNotes;
    private NotesRoomDb db;

    public NoteRepository(Application app){
        db = NotesRoomDb.getInstance(app);
        mNoteDao = db.noteDao();
        mPhotoNoteDao = db.photoNoteDao();
        getAllNotes = mNoteDao.getAllNotes();
        getAllPhotoNotes = mPhotoNoteDao.getAllPhotoNotes();
    }


    public LiveData<List<Note>> getAllNotes(){
        return getAllNotes;
    }

    public LiveData<List<PhotoNote>> getAllPhotoNotes(){
        if (getAllPhotoNotes == null){
            getAllPhotoNotes = db.photoNoteDao().getAllPhotoNotes();
        }
        return getAllPhotoNotes;
    }

    public LiveData<List<Note>> getAllNotesMerged(){
        MediatorLiveData liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(getAllNotes, new Observer() {
            @Override
            public void onChanged(Object value) {
                liveDataMerger.setValue(value);
            }
        });

        liveDataMerger.addSource(getAllPhotoNotes, new Observer() {
            @Override
            public void onChanged(Object o) {
                liveDataMerger.setValue(o);
            }
        });

        return liveDataMerger;
    }


    //insert
    public void insertNote(Note note) {
            new InsertAsyncTaskNote(mNoteDao).execute(note);}

    public void deleteNote(Note note) { new DeleteAsyncTaskNote(mNoteDao).execute(note);}

    public void updateNote(Note note){ new UpdateAsyncTaskNote(mNoteDao).execute(note);}


    public void insertPhotoNote(PhotoNote photoNote) {
        new InsertAsyncTaskPhotoNote(mPhotoNoteDao).execute(photoNote);
    }


    public void deleteAllNotes(){
        new DeleteAllAsyncTaskNote(mNoteDao).execute();
        new DeleteAllAsyncTaskPhotoNote(mPhotoNoteDao).execute();
    }


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

    private static class DeleteAsyncTaskNote extends AsyncTask<Note, Void, Void>{

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

    private static class DeleteAllAsyncTaskNote extends AsyncTask<Note, Void, Void>{
        NoteDao mNoteDao;

        public DeleteAllAsyncTaskNote(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.deleteAllNotes();
            return null;
        }
    }

    private static class DeleteAllAsyncTaskPhotoNote extends AsyncTask<PhotoNote, Void, Void>{
        PhotoNoteDao mPhotoNoteDao;

        public DeleteAllAsyncTaskPhotoNote(PhotoNoteDao photoNoteDao) {
            this.mPhotoNoteDao = photoNoteDao;
        }

        @Override
        protected Void doInBackground(PhotoNote... photoNotes) {
            mPhotoNoteDao.deleteAllNotes();
            return null;
        }
    }

    private static class UpdateAsyncTaskNote extends AsyncTask<Note,Void,Void>{

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

        private PhotoNoteDao mNotesDao;

        public InsertAsyncTaskPhotoNote(PhotoNoteDao photoNoteDao){
            mNotesDao = photoNoteDao;
        }

        @Override
        protected Void doInBackground(PhotoNote... photoNotes) {
            mNotesDao.insert(photoNotes[0]);

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
