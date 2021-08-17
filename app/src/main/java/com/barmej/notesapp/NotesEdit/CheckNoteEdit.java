package com.barmej.notesapp.NotesEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.barmej.notesapp.extra.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.classes.CheckNote;
import com.barmej.notesapp.room.ViewModels.NoteViewModel;

public class CheckNoteEdit extends AppCompatActivity {
    private EditText checkNoteEditEditText;
    private Button changeBtn;
    private CheckBox checkBox;
    private ConstraintLayout constraintLayout;
    private int position;
    private NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_check_details);

        checkNoteEditEditText = findViewById(R.id.checkNoteEditText);
        changeBtn = findViewById(R.id.changeBtn);
        constraintLayout = findViewById(R.id.ConstraintLayout);
        checkBox = findViewById(R.id.checkNoteCheckBox);

        Bundle bundle = getIntent().getExtras();

        String noteText = bundle.getString(Constants.EXTRA_NOTE_TEXT);
        int noteColor = bundle.getInt(Constants.COLOR);
        position = bundle.getInt(Constants.EXTRA_ID);
        boolean checkBoxStatus = bundle.getBoolean("CheckBox status");

        checkBox.setChecked(checkBoxStatus);
        if (checkBox.isChecked()){
            checkBox.setBackgroundColor(Color.GREEN);
        }
        constraintLayout.setBackgroundColor(noteColor);
        checkNoteEditEditText.setText(noteText);
        mNoteViewModel = ViewModelProviders.of(this ).get(NoteViewModel.class);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newText = checkNoteEditEditText.getText().toString();
                CheckNote checkNoteRoom = new CheckNote(noteColor, newText, checkBox.isChecked());
                checkNoteRoom.setId(position);
                mNoteViewModel.updateCheckNote(checkNoteRoom);
                finish();
            }
        });
    }
}