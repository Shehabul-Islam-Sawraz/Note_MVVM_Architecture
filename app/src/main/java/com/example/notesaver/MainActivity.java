package com.example.notesaver;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private FloatingActionButton addNoteButton;
    private ActivityResultLauncher<Intent> addNoteActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        addNoteButton = findViewById(R.id.note_add);

        addNoteActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->  {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String title = data.getStringExtra(AddNote.EXTRA_TITLE);
                        String description = data.getStringExtra(AddNote.EXTRA_DESCRIPTION);
                        int priority = data.getIntExtra(AddNote.EXTRA_PRIORITY, 1);
                        int saved = data.getIntExtra(AddNote.NOTE_SAVED,1);
                        if (saved == 200) {
                            Note note = new Note(title, description, priority);
                            noteViewModel.insert(note);
                            Toast.makeText(MainActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Can't Save the Note", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        addNoteButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNote.class);
            //startActivity(intent);
            addNoteActivityResultLauncher.launch(intent);
        });
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // This function will be called when we start observing. So it will be called at
                // the moment the app is instantiated.
                noteAdapter.setNotes(notes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNoteAtPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            String title = data.getStringExtra(AddNote.EXTRA_TITLE);
            String description = data.getStringExtra(AddNote.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNote.EXTRA_PRIORITY,1);
            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Can't Save the Note", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes_menu:
                noteViewModel.deleteAllNotes();
                Toast.makeText(MainActivity.this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}