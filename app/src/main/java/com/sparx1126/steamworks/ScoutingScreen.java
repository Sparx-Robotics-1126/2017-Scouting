package com.sparx1126.steamworks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.gosparx.scouting.aerialassist.dto.ScoutingData;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;
import org.gosparx.scouting.aerialassist.networking.NetworkCallback;
import org.gosparx.scouting.aerialassist.networking.SparxPosting;

import static com.sparx1126.steamworks.R.layout.scouting_screen;
import static org.gosparx.scouting.aerialassist.networking.NetworkHelper.isNetworkAvailable;

public class ScoutingScreen extends AppCompatActivity {
    private ScoutingInfo currentInfo;
    private ScoutingData scoutingBeingEntered;

    private ToggleButton crossedBaseLineAutoInput;
    private EditText hoppersDumpedAutoInput;
    private CheckBox putGearLeftAuto;
    private CheckBox putGearCenterAuto;
    private CheckBox putGearRightAuto;
    private EditText gearsScoredTeleInput;
    private EditText gearsDeliveredInput;
    private EditText numberOfGearsFromFloorInput;
    private EditText numberOfGearsFromHumanInput;
    private RadioButton scoresHighNeverAuto;
    private RadioButton scoresHighSometimesAuto;
    private RadioButton scoresHighOftenAuto;
    private RadioButton scoresLowNeverAuto;
    private RadioButton scoresLowSometimesAuto;
    private RadioButton scoresLowOftenAuto;
    private EditText numberOfBallsScoredHighGoalInput;
    private EditText timesCollectedFromHumanInput;
    private EditText timesCollectedFromHopperInput;
    private EditText timesCollectedFromFloorInput;
    private EditText fuelScoredLowGoalCycleInput;
    private EditText numberOfLowGoalCyclesInput;
    private RadioButton highGoalAccuracyScoutPoor;
    private RadioButton highGoalAccuracyScoutOk;
    private RadioButton highGoalAccuracyScoutGreat;
    private ToggleButton didScaleInput;
    private EditText scaledFromWhereInput;
    private ToggleButton matchScoutedInput;
    private Button submitScouting;

    //   Zzzzz  |\      _,,,--,,_
    //          /,`.-'`'   ._  \-;;,_
    //         |,4-  ) )_   .;.(  `'-'
    //  doggo '---''(_/._)-'(_\_)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(scouting_screen);

        ImageButton home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        currentInfo = ScoutingInfo.getCurrentInfo();
        scoutingBeingEntered = currentInfo.getScoutingBeingEnteredData();

        crossedBaseLineAutoInput = (ToggleButton) findViewById(R.id.crossedBaseLineAutoInput);
        hoppersDumpedAutoInput = (EditText) findViewById(R.id.hoppersDumpedAutoInput);
        putGearLeftAuto = (CheckBox) findViewById(R.id.putGearLeftAuto);
        putGearCenterAuto = (CheckBox) findViewById(R.id.putGearCenterAuto);
        putGearRightAuto = (CheckBox) findViewById(R.id.putGearRightAuto);
        gearsScoredTeleInput = (EditText) findViewById(R.id.gearsScoredTeleInput).findViewById(R.id.edit_text);
        gearsDeliveredInput = (EditText) findViewById(R.id.gearsDeliveredInput).findViewById(R.id.edit_text);
        numberOfGearsFromFloorInput = (EditText) findViewById(R.id.numberOfGearsFromFloorInput).findViewById(R.id.edit_text);
        numberOfGearsFromHumanInput = (EditText) findViewById(R.id.numberOfGearsFromHumanInput).findViewById(R.id.edit_text);
        scoresHighNeverAuto = (RadioButton) findViewById(R.id.scoresHighNeverAuto);
        scoresHighSometimesAuto = (RadioButton) findViewById(R.id.scoresHighSometimesAuto);
        scoresHighOftenAuto = (RadioButton) findViewById(R.id.scoresHighOftenAuto);
        scoresLowNeverAuto = (RadioButton) findViewById(R.id.scoresLowNeverAuto);
        scoresLowSometimesAuto = (RadioButton) findViewById(R.id.scoresLowSometimesAuto);
        scoresLowOftenAuto = (RadioButton) findViewById(R.id.scoresLowOftenAuto);
        numberOfBallsScoredHighGoalInput = (EditText) findViewById(R.id.numberOfBallsScoredHighGoalInput).findViewById(R.id.edit_text);
        timesCollectedFromHumanInput = (EditText) findViewById(R.id.timesCollectedFromHumanInput).findViewById(R.id.edit_text);
        timesCollectedFromHopperInput = (EditText) findViewById(R.id.timesCollectedFromHopperInput).findViewById(R.id.edit_text);
        timesCollectedFromFloorInput = (EditText) findViewById(R.id.timesCollectedFromFloorInput).findViewById(R.id.edit_text);
        fuelScoredLowGoalCycleInput = (EditText) findViewById(R.id.fuelScoredLowGoalCycleInput).findViewById(R.id.edit_text);
        numberOfLowGoalCyclesInput = (EditText) findViewById(R.id.numberOfLowGoalCyclesInput).findViewById(R.id.edit_text);
        highGoalAccuracyScoutPoor = (RadioButton) findViewById(R.id.highGoalAccuracyScoutPoor);
        highGoalAccuracyScoutOk = (RadioButton) findViewById(R.id.highGoalAccuracyScoutOk);
        highGoalAccuracyScoutGreat = (RadioButton) findViewById(R.id.highGoalAccuracyScoutGreat);
        didScaleInput = (ToggleButton) findViewById(R.id.didTheyScale);
        scaledFromWhereInput = (EditText) findViewById(R.id.scaledFromWhereInput);
        matchScoutedInput = (ToggleButton) findViewById(R.id.matchScouted);
        matchScoutedInput.setOnClickListener(matchScoutedClicked);
        submitScouting = (Button) findViewById(R.id.submitScouting);
        submitScouting.setOnClickListener(submitScoutingClicked);

