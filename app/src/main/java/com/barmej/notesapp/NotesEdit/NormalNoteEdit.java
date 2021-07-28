package com.barmej.notesapp.NotesEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.barmej.notesapp.AddNewNoteActivity;
import com.barmej.notesapp.Constants;
import com.barmej.notesapp.EditNormalNoteViewModel;
import com.barmej.notesapp.R;
import com.barmej.notesapp.classes.Note;

public class NormalNoteEdit extends AppCompatActivity {
    private EditText normalNoteEditEditText;
    private Button changeBtn;
    private ConstraintLayout constraintLayout;
    private int position;
    private EditNormalNoteViewModel mEditNormalNoteViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        normalNoteEditEditText = findViewById(R.id.noteEditText);
        changeBtn = findViewById(R.id.changeBtn);
        constraintLayout = findViewById(R.id.ConstraintLayout);
        Note note = (Note) getIntent().getSerializableExtra("Note");

//        normalNoteEditEditText.setText(note.getNote());
//        constraintLayout.setBackgroundColor(note.getColor());
//
        Bundle positionBundle = getIntent().getExtras();
        position = positionBundle.getInt(Constants.EXTRA_ID);

        mEditNormalNoteViewModel = ViewModelProviders.of(NormalNoteEdit.this).get(EditNormalNoteViewModel.class);


        changeBtn.setOnClickListener(view -> {
            String newText = normalNoteEditEditText.getText().toString();
            Note noteRoom = new Note(Color.BLUE , newText);
            noteRoom.setId(position);
            mEditNormalNoteViewModel.update(noteRoom);
//            Intent newIntent = new Intent();
//            newIntent.putExtra("NewText" , newText);
//            newIntent.putExtra("Position2" , position);
//            setResult(RESULT_OK , newIntent);
            finish();
        });
    }
}