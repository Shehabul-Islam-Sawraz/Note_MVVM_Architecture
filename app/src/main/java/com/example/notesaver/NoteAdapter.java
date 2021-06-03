package com.example.notesaver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView noteTitle;
        private TextView noteDescription;
        private TextView notePriority;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteDescription = itemView.findViewById(R.id.note_description);
            notePriority = itemView.findViewById(R.id.note_priority);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(listener!=null && pos!=RecyclerView.NO_POSITION){
                        listener.onItemClick(notes.get(pos));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = notes.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteDescription.setText(note.getDescription());
        holder.notePriority.setText(String.valueOf(note.getPriority()));
    }

    public interface OnItemClickListener{ // Setting a listener to the recycle view items for editing
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged(); // This is used to notify the adapter that our data has been changed
    }

    public Note getNoteAtPosition(int position) {
        return notes.get(position);
    }
}
