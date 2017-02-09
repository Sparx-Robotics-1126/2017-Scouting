package com.sparx1126.steamworks;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import org.gosparx.scouting.aerialassist.dto.ScouterData;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

/**
 * Created by Amanda on 1/17/17.
 */

public class BenchmarkScreen extends AppCompatActivity {

    ImageButton home_auto;
    private ScoutingInfo currentInfo;

    // new
    private EditText driveSystem;
    private EditText drivesSpeed;
    private EditText maximumBallCapacity;
    private EditText typeOfShooter;
    private EditText ballsPerSecond;
    private EditText whereCanShoot;
    private EditText preferredShootingLocation;
    private EditText highGoalAccuracy;
    private EditText highGoalRating;
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
        System.out.println("Oh no");
        System.out.println(currentInfo.getEventKey());


        // <o/  D
        //  |   A
        // / \  B

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
                String accuracyHighGoalRatingString = highGoalRating.getEditableText().toString();
                if (!accuracyHighGoalRatingString.isEmpty()) {
                    int textEntered = Integer.getInteger(accuracyHighGoalRatingString);
                    currentInfo.getCurrentData().setHighGoalRating(textEntered);
                }
            }
        });


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
        /*
        String maximumBallCapacityString = maximumBallCapacity.getEditableText().toString();
                if (!maximumBallCapacityString.isEmpty()) {
                    float textEntered = Float.parseFloat(maximumBallCapacityString);
                    currentInfo.getCurrentData().setBallCapacity(textEntered);
                }
                */

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
                ScouterData currentData = currentInfo.getCurrentData();
                currentInfo.getCurrentData().setDriveSystemDescription(textEntered);
                System.out.println(currentInfo.getCurrentData().getDriveSystemDescription());
            }
        });
    }

    private void home_autoButtonClicked(View v) {
        Context context = BenchmarkScreen.this;
        Class destination = MainScreen.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }

}