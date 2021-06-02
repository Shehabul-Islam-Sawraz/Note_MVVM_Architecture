package com.example.notesaver;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao // Dao means Data Access Object, which is a room entity to connect with database
public interface NoteDataInterface { // This is a interface that will be implemented by the room. Room will create
                                 // all the necessary codes for us.
    @Insert // We are defining that 'insert(Note note)' method will be used to insert data in database
    void insert(Note note);

    @Update // We are defining that 'update(Note note)' method will be used to update data in database
    void update(Note note);

    @Delete // We are defining that 'delete(Note note)' method will be used to delete data from database
    void delete(Note note);

    @Query("DELETE FROM Notes") // Here we are defining the basic query that will delete all the notes from the database named 'Notes'
    void deleteAllNotes();

    @Query("SELECT * FROM Notes ORDER BY priority DESC") // Here we are defining the query to get all notes.
    LiveData<List<Note>> getAllNotes(); // Here we are using livedata, so that if there is any changes in the 'Note'
                                        // database, this function will be notified.
}
