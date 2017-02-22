package com.sparx1126.steamworks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.gosparx.scouting.aerialassist.dto.BenchmarkingData;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BenchmarkScreen extends AppCompatActivity {
    private static final int REQUEST_TAKE_PHOTO  = 1;

    private ScoutingInfo currentInfo;
    private BenchmarkingData currentData;

    // new
    private EditText driveSystem;
    private EditText drivesSpeed;
    private ToggleButton canPlayDefenseBenchButton;
    private ToggleButton abilityToShootHighGoalBenchButton;
    private EditText typeOfShooterBenchInput;
    private EditText ballsPerSecondBenchInput;
    private EditText ballsInCycleBenchInput;
    private EditText cycleTimeHighBenchInput;
    private EditText shootingRangeBenchInput;
    private EditText preferredShootingLocationBenchInput;
    private EditText accuracyHighBenchInput;
    private ToggleButton pickupBallHopperBenchButton;
    private ToggleButton pickupBallFloorBenchButton;
    private ToggleButton pickupBallHumanBenchButton;
    private EditText pickupBallPreferredBenchInput;
    private EditText maximumBallCapacityBenchInput;
    private ToggleButton canScoreGearsBenchButton;
    private ToggleButton pickupGearFloorBenchButton;
    private ToggleButton pickupGearRetrievalBenchButton;
    private ToggleButton pickupGearPreferredBenchButton;
    private CheckBox canGearLeftBench;
    private CheckBox canGearCenterBench;
    private CheckBox canGearRightBench;
    private RadioButton radioGearRight;
    private RadioButton radioGearCenter;
    private RadioButton radioGearLeft;
    private EditText cycleTimeGearsBenchInput;
    private ToggleButton abilityToShootLowGoalBenchButton;
    private EditText cycleTimeLowBenchInput;
    private EditText cycleNumberLowBenchInput;
    private ToggleButton abilityScaleBenchButton;
    private EditText placesCanScaleBenchInput;
    private EditText preferredPlacesScaleInput;
    private EditText autoAbilitiesBench;
    private EditText commentsBench;
    private Button submitTeleopBenchmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmark);

        ImageButton home_auto = (ImageButton) findViewById(R.id.home_auto);
        home_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        currentInfo = ScoutingInfo.getCurrentInfo();
        currentData = currentInfo.getBenchmarkingData();
        Button cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraButton();
            }
        });

        driveSystem = (EditText) findViewById(R.id.drivesSystem);
        drivesSpeed = (EditText) findViewById(R.id.drivesSpeed);
        canPlayDefenseBenchButton = (ToggleButton) findViewById(R.id.canPlayDefenseBenchButton);
        abilityToShootHighGoalBenchButton = (ToggleButton) findViewById(R.id.abilityToShootHighGoalBenchButton);
        typeOfShooterBenchInput = (EditText) findViewById(R.id.typeOfShooterBenchInput);
        ballsPerSecondBenchInput = (EditText) findViewById(R.id.ballsPerSecondBenchInput);
        ballsInCycleBenchInput = (EditText) findViewById(R.id.ballsInCycleBenchInput);
        cycleTimeHighBenchInput = (EditText) findViewById(R.id.cycleTimeHighBenchInput);
        shootingRangeBenchInput = (EditText) findViewById(R.id.shootingRangeBenchInput);
        preferredShootingLocationBenchInput = (EditText) findViewById(R.id.preferredShootingLocationBenchInput);
        accuracyHighBenchInput = (EditText) findViewById(R.id.accuracyHighBenchInput);
        pickupBallHopperBenchButton = (ToggleButton) findViewById(R.id.pickupBallHopperBenchButton);
        pickupBallFloorBenchButton = (ToggleButton) findViewById(R.id.pickupBallFloorBenchButton);
        pickupBallHumanBenchButton = (ToggleButton) findViewById(R.id.pickupBallHumanBenchButton);
        pickupBallPreferredBenchInput = (EditText) findViewById(R.id.pickupBallPreferredBenchInput);
        maximumBallCapacityBenchInput = (EditText) findViewById(R.id.maximumBallCapacityBenchInput);
        canScoreGearsBenchButton = (ToggleButton) findViewById(R.id.canScoreGearsBenchButton);
        pickupGearFloorBenchButton = (ToggleButton) findViewById(R.id.pickupGearFloorBenchButton);
        pickupGearRetrievalBenchButton = (ToggleButton) findViewById(R.id.pickupGearRetrievalBenchButton);
        pickupGearPreferredBenchButton = (ToggleButton) findViewById(R.id.pickupGearPreferredBenchButton);
        canGearLeftBench = (CheckBox) findViewById(R.id.canGearLeftBench);
        canGearCenterBench = (CheckBox) findViewById(R.id.canGearCenterBench);
        canGearRightBench = (CheckBox) findViewById(R.id.canGearRightBench);
        radioGearRight = (RadioButton) findViewById(R.id.radioGearRight);
        radioGearCenter = (RadioButton) findViewById(R.id.radioGearCenter);
        radioGearLeft = (RadioButton) findViewById(R.id.radioGearLeft);
        cycleTimeGearsBenchInput = (EditText) findViewById(R.id.cycleTimeGearsBenchInput);
        abilityToShootLowGoalBenchButton = (ToggleButton) findViewById(R.id.abilityToShootLowGoalBenchButton);
        cycleTimeLowBenchInput = (EditText) findViewById(R.id.cycleTimeLowBenchInput);
        cycleNumberLowBenchInput = (EditText) findViewById(R.id.cycleNumberLowBenchInput);
        abilityScaleBenchButton = (ToggleButton) findViewById(R.id.abilityScaleBenchButton);
        placesCanScaleBenchInput = (EditText) findViewById(R.id.placesCanScaleBenchInput);
        preferredPlacesScaleInput = (EditText) findViewById(R.id.preferredPlacesScaleInput);
        autoAbilitiesBench = (EditText) findViewById(R.id.autoAbilitiesBench);
        commentsBench = (EditText) findViewById(R.id.commentsBench);
        submitTeleopBenchmark = (Button) findViewById(R.id.submitTeleopBenchmark);

        // <o/  D
        //  |   A
        // / \  B

        updateScreen();
    }

    protected void onDestroy() {
        super.onDestroy();
        currentData.setDriveSystem(driveSystem.getText().toString());
        String valueAsSring = drivesSpeed.getText().toString();
        if(!valueAsSring.isEmpty()) {
            currentData.setDrivesSpeed(Double.parseDouble(valueAsSring));
        }
        currentData.setCanPlayDefenseBenchButton(canPlayDefenseBenchButton.isChecked());
        currentData.setAbilityToShootHighGoalBenchButton(abilityToShootHighGoalBenchButton.isChecked());
        currentData.setTypeOfShooterBenchInput(typeOfShooterBenchInput.getText().toString());
        valueAsSring = ballsPerSecondBenchInput.getText().toString();
        if(!valueAsSring.isEmpty()) {
            currentData.setBallsPerSecondBenchInput(Double.parseDouble(valueAsSring));
        }
        valueAsSring = ballsInCycleBenchInput.getText().toString();
        if(!valueAsSring.isEmpty()) {
            currentData.setBallsInCycleBenchInput(Integer.parseInt(valueAsSring));
        }
        valueAsSring = cycleTimeHighBenchInput.getText().toString();
        if(!valueAsSring.isEmpty()) {
            currentData.setCycleTimeHighBenchInput(Integer.parseInt(valueAsSring));
        }
        valueAsSring = shootingRangeBenchInput.getText().toString();
        if(!valueAsSring.isEmpty()) {
            currentData.setShootingRangeBenchInput(Double.parseDouble(valueAsSring));
        }
        currentData.setPreferredShootingLocationBenchInput(preferredShootingLocationBenchInput.getText().toString());
        valueAsSring = accuracyHighBenchInput.getText().toString();
        if(!valueAsSring.isEmpty()) {
            currentData.setAccuracyHighBenchInput(Double.parseDouble(valueAsSring));
        }
        currentData.setPickupBallHopperBenchButton(pickupBallHopperBenchButton.isChecked());
        currentData.setPickupBallFloorBenchButton(pickupBallFloorBenchButton.isChecked());
        currentData.setPickupBallHumanBenchButton(pickupBallHumanBenchButton.isChecked());
        currentData.setPickupBallPreferredBenchInput(pickupBallPreferredBenchInput.getText().toString());
        valueAsSring = maximumBallCapacityBenchInput.getText().toString();
        if(!valueAsSring.isEmpty()) {
            currentData.setMaximumBallCapacityBenchInput(Integer.parseInt(valueAsSring));
        }
        currentData.setCanScoreGearsBenchButton(canScoreGearsBenchButton.isChecked());
        currentData.setPickupGearFloorBenchButton(pickupGearFloorBenchButton.isChecked());
        currentData.setPickupGearRetrievalBenchButton(pickupGearRetrievalBenchButton.isChecked());
        currentData.setPickupGearPreferredBenchButton(pickupGearPreferredBenchButton.isChecked());
        currentData.setCanGearLeftBench(canGearLeftBench.isChecked());
        currentData.setCanGearCenterBench(canGearCenterBench.isChecked());
        currentData.setCanGearRightBench(canGearRightBench.isChecked());
        if(radioGearRight.isChecked()) {
            currentData.setRadioPreferredGear("radioGearRight");
        }
        else if(radioGearCenter.isChecked()) {
            currentData.setRadioPreferredGear("radioGearCenter");
        }
        else if(radioGearLeft.isChecked()) {
            currentData.setRadioPreferredGear("radioGearLeft");
        }
        valueAsSring = cycleTimeGearsBenchInput.getText().toString();
        if(!valueAsSring.isEmpty()) {
            currentData.setCycleTimeGearsBenchInput(Integer.parseInt(valueAsSring));
        }
        currentData.setAbilityToShootLowGoalBenchButton(abilityToShootLowGoalBenchButton.isChecked());
        valueAsSring = cycleTimeLowBenchInput.getText().toString();
        if(!valueAsSring.isEmpty()) {
            currentData.setCycleTimeLowBenchInput(Integer.parseInt(valueAsSring));
        }
        valueAsSring = cycleNumberLowBenchInput.getText().toString();
        if(!valueAsSring.isEmpty()) {
            currentData.setCycleNumberLowBenchInput(Integer.parseInt(valueAsSring));
        }
        currentData.setAbilityScaleBenchButton(abilityScaleBenchButton.isChecked());
        currentData.setPlacesCanScaleBenchInput(placesCanScaleBenchInput.getText().toString());
        currentData.setPreferredPlacesScaleInput(preferredPlacesScaleInput.getText().toString());
        currentData.setAutoAbilitiesBench(autoAbilitiesBench.getText().toString());
        currentData.setCommentsBench(commentsBench.getText().toString());
    }

    void SetStringIntoTextView(TextView item, String _value){
        if((_value != null) && !_value.isEmpty()) {
            item.setText(_value);
        }
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

    private void cameraButton() {
        dispatchTakePictureIntent();
    }

    private void updateScreen() {
        SetStringIntoTextView(driveSystem, currentData.getDriveSystem());
        if(currentData.getDrivesSpeed() != Double.MAX_VALUE) {
            SetStringIntoTextView(drivesSpeed, String.valueOf(currentData.getDrivesSpeed()));
        }
        canPlayDefenseBenchButton.setChecked(currentData.isCanPlayDefenseBenchButton());
        abilityToShootHighGoalBenchButton.setChecked(currentData.isAbilityToShootHighGoalBenchButton());
        SetStringIntoTextView(typeOfShooterBenchInput, currentData.getTypeOfShooterBenchInput());
        if(currentData.getBallsPerSecondBenchInput() != Double.MAX_VALUE) {
            SetStringIntoTextView(ballsPerSecondBenchInput, String.valueOf(currentData.getBallsPerSecondBenchInput()));
        }
        if(currentData.getBallsInCycleBenchInput() != Integer.MAX_VALUE) {
            SetStringIntoTextView(ballsInCycleBenchInput, String.valueOf(currentData.getBallsInCycleBenchInput()));
        }
        if(currentData.getCycleTimeHighBenchInput() != Integer.MAX_VALUE) {
            SetStringIntoTextView(cycleTimeHighBenchInput, String.valueOf(currentData.getCycleTimeHighBenchInput()));
        }
        if(currentData.getShootingRangeBenchInput() != Double.MAX_VALUE) {
            SetStringIntoTextView(shootingRangeBenchInput, String.valueOf(currentData.getShootingRangeBenchInput()));
        }
        SetStringIntoTextView(preferredShootingLocationBenchInput, currentData.getPreferredShootingLocationBenchInput());
        if(currentData.getAccuracyHighBenchInput() != Double.MAX_VALUE) {
            SetStringIntoTextView(accuracyHighBenchInput, String.valueOf(currentData.getAccuracyHighBenchInput()));
        }
        pickupBallHopperBenchButton.setChecked(currentData.isPickupBallHopperBenchButton());
        pickupBallFloorBenchButton.setChecked(currentData.isPickupBallFloorBenchButton());
        pickupBallHumanBenchButton.setChecked(currentData.isPickupBallHumanBenchButton());
        SetStringIntoTextView(pickupBallPreferredBenchInput, currentData.getPickupBallPreferredBenchInput());
        if(currentData.getMaximumBallCapacityBenchInput() != Integer.MAX_VALUE) {
            SetStringIntoTextView(maximumBallCapacityBenchInput, String.valueOf(currentData.getMaximumBallCapacityBenchInput()));
        }
        canScoreGearsBenchButton.setChecked(currentData.isCanScoreGearsBenchButton());
        pickupGearFloorBenchButton.setChecked(currentData.isPickupGearFloorBenchButton());
        pickupGearRetrievalBenchButton.setChecked(currentData.isPickupGearRetrievalBenchButton());
        pickupGearPreferredBenchButton.setChecked(currentData.isPickupGearPreferredBenchButton());
        canGearLeftBench.setChecked(currentData.isCanGearLeftBench());
        canGearCenterBench.setChecked(currentData.isCanGearCenterBench());
        canGearRightBench.setChecked(currentData.isCanGearRightBench());
        if(currentData.getRadioPreferredGear() != null) {
            switch(currentData.getRadioPreferredGear()) {
                case "radioGearRight":
                    radioGearRight.setChecked(true);
                    break;
                case "radioGearCenter":
                    radioGearCenter.setChecked(true);
                    break;
                case "radioGearLeft":
                    radioGearLeft.setChecked(true);
                    break;
                default:
                    break;
            }
        }
        if(currentData.getCycleTimeGearsBenchInput() != Integer.MAX_VALUE) {
            SetStringIntoTextView(cycleTimeGearsBenchInput, String.valueOf(currentData.getCycleTimeGearsBenchInput()));
        }
        abilityToShootLowGoalBenchButton.setChecked(currentData.isAbilityToShootLowGoalBenchButton());
        if (currentData.getCycleTimeLowBenchInput() != Integer.MAX_VALUE) {
            SetStringIntoTextView(cycleTimeLowBenchInput, String.valueOf(currentData.getCycleTimeLowBenchInput()));
        }
        if(currentData.getCycleNumberLowBenchInput() != Integer.MAX_VALUE) {
            SetStringIntoTextView(cycleNumberLowBenchInput, String.valueOf(currentData.getCycleNumberLowBenchInput()));
        }
        abilityScaleBenchButton.setChecked(currentData.isAbilityScaleBenchButton());
        SetStringIntoTextView(placesCanScaleBenchInput, currentData.getPlacesCanScaleBenchInput());
        SetStringIntoTextView(preferredPlacesScaleInput, currentData.getPreferredPlacesScaleInput());
        SetStringIntoTextView(autoAbilitiesBench, currentData.getAutoAbilitiesBench());
        SetStringIntoTextView(commentsBench, currentData.getCommentsBench());
    }

}