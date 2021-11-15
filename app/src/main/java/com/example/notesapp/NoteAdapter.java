package com.example.notesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Create basic adapter extending from RecyclerView.Adapter

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflates view based on note item layout
        View itemView = inflater.inflate(R.layout.note_item, parent, false);
        NoteHolder noteHolder = new NoteHolder(itemView);
        return noteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = notes.get(position);

        TextView titleTextView = holder.titleTextView;
        titleTextView.setText(note.getTitle());

        TextView descriptionTextView = holder.descriptionTextView;
        descriptionTextView.setText(note.getDescription());

        TextView priorityTextView = holder.priorityTextView;
        priorityTextView.setText(String.valueOf(note.getPriority()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    // Method that is used to get list of notes into adapter
    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    // Get note at position in adapter
    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    // A ViewHolder describes an item view and metadata
    // about its place within the RecyclerView.
    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView priorityTextView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            descriptionTextView = itemView.findViewById(R.id.text_view_description);
            priorityTextView = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }
    // Method that will be called on Main Activity
    // Which will pass onItemClickListener to RecyclerView class and save it to global variable
    // then the ViewHolder class in the RecyclerView will call onClick method defined in the interface,
    // which will pass the Note which is clicked to the onClick implementation in the Main Activity class
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
