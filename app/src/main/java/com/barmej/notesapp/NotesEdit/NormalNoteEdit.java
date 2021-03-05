package com.barmej.notesapp.NotesEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.barmej.notesapp.R;
import com.barmej.notesapp.classes.Note;

public class NormalNoteEdit extends AppCompatActivity {
    private EditText normalNoteEditEditText;
    private Button changeBtn;
    private ConstraintLayout constraintLayout;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        normalNoteEditEditText = findViewById(R.id.noteEditText);
        changeBtn = findViewById(R.id.changeBtn);
        constraintLayout = findViewById(R.id.ConstraintLayout);
        Note note = (Note) getIntent().getSerializableExtra("Note");
        normalNoteEditEditText.setText(note.getNote());
        constraintLayout.setBackgroundColor(note.getColor());

        Bundle positionBundle = getIntent().getExtras();
        position = positionBundle.getInt("Position");

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newText = normalNoteEditEditText.getText().toString();
                Intent newIntent = new Intent();
                newIntent.putExtra("NewText" , newText);
                newIntent.putExtra("Position2" , position);
                setResult(RESULT_OK , newIntent);
                finish();
            }
        });
    }
}