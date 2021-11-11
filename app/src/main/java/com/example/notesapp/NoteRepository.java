package com.example.notesapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private LiveData<List<Note>> allNotes;
    private NoteDao noteDao;

    public NoteRepository(Application application) {
        NoteRoomDatabase database = NoteRoomDatabase.getInstance(application);
        // Calls abstract method in NoteDatabase class
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }
    // Because main activity uses repo method to get data,
    // they are needed to be implemented in repo class
    public void insert(Note note) {
        InsertNoteAsyncTask insertNoteAsyncTask = new InsertNoteAsyncTask(noteDao);
        insertNoteAsyncTask.execute(note);
    }

    public void delete(Note note) {
        DeleteNoteAsyncTask deleteNoteAsyncTask = new DeleteNoteAsyncTask(noteDao);
        deleteNoteAsyncTask.execute(note);
    }


    public void deleteAllNotes() {
        DeleteAllNotesAsyncTask deleteAllNotesAsyncTask = new DeleteAllNotesAsyncTask(noteDao);
        deleteAllNotesAsyncTask.execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    // All changes needs to be executed on background thread
    // so async task are created
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        public DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
