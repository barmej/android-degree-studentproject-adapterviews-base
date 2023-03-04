package com.barmej.notesapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barmej.notesapp.R;
import com.barmej.notesapp.entity.CheckableNote;
import com.barmej.notesapp.entity.Note;
import com.barmej.notesapp.entity.PhotoNote;
import com.barmej.notesapp.entity.TextNote;
import com.barmej.notesapp.ui.listeners.OnCheckboxListener;
import com.barmej.notesapp.ui.listeners.OnNoteClickListener;
import com.barmej.notesapp.ui.listeners.OnNoteLongClickListener;

import java.util.List;

/**
 * RecyclerView adapter for all types of notes
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    /**
     * Constants that represents note view types
     */
    private static final int TEXT_NOTE = 0;
    private static final int CHECKABLE_NOTE = 1;
    private static final int PHOTO_NOTE = 2;

    /**
     * List to keep notes of different types used as data source for this adapter
     */
    private final List<Note> mNotes;

    /**
     * References listeners to actions to MainActivity
     */
    private final OnNoteClickListener onNoteClickListener;
    private final OnNoteLongClickListener onNoteLongClickListener;
    private final OnCheckboxListener onCheckBoxListener;

    public NotesAdapter(List<Note> mNotes, OnNoteClickListener onClickItemListener, OnNoteLongClickListener onNoteLongClickListener, OnCheckboxListener onCheckBoxListener) {
        this.mNotes = mNotes;
        this.onNoteClickListener = onClickItemListener;
        this.onNoteLongClickListener = onNoteLongClickListener;
        this.onCheckBoxListener = onCheckBoxListener;
    }

    @Override
    public int getItemViewType(int position) {
        Note note = mNotes.get(position);
        if (note instanceof PhotoNote) {
            return PHOTO_NOTE;
        } else if (note instanceof CheckableNote) {
            return CHECKABLE_NOTE;
        } else if (note instanceof TextNote) {
            return TEXT_NOTE;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case PHOTO_NOTE:
                View noteWithPhotoView = inflater.inflate(R.layout.item_note_photo, parent, false);
                return new PhotoNoteViewHolder(noteWithPhotoView);
            case CHECKABLE_NOTE:
                View checkableNoteView = inflater.inflate(R.layout.item_note_check, parent, false);
                return new CheckableNoteViewHolder(checkableNoteView);
            case TEXT_NOTE:
                View textNoteView = inflater.inflate(R.layout.item_note, parent, false);
                return new NoteViewHolder(textNoteView);
            default:
                throw new IllegalArgumentException("Invalid view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(mNotes.get(position));
    }


    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    /**
     * ViewHolder for text notes and super class for other notes view holders
     */
    public class NoteViewHolder extends RecyclerView.ViewHolder {

        protected final ViewGroup noteBox;
        private final TextView textView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteBox = itemView.findViewById(R.id.note_box);
            textView = itemView.findViewById(R.id.noteTextView);
            itemView.setOnClickListener(v -> onNoteClickListener.onNoteClick(getAdapterPosition(), mNotes.get(getAdapterPosition())));
            itemView.setOnLongClickListener(v -> {
                onNoteLongClickListener.onNoteLongClickListener(getAdapterPosition(), mNotes.get(getAdapterPosition()));
                return true;
            });
        }

        public void bind(Note note) {
            noteBox.setBackgroundColor(note.getBackgroundColor());
            textView.setText(note.getText());
        }

    }

    /**
     * ViewHolder for checkable notes
     */
    public class CheckableNoteViewHolder extends NoteViewHolder {

        private final CheckBox checkBox;

        public CheckableNoteViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.noteCheckBox);
            checkBox.setOnClickListener(v -> onCheckBoxListener.onClick(getAdapterPosition()));
        }

        @Override
        public void bind(Note note) {
            super.bind(note);
            CheckableNote checkableNote = (CheckableNote) note;
            checkBox.setChecked(checkableNote.isChecked());
            if(checkableNote.isChecked()) {
                noteBox.setBackgroundColor(Color.GREEN);
            }
        }

    }

    /**
     * ViewHolder for photo notes
     */
    private class PhotoNoteViewHolder extends NoteViewHolder {

        private final ImageView mImageView;

        PhotoNoteViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.noteImageView);
        }

        @Override
        public void bind(Note note) {
            super.bind(note);
            PhotoNote photoNote = (PhotoNote) note;
            mImageView.setImageURI(photoNote.getPhotoUri());
        }

    }

}
