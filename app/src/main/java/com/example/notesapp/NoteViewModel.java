package com.example.notesapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// ViewModel is a class that is responsible for
// preparing and managing the data for an Activity


public class NoteViewModel extends AndroidViewModel {

    public static final String LOG_TAG = "NoteViewModel";

    private NoteRepository noteRepository;
    private LiveData<List<Note>> getAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        getAllNotes = noteRepository.getAllNotes();
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void update(Note note) {
        noteRepository.update(note);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public void deleteAllNotes() {
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getGetAllNotes() {
        return getAllNotes;
    }
}
