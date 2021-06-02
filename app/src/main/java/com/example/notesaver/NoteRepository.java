package com.example.notesaver;

import android.app.Application;
//import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDatabase noteDatabase;
    private NoteDataInterface noteDataInterface;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        noteDatabase = NoteDatabase.getInstance(application);
        noteDataInterface = noteDatabase.noteDao(); // This is gonna create all the necessary codes for handling data in the database
        allNotes = noteDataInterface.getAllNotes();
    }

    // Now we want to do our database operations in background thread. If we do it in main thread, it can cause
    // out app to crash. So to do that, we have to use ExecutorService method.
    public void insert(Note note) {
        noteDatabase.executorService.execute(new Runnable() {
            @Override
            public void run() {
                noteDataInterface.insert(note);
            }
        });
    }
    public void delete(Note note) {
        noteDatabase.executorService.execute(() -> noteDataInterface.delete(note));
    }
    public void update(Note note)
    {
        noteDatabase.executorService.execute(()-> noteDataInterface.update(note));
    }
    public void deleteAllNotes(){
        noteDatabase.executorService.execute(() -> noteDataInterface.deleteAllNotes());
    }
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

}
