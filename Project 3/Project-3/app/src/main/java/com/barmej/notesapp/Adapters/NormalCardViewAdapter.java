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

public class NormalCardViewAdapter extends RecyclerView.Adapter<NormalCardViewAdapter.NormalViewHolder> {

    List<Notes> notesArray;
    private ItemLongClickListener mItemLongClickListener;
    private ItemClickListener mItemClickerListener;

    public NormalCardViewAdapter(List<Notes> notesList, ItemLongClickListener mItemLongClickListener, ItemClickListener mItemClickerListener) {
        notesArray = notesList;
        this.mItemLongClickListener = mItemLongClickListener;
        this.mItemClickerListener = mItemClickerListener;
    }

    @NonNull
    @Override
    public NormalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.item_note , parent , false);
        NormalViewHolder normalViewHolder = new NormalViewHolder(view , mItemClickerListener , mItemLongClickListener);
        return normalViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NormalCardViewAdapter.NormalViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    static class NormalViewHolder extends RecyclerView.ViewHolder {
        int position;

        public NormalViewHolder(@NonNull View itemView , ItemClickListener mItemClickListener , ItemLongClickListener mItemLongclickListener) {
            super(itemView);
        }
    }
}
