package com.barmej.notesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.barmej.notesapp.R;
import com.barmej.notesapp.entity.Note;

/**
 * Class to view text note details and edit it
 */
public class TextNoteDetailsActivity extends AppCompatActivity {

    private EditText mNoteEditText;
    private Note note;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        // Init views
        ViewGroup container = findViewById(R.id.container);
        mNoteEditText = findViewById(R.id.noteEditText);
        // Get info from the intent
        Intent intent = getIntent();
        if (!intent.hasExtra(MainActivity.EXTRA_NOTE) || !intent.hasExtra(MainActivity.EXTRA_POSITION)) {
            finish();
            return;
        }
        note = intent.getParcelableExtra(MainActivity.EXTRA_NOTE);
        position = intent.getIntExtra(MainActivity.EXTRA_POSITION, -1);
        // Show info
        container.setBackgroundColor(note.getBackgroundColor());
        mNoteEditText.setText(note.getText());
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(TextNoteDetailsActivity.this)
                .setMessage(R.string.save_confirmation)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    returnUpdatedNote();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                    dialog.dismiss();
                    super.onBackPressed();
                })
                .create();
        alertDialog.show();
    }

    /**
     * Return updated note to MainActivity to update the note in the list
     */
    private void returnUpdatedNote() {
        String noteText = mNoteEditText.getText().toString().trim();
        if(!TextUtils.isEmpty(noteText)) {
            note.setText(noteText);
            Intent intent = new Intent();
            intent.putExtra(MainActivity.EXTRA_NOTE, note);
            intent.putExtra(MainActivity.EXTRA_POSITION, position);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            mNoteEditText.setError(getString(R.string.error_empty_note));
        }
    }

}
