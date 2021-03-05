package com.barmej.notesapp.NotesEdit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.barmej.notesapp.R;
import com.barmej.notesapp.classes.PhotoNote;

public class PhotoNoteEdit extends AppCompatActivity {

    private static final int PICK_IMAGE = 10;
    private Uri mSelectedPhotoUri;

    private EditText photoNoteEditEditText;
    private ImageView photoNoteEditImageView;
    private Button changeBtn;
    private ConstraintLayout constraintLayout;
    private int Position;
    private String newText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_photo_details);

        photoNoteEditEditText = findViewById(R.id.photoNoteEditText);
        photoNoteEditImageView = findViewById(R.id.photoImageView);
        changeBtn = findViewById(R.id.changeBtn);
        constraintLayout = findViewById(R.id.ConstraintLayout);
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        PhotoNote photoNote = (PhotoNote) getIntent().getSerializableExtra("photoNote");

        photoNoteEditEditText.setText(photoNote.getNote());
        photoNoteEditImageView.setImageURI(photoNote.getImage());
        constraintLayout.setBackgroundColor(photoNote.getColor());
        Position = getIntent().getExtras().getInt("Position");

        photoNoteEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                newText = photoNoteEditEditText.getText().toString();

                if (mSelectedPhotoUri != null)
                {
                    Intent newIntent = new Intent();

                    newIntent.putExtra("NewTextPhoto", newText);
                    newIntent.putExtra("Photo", mSelectedPhotoUri);
                    newIntent.putExtra("Position2", Position);

                    setResult(RESULT_OK, newIntent);
                    finish();
                }
                else
                {
                    finish();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {       super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE)
        {
            if (resultCode == RESULT_OK && data != null && data.getData() != null)
            {
                setSelectedPhoto(data.getData());
                getContentResolver().takePersistableUriPermission(data.getData() , Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            else
            {
                Toast.makeText(this, R.string.failed_to_get_image, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void selectPhoto()
    {
        firePickPhotoIntent();
    }

    private void setSelectedPhoto(Uri data)
    {
        photoNoteEditImageView.setImageURI(data);
        mSelectedPhotoUri = data;
    }

    private void firePickPhotoIntent()
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent , getString(R.string.choose_picture) ) , PICK_IMAGE);
    }
}