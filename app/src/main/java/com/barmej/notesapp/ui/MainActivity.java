package com.barmej.notesapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.barmej.notesapp.Adapters.NotesAdapter;
import com.barmej.notesapp.extra.Constants;
import com.barmej.notesapp.Listener.ItemClickListener;
import com.barmej.notesapp.Listener.ItemLongClickListener;
import com.barmej.notesapp.NotesEdit.CheckNoteEdit;
import com.barmej.notesapp.NotesEdit.NormalNoteEdit;
import com.barmej.notesapp.NotesEdit.PhotoNoteEdit;
import com.barmej.notesapp.R;
import com.barmej.notesapp.room.ViewModels.NoteViewModel;
import com.barmej.notesapp.extra.ViewSpaces;
import com.barmej.notesapp.classes.CheckNote;
import com.barmej.notesapp.classes.Note;
import com.barmej.notesapp.classes.PhotoNote;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> notesItems = new ArrayList<>();
    private NotesAdapter mNotesAdapter;
    private static final int RECEIVE_NOTES = 2003;
    private static final int EDIT_NOTES = 1000;
    private NoteViewModel mNoteViewModel;
    private LiveData<List<PhotoNote>> allPhotoNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, notesItems.size()+"", Toast.LENGTH_SHORT).show();

        RecyclerView notesRecyclerView = findViewById(R.id.recycler_view_photos);

        findViewById(R.id.floating_button_add).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivityForResult(new Intent(MainActivity.this , AddNewNoteActivity.class) , RECEIVE_NOTES);
            }
        });


        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2 ,1);


        mNotesAdapter = new NotesAdapter(notesItems, new ItemLongClickListener()
        {
            @Override
            public void onLongClickItem(int position)
            {
                Toast.makeText(MainActivity.this, position+"", Toast.LENGTH_SHORT).show();
                removeNote(position);

            }
        }, new ItemClickListener()
        {
            @Override
            public void onClickListener(int position)
            {
//                editNote(position);
            }

            @Override
            public void onItemClick(Note note) {
                if (note instanceof PhotoNote){
                    Intent intent = new Intent(MainActivity.this , PhotoNoteEdit.class);
                    intent.putExtra(Constants.EXTRA_ID , note.getId());
                    intent.putExtra(Constants.EXTRA_NOTE_TEXT , note.getNote());
                    intent.putExtra(Constants.COLOR, note.getColor());
                    startActivity(intent);

                } else if (note instanceof CheckNote){
                    Intent intent = new Intent(MainActivity.this , CheckNoteEdit.class);
                    intent.putExtra(Constants.EXTRA_ID , note.getId());
                    intent.putExtra(Constants.EXTRA_NOTE_TEXT , note.getNote());
                    intent.putExtra(Constants.COLOR, note.getColor());
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(MainActivity.this , NormalNoteEdit.class);
                    intent.putExtra(Constants.EXTRA_ID , note.getId());
                    intent.putExtra(Constants.EXTRA_NOTE_TEXT , note.getNote());
                    intent.putExtra(Constants.COLOR, note.getColor());
                    startActivity(intent);
                }

            }
        });

        notesRecyclerView.setAdapter(mNotesAdapter);
        notesRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        notesRecyclerView.addItemDecoration(new ViewSpaces(20));
        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mNoteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                mNotesAdapter.setNormalNotes(notes);
            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (mNotesAdapter.getNoteAt(position) instanceof PhotoNote){
                    mNotesAdapter.notifyItemRemoved(position);
                    notesItems.remove(position);
                    Toast.makeText(MainActivity.this, "This was a PhotoNote", Toast.LENGTH_SHORT).show();
                }else{
                    mNoteViewModel.deleteNormalNote(mNotesAdapter.getNoteAt(position));
//                    notesItems.remove(position);
                    Toast.makeText(MainActivity.this, "This was a NormalNote", Toast.LENGTH_SHORT).show();
                    mNotesAdapter.notifyItemRemoved(position);
                }
            }
        }).attachToRecyclerView(notesRecyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == RECEIVE_NOTES)
//        {
//            if (resultCode == RESULT_OK && data != null)
//            {
//                Uri photoUri = data.getParcelableExtra(Constants.EXTRA_PHOTO_URI);
//
//                String NoteCallBack = data.getStringExtra(Constants.THE_NOTE);
//
//                String checkBoxCallBack = data.getStringExtra(Constants.CHECK_BOX_TEXT);
//
//                int color = data.getIntExtra(Constants.COLOR , 1);
//
//                if (photoUri != null)
//                {
//                    PhotoNote photoNote = new PhotoNote(color ,NoteCallBack , photoUri);
//                    notesItems.add(photoNote);
//                    mNotesAdapter.notifyItemInserted(notesItems.size());
//                }
//
//                else if (checkBoxCallBack != null)
//                {
//                    CheckNote checkNote = new CheckNote(color ,checkBoxCallBack, false);
//                    notesItems.add(checkNote);
//                    mNotesAdapter.notifyItemInserted(notesItems.size());
//                }
//
//                else
//                {
//                    Note note1 = new Note(color, NoteCallBack);
//                    notesItems.add(note1);
//                    mNotesAdapter.notifyItemInserted(notesItems.size());
//
//                }
//
//            }
//            else
//            {
//                Toast.makeText(this, R.string.something_happened , Toast.LENGTH_SHORT).show();
//            }
//        }
//        else if (requestCode == EDIT_NOTES)
//        {
//            if (resultCode == RESULT_OK)
//            {
//                Bundle bundle = data.getExtras();
//                int notePosition = bundle.getInt("Position2");
//                Note note = notesItems.get(notePosition);
//
//                String newTextCallBack = bundle.getString("NewText");
//                String newTextPhotoCallBack = bundle.getString("NewTextPhoto");
//
//                Uri photoNoteImage = bundle.getParcelable("Photo");
//
//                if (note instanceof CheckNote)
//                {
//                    note.setNote(newTextCallBack);
//                    mNotesAdapter.notifyItemChanged(notePosition);
//                }
//                else if (note instanceof PhotoNote)
//                {
//                    PhotoNote photoNote = (PhotoNote) note;
//                    photoNote.setImage(photoNoteImage);
//                    photoNote.setNote(newTextPhotoCallBack);
//                    photoNote.setImage(photoNoteImage);
//                    mNotesAdapter.notifyItemChanged(notePosition);
//                }
//                else
//                {
//                    note.setNote(newTextCallBack);
//                    mNotesAdapter.notifyItemChanged(notePosition);
//                }
//
//            }else{
//                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    private void removeNote(final int position) {

        AlertDialog alertDialog = new AlertDialog.Builder(this )
                .setMessage(R.string.delete_confirmation)

                .setPositiveButton(R.string.Confirm, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if (mNotesAdapter.getNoteAt(position) instanceof PhotoNote){
                            mNotesAdapter.notifyItemRemoved(position);
                            Toast.makeText(MainActivity.this, "This was a PhotoNote", Toast.LENGTH_SHORT).show();
                        }else{
                            mNoteViewModel.deleteNormalNote(mNotesAdapter.getNoteAt(position));
//                            notesItems.remove(position);
                            Toast.makeText(MainActivity.this, "This was a NormalNote", Toast.LENGTH_SHORT).show();
                            mNotesAdapter.notifyItemRemoved(position);
                        }

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        alertDialog.show();

    }

    private void editNote(int position)
    {
        Note noteEdit = notesItems.get(position);

        if (noteEdit instanceof PhotoNote)
        {
            Intent photoNoteIntent = new Intent(MainActivity.this , PhotoNoteEdit.class);

            photoNoteIntent.putExtra("photoNote" , noteEdit);
            photoNoteIntent.putExtra("Position" , position);

            startActivityForResult( photoNoteIntent,EDIT_NOTES );

        } else if (noteEdit instanceof CheckNote)
        {
//            Intent intent = new Intent(MainActivity.this , NormalNoteEdit.class);
//            intent.putExtra(Constants.EXTRA_ID , note.getId());
//            intent.putExtra(Constants.EXTRA_NOTE_TEXT , note.getNote());
//            intent.putExtra(Constants.COLOR, note.getColor());
//            startActivity(intent);

        } else
        {

        }
    }



}