package com.barmej.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.barmej.notesapp.Adapters.NotesAdapter;
import com.barmej.notesapp.Listener.ItemClickListener;
import com.barmej.notesapp.Listener.ItemLongClickListener;
import com.barmej.notesapp.NotesEdit.CheckNoteEdit;
import com.barmej.notesapp.NotesEdit.NormalNoteEdit;
import com.barmej.notesapp.NotesEdit.PhotoNoteEdit;
import com.barmej.notesapp.classes.CheckNote;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView notesRecyclerView;
    ArrayList<Note> notesItems;
    Uri photoUri;
    NotesAdapter mNotesAdapter;
    FloatingActionButton mFloatingActionButton;
    private static final int RECEIVE_NOTES = 2003;
    private static final int EDIT_NOTES = 1000;
    private static final int EDIT_PHOTO_NOTE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFloatingActionButton = findViewById(R.id.floating_button_add);
        notesRecyclerView = findViewById(R.id.recycler_view_photos);

         StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2 ,1);
        notesItems = new ArrayList<>();

        mNotesAdapter = new NotesAdapter(notesItems, new ItemLongClickListener() {
            @Override
            public void onLongClickItem(int position) {
                removeNote(position);

            }
        }, new ItemClickListener()
        {
            @Override
            public void onClickListener(int position)
            {
                editNote(position);
            }
        } , MainActivity.this);

        notesRecyclerView.setAdapter(mNotesAdapter);

        notesRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        notesRecyclerView.addItemDecoration(new ViewSpaces(20));


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AddNewNoteActivity.class);
                startActivityForResult(intent , RECEIVE_NOTES);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RECEIVE_NOTES)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                Toast.makeText(this, "notesItems: "+notesItems.size(), Toast.LENGTH_SHORT).show();
                photoUri = data.getParcelableExtra(Constants.EXTRA_PHOTO_URI);
                String NoteCallBack = data.getStringExtra(Constants.THE_NOTE);
                String checkBoxCallBack = data.getStringExtra(Constants.CHECK_BOX_TEXT);
                int color = data.getIntExtra(Constants.COLOR , 1);

                if (photoUri != null)
                {
                   PhotoNote photoNote = new PhotoNote(color ,NoteCallBack , photoUri );
                    notesItems.add(photoNote);
                    mNotesAdapter.notifyItemInserted(notesItems.size());
                }
                else if (checkBoxCallBack != null)
                {
                    CheckNote checkNote = new CheckNote(color ,checkBoxCallBack, false);
                    notesItems.add(checkNote);
                    mNotesAdapter.notifyItemInserted(notesItems.size());
                }
                else
                {
                    Note note1 = new Note(color, NoteCallBack);
                    notesItems.add(note1);
                    mNotesAdapter.notifyItemInserted(notesItems.size());

                }

            }
            else
            {
                Toast.makeText(this, R.string.didnt_add_photo , Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == EDIT_NOTES)
        {
            if (resultCode == RESULT_OK)
            {

                Bundle bundle = data.getExtras();
                String newTextCallBack = bundle.getString("NewText");
                int notePosition = bundle.getInt("Position2");
                Note note = notesItems.get(notePosition);
                String newTextPhotoCallBack = bundle.getString("NewTextPhoto");
                Uri photoNoteImage = bundle.getParcelable("Photo");

                if (note instanceof CheckNote)
                {
                    note.setNote(newTextCallBack);
                    mNotesAdapter.notifyItemChanged(notePosition);
                }
                else if (note instanceof PhotoNote)
                {
                    PhotoNote photoNote = (PhotoNote) note;
                    photoNote.setNote(newTextPhotoCallBack);
                    photoNote.setImage(photoNoteImage);
                    mNotesAdapter.notifyItemChanged(notePosition);
                }
                else
                {
                    note.setNote(newTextCallBack);
                    mNotesAdapter.notifyItemChanged(notePosition);
                }

            }
        }
    }

    private void removeNote(final int position) {
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

    private void editNote(int position){
        Note noteEdit = notesItems.get(position);
        if (noteEdit instanceof PhotoNote){
            Intent photoNoteIntent = new Intent(MainActivity.this , PhotoNoteEdit.class);
            photoNoteIntent.putExtra("photoNote" , noteEdit);
            photoNoteIntent.putExtra("Position" , position);
            startActivityForResult( photoNoteIntent,EDIT_NOTES );

        } else if (noteEdit instanceof CheckNote){
            Intent checkNoteIntent = new Intent(MainActivity.this , CheckNoteEdit.class);
            checkNoteIntent.putExtra("checkNote" ,  noteEdit);
            checkNoteIntent.putExtra("Position" , position);
            startActivityForResult(checkNoteIntent , EDIT_NOTES);

        } else {
            Intent normalNoteEdit = new Intent(MainActivity.this , NormalNoteEdit.class);
            normalNoteEdit.putExtra("Note" ,  noteEdit);
            normalNoteEdit.putExtra("Position" , position);
            startActivityForResult(normalNoteEdit , EDIT_NOTES);
        }
    }
}
