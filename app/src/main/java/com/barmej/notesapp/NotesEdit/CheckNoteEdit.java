package com.barmej.notesapp.NotesEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.barmej.notesapp.R;
import com.barmej.notesapp.classes.CheckNote;

public class CheckNoteEdit extends AppCompatActivity {
    EditText checkNoteEditEditText;
    Button changeBtn;
    ConstraintLayout constraintLayout;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_check_details);
        checkNoteEditEditText = findViewById(R.id.checkNoteEditText);
        changeBtn = findViewById(R.id.changeBtn);
        constraintLayout = findViewById(R.id.ConstraintLayout);
        CheckNote checkNote = (CheckNote) getIntent().getSerializableExtra("checkNote");
        checkNoteEditEditText.setText(checkNote.getNote());
        constraintLayout.setBackgroundColor(checkNote.getColor());

        Bundle positionBundle = getIntent().getExtras();
        position = positionBundle.getInt("Position");

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newText = checkNoteEditEditText.getText().toString();
                Intent newIntent = new Intent();
                newIntent.putExtra("NewText" , newText);
                newIntent.putExtra("Position2" , position);
                setResult(RESULT_OK , newIntent);
                finish();
            }
        });
    }
}