package com.sparx1126.steamworks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.gosparx.scouting.aerialassist.dto.ScouterData;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Amanda on 1/17/17.
 */

public class BenchmarkScreen extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO  = 1;
    String mCurrentPhotoPath;
    Button benchmarkAutoSwitcher;
    Button cameraButton;
    ImageButton home_auto;
    private ScoutingInfo currentInfo;

    // new
    private EditText driveSystem;
    private EditText drivesSpeed;
    private EditText maximumBallCapacity;
    private EditText typeOfShooter;
    private EditText ballsPerSecond;
    private EditText whereCanShoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmark);

        home_auto = (ImageButton)findViewById(R.id.home_auto);
        home_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_autoButtonClicked(v);
            }
        });
        currentInfo = (ScoutingInfo)getIntent().getParcelableExtra(CommonDefs.SCOUTER_INFO);
        System.out.println("Oooooooh no");
        System.out.println(currentInfo.getEventKey());
        cameraButton = (Button)findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraButton(v);
            }
        });

        // <o/  D
        //  |   A
        // / \  B

        // new
        whereCanShoot = (EditText) findViewById(R.id.whereCanShootBenchInput);
        whereCanShoot.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String textEntered = whereCanShoot.getEditableText().toString();
                currentInfo.getCurrentData().setShootingLocation(textEntered);
                System.out.println(currentInfo.getCurrentData().getShootingLocation());
            }
        });

        ballsPerSecond = (EditText) findViewById(R.id.ballsPerSecondBenchInput);
        ballsPerSecond.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String ballsSecondString = ballsPerSecond.getEditableText().toString();
                int textEntered = Integer.getInteger(ballsSecondString);
                currentInfo.getCurrentData().setBallsPerSecond(textEntered);
            }
        });
        typeOfShooter = (EditText) findViewById(R.id.typeOfShooterBenchInput);
        typeOfShooter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String textEntered = typeOfShooter.getEditableText().toString();
                currentInfo.getCurrentData().setTypeOfShooter(textEntered);
            }
        });
        maximumBallCapacity = (EditText) findViewById(R.id.maximumBallCapacityBenchInput);
        maximumBallCapacity.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String textEntered = maximumBallCapacity.getEditableText().toString();
                currentInfo.getCurrentData().setBallCapacity(Integer.parseInt(textEntered));
            }
        });
        drivesSpeed = (EditText) findViewById(R.id.drivesSpeed);
        drivesSpeed.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String textEntered = drivesSpeed.getEditableText().toString();
                currentInfo.getCurrentData().setApproxSpeedFeetPerSecond(Double.parseDouble(textEntered));
            }
        });


        driveSystem = (EditText) findViewById(R.id.drivesSystem);
        driveSystem.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String textEntered = driveSystem.getEditableText().toString();
                ScouterData currentData = currentInfo.getCurrentData();
                currentInfo.getCurrentData().setDriveSystemDescription(textEntered);
                System.out.println(currentInfo.getCurrentData().getDriveSystemDescription());
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
    private void home_autoButtonClicked(View v) {
        Context context = BenchmarkScreen.this;
        Class destination = MainScreen.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }

    private void cameraButton(View v) {
        dispatchTakePictureIntent();
    }

}