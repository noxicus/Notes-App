package com.example.notesapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    public static NoteRoomDatabase INSTANCE;
    // Constructor for creating RoomDatabase
    public static synchronized NoteRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    NoteRoomDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    // Add callback to database
                    .addCallback(dbCallback)
                    .build();
        }
        return INSTANCE;
    }

    // Create room database callback
    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // After create database execute populateDatabaseAsyncTask
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    // Callback definition for database population
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteRoomDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Zapis 1", "Opis 1", 5));
            noteDao.insert(new Note("Zapis 2", "Opis 2", 2));
            noteDao.insert(new Note("Zapis 3", "Opis 3", 6));
            return null;
        }
    }
}
