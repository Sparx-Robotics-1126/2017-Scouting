package com.sparx1126.steamworks;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ToggleButton;
import android.widget.LinearLayout;

import com.sparx1126.steamworks.components.Utility;

import org.gosparx.scouting.aerialassist.BenchmarkingData;
import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.TeamData;
import org.gosparx.scouting.aerialassist.dto.Event;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;
import org.gosparx.scouting.aerialassist.networking.NetworkCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BenchmarkScreen extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private Utility utility;
    private BenchmarkingData currentData;
    private List<String> teamsList;
    private SharedPreferences settings;
    private AutoCompleteTextView driveSystem;
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
    private RadioButton radioGearNone;
    private RadioButton radioBallHopper;
    private RadioButton radioBallFloor;
    private RadioButton radioBallHuman;
    private RadioButton radioBallNone;
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
    private RadioButton radioPreferredPlacesScaleNone;
    private EditText autoAbilitiesBench;
    private EditText commentsBench;
    private EditText teamNumber;
    private ToggleButton hasActiveGearSystemButton;
    // new
    private  LinearLayout theAllKnower;
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

    private Map<String, String> eventNamesToKey;

    private static final int REQUEST_TAKE_PHOTO = 1;
    private String[] driveTypes = {"Swerve", "Mechanum", "Tank Treads",
            "8 wheel traction drive", "6 wheel traction drive", "4 wheel traction drive",
            "8 wheel omni drive", "6 wheel omni drive", "4 wheel omni drive",
            "8 wheel traction + omni drive", "6 wheel traction + omni drive", "4 wheel traction + omni drive",
            "Mechanum traction hyrbid", "kiwi", "West coast"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmark_screen);

        dbHelper = DatabaseHelper.getInstance(this);
        utility = Utility.getInstance();

        ImageButton home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageButton cameraButton = (ImageButton) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        teamsList = new ArrayList<>();

        eventNamesToKey = new HashMap<>();
        driveSystem = (AutoCompleteTextView) findViewById(R.id.drivesSystem);
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
        radioGearNone = (RadioButton) findViewById(R.id.radioGearNone);
        radioBallHopper = (RadioButton) findViewById(R.id.radioBallHopper);
        radioBallFloor = (RadioButton) findViewById(R.id.radioBallFloor);
        radioBallHuman = (RadioButton) findViewById(R.id.radioBallHuman);
        radioBallNone = (RadioButton) findViewById(R.id.radioBallNone);
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
        radioPreferredPlacesScaleNone = (RadioButton) findViewById(R.id.radioPreferredScaleNone);
        autoAbilitiesBench = (EditText) findViewById(R.id.autoAbilitiesBench);
        commentsBench = (EditText) findViewById(R.id.commentsBench);
        Button submitBenchmark = (Button) findViewById(R.id.submitBenchmark);
        submitBenchmark.setOnClickListener(submitButtonClicked);

        teamNumber = (EditText) findViewById(R.id.teamNumber);
        teamNumber.addTextChangedListener(teamTextEntered);


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
        theAllKnower = (LinearLayout) findViewById(R.id.theAllKnower);
        theAllKnower.setVisibility(View.INVISIBLE);

        hasActiveGearSystemButton = (ToggleButton) findViewById(R.id.hasActiveGearSystemButton);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,driveTypes);
        driveSystem.setAdapter(adapter);
        driveSystem.setThreshold(0);
      
        setupTeamList();

        // <o/  D
        //  |   A
        // / \  B

        //restorePreferences();
        //ActionBar bar = getSupportActionBar();
        /*if(bar != null) {
            bar.setTitle(getString(R.string.benchmark_title) + String.valueOf(currentData.getTeamNumber()));
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(currentData != null) {
            saveData();
        }
    }

    private void restorePreferences() {
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
        hasActiveGearSystemButton.setChecked(currentData.isHasActiveGearSystemButton());
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
                case "radioGearNone":
                    radioGearNone.setChecked(true);
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
                case "radioBallNone":
                    radioBallNone.setChecked(true);
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
                case "radioPreferredPlacesScaleNone":
                    radioPreferredPlacesScaleNone.setChecked(true);
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
        else if(radioGearNone.isChecked()) {
            currentData.setRadioPreferredGear("radioGearNone");
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
        else if(radioBallNone.isChecked()) {
            currentData.setPickupBallPreferredBenchInput("radioBallNone");
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
            currentData.setPreferredPlacesScaleInput("radioPreferredPlacesScaleCenter");
        }
        else if(radioPreferredPlacesScaleLeft.isChecked()) {
            currentData.setPreferredPlacesScaleInput("radioPreferredPlacesScaleLeft");
        }
        else if(radioPreferredPlacesScaleNone.isChecked()) {
            currentData.setPreferredPlacesScaleInput("radioPreferredPlacesScaleNone");
        }

        currentData.setAutoAbilitiesBench(autoAbilitiesBench.getText().toString());
        currentData.setCommentsBench(commentsBench.getText().toString());
        currentData.setBenchmarkWasDoneButton(benchmarkWasDoneButton.isChecked());
        currentData.setHasActiveGearSystemButton(hasActiveGearSystemButton.isChecked());

        if(dbHelper.doesBenchmarkingDataExist(currentData)) {
            dbHelper.updateBenchmarkingData(currentData);
        }
        else {
            dbHelper.createBenchmarkingData(currentData);
        }
    }

    private final View.OnClickListener highGoalButtonClicked =  new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            hideHighGoal();
        }
    };

    private void hideHighGoal(){
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

    private void hideGear(){
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

    private void lowGoalHide(){
        int visibility = View.GONE;
        if(abilityToShootLowGoalBenchButton.isChecked()){
            visibility = View.VISIBLE;
        }
        lowGoalCycleTimeLinear.setVisibility(visibility);
        lowGoalNumberOfCyclesLinear.setVisibility(visibility);
    }

    private final TextWatcher teamTextEntered = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if(teamsList.contains(teamNumber.getText().toString())){
                theAllKnower.setVisibility(View.VISIBLE);
                TeamData.setTeamData(getTeamNumber(), settings.getString(getString(R.string.pref_event), ""), settings.getString(getString(R.string.pref_scouter), ""));
                TeamData teamData = TeamData.getCurrentTeam();
                currentData = teamData.getBenchmarkingData();
                restorePreferences();
            }
            else{
                theAllKnower.setVisibility(View.INVISIBLE);
            }
        }
    };


    private int getTeamNumber() {
        int value = 0;
        String textEntered = teamNumber.getText().toString();
        if (!textEntered.isEmpty()) {
            value = Integer.parseInt(textEntered);
        }
        return value;
    }


    private final View.OnClickListener canScaleButtonClicked =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            scaleHide();
        }
    };

    private void scaleHide(){
        int visibility = View.GONE;
        if(abilityScaleBenchButton.isChecked()){
            visibility = View.VISIBLE;
        }
        placesCanScaleFromLinear.setVisibility(visibility);
        prefPlaceToScaleLinear.setVisibility(visibility);
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
        // Create an rotate_indefinetly file name
        String imageFileName = String.valueOf(currentData.getTeamNumber() + "Robot");
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    private final View.OnClickListener submitButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveData();
            if(benchmarkWasDoneButton.isChecked() &&  isPictures()) {
                utility.uploadBenchmarkingData(BenchmarkScreen.this, false);
                utility.uploadPictures(BenchmarkScreen.this, false);
            }
            else {
                utility.alertUser(BenchmarkScreen.this, getString(R.string.benchmark_not_done), getString(R.string.check_submit_buttom)).show();
            }
        }
    };

    private void setupTeamList() {
        if(teamsList != null){
            teamsList.clear();
        }
        String event_key = settings.getString(getResources().getString(R.string.pref_event_key), "");
        try (Cursor teamCursor = dbHelper.createTeamCursor(dbHelper.getEvent(event_key))) {
            while (teamCursor.moveToNext()) {
                String teamNumber = teamCursor.getString(teamCursor.getColumnIndex(DatabaseHelper.TABLE_TEAMS_TEAM_NUMBER));
                teamsList.add(teamNumber);

            }
        }
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(getResources().getString(R.string.pref_number_teams), TextUtils.join(",", teamsList));
        editor.apply();
        //teamNumberChecker();
    }

    private boolean isPictures() {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir == null)
            throw new AssertionError("Cannot read " + Environment.DIRECTORY_PICTURES);
        String path = storageDir.getAbsolutePath();

        File directory = new File(path);
        File[] files = directory.listFiles();
        boolean found = false;
        for (int index = 0; index < files.length; index++) {
            String fileName = files[index].getName();
            if (fileName.contains(currentData.getTeamNumber() + "Robot")) {
                found = true;
                break;
            }
        }
        return found;
    }
}