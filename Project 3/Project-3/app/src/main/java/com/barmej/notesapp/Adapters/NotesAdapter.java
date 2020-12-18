package com.barmej.notesapp.Adapters;

import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.Listener.ItemClickListener;
import com.barmej.notesapp.Listener.ItemLongClickListener;
import com.barmej.notesapp.Notes;
import com.barmej.notesapp.NotesObjects.CheckNoteObject;
import com.barmej.notesapp.NotesObjects.PhotoNoteObject;
import com.barmej.notesapp.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Notes> notesArray;
     ItemLongClickListener mItemLongClickListener;
     ItemClickListener mItemClickListener;
    View view;

    public NotesAdapter(List<Notes> notesList , ItemLongClickListener mItemLongClickListener , ItemClickListener mItemClickListener) {
        notesArray = notesList;
        this.mItemLongClickListener = mItemLongClickListener;
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        Notes notes = notesArray.get(position);
        if(notes instanceof PhotoNoteObject) {
            return Constants.PHOTO_NOTE;
        } else if(notes instanceof CheckNoteObject) {
            return Constants.CHECK_NOTE;
        } else {
            return Constants.PHOTO_NOTE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Constants.PHOTO_NOTE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_photo , parent , false);
                return new NotesViewHolderPhoto(view , mItemLongClickListener , mItemClickListener);

                case Constants.CHECK_NOTE:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_check , parent , false);
                    return new NotesViewHolderCheck(view , mItemLongClickListener , mItemClickListener);

                    case Constants.TEXT_NOTE:
                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note , parent , false);
                        return new NotesViewHolder(view , mItemLongClickListener , mItemClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notesArray.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView test;
        ImageView newNotePhoto;
        int position;

        public NotesViewHolder(@NonNull View itemView, final ItemLongClickListener mItemLongClickListener , final ItemClickListener mItemClickListener) {
            super(itemView);

            test = itemView.findViewById(R.id.photo_note_text_view);
            newNotePhoto = itemView.findViewById(R.id.photo_note_image_view);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mItemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onClickListener(position);
                }
            });
        }
    }

    static class NotesViewHolderCheck extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView checkBoxText;
        int position;
        public NotesViewHolderCheck(@NonNull View itemView , final ItemLongClickListener mItemLongClickListener , final ItemClickListener mItemClickListener) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            checkBoxText = itemView.findViewById(R.id.textView2);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mItemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onClickListener(position);
                }
            });

        }
    }
    static class NotesViewHolderPhoto extends RecyclerView.ViewHolder {
        ImageView notesImage;
        TextView photoText;
        int position;
        public NotesViewHolderPhoto(@NonNull View itemView ,  final ItemLongClickListener mItemLongClickListener , final ItemClickListener mItemClickListener ) {
            super(itemView);
            notesImage = itemView.findViewById(R.id.photo_note_image_view);
            photoText = itemView.findViewById(R.id.photo_note_text_view);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mItemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onClickListener(position);
                }
            });
        }
    }
}
