package com.example.notesaver;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes") // This is used for room annotation and in compile time it creates all the
                            // necessary code to create an sqlite table for this object. Here we are defining
                            //table name as 'Note'. Room automatically will generate columns for the defined variables.
public class Note {
    @PrimaryKey(autoGenerate = true) // Here we are defining id as primary key & telling it to auto increment itself
    private int id; // A primary key for storing the notes
    private String title; // Title of the note
    private String description; // Description of tje note
    private int priority; // Will control the priority for the note.

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
