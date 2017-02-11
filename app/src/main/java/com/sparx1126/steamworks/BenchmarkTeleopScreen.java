package com.sparx1126.steamworks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.content.SharedPreferences;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BenchmarkTeleopScreen extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO  = 1;
    String mCurrentPhotoPath;
    Button benchmarkAutoSwitcher;
    Button cameraButton;
    ImageButton home_teleop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmarkteleop);

        home_teleop = (ImageButton)findViewById(R.id.home_teleop);
        home_teleop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_teleopImageButtonClicked(v);
            }
        });

        benchmarkAutoSwitcher = (Button)findViewById(R.id.benchmarkAutoSwitcher);
        benchmarkAutoSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                benchmarkAutoSwitcherButtonClicked(v);
            }
        });

        cameraButton = (Button)findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraButton(v);
            }
        });
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Steamworks " + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    private void home_teleopImageButtonClicked(View v) {
        Context context = BenchmarkTeleopScreen.this;
        Class destination = MainScreen.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }

    private void benchmarkAutoSwitcherButtonClicked(View v) {
        Context context = BenchmarkTeleopScreen.this;
        Class destination = BenchmarkScreen.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }
    private void cameraButton(View v) {
        dispatchTakePictureIntent();
    }

}