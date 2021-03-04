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

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.Listener.ItemClickListener;
import com.barmej.notesapp.Listener.ItemLongClickListener;
import com.barmej.notesapp.classes.CheckNote;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;
import com.barmej.notesapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> implements Serializable
{

    ArrayList<Note> notesArray;
    ItemLongClickListener mItemLongClickListener;
    ItemClickListener mItemClickListener;
    static Context context;

    public NotesAdapter(ArrayList<Note> notesList, ItemLongClickListener mItemLongClickListener, ItemClickListener mItemClickListener , Context context)
    {
        notesArray = notesList;
        this.mItemLongClickListener = mItemLongClickListener;
        this.mItemClickListener = mItemClickListener;
        this.context = context;
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
            Log.d("onCreateViewHolder viewType =" , "Constants.Photo_Note");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_photo, parent, false);
            viewHolder = new PhotoNoteViewHolder(view, mItemLongClickListener, mItemClickListener);
        }
        else if (viewType == Constants.CHECK_NOTE)
        {
            Log.d("onCreateViewHolder viewType =" , "Constants.Check_Note");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_check, parent, false);
            viewHolder = new CheckNoteViewHolder(view, mItemLongClickListener, mItemClickListener);
        }
        else
        {
            Log.d("onCreateViewHolder viewType =" , "Constants.Normal_Note");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
            viewHolder = new NotesViewHolder(view, mItemLongClickListener, mItemClickListener);
        }

        return viewHolder;
    }


        @Override
        public void onBindViewHolder (@NonNull final NotesViewHolder holder, int position){
            final Note notesMain = notesArray.get(position);

            if (notesMain instanceof PhotoNote)
            {

                PhotoNote photoNotes = (PhotoNote) notesMain;
                PhotoNoteViewHolder photoNotesViewHolder = (PhotoNoteViewHolder) holder;
                photoNotesViewHolder.photoNoteImageView.setImageURI(photoNotes.getImage());
                photoNotesViewHolder.noteText.setText(photoNotes.getNote());
                holder.cardView.setBackgroundColor(photoNotes.getColor());
            }
            else if (notesMain instanceof CheckNote)
            {

                final CheckNote checkBoxNotes = (CheckNote) notesMain;
                final CheckNoteViewHolder checkBoxNotesViewHolder = (CheckNoteViewHolder) holder;
                checkBoxNotesViewHolder.noteText.setText(checkBoxNotes.getNote());
                checkBoxNotesViewHolder.checkBox.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        final boolean isChecked = checkBoxNotesViewHolder.checkBox.isChecked();
                        int checkColor = notesMain.getColor();
                        checkBoxNotes.setCheckBox(isChecked);
                        if (isChecked)

                        {
                            holder.cardView.setBackgroundColor(Color.GREEN);
                        }
                        else
                        {
                            holder.cardView.setBackgroundColor(checkColor);
                        }
                    }
                });

                int checkColor2 = notesMain.getColor();
                checkBoxNotesViewHolder.checkBox.setChecked(checkBoxNotes.getCheckBox());

                if (checkBoxNotes.getCheckBox())
                {
                    holder.cardView.setBackgroundColor(Color.GREEN);
                }

                else
                {
                    holder.cardView.setBackgroundColor(checkColor2);
                }

            }
            else
            {
                holder.noteText.setText(notesMain.getNote());
                holder.cardView.setBackgroundColor(notesMain.getColor());
                holder.position = position;
            }


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
                        Log.d("positionValueOnLongClickNormal" , position+"");
                        return false;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        mItemClickListener.onClickListener(position);
                        Log.d("positionValueOnClickNormal" , position+"");
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

                itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        mItemClickListener.onClickListener(position);
                        Log.d("positionValueOnClickPhoto" , position+"");
                    }
                });

            }
        }

        public static class CheckNoteViewHolder extends NotesViewHolder implements Serializable
        {
            CheckBox checkBox;
            public CheckNoteViewHolder(@NonNull View itemView, ItemLongClickListener mItemLongClickListener, final ItemClickListener mItemClickListener)
            {
                super(itemView, mItemLongClickListener, mItemClickListener);
                checkBox = itemView.findViewById(R.id.checkBox);

                itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        mItemClickListener.onClickListener(position);
                        Log.d("positionValueOnClickCheck" , position+"");
                    }
                });
            }
        }


}