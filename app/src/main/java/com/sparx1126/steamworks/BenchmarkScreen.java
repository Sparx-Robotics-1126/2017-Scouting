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

import org.gosparx.scouting.aerialassist.dto.BenchmarkingData;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BenchmarkScreen extends AppCompatActivity {
    private static final int REQUEST_TAKE_PHOTO  = 1;
    Button benchmarkAutoSwitcher;
    private ScoutingInfo currentInfo;

    // new
    private EditText driveSystem;
    private EditText drivesSpeed;
    private EditText maximumBallCapacity;
    private EditText typeOfShooter;
    private EditText ballsPerSecond;
    private EditText preferredShootingLocation;
    private EditText highGoalAccuracy;
    private EditText placesCanScale;
    private EditText preferredScalePlace;
    private EditText comments;
    //No longer used in layout private EditText gearsScored;
    //No longer used in layout private EditText ballsScored;
    //No longer used in layout private EditText highGoalRating;
    //No longer used in layout private EditText lowGoalRating;
    //No longer used in layout private EditText whereCanShoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmark);

        ImageButton home_auto = (ImageButton) findViewById(R.id.home_auto);
        home_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_autoButtonClicked();
            }
        });
        currentInfo = getIntent().getParcelableExtra(CommonDefs.SCOUTER_INFO);
        System.out.println("Oooooooh no");
        System.out.println(currentInfo.getEventKey());
        Button cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraButton();
            }
        });

        // <o/  D
        //  |   A
        // / \  B

        comments = (EditText) findViewById(R.id.commentsBench);
        comments.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String textEntered = comments.getEditableText().toString();
                currentInfo.getCurrentData().setComments(textEntered);
            }
        });
/* No longer used in layout
        ballsScored = (EditText) findViewById(R.id.numberOfBallsPerMatchBenchInput);
        ballsScored.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String ballsScoredString = ballsScored.getEditableText().toString();
                if (!ballsScoredString.isEmpty()) {
                    int textEntered = Integer.parseInt(ballsScoredString);
                    currentInfo.getCurrentData().setNumberOfBallsScored(textEntered);
                }
            }
        });



        gearsScored = (EditText) findViewById(R.id.numberOfGearsPerMatchInput);
        gearsScored.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String gearsScoredString = gearsScored.getEditableText().toString();
                if (!gearsScoredString.isEmpty()) {
                    int textEntered = Integer.parseInt(gearsScoredString);
                    currentInfo.getCurrentData().setNumberOfGearsScored(textEntered);
                }
            }
        });
*/



        preferredScalePlace = (EditText) findViewById(R.id.preferredPlacesScaleInput);
        preferredScalePlace.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String textEntered = preferredScalePlace.getEditableText().toString();
                currentInfo.getCurrentData().setPreferredScalePlace(textEntered);
            }
        });



        placesCanScale = (EditText) findViewById(R.id.placesCanScaleBenchInput);
        placesCanScale.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String textEntered = placesCanScale.getEditableText().toString();
                currentInfo.getCurrentData().setPlacesCanScaleFrom(textEntered);
            }
        });

/* No longer used in layout
        lowGoalRating = (EditText) findViewById(R.id.lowGoalRatingBenchInput);
        lowGoalRating.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String lowGoalRatingString = lowGoalRating.getEditableText().toString();
                if (!lowGoalRatingString.isEmpty()) {
                    int textEntered = Integer.parseInt(lowGoalRatingString);
                    currentInfo.getCurrentData().setLowGoalRating(textEntered);
                }
            }
        });

        highGoalRating = (EditText) findViewById(R.id.highGoalRatingBenchInput);
        highGoalRating.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String highGoalRatingString = highGoalRating.getEditableText().toString();
                if (!highGoalRatingString.isEmpty()) {
                    int textEntered = Integer.parseInt(highGoalRatingString);
                    currentInfo.getCurrentData().setHighGoalRating(textEntered);
                }
            }
        });
*/

        highGoalAccuracy = (EditText) findViewById(R.id.accuracyHighBenchInput);
        highGoalAccuracy.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String accuracyHighGoalString = highGoalAccuracy.getEditableText().toString();
                if (!accuracyHighGoalString.isEmpty()) {
                    float textEntered = Float.parseFloat(accuracyHighGoalString);
                    currentInfo.getCurrentData().setBallCapacity(textEntered);
                }
            }
        });

         preferredShootingLocation = (EditText) findViewById(R.id.preferredShootingLocationBenchInput);
        preferredShootingLocation.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String textEntered = preferredShootingLocation.getEditableText().toString();
                currentInfo.getCurrentData().setPreferredShootingLocation(textEntered);
                System.out.println(currentInfo.getCurrentData().getPreferredShootingLocation());
            }
        });
/* No longer used in layout
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

   */

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
                if (!ballsSecondString.isEmpty()) {
                    float textEntered = Float.parseFloat(ballsSecondString);
                    currentInfo.getCurrentData().setBallsPerSecond(textEntered);
                }
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
                String maximumBallCapacityString = maximumBallCapacity.getEditableText().toString();
                if (!maximumBallCapacityString.isEmpty()) {
                    float textEntered = Float.parseFloat(maximumBallCapacityString);
                    currentInfo.getCurrentData().setBallCapacity(textEntered);
                }
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
                if (!textEntered.isEmpty()) {
                currentInfo.getCurrentData().setApproxSpeedFeetPerSecond(Double.parseDouble(textEntered));
            }}
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
                BenchmarkingData currentData = currentInfo.getCurrentData();
                currentData.setDriveSystemDescription(textEntered);
                System.out.println(currentData.getDriveSystemDescription());
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
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "Steamworks " + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }
    private void home_autoButtonClicked() {
        Context context = BenchmarkScreen.this;
        Class destination = MainScreen.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }

    private void cameraButton() {
        dispatchTakePictureIntent();
    }

}