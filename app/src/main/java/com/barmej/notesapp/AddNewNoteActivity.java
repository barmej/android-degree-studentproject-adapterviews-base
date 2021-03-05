package com.barmej.notesapp;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import java.io.Serializable;


public class AddNewNoteActivity extends AppCompatActivity implements Serializable {
    private static final int PICK_IMAGE = 10;
    private static final int READ_PHOTO_FROM_GALLERY_PERMISSION = 11;
    private Uri mSelectedPhotoUri;

    private ConstraintLayout constraintLayout;

    private CardView checkBoxCardView;
    private CardView imageCardView;
    private CardView normalCardView;

    private RadioButton normalNoteRadio;
    private RadioButton checkBoxNoteRadio;
    private RadioButton imageNoteRadio;

    private RadioButton blueNote;
    private RadioButton redNote;
    private RadioButton yellowNote;

    private int cardViewColor;

    private ImageView imageView;

    private EditText normalNoteEditText;
    private EditText photoNoteEditText;
    private EditText checkNoteEditText;

    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_note);
        imageView = findViewById(R.id.photoImageView);
        addButton = findViewById(R.id.button_submit);

        constraintLayout = findViewById(R.id.ConstraintLayout);
        normalNoteEditText = findViewById(R.id.noteEditText);
        photoNoteEditText = findViewById(R.id.photoNoteEditText);
        checkNoteEditText = findViewById(R.id.checkNoteEditText);

        checkBoxCardView = findViewById(R.id.cardViewCheckNote);
        normalCardView = findViewById(R.id.cardViewNote);
        imageCardView = findViewById(R.id.cardViewPhoto);

        blueNote = findViewById(R.id.radioButton3);
        redNote = findViewById(R.id.radioButton2);
        yellowNote = findViewById(R.id.radioButton);

        imageNoteRadio = findViewById(R.id.radioButton4);
        normalNoteRadio = findViewById(R.id.radioButton6);
        checkBoxNoteRadio = findViewById(R.id.radioButton5);

        yellowNote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                normalCardView.setBackgroundColor(getResources().getColor(R.color.yellow));
                checkBoxCardView.setBackgroundColor(getResources().getColor(R.color.yellow));
                imageCardView.setBackgroundColor(getResources().getColor(R.color.yellow));
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.yellow));

                cardViewColor = getResources().getColor(R.color.yellow);

            }
        });

        redNote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                normalCardView.setBackgroundColor(getResources().getColor(R.color.red));
                checkBoxCardView.setBackgroundColor(getResources().getColor(R.color.red));
                imageCardView.setBackgroundColor(getResources().getColor(R.color.red));
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.red));

                cardViewColor = getResources().getColor(R.color.red);

            }
        });

        blueNote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                normalCardView.setBackgroundColor(getResources().getColor(R.color.blue));
                checkBoxCardView.setBackgroundColor(getResources().getColor(R.color.blue));
                imageCardView.setBackgroundColor(getResources().getColor(R.color.blue));
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.blue));

                cardViewColor = getResources().getColor(R.color.blue);
            }
        });

        checkBoxNoteRadio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                imageCardView.setVisibility(View.GONE);
                normalCardView.setVisibility(View.GONE);
                checkBoxCardView.setVisibility(View.VISIBLE);
            }
        });

        normalNoteRadio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkBoxCardView.setVisibility(View.GONE);
                imageCardView.setVisibility(View.GONE);
                normalCardView.setVisibility(View.VISIBLE);
            }
        });

        imageNoteRadio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkBoxCardView.setVisibility(View.GONE);
                normalCardView.setVisibility(View.GONE);
                imageCardView.setVisibility(View.VISIBLE);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                submit();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectPhoto();
            }
        });
    }

    private void submit()
    {
        if (imageNoteRadio.isChecked())
        {
            String photoNoteText = photoNoteEditText.getText().toString();

            if (!photoNoteText.equals(""))
            {
                if (mSelectedPhotoUri != null)
                {
                    Intent imageNoteIntent = new Intent();

                    imageNoteIntent.putExtra(Constants.EXTRA_PHOTO_URI , mSelectedPhotoUri);
                    imageNoteIntent.putExtra(Constants.THE_NOTE , photoNoteText);
                    imageNoteIntent.putExtra(Constants.COLOR, cardViewColor);

                    setResult(RESULT_OK , imageNoteIntent);
                    finish();
                }
                else
                {
                    Toast.makeText(this, R.string.didnt_add_photo, Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, R.string.Must_Enter_Text, Toast.LENGTH_SHORT).show();
            }

        }
        else if (checkBoxNoteRadio.isChecked())
        {
            String checkBoxText = checkNoteEditText.getText().toString();

            if (!checkBoxText.equals(""))
            {
                Intent checkNoteIntent = new Intent();

                checkNoteIntent.putExtra(Constants.CHECK_BOX_TEXT, checkBoxText);
                checkNoteIntent.putExtra(Constants.COLOR, cardViewColor);
                setResult(RESULT_OK , checkNoteIntent);

                finish();
            }
            else
            {
                Toast.makeText(this, "the text is empty", Toast.LENGTH_SHORT).show();
            }
        }
        else if (normalNoteRadio.isChecked())
        {
            String normalNoteText = normalNoteEditText.getText().toString();

            if (!normalNoteText.equals(""))
            {
                Intent normalNoteIntent = new Intent();

                normalNoteIntent.putExtra(Constants.THE_NOTE , normalNoteText);
                normalNoteIntent.putExtra(Constants.COLOR, cardViewColor);
                setResult(RESULT_OK , normalNoteIntent);

                finish();
            }
            else
            {
                Toast.makeText(this, "the text is empty", Toast.LENGTH_SHORT).show();
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
        if (ActivityCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this , new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE} , READ_PHOTO_FROM_GALLERY_PERMISSION);
        }
        else
        {
            firePickPhotoIntent();
        }
    }

    private void setSelectedPhoto(Uri data)
    {
        imageView.setImageURI(data);
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
