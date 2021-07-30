package com.barmej.notesapp.NotesEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.classes.CheckNote;

public class CheckNoteEdit extends AppCompatActivity {
    private EditText checkNoteEditEditText;
    private Button changeBtn;
    private ConstraintLayout constraintLayout;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_check_details);
        checkNoteEditEditText = findViewById(R.id.checkNoteEditText);
        changeBtn = findViewById(R.id.changeBtn);
        constraintLayout = findViewById(R.id.ConstraintLayout);
        Bundle bundle = getIntent().getExtras();
        String noteText = bundle.getString(Constants.EXTRA_NOTE_TEXT);
        checkNoteEditEditText.setText(noteText);
        int noteColor = bundle.getInt(Constants.COLOR);
        position = bundle.getInt(Constants.EXTRA_ID);


        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newText = checkNoteEditEditText.getText().toString();
                CheckNote checkNoteRoom = new CheckNote(noteColor, newText, false);
                finish();
            }
        });
    }
}