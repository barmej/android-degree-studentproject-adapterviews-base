package com.barmej.notesapp.NotesEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.barmej.notesapp.extra.Constants;
import com.barmej.notesapp.room.ViewModels.EditNormalNoteViewModel;
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

        Bundle bundle = getIntent().getExtras();
        String bundleNoteText = bundle.getString(Constants.EXTRA_NOTE_TEXT);

        int bundleNoteColor = bundle.getInt(Constants.COLOR);
        normalNoteEditEditText.setText(bundleNoteText);

        position = bundle.getInt(Constants.EXTRA_ID);
        constraintLayout.setBackgroundColor(bundleNoteColor);

        mEditNormalNoteViewModel = ViewModelProviders.of(NormalNoteEdit.this).get(EditNormalNoteViewModel.class);

        changeBtn.setOnClickListener(view -> {
            String newText = normalNoteEditEditText.getText().toString();
            Note noteRoom = new Note(bundleNoteColor , newText);
            noteRoom.setId(position);
            mEditNormalNoteViewModel.update(noteRoom);
            finish();
        });
    }
}