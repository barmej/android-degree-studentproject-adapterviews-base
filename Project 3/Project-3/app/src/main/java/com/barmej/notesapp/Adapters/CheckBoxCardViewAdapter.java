package com.barmej.notesapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barmej.notesapp.Listener.ItemClickListener;
import com.barmej.notesapp.Listener.ItemLongClickListener;
import com.barmej.notesapp.Notes;
import com.barmej.notesapp.R;

import java.util.List;

public class CheckBoxCardViewAdapter extends RecyclerView.Adapter<CheckBoxCardViewAdapter.CheckBoxViewHolder> {

    List<Notes> notesArray;
    private ItemLongClickListener mItemLongClickListener;
    private ItemClickListener mItemClickListener;

    public CheckBoxCardViewAdapter(List<Notes> notesList , ItemLongClickListener mItemLongClickListener , ItemClickListener mItemClickListener) {
        notesArray = notesList;
        this.mItemLongClickListener = mItemLongClickListener;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public CheckBoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_note_check , parent , false);
        CheckBoxViewHolder checkBoxViewHolder = new CheckBoxViewHolder(view , mItemLongClickListener , mItemClickListener);

        return checkBoxViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckBoxViewHolder holder, int position) {

    }

    @Override
        public int getItemCount() {
            return notesArray.size();
    }

    static class CheckBoxViewHolder extends RecyclerView.ViewHolder {
        int position;
        public CheckBoxViewHolder(@NonNull View itemView , final ItemLongClickListener mItemLongClickListener , final ItemClickListener mItemClickListener) {
            super(itemView);


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
