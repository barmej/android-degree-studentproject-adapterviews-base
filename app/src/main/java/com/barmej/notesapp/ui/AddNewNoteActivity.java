package com.barmej.notesapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.barmej.notesapp.R;
import com.barmej.notesapp.entity.CheckableNote;
import com.barmej.notesapp.entity.PhotoNote;
import com.barmej.notesapp.entity.TextNote;

public class AddNewNoteActivity extends AppCompatActivity {

    /**
     * MainActivity
     */
    private static final String EXTRA_BACKGROUND_COLOR = "EXTRA_BACKGROUND_COLOR";
    private static final String EXTRA_PHOTO_URI = "EXTRA_PHOTO_URI";
    private final int RC_PERMISSION_READ_EXTERNAL_STORAGE = 100;
    private final int RC_PICK_IMAGE = 200;

    /**
     * Notes CardViews
     */
    private CardView mNoteCardView;
    private CardView mCheckableNoteCardView;
    private CardView mPhotoNoteCardView;

    /**
     * Note EditTexts
     */
    private EditText mNoteEditText;
    private EditText mCheckableNoteEditText;
    private EditText mPhotoNoteEditText;

    /**
     * Checkable Note CheckBox
     */
    private CheckBox mNoteCheckbox;

    /**
     * Photo Note ImageView
     */
    private ImageView mNoteImageView;

    /**
     * Variables to store note data
     */
    private int mBackgroundColor;
    private Uri mPhotoUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        mNoteCardView = findViewById(R.id.cardViewNote);
        mCheckableNoteCardView = findViewById(R.id.cardViewCheckNote);
        mPhotoNoteCardView = findViewById(R.id.cardViewPhoto);

        mNoteEditText = findViewById(R.id.noteEditText);
        mCheckableNoteEditText = findViewById(R.id.checkNoteEditText);
        mPhotoNoteEditText = findViewById(R.id.photoNoteEditText);

        mNoteCheckbox = findViewById(R.id.checkNoteCheckBox);
        mNoteImageView = findViewById(R.id.photoImageView);

        RadioGroup noteTypeRadioGroup = findViewById(R.id.typeRadioGroup);
        noteTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            mCheckableNoteCardView.setVisibility(View.GONE);
            mPhotoNoteCardView.setVisibility(View.GONE);
            mNoteCardView.setVisibility(View.GONE);
            if (checkedId == R.id.textTypeRadioButton) {
                mNoteCardView.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.checkableTypeRadioButton) {
                mCheckableNoteCardView.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.photoTypeRadioButton) {
                mPhotoNoteCardView.setVisibility(View.VISIBLE);
            }
        });

        RadioGroup noteColorRadioGroup = findViewById(R.id.colorRadioGroup);
        noteColorRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.blueRadioButton) {
                setColor(R.color.blue);
            } else if (checkedId == R.id.yellowRadioButton) {
                setColor(R.color.yellow);
            } else if (checkedId == R.id.redRadioButton) {
                setColor(R.color.red);
            }
        });

        mNoteImageView.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if ((ActivityCompat.checkSelfPermission(AddNewNoteActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(AddNewNoteActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, RC_PERMISSION_READ_EXTERNAL_STORAGE);
                } else {
                    pickPhoto();
                }
            } else {
                if ((ActivityCompat.checkSelfPermission(AddNewNoteActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(AddNewNoteActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_PERMISSION_READ_EXTERNAL_STORAGE);
                } else {
                    pickPhoto();
                }
            }
        });

        findViewById(R.id.submitButton).setOnClickListener(v -> addNewNote());


        // Initialize notes values and restore previous values if exist
        if (savedInstanceState != null) {
            mBackgroundColor = savedInstanceState.getInt(EXTRA_BACKGROUND_COLOR, ContextCompat.getColor(this, R.color.blue));
            mPhotoUri = savedInstanceState.getParcelable(EXTRA_PHOTO_URI);
            if (mPhotoUri != null) {
                mNoteImageView.setImageURI(mPhotoUri);
            }
        } else {
            mBackgroundColor = ContextCompat.getColor(this, R.color.blue);
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_BACKGROUND_COLOR, mBackgroundColor);
        outState.putParcelable(EXTRA_PHOTO_URI, mPhotoUri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PICK_IMAGE) {
            if (data != null && data.getData() != null) {
                mPhotoUri = data.getData();
                System.out.println(mPhotoUri.toString());
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
     * Change note color and update UI
     * @param colorResourceId note background color
     */
    private void setColor(int colorResourceId) {
        mBackgroundColor = ContextCompat.getColor(AddNewNoteActivity.this, colorResourceId);
        ColorStateList colorBackGroundTint = ContextCompat.getColorStateList(this, colorResourceId);
        mNoteCardView.setBackgroundTintList(colorBackGroundTint);
        mCheckableNoteCardView.setBackgroundTintList(colorBackGroundTint);
        mPhotoNoteCardView.setBackgroundTintList(colorBackGroundTint);
    }

    /**
     * Validate input and send it as a Note object ot MainActivity
     */
    private void addNewNote() {
        Intent intent = new Intent();
        String noteText;
        // Text notes
        if (mNoteCardView.getVisibility() == View.VISIBLE) {
            noteText = mNoteEditText.getText().toString().trim();
            if(TextUtils.isEmpty(noteText)) {
                mNoteEditText.setError(getString(R.string.error_empty_note));
                return;
            }
            intent.putExtra(MainActivity.EXTRA_NOTE, new TextNote(mBackgroundColor, noteText));
        }
        // Checkable notes
        else if (mCheckableNoteCardView.getVisibility() == View.VISIBLE) {
            noteText = mCheckableNoteEditText.getText().toString().trim();
            if(TextUtils.isEmpty(noteText)) {
                mCheckableNoteEditText.setError(getString(R.string.error_empty_note));
                return;
            }
            intent.putExtra(MainActivity.EXTRA_NOTE, new CheckableNote(mBackgroundColor, noteText, mNoteCheckbox.isChecked()));
        }
        // Photo notes
        else if (mPhotoNoteCardView.getVisibility() == View.VISIBLE) {
            noteText = mPhotoNoteEditText.getText().toString().trim();
            if(mPhotoUri == null) {
                Toast.makeText(this, getString(R.string.error_no_photo), Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(noteText)) {
                mPhotoNoteEditText.setError(getString(R.string.error_empty_note));
                return;
            }
            intent.putExtra(MainActivity.EXTRA_NOTE, new PhotoNote(mBackgroundColor, noteText, mPhotoUri));
        }
        // Send the result back and close current Activity
        setResult(RESULT_OK, intent);
        finish();
    }


}