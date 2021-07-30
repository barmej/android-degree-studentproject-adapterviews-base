package com.barmej.notesapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barmej.notesapp.AddNewNoteActivity;
import com.barmej.notesapp.Constants;
import com.barmej.notesapp.Listener.ItemClickListener;
import com.barmej.notesapp.Listener.ItemLongClickListener;
import com.barmej.notesapp.classes.CheckNote;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;
import com.barmej.notesapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> implements Serializable
{

    private static List<Note> notesArray;
    ItemLongClickListener mItemLongClickListener;
    ItemClickListener mItemClickListener;


    public NotesAdapter(List<Note> notesList, ItemLongClickListener mItemLongClickListener, ItemClickListener mItemClickListener)
    {
        notesArray = notesList;
        this.mItemLongClickListener = mItemLongClickListener;
        this.mItemClickListener = mItemClickListener;

    }

    @Override
    public int getItemViewType(int position)
    {
        Note note = notesArray.get(position);
        if (note instanceof PhotoNote)
            return Constants.PHOTO_NOTE;

        else if (note instanceof CheckNote)
            return Constants.CHECK_NOTE;

        else
            return Constants.NORMAL_NOTE;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotesViewHolder viewHolder;
        View view;

        if (viewType == Constants.PHOTO_NOTE)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_photo, parent, false);
            viewHolder = new PhotoNoteViewHolder(view, mItemLongClickListener, mItemClickListener);
        }
        else if (viewType == Constants.CHECK_NOTE)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_check, parent, false);
            viewHolder = new CheckNoteViewHolder(view, mItemLongClickListener, mItemClickListener);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
            viewHolder = new NotesViewHolder(view, mItemLongClickListener, mItemClickListener);
        }

        return viewHolder;
    }


    @Override
    public void onBindViewHolder (@NonNull final NotesViewHolder holder, int position){
        final Note notesMain = notesArray.get(position);

        if (notesMain instanceof PhotoNote) {
            PhotoNote photoNote = (PhotoNote) notesMain;
            PhotoNoteViewHolder photoNoteViewHolder = (PhotoNoteViewHolder) holder;
            photoNoteViewHolder.photoNoteImageView.setImageURI(photoNote.getImage());
            holder.cardView.setBackgroundColor(notesMain.getColor());
        } else if (notesMain instanceof CheckNote) {
            final CheckNote checkNote = (CheckNote) notesMain;
            final CheckNoteViewHolder noteCheckViewHolder = (CheckNoteViewHolder) holder;

            noteCheckViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final boolean isChecked = noteCheckViewHolder.checkBox.isChecked();
                    int colorChosen = notesMain.getColor();
                    checkNote.setCheckBox(isChecked);
                    if (isChecked) {
                        holder.cardView.setBackgroundColor(Color.parseColor("#ADFF2F"));
                    } else {
                        holder.cardView.setBackgroundColor(colorChosen);
                    }
                }
            });
            int colorChosen = notesMain.getColor();
            noteCheckViewHolder.checkBox.setChecked(checkNote.getCheckBox());
            if (checkNote.getCheckBox()) {
                holder.cardView.setBackgroundColor(Color.parseColor("#ADFF2F"));
            } else {
                holder.cardView.setBackgroundColor(colorChosen);
            }
        }else {
            holder.cardView.setBackgroundColor(notesMain.getColor());
        }
        holder.noteText.setText(notesMain.getNote());
        holder.position = position;
    }


    public void setNormalNotes(List<Note> notes){
        notesArray = notes;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount()
    {
        return notesArray.size();

    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder implements Serializable
    {

        TextView noteText;
        int position;
        View cardView;

        public NotesViewHolder(@NonNull View itemView, final ItemLongClickListener mItemLongClickListener, final ItemClickListener mItemClickListener)
        {
            super(itemView);
            noteText = itemView.findViewById(R.id.Note);
            cardView = itemView.findViewById(R.id.cardView);

            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view)
                {
                    mItemLongClickListener.onLongClickItem(position);
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int index = getAdapterPosition();
                    mItemClickListener.onClickListener(position);
                    mItemClickListener.onItemClick(notesArray.get(index));
                }
            });

        }
    }

    public static class PhotoNoteViewHolder extends NotesViewHolder implements Serializable
    {
        ImageView photoNoteImageView;
        public PhotoNoteViewHolder(@NonNull View itemView, final ItemLongClickListener mItemLongClickListener, final ItemClickListener mItemClickListener)
        {
            super(itemView, mItemLongClickListener, mItemClickListener);
            photoNoteImageView = itemView.findViewById(R.id.imageView_photo_note);

        }
    }

    public static class CheckNoteViewHolder extends NotesViewHolder implements Serializable
    {
        CheckBox checkBox;
        public CheckNoteViewHolder(@NonNull View itemView, ItemLongClickListener mItemLongClickListener, final ItemClickListener mItemClickListener)
        {
            super(itemView, mItemLongClickListener, mItemClickListener);
            checkBox = itemView.findViewById(R.id.checkBox);
            Log.d("" , "");
        }
    }

    public Note getNoteAt(int pos){
        return notesArray.get(pos);
    }
}