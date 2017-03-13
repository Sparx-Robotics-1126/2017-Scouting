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
import android.widget.ToggleButton;
import android.widget.LinearLayout;

import com.sparx1126.steamworks.components.Utility;

import org.gosparx.scouting.aerialassist.BenchmarkingData;
import org.gosparx.scouting.aerialassist.TeamData;

import java.io.File;
import java.io.IOException;

public class BenchmarkScreen extends AppCompatActivity {
    private static final int REQUEST_TAKE_PHOTO = 1;
    private TeamData teamData;
    private BenchmarkingData currentData;
    private Utility utility;

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
    private EditText maximumBallCapacityBenchInput;
    private ToggleButton canScoreGearsBenchButton;
    private ToggleButton pickupGearFloorBenchButton;
    private ToggleButton pickupGearRetrievalBenchButton;
    private ToggleButton benchmarkWasDoneButton;
    private RadioButton radioFloor;
    private RadioButton radioZone;
    private CheckBox canGearLeftBench;
    private CheckBox canGearCenterBench;
    private CheckBox canGearRightBench;
    private RadioButton radioGearRight;
    private RadioButton radioGearCenter;
    private RadioButton radioGearLeft;
    private RadioButton radioBallHopper;
    private RadioButton radioBallFloor;
    private RadioButton radioBallHuman;
    private EditText cycleTimeGearsBenchInput;
    private ToggleButton abilityToShootLowGoalBenchButton;
    private EditText cycleTimeLowBenchInput;
    private EditText cycleNumberLowBenchInput;
    private ToggleButton abilityScaleBenchButton;
    private CheckBox canScaleLeftBench;
    private CheckBox canScaleRightBench;
    private CheckBox canScaleCenterBench;
    private RadioButton radioPreferredPlacesScaleRight;
    private RadioButton radioPreferredPlacesScaleCenter;
    private RadioButton radioPreferredPlacesScaleLeft;
    private EditText autoAbilitiesBench;
    private EditText commentsBench;
    private Button submitBenchmark;
    // new
    //High Goal related Linears
    private LinearLayout typeOfShooterLinear;
    private LinearLayout ballsPerSecondLinear;
    private LinearLayout ballsPerCycleLinear;
    private LinearLayout cycleTimeLinear;
    private LinearLayout maxShootingRangeLinear;
    private LinearLayout prefPlaceToShootLinear;
    private LinearLayout accuracyHighGoalLinear;
    //Gear related Linears
    private LinearLayout whereCanScoreGearsLinear;
    private LinearLayout prefScoringPlaceLinear;
    private LinearLayout gearCycleTimeLinear;
    //Low Goal related Linears
    private LinearLayout lowGoalCycleTimeLinear;
    private LinearLayout lowGoalNumberOfCyclesLinear;
    //Scaling related Linear
    private LinearLayout placesCanScaleFromLinear;
    private LinearLayout prefPlaceToScaleLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmark_screen);

        ImageButton home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        teamData = TeamData.getCurrentTeam();
        currentData = teamData.getBenchmarkingData();
        utility = Utility.getInstance();
        ImageButton cameraButton = (ImageButton) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        driveSystem = (EditText) findViewById(R.id.drivesSystem);
        drivesSpeed = (EditText) findViewById(R.id.drivesSpeed);
        canPlayDefenseBenchButton = (ToggleButton) findViewById(R.id.canPlayDefenseBenchButton);
        abilityToShootHighGoalBenchButton = (ToggleButton) findViewById(R.id.abilityToShootHighGoalBenchButton);
        abilityToShootHighGoalBenchButton.setOnClickListener(highGoalButtonClicked);
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
        maximumBallCapacityBenchInput = (EditText) findViewById(R.id.maximumBallCapacityBenchInput);
        canScoreGearsBenchButton = (ToggleButton) findViewById(R.id.canScoreGearsBenchButton);
        canScoreGearsBenchButton.setOnClickListener(canGearButtonClicked);
        pickupGearFloorBenchButton = (ToggleButton) findViewById(R.id.pickupGearFloorBenchButton);
        pickupGearRetrievalBenchButton = (ToggleButton) findViewById(R.id.pickupGearRetrievalBenchButton);
        radioFloor = (RadioButton) findViewById(R.id.radioFloor);
        radioZone = (RadioButton) findViewById(R.id.radioZone);
        canGearLeftBench = (CheckBox) findViewById(R.id.canGearLeftBench);
        canGearCenterBench = (CheckBox) findViewById(R.id.canGearCenterBench);
        canGearRightBench = (CheckBox) findViewById(R.id.canGearRightBench);
        radioGearRight = (RadioButton) findViewById(R.id.radioGearRight);
        radioGearCenter = (RadioButton) findViewById(R.id.radioGearCenter);
        radioGearLeft = (RadioButton) findViewById(R.id.radioGearLeft);
        radioBallHopper = (RadioButton) findViewById(R.id.radioBallHopper);
        radioBallFloor = (RadioButton) findViewById(R.id.radioBallFloor);
        radioBallHuman = (RadioButton) findViewById(R.id.radioBallHuman);
        cycleTimeGearsBenchInput = (EditText) findViewById(R.id.cycleTimeGearsBenchInput);
        abilityToShootLowGoalBenchButton = (ToggleButton) findViewById(R.id.abilityToShootLowGoalBenchButton);
        abilityToShootLowGoalBenchButton.setOnClickListener(lowGoalButtonClicked);
        cycleTimeLowBenchInput = (EditText) findViewById(R.id.cycleTimeLowBenchInput);
        cycleNumberLowBenchInput = (EditText) findViewById(R.id.cycleNumberLowBenchInput);
        abilityScaleBenchButton = (ToggleButton) findViewById(R.id.abilityScaleBenchButton);
        abilityScaleBenchButton.setOnClickListener(canScaleButtonClicked);
        canScaleRightBench = (CheckBox) findViewById(R.id.canScaleRightBench);
        canScaleCenterBench = (CheckBox) findViewById(R.id.canScaleCenterBench);
        canScaleLeftBench = (CheckBox) findViewById(R.id.canScaleLeftBench);
        radioPreferredPlacesScaleRight = (RadioButton) findViewById(R.id.radioPreferredScaleRight);
        radioPreferredPlacesScaleCenter = (RadioButton) findViewById(R.id.radioPreferredScaleCenter);
        radioPreferredPlacesScaleLeft = (RadioButton) findViewById(R.id.radioPreferredScaleLeft);
        autoAbilitiesBench = (EditText) findViewById(R.id.autoAbilitiesBench);
        commentsBench = (EditText) findViewById(R.id.commentsBench);
        submitBenchmark = (Button) findViewById(R.id.submitBenchmark);
        submitBenchmark.setOnClickListener(submitButtonClicked);
        typeOfShooterLinear = (LinearLayout) findViewById(R.id.typeOfShooterLinear);
        ballsPerSecondLinear = (LinearLayout) findViewById(R.id.ballsPerSecondLinear);
        ballsPerCycleLinear = (LinearLayout) findViewById(R.id.ballsPerCycleLinear);
        cycleTimeLinear = (LinearLayout) findViewById(R.id.cycleTimeLinear);
        maxShootingRangeLinear = (LinearLayout) findViewById(R.id.maxShootingRangeLinear);
        prefPlaceToShootLinear = (LinearLayout) findViewById(R.id.prefPlaceToShootLinear);
        accuracyHighGoalLinear = (LinearLayout) findViewById(R.id.accuracyHighGoalLinear);
        whereCanScoreGearsLinear = (LinearLayout) findViewById(R.id.whereCanScoreGearsLinear);
        prefScoringPlaceLinear = (LinearLayout) findViewById(R.id.prefScoringPlaceLinear);
        gearCycleTimeLinear = (LinearLayout) findViewById(R.id.gearCycleTimeLinear);
        lowGoalCycleTimeLinear = (LinearLayout) findViewById(R.id.lowGoalCycleTimeLinear);
        lowGoalNumberOfCyclesLinear = (LinearLayout) findViewById(R.id.lowGoalNumberOfCyclesLinear);
        placesCanScaleFromLinear = (LinearLayout) findViewById(R.id.placesCanScaleFromLinear);
        prefPlaceToScaleLinear = (LinearLayout) findViewById(R.id.prefPlaceToScaleLinear);
        benchmarkWasDoneButton = (ToggleButton) findViewById(R.id.benchmarkingWasDoneButton);

        // <o/  D
        //  |   A
        // / \  B

        updateScreen();
    }

    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    private void saveData() {
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
        valueAsSring = maximumBallCapacityBenchInput.getText().toString();
        if(!valueAsSring.isEmpty()) {
            currentData.setMaximumBallCapacityBenchInput(Integer.parseInt(valueAsSring));
        }
        currentData.setCanScoreGearsBenchButton(canScoreGearsBenchButton.isChecked());
        currentData.setPickupGearFloorBenchButton(pickupGearFloorBenchButton.isChecked());
        currentData.setPickupGearRetrievalBenchButton(pickupGearRetrievalBenchButton.isChecked());
        currentData.setBenchmarkWasDoneButton(benchmarkWasDoneButton.isChecked());
        if(radioFloor.isChecked()) {
            currentData.setPickupGearPreferred("radioFloor");
        }
        else if(radioZone.isChecked()) {
            currentData.setPickupGearPreferred("radioZone");
        }
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
        if(radioBallHopper.isChecked()) {
            currentData.setPickupBallPreferredBenchInput("radioBallHopper");
        }
        else if(radioBallFloor.isChecked()) {
            currentData.setPickupBallPreferredBenchInput("radioBallFloor");
        }
        else if(radioBallHuman.isChecked()) {
            currentData.setPickupBallPreferredBenchInput("radioBallHuman");
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
        currentData.setPlacesCanScaleCenter(canScaleCenterBench.isChecked());
        currentData.setPlacesCanScaleLeft(canScaleLeftBench.isChecked());
        currentData.setPlacesCanScaleRight(canScaleRightBench.isChecked());
        if(radioPreferredPlacesScaleRight.isChecked()) {
            currentData.setPreferredPlacesScaleInput("radioPreferredPlacesScaleRight");
        }
        else if(radioPreferredPlacesScaleCenter.isChecked()) {
            currentData.setPreferredPlacesScaleInput("radioPreferredPlacesScaleRight");
        }
        else if(radioPreferredPlacesScaleLeft.isChecked()) {
            currentData.setPreferredPlacesScaleInput("radioPreferredPlacesScaleRight");
        }
        currentData.setAutoAbilitiesBench(autoAbilitiesBench.getText().toString());
        currentData.setCommentsBench(commentsBench.getText().toString());
    }

    private void updateScreen() {
        utility.setStringIntoTextView(driveSystem, currentData.getDriveSystem());
        utility.setDoubleIntoTextView(drivesSpeed, currentData.getDrivesSpeed());
        canPlayDefenseBenchButton.setChecked(currentData.isCanPlayDefenseBenchButton());
        abilityToShootHighGoalBenchButton.setChecked(currentData.isAbilityToShootHighGoalBenchButton());
        utility.setStringIntoTextView(typeOfShooterBenchInput, currentData.getTypeOfShooterBenchInput());
        utility.setDoubleIntoTextView(ballsPerSecondBenchInput, currentData.getBallsPerSecondBenchInput());
        utility.setIntegerIntoTextView(ballsInCycleBenchInput, currentData.getBallsInCycleBenchInput());
        utility.setIntegerIntoTextView(cycleTimeHighBenchInput, currentData.getCycleTimeHighBenchInput());
        utility.setDoubleIntoTextView(shootingRangeBenchInput, currentData.getShootingRangeBenchInput());
        utility.setStringIntoTextView(preferredShootingLocationBenchInput, currentData.getPreferredShootingLocationBenchInput());
        utility.setDoubleIntoTextView(accuracyHighBenchInput, currentData.getAccuracyHighBenchInput());
        pickupBallHopperBenchButton.setChecked(currentData.isPickupBallHopperBenchButton());
        pickupBallFloorBenchButton.setChecked(currentData.isPickupBallFloorBenchButton());
        pickupBallHumanBenchButton.setChecked(currentData.isPickupBallHumanBenchButton());
        utility.setIntegerIntoTextView(maximumBallCapacityBenchInput, currentData.getMaximumBallCapacityBenchInput());
        canScoreGearsBenchButton.setChecked(currentData.isCanScoreGearsBenchButton());
        pickupGearFloorBenchButton.setChecked(currentData.isPickupGearFloorBenchButton());
        pickupGearRetrievalBenchButton.setChecked(currentData.isPickupGearRetrievalBenchButton());
        benchmarkWasDoneButton.setChecked(currentData.isBenchmarkingWasDoneButton());
        if(currentData.getPickupGearPreferred() != null) {
            switch(currentData.getPickupGearPreferred()) {
                case "radioFloor":
                    radioFloor.setChecked(true);
                    break;
                case "radioZone":
                    radioZone.setChecked(true);
                    break;
                default:
                    break;
            }
        }
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
        if(currentData.getPickupBallPreferredBenchInput() != null) {
            switch(currentData.getPickupBallPreferredBenchInput()) {
                case "radioBallHopper":
                    radioBallHopper.setChecked(true);
                    break;
                case "radioBallFloor":
                    radioBallFloor.setChecked(true);
                    break;
                case "radioBallHuman":
                    radioBallHuman.setChecked(true);
                    break;
                default:
                    break;
            }
        }
        utility.setIntegerIntoTextView(cycleTimeGearsBenchInput, currentData.getCycleTimeGearsBenchInput());
        abilityToShootLowGoalBenchButton.setChecked(currentData.isAbilityToShootLowGoalBenchButton());
        utility.setIntegerIntoTextView(cycleTimeLowBenchInput, currentData.getCycleTimeLowBenchInput());
        utility.setIntegerIntoTextView(cycleNumberLowBenchInput, currentData.getCycleNumberLowBenchInput());
        abilityScaleBenchButton.setChecked(currentData.isAbilityScaleBenchButton());
        canScaleCenterBench.setChecked(currentData.isPlacesCanScaleCenter());
        canScaleLeftBench.setChecked(currentData.isPlacesCanScaleLeft());
        canScaleRightBench.setChecked(currentData.isPlacesCanScaleRight());
        if(currentData.getPreferredPlacesScaleInput() != null) {
            switch(currentData.getPreferredPlacesScaleInput()) {
                case "radioPreferredPlacesScaleRight":
                    radioPreferredPlacesScaleRight.setChecked(true);
                    break;
                case "radioPreferredPlacesScaleCenter":
                    radioPreferredPlacesScaleCenter.setChecked(true);
                    break;
                case "radioPreferredPlacesScaleLeft":
                    radioPreferredPlacesScaleLeft.setChecked(true);
                    break;
                default:
                    break;
            }
        }
        utility.setStringIntoTextView(autoAbilitiesBench, currentData.getAutoAbilitiesBench());
        utility.setStringIntoTextView(commentsBench, currentData.getCommentsBench());
        hideHighGoal();
        hideGear();
        lowGoalHide();
        scaleHide();
    }

    private final View.OnClickListener highGoalButtonClicked =  new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            hideHighGoal();
        }
    };


    public void hideHighGoal(){
        int visibility = View.GONE;
        if(abilityToShootHighGoalBenchButton.isChecked()){
            visibility = View.VISIBLE;
        }
        typeOfShooterLinear.setVisibility(visibility);
        ballsPerSecondLinear.setVisibility(visibility);
        ballsPerCycleLinear.setVisibility(visibility);
        cycleTimeLinear.setVisibility(visibility);
        maxShootingRangeLinear.setVisibility(visibility);
        prefPlaceToShootLinear.setVisibility(visibility);
        accuracyHighGoalLinear.setVisibility(visibility);
    }

    private final View.OnClickListener canGearButtonClicked =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideGear();
        }
    };

    public void hideGear(){
        int visibility = View.GONE;
        if(canScoreGearsBenchButton.isChecked()){
            visibility = View.VISIBLE;
        }
        whereCanScoreGearsLinear.setVisibility(visibility);
        prefScoringPlaceLinear.setVisibility(visibility);
        gearCycleTimeLinear.setVisibility(visibility);
    }

    private final View.OnClickListener lowGoalButtonClicked =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            lowGoalHide();
        }
    };

    public void lowGoalHide(){
        int visibility = View.GONE;
        if(abilityToShootLowGoalBenchButton.isChecked()){
            visibility = View.VISIBLE;
        }
        lowGoalCycleTimeLinear.setVisibility(visibility);
        lowGoalNumberOfCyclesLinear.setVisibility(visibility);
    }

    private final View.OnClickListener canScaleButtonClicked =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            scaleHide();
        }
    };

    public void scaleHide(){
        int visibility = View.GONE;
        if(abilityScaleBenchButton.isChecked()){
            visibility = View.VISIBLE;
        }
        placesCanScaleFromLinear.setVisibility(visibility);
        prefPlaceToScaleLinear.setVisibility(visibility);
    }

    private final View.OnClickListener submitButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveData();
            utility.uploadBenchmarkingData(BenchmarkScreen.this);
            utility.uploadPictures(BenchmarkScreen.this);
        }
    };

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            //    currentData.addPicturePath(photoFile.getAbsolutePath());
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
        // Create an rotate_indefinetly file name
        String imageFileName = String.valueOf(currentData.getTeamNumber() + "Robot");
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }
}