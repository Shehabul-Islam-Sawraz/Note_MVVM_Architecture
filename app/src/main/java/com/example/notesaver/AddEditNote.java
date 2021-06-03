package com.example.notesaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNote extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.notesaver.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.notesaver.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.notesaver.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.notesaver.EXTRA_PRIORITY";
    public static final String NOTE_SAVED = "com.example.notesaver.NOTE_SAVED";


    private EditText title;
    private EditText description;
    private NumberPicker priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.add_note_title);
        description = findViewById(R.id.add_note_description);
        priority = findViewById(R.id.add_note_number_picker);

        priority.setMinValue(1);
        priority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            title.setText(intent.getStringExtra(EXTRA_TITLE));
            description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            priority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }
        else{
            setTitle("Add New Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String note_title = title.getText().toString();
        String note_description = description.getText().toString();
        int note_priority = priority.getValue();
        if(note_title.trim().isEmpty()){
            Toast.makeText(this, "Please add a title", Toast.LENGTH_SHORT).show();
        }
        else if(note_description.trim().isEmpty()){
            Toast.makeText(this, "Please add a description", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent();
            int id = getIntent().getIntExtra(EXTRA_ID,-1);
            intent.putExtra(EXTRA_TITLE,note_title);
            intent.putExtra(EXTRA_DESCRIPTION,note_description);
            intent.putExtra(EXTRA_PRIORITY,note_priority);
            intent.putExtra(NOTE_SAVED,200);
            if(id!=-1){
                intent.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_OK,intent); // This intent will be accessed from MainActivity
            finish();
        }
    }
}