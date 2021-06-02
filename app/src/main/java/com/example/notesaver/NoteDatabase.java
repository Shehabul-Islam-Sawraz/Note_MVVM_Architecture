package com.example.notesaver;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public static NoteDatabase instance;
    public abstract NoteDataInterface noteDao();
    public ExecutorService executorService; // This will handle the task in background
    private static final int THREAD_POOL_SECONDS =20; // This defines how many threads can run at a time

    public static synchronized NoteDatabase getInstance(Context context) { // We are making it synchronized so that it will not be called from different threads at a time.
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database"). // The database name will be 'note_database' which will be based on 'NoteDatabase' class.
                    fallbackToDestructiveMigration(). //Allows Room to destructively recreate database tables if Migrations that would migrate old database schemas to the latest schema version are not found.
                    addCallback(roomCallBack). // This will add our declared values in the database by itself
                    build(); // This will build the instance
        }
        instance.executorService = Executors.newFixedThreadPool(THREAD_POOL_SECONDS); // Creating a service to do work in background. This will be used to insert, delete,update data in database
        return instance;
    }
    public static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){ // This function is entering some value to the database.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) { // This function will be called when the database is created. On another note, when our instance will be first created, instance will call this onCreate method.
            super.onCreate(db);
            Executors.newFixedThreadPool(1).execute(()->{
                instance.noteDao().insert(new Note("Title 1","Description 1",1));
                instance.noteDao().insert(new Note("Title 2","Description 2",2));
                instance.noteDao().insert(new Note("Title 3","Description 3",3));
            });
        }
    };
}
