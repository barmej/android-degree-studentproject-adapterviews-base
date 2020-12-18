package com.barmej.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.barmej.notesapp.Adapters.CheckBoxCardViewAdapter;
import com.barmej.notesapp.Adapters.NormalCardViewAdapter;
import com.barmej.notesapp.Adapters.NotesAdapter;
import com.barmej.notesapp.Listener.ItemClickListener;
import com.barmej.notesapp.Listener.ItemLongClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView notesRecyclerView;
    RecyclerView.LayoutManager mLinearLayoutManager;
    RecyclerView.LayoutManager mGridLayoutManager;
    RecyclerView.LayoutManager mStaggeredLayoutManager;
    List<Notes> notesItems;
    CheckBoxCardViewAdapter checkBoxCardViewAdapter;
    NotesAdapter mNotesAdapter;
    NormalCardViewAdapter mNormalCardViewAdapter;
    FloatingActionButton mFloatingActionButton;
    private static final int RECIEVE_NOTES = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFloatingActionButton = findViewById(R.id.floating_button_add);
        notesRecyclerView = findViewById(R.id.recycler_view_photos);
        mLinearLayoutManager = new LinearLayoutManager(this , RecyclerView.VERTICAL , false);
        mGridLayoutManager = new GridLayoutManager(this , 2);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(2 ,StaggeredGridLayoutManager.VERTICAL);

       notesItems = NewsListGenerator.generateList(0);

        mNotesAdapter = new NotesAdapter(notesItems, new ItemLongClickListener() {
            @Override
            public void onLongClickItem(int position) {
                deleteItem(position);
            }
        }, new ItemClickListener() {
            @Override
            public void onClickListener(int position) {
                Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
            }
        });
        notesRecyclerView.setAdapter(mNotesAdapter);


        notesRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AddNewNoteActivity.class);
                startActivityForResult(intent , RECIEVE_NOTES);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECIEVE_NOTES) {
            if (resultCode == RESULT_OK && data != null) {

                String Note = data.getStringExtra("StringNotes");
                int Color = data.getIntExtra(String.valueOf(Constants.NOTE_COLOR_YELLOW), 0);
                int noteType = data.getIntExtra(String.valueOf(Constants.CHECK_NOTE) , 0);

                Notes notes = new Notes(Note , Color , noteType);

                addItem(notes);
            } else {
                Toast.makeText(this, R.string.didnt_add_photo , Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void addItem(Notes notes) {
        notesItems.add(notes);
        mNotesAdapter.notifyItemInserted(notesItems.size()-1);
    }

    private void deleteItem(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this )
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.Confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notesItems.remove(position);
                        mNotesAdapter.notifyItemRemoved(position);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        alertDialog.show();
    }
}
