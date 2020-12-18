package com.barmej.notesapp;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;


public class AddNewNoteActivity extends AppCompatActivity {
    private static final int READ_PHOTO_FROM_GALLERY_PERMISSION = 1;
    private static final int PICK_IMAGE = 10;
    Uri mSelectedPhotoUri;

    CardView checkBoxCardView;
    CardView imageCardView;
    CardView normalCardView;
    int cardView;

    RadioButton normalNote;
    RadioButton checkBoxNote;
    RadioButton imageNote;

    RadioButton blueNote;
    RadioButton redNote;
    RadioButton yellowNote;
    int cardViewColor;

    ImageView imageView;
    EditText newNoteEditText;
    Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        imageView = findViewById(R.id.photoImageView);
        addButton = findViewById(R.id.button_submit);
        newNoteEditText = findViewById(R.id.photoNoteEditText);

        checkBoxCardView = findViewById(R.id.cardViewCheckNote);
        normalCardView = findViewById(R.id.cardViewNote);
        imageCardView = findViewById(R.id.cardViewPhoto);

        blueNote = findViewById(R.id.radioButton3);
        redNote = findViewById(R.id.radioButton2);
        yellowNote = findViewById(R.id.radioButton);

        imageNote = findViewById(R.id.radioButton4);
        normalNote = findViewById(R.id.radioButton6);
        checkBoxNote = findViewById(R.id.radioButton5);

        yellowNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalCardView.setBackgroundColor(getResources().getColor(R.color.yellow));
                checkBoxCardView.setBackgroundColor(getResources().getColor(R.color.yellow));
                imageCardView.setBackgroundColor(getResources().getColor(R.color.yellow));

                cardViewColor = 1;


            }
        });

        redNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalCardView.setBackgroundColor(getResources().getColor(R.color.red));
                checkBoxCardView.setBackgroundColor(getResources().getColor(R.color.red));
                imageCardView.setBackgroundColor(getResources().getColor(R.color.red));

                cardViewColor = 2;

            }
        });

        blueNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalCardView.setBackgroundColor(getResources().getColor(R.color.blue));
                checkBoxCardView.setBackgroundColor(getResources().getColor(R.color.blue));
                imageCardView.setBackgroundColor(getResources().getColor(R.color.blue));

                cardViewColor = 3;


            }
        });

        checkBoxNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCardView.setVisibility(View.GONE);
                normalCardView.setVisibility(View.GONE);
                checkBoxCardView.setVisibility(View.VISIBLE);

                cardView = Constants.CHECK_NOTE;

            }
        });

        normalNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkBoxCardView.setVisibility(View.GONE);
                imageCardView.setVisibility(View.GONE);
                normalCardView.setVisibility(View.VISIBLE);

                cardView = Constants.TEXT_NOTE;


            }
        });

        imageNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxCardView.setVisibility(View.GONE);
                normalCardView.setVisibility(View.GONE);
                imageCardView.setVisibility(View.VISIBLE);

                cardView = Constants.PHOTO_NOTE;

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
    }

    private void submit() {
        String newNoteString = newNoteEditText.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_PHOTO_URI, mSelectedPhotoUri);
        intent.putExtra(String.valueOf(Constants.PHOTO_NOTE), cardView);
        intent.putExtra(String.valueOf(Constants.CHECK_NOTE), cardView);
        intent.putExtra(String.valueOf(Constants.TEXT_NOTE), cardView);
        intent.putExtra("StringNotes", newNoteString);
        setResult(RESULT_OK, intent);
        finish();
        if (mSelectedPhotoUri != null ) {

            if (newNoteString.equals("")) {
                Toast.makeText(this, R.string.Must_Enter_Text, Toast.LENGTH_SHORT).show();
            } else {

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_PHOTO_FROM_GALLERY_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                firePickPhotoIntent();
            }
            else {
                Toast.makeText(this, R.string.read_permission_needed_to_access_files, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                setSelectedPhoto(data.getData());

                getContentResolver().takePersistableUriPermission(data.getData() , Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                Toast.makeText(this, R.string.failed_to_get_image, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void selectPhoto() {
        if (ActivityCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this , new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE} , READ_PHOTO_FROM_GALLERY_PERMISSION);
        }
        else {
            firePickPhotoIntent();

        }

    }

    private void setSelectedPhoto(Uri data) {
        imageView.setImageURI(data);
        mSelectedPhotoUri = data;
    }

    private void firePickPhotoIntent() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent , getString(R.string.choose_picture) ) , PICK_IMAGE);
    }

}
