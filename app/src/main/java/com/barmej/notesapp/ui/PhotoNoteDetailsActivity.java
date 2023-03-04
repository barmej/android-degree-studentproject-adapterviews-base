package com.barmej.notesapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.barmej.notesapp.R;
import com.barmej.notesapp.entity.PhotoNote;

/**
 * Class to view photo note details and edit it
 */
public class PhotoNoteDetailsActivity extends AppCompatActivity {

    private final int RC_PERMISSION_READ_EXTERNAL_STORAGE = 100;
    private final int RC_PICK_IMAGE = 200;
    private EditText mNoteEditText;
    private ImageView mNoteImageView;
    private PhotoNote note;
    private int position;
    private Uri mPhotoUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_photo_details);
        // Init views
        ViewGroup container = findViewById(R.id.container);
        mNoteEditText = findViewById(R.id.photoNoteEditText);
        mNoteImageView = findViewById(R.id.photoImageView);
        // Get info from the intent
        Intent intent = getIntent();
        if (!intent.hasExtra(MainActivity.EXTRA_NOTE) || !intent.hasExtra(MainActivity.EXTRA_POSITION)) {
            finish();
            return;
        }
        note = intent.getParcelableExtra(MainActivity.EXTRA_NOTE);
        position = intent.getIntExtra(MainActivity.EXTRA_POSITION, -1);
        mPhotoUri = note.getPhotoUri();
        // Show info
        container.setBackgroundColor(note.getBackgroundColor());
        mNoteEditText.setText(note.getText());
        mNoteImageView.setImageURI(note.getPhotoUri());
        mNoteImageView.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if ((ActivityCompat.checkSelfPermission(PhotoNoteDetailsActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(PhotoNoteDetailsActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, RC_PERMISSION_READ_EXTERNAL_STORAGE);
                } else {
                    pickPhoto();
                }
            } else {
                if ((ActivityCompat.checkSelfPermission(PhotoNoteDetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(PhotoNoteDetailsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_PERMISSION_READ_EXTERNAL_STORAGE);
                } else {
                    pickPhoto();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(PhotoNoteDetailsActivity.this)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PICK_IMAGE) {
            if (data != null && data.getData() != null) {
                mPhotoUri = data.getData();
                mNoteImageView.setImageURI(mPhotoUri);
                getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickPhoto();
            } else {
                Toast.makeText(this, R.string.error_read_permission_not_granted, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Select an image using the Gallery app
     */
    private void pickPhoto() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, RC_PICK_IMAGE);
    }

    /**
     * Return updated note to MainActivity to update the note in the list
     */
    private void returnUpdatedNote() {
        String noteText = mNoteEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(noteText)) {
            note.setText(noteText);
            note.setPhotoUri(mPhotoUri);
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