        updateScreen();
    }

    protected void onDestroy() {
        super.onDestroy();
        scoutingBeingEntered.setMatchScouted(matchScoutedInput.isChecked());
        scoutingBeingEntered.setCrossedBaseline(crossedBaseLineAutoInput.isChecked());
        String valueAsSring = hoppersDumpedAutoInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            scoutingBeingEntered.setHoppersDumped(Integer.parseInt(valueAsSring));
        }
        scoutingBeingEntered.setGearScoredLeftAuto(putGearLeftAuto.isChecked());
        scoutingBeingEntered.setGearScoredCenterAuto(putGearCenterAuto.isChecked());
        scoutingBeingEntered.setGearScoredRightAuto(putGearRightAuto.isChecked());
        valueAsSring = gearsScoredTeleInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            scoutingBeingEntered.setGearsScored(Integer.parseInt(valueAsSring));
        }
        valueAsSring = gearsDeliveredInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            scoutingBeingEntered.setGearsDelivered(Integer.parseInt(valueAsSring));
        }
        valueAsSring = numberOfGearsFromFloorInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            scoutingBeingEntered.setGearsCollectedFromFloor(Integer.parseInt(valueAsSring));
        }
        valueAsSring = numberOfGearsFromHumanInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            scoutingBeingEntered.setGearsFromHuman(Integer.parseInt(valueAsSring));
        }
        if (scoresHighNeverAuto.isChecked()) {
            scoutingBeingEntered.setScoresHighAuto("scoresHighNeverAuto");
        } else if (scoresHighSometimesAuto.isChecked()) {
            scoutingBeingEntered.setScoresHighAuto("scoresHighSometimesAuto");
        } else if (scoresHighOftenAuto.isChecked()) {
            scoutingBeingEntered.setScoresHighAuto("scoresHighOftenAuto");
        }
        if (scoresLowNeverAuto.isChecked()) {
            scoutingBeingEntered.setScoresLowAuto("scoresLowNeverAuto");
        } else if (scoresLowSometimesAuto.isChecked()) {
            scoutingBeingEntered.setScoresLowAuto("scoresLowSometimesAuto");
        } else if (scoresLowOftenAuto.isChecked()) {
            scoutingBeingEntered.setScoresLowAuto("scoresLowOftenAuto");
        }
        valueAsSring = numberOfBallsScoredHighGoalInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            scoutingBeingEntered.setBallsInHighCycle(Integer.parseInt(valueAsSring));
        }
        valueAsSring = timesCollectedFromHumanInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            scoutingBeingEntered.setBallsFromHuman(Integer.parseInt(valueAsSring));
        }
        valueAsSring = timesCollectedFromHopperInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            scoutingBeingEntered.setBallsFromHopper(Integer.parseInt(valueAsSring));
        }
        valueAsSring = timesCollectedFromFloorInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            scoutingBeingEntered.setBallsFromFloor(Integer.parseInt(valueAsSring));
        }
        valueAsSring = fuelScoredLowGoalCycleInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            scoutingBeingEntered.setFuelInLowCycle(Integer.parseInt(valueAsSring));
        }
        valueAsSring = numberOfLowGoalCyclesInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            scoutingBeingEntered.setNumberOfLowCycles(Integer.parseInt(valueAsSring));
        }
        if (highGoalAccuracyScoutPoor.isChecked()) {
            scoutingBeingEntered.setHighGoalAccuracy("highGoalAccuracyScoutPoor");
        } else if (highGoalAccuracyScoutOk.isChecked()) {
            scoutingBeingEntered.setHighGoalAccuracy("highGoalAccuracyScoutOk");
        } else if (highGoalAccuracyScoutGreat.isChecked()) {
            scoutingBeingEntered.setHighGoalAccuracy("highGoalAccuracyScoutGreat");
        }
        scoutingBeingEntered.setDidScale(didScaleInput.isChecked());
        scoutingBeingEntered.setWhereScaled(scaledFromWhereInput.getText().toString());
        if(matchScoutedInput.isChecked()) {
            currentInfo.addScoutingData();
        }
    }

    private void updateScreen() {
        crossedBaseLineAutoInput.setChecked(scoutingBeingEntered.isCrossedBaseline());
        if (scoutingBeingEntered.getHoppersDumped() != Integer.MAX_VALUE) {
            SetStringIntoTextView(hoppersDumpedAutoInput, String.valueOf(scoutingBeingEntered.getHoppersDumped()));
        }
        putGearLeftAuto.setChecked(scoutingBeingEntered.isGearScoredLeftAuto());
        putGearCenterAuto.setChecked(scoutingBeingEntered.isGearScoredCenterAuto());
        putGearRightAuto.setChecked(scoutingBeingEntered.isGearScoredRightAuto());
        if (scoutingBeingEntered.getGearsScored() != Integer.MAX_VALUE) {
            SetStringIntoTextView(gearsScoredTeleInput, String.valueOf(scoutingBeingEntered.getGearsScored()));
        }
        if (scoutingBeingEntered.getGearsDelivered() != Integer.MAX_VALUE) {
            SetStringIntoTextView(gearsDeliveredInput, String.valueOf(scoutingBeingEntered.getGearsDelivered()));
        }
        if (scoutingBeingEntered.getGearsCollectedFromFloor() != Integer.MAX_VALUE) {
            SetStringIntoTextView(numberOfGearsFromFloorInput, String.valueOf(scoutingBeingEntered.getGearsCollectedFromFloor()));
        }
        if (scoutingBeingEntered.getGearsFromHuman() != Integer.MAX_VALUE) {
            SetStringIntoTextView(numberOfGearsFromHumanInput, String.valueOf(scoutingBeingEntered.getGearsFromHuman()));
        }
        if (scoutingBeingEntered.getScoresHighAuto() != null) {
            switch (scoutingBeingEntered.getScoresHighAuto()) {
                case "scoresHighNeverAuto":
                    scoresHighNeverAuto.setChecked(true);
                    break;
                case "scoresHighSometimesAuto":
                    scoresHighSometimesAuto.setChecked(true);
                    break;
                case "scoresHighOftenAuto":
                    scoresHighOftenAuto.setChecked(true);
                    break;
                default:
                    break;
            }
        }
        if (scoutingBeingEntered.getScoresLowAuto() != null) {
            switch (scoutingBeingEntered.getScoresLowAuto()) {
                case "scoresLowNeverAuto":
                    scoresLowNeverAuto.setChecked(true);
                    break;
                case "scoresLowSometimesAuto":
                    scoresLowSometimesAuto.setChecked(true);
                    break;
                case "scoresLowOftenAuto":
                    scoresLowOftenAuto.setChecked(true);
                    break;
                default:
                    break;
            }
        }
        if (scoutingBeingEntered.getBallsInHighCycle() != Integer.MAX_VALUE) {
            SetStringIntoTextView(numberOfBallsScoredHighGoalInput, String.valueOf(scoutingBeingEntered.getBallsInHighCycle()));
        }
        if (scoutingBeingEntered.getBallsFromHuman() != Integer.MAX_VALUE) {
            SetStringIntoTextView(timesCollectedFromHumanInput, String.valueOf(scoutingBeingEntered.getBallsFromHuman()));
        }
        if (scoutingBeingEntered.getBallsFromHopper() != Integer.MAX_VALUE) {
            SetStringIntoTextView(timesCollectedFromHopperInput, String.valueOf(scoutingBeingEntered.getBallsFromHopper()));
        }
        if (scoutingBeingEntered.getBallsFromFloor() != Integer.MAX_VALUE) {
            SetStringIntoTextView(timesCollectedFromFloorInput, String.valueOf(scoutingBeingEntered.getBallsFromFloor()));
        }
        if (scoutingBeingEntered.getFuelInLowCycle() != Integer.MAX_VALUE) {
            SetStringIntoTextView(fuelScoredLowGoalCycleInput, String.valueOf(scoutingBeingEntered.getFuelInLowCycle()));
        }
        if (scoutingBeingEntered.getNumberOfLowCycles() != Integer.MAX_VALUE) {
            SetStringIntoTextView(numberOfLowGoalCyclesInput, String.valueOf(scoutingBeingEntered.getNumberOfLowCycles()));
        }
        if (scoutingBeingEntered.getHighGoalAccuracy() != null) {
            switch (scoutingBeingEntered.getHighGoalAccuracy()) {
                case "highGoalAccuracyScoutPoor":
                    highGoalAccuracyScoutPoor.setChecked(true);
                    break;
                case "highGoalAccuracyScoutOk":
                    highGoalAccuracyScoutOk.setChecked(true);
                    break;
                case "highGoalAccuracyScoutGreat":
                    highGoalAccuracyScoutGreat.setChecked(true);
                    break;
                default:
                    break;
            }
        }
        didScaleInput.setChecked(scoutingBeingEntered.isDidScale());
        SetStringIntoTextView(scaledFromWhereInput, scoutingBeingEntered.getWhereScaled());
    }

    void SetStringIntoTextView(TextView item, String _value){
        if((_value != null) && !_value.isEmpty()) {
            item.setText(_value);
        }
    }

    private final View.OnClickListener submitScoutingClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            uploadBenchmarkingData();
        }
    };

    private void uploadBenchmarkingData() {
        if (!isNetworkAvailable(this)) {
            alertUser("No Network", "The upload function is not available. Connect to a network and try again.").show();
        } else {
            final Dialog alert = createUploadDialog("Please wait while scouting data is uploaded...");
            alert.show();
            SparxPosting ss = SparxPosting.getInstance(this);
            ss.postAllScouting(new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    ScoutingScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            if (!success)
                                alertUser("Failure", "Did not successfully upload scouting data!").show();
                        }
                    });
                }
            });
        }
    }

    public AlertDialog alertUser(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    private AlertDialog createUploadDialog(String message) {
        return createPleaseWaitDialog(message, R.string.uploading_data);
    }

    private AlertDialog createPleaseWaitDialog(String message, int titleID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titleID);
        builder.setMessage(message);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BlueAlliance.getInstance(ScoutingScreen.this).cancelAll();
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    private final View.OnClickListener matchScoutedClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(matchScoutedInput.isChecked()) {
                currentInfo.addScoutingData();
            }
        }
    };
}