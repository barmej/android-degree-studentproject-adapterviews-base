package com.barmej.notesapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.barmej.notesapp.R;
import com.barmej.notesapp.adapters.NotesAdapter;
import com.barmej.notesapp.entity.CheckableNote;
import com.barmej.notesapp.entity.Note;
import com.barmej.notesapp.entity.PhotoNote;
import com.barmej.notesapp.entity.TextNote;
import com.barmej.notesapp.ui.listeners.OnCheckboxListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE_LIST = "EXTRA_NOTE_LIST";
    public static final String EXTRA_NOTE = "note";
    public static final String EXTRA_POSITION = "position";
    public static final int RC_ADD_NOTE =  1000;
    public static final int RC_EDIT_NOTE = 1001;
    private NotesAdapter mNotesAdapter;
    private ArrayList<Note> mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init views
        FloatingActionButton mAddNoteActionButton = findViewById(R.id.addFloatingActionButton);
        RecyclerView mNotesRecyclerView = findViewById(R.id.notesRecyclerView);

        // Init array
        if(savedInstanceState != null && savedInstanceState.containsKey(EXTRA_NOTE_LIST)) {
            mNotes = savedInstanceState.getParcelableArrayList(EXTRA_NOTE_LIST);
        } else {
            mNotes = new ArrayList<>();
        }

        // Init adapter
        mNotesAdapter = new NotesAdapter(mNotes, this::openEditActivity, this::showDeleteDialog, position -> {
            CheckableNote checkableNote = (CheckableNote)mNotes.get(position);
            checkableNote.setChecked(!checkableNote.isChecked());
            mNotesAdapter.notifyItemChanged(position);
        });

        // Set up the RecyclerView and NoteAdapter
        mNotesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mNotesRecyclerView.setAdapter(mNotesAdapter);

        // Add listener for FloatingActionButton click
        mAddNoteActionButton.setOnClickListener(v -> openAddNoteActivity());

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_NOTE_LIST, mNotes);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK) {
                if (requestCode == MainActivity.RC_ADD_NOTE) {
                    mNotes.add(data.getParcelableExtra(MainActivity.EXTRA_NOTE));
                    mNotesAdapter.notifyItemInserted(mNotes.size());
                    Toast.makeText(this, R.string.note_added_successfully, Toast.LENGTH_SHORT).show();
                } else {
                    if(data.hasExtra(MainActivity.EXTRA_POSITION)) {
                        int position = data.getIntExtra(MainActivity.EXTRA_POSITION, -1);
                        mNotes.set(position, data.getParcelableExtra(MainActivity.EXTRA_NOTE));
                        mNotesAdapter.notifyItemChanged(position);
                        Toast.makeText(this, R.string.note_updated_successfully, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void openAddNoteActivity() {
        Intent intent = new Intent(MainActivity.this, AddNewNoteActivity.class);
        startActivityForResult(intent, MainActivity.RC_ADD_NOTE);
    }

    private void openEditActivity(int position, Note note) {
        // Open the appropriate activity based on the type of Note clicked
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_NOTE, note);
        intent.putExtra(MainActivity.EXTRA_POSITION, position);
        if (note instanceof TextNote) {
            intent.setClass(MainActivity.this, TextNoteDetailsActivity.class);
        } else if (note instanceof CheckableNote) {
            intent.setClass(MainActivity.this, CheckableNoteDetailsActivity.class);
        } else if (note instanceof PhotoNote) {
            intent.setClass(MainActivity.this, PhotoNoteDetailsActivity.class);
        }
        startActivityForResult(intent, MainActivity.RC_EDIT_NOTE);
    }

    private void showDeleteDialog(int position, Note note) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    mNotes.remove(note);
                    mNotesAdapter.notifyItemRemoved(position);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();
    }

}
