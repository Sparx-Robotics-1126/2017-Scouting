package com.sparx1126.steamworks;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import com.sparx1126.steamworks.components.Utility;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.ScoutingData;
import org.gosparx.scouting.aerialassist.TeamData;
import org.gosparx.scouting.aerialassist.dto.Match;

import java.util.HashMap;
import java.util.Map;

import static com.sparx1126.steamworks.R.layout.scouting_screen;

public class ScoutingScreen extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private Utility utility;
    private ScoutingData scoutingBeingEntered;
    private Map<String, String> matchMap;
    private SharedPreferences settings;

    private EditText qualMatchNumber;
    private ToggleButton crossedBaseLineAutoInput;
    private EditText hoppersDumpedAutoInput;
    private CheckBox putGearLeftAuto;
    private CheckBox putGearCenterAuto;
    private CheckBox putGearRightAuto;
    private RadioButton scoresHighDuringAuto;
    private RadioButton scoresLowDuringAuto;
    private RadioButton doesntScoreDuringAuto;
    private EditText gearsScoredTeleInput;
    private EditText numberOfGearsFromFloorInput;
    private EditText numberOfGearsFromHumanInput;
    private EditText numberOfBallsScoredHighGoalInput;
    private EditText timesCollectedFromHumanInput;
    private EditText timesCollectedFromHopperInput;
    private EditText timesCollectedFromFloorInput;
    private EditText fuelScoredLowGoalCycleInput;
    private RadioButton highGoalAccuracyScoutPoor;
    private RadioButton highGoalAccuracyScoutOk;
    private RadioButton highGoalAccuracyScoutGreat;
    private ToggleButton didScaleInput;
    private RadioButton scaledLeft;
    private RadioButton scaledCenter;
    private RadioButton scaledRight;
    private EditText scoutingComments;
    private ToggleButton matchScoutedInput;

    private LinearLayout allKnowingLinear;
    private int teamNumber = 0;

    //   Zzzzz  |\      _,,,--,,_
    //          /,`.-'`'   ._  \-;;,_
    //         |,4-  ) )_   .;.(  `'-'
    //  doggo '---''(_/._)-'(_\_)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(scouting_screen);

        dbHelper = DatabaseHelper.getInstance(this);
        utility = Utility.getInstance();

        ImageButton home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);
        matchMap  = new HashMap<>();

        qualMatchNumber = (EditText) findViewById(R.id.qualMatchEdit);
        qualMatchNumber.addTextChangedListener(matchTextEntered);
        crossedBaseLineAutoInput = (ToggleButton) findViewById(R.id.crossedBaseLineAutoInput);
        hoppersDumpedAutoInput = (EditText) findViewById(R.id.hoppersDumpedAutoInput);
        putGearLeftAuto = (CheckBox) findViewById(R.id.putGearLeftAuto);
        putGearCenterAuto = (CheckBox) findViewById(R.id.putGearCenterAuto);
        putGearRightAuto = (CheckBox) findViewById(R.id.putGearRightAuto);
        scoresHighDuringAuto = (RadioButton) findViewById(R.id.scoresHighAuto);
        scoresLowDuringAuto = (RadioButton) findViewById(R.id.scoresLowAuto);
        doesntScoreDuringAuto = (RadioButton) findViewById(R.id.doesntScoreAuto);
        gearsScoredTeleInput = (EditText) findViewById(R.id.gearsScoredTeleInput).findViewById(R.id.edit_text);
        numberOfGearsFromFloorInput = (EditText) findViewById(R.id.numberOfGearsFromFloorInput).findViewById(R.id.edit_text);
        numberOfGearsFromHumanInput = (EditText) findViewById(R.id.numberOfGearsFromHumanInput).findViewById(R.id.edit_text);
        numberOfBallsScoredHighGoalInput = (EditText) findViewById(R.id.numberOfBallsScoredHighGoalInput).findViewById(R.id.edit_text);
        timesCollectedFromHumanInput = (EditText) findViewById(R.id.timesCollectedFromHumanInput).findViewById(R.id.edit_text);
        timesCollectedFromHopperInput = (EditText) findViewById(R.id.timesCollectedFromHopperInput).findViewById(R.id.edit_text);
        timesCollectedFromFloorInput = (EditText) findViewById(R.id.timesCollectedFromFloorInput).findViewById(R.id.edit_text);
        fuelScoredLowGoalCycleInput = (EditText) findViewById(R.id.fuelScoredLowGoalCycleInput).findViewById(R.id.edit_text);
        highGoalAccuracyScoutPoor = (RadioButton) findViewById(R.id.highGoalAccuracyScoutPoor);
        highGoalAccuracyScoutOk = (RadioButton) findViewById(R.id.highGoalAccuracyScoutOk);
        highGoalAccuracyScoutGreat = (RadioButton) findViewById(R.id.highGoalAccuracyScoutGreat);
        didScaleInput = (ToggleButton) findViewById(R.id.didTheyScale);
        scaledLeft = (RadioButton) findViewById(R.id.scaledFromLeft);
        scaledCenter = (RadioButton) findViewById(R.id.scaledFromCenter);
        scaledRight = (RadioButton) findViewById(R.id.scaledFromRight);
        scoutingComments = (EditText) findViewById(R.id.commentsScouting);
        matchScoutedInput = (ToggleButton) findViewById(R.id.matchScouted);
        Button submitScouting = (Button) findViewById(R.id.submitScouting);
        submitScouting.setOnClickListener(submitButtonClicked);
        allKnowingLinear = (LinearLayout) findViewById(R.id.allKnowingLinear);
        allKnowingLinear.setVisibility(View.INVISIBLE);


        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.setTitle(getString(R.string.scouting_title));
        }

        setupMatchList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
        if((scoutingBeingEntered != null) && scoutingBeingEntered.isMatchScouted()) {
            TeamData.setTeamData(teamNumber, settings.getString(getString(R.string.pref_event), ""));
            TeamData teamData = TeamData.getCurrentTeam();
            teamData.setStudent(settings.getString(getString(R.string.pref_scouter), ""));
            teamData.addScoutingData(scoutingBeingEntered);
            dbHelper.createScoutingData(scoutingBeingEntered);
        }
    }

    private void saveData() {
        if(scoutingBeingEntered == null) {
            String key = String.valueOf(teamNumber) + "_" + utility.getEpoch();
            scoutingBeingEntered = new ScoutingData(key, teamNumber, settings.getString(getString(R.string.pref_event), ""), settings.getString(getString(R.string.pref_scouter), ""));
        }
        scoutingBeingEntered.setCrossedBaseline(crossedBaseLineAutoInput.isChecked());
        String valueAsSring = hoppersDumpedAutoInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            int value = Integer.parseInt(valueAsSring);
            if (value == Integer.MAX_VALUE) value = 0;
            scoutingBeingEntered.setHoppersDumped(value);
        }
        scoutingBeingEntered.setGearScoredLeftAuto(putGearLeftAuto.isChecked());
        scoutingBeingEntered.setGearScoredCenterAuto(putGearCenterAuto.isChecked());
        scoutingBeingEntered.setGearScoredRightAuto(putGearRightAuto.isChecked());
        if (scoresHighDuringAuto.isChecked()) {
            scoutingBeingEntered.setAutoShooting("scoresHighAuto");
        } else if (scoresLowDuringAuto.isChecked()) {
            scoutingBeingEntered.setAutoShooting("scoresLowAuto");
        } else if (doesntScoreDuringAuto.isChecked()) {
            scoutingBeingEntered.setAutoShooting("doesntScoreAuto");
        }
        valueAsSring = gearsScoredTeleInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            int value = Integer.parseInt(valueAsSring);
            if (value == Integer.MAX_VALUE) value = 0;
            scoutingBeingEntered.setGearsScored(value);
        }
        valueAsSring = numberOfGearsFromFloorInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            int value = Integer.parseInt(valueAsSring);
            if (value == Integer.MAX_VALUE) value = 0;
            scoutingBeingEntered.setGearsCollectedFromFloor(value);
        }
        valueAsSring = numberOfGearsFromHumanInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            int value = Integer.parseInt(valueAsSring);
            if (value == Integer.MAX_VALUE) value = 0;
            scoutingBeingEntered.setGearsFromHuman(value);
        }
        valueAsSring = numberOfBallsScoredHighGoalInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            int value = Integer.parseInt(valueAsSring);
            if (value == Integer.MAX_VALUE) value = 0;
            scoutingBeingEntered.setBallsInHighCycle(value);
        }
        valueAsSring = timesCollectedFromHumanInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            int value = Integer.parseInt(valueAsSring);
            if (value == Integer.MAX_VALUE) value = 0;
            scoutingBeingEntered.setBallsFromHuman(value);
        }
        valueAsSring = timesCollectedFromHopperInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            int value = Integer.parseInt(valueAsSring);
            if (value == Integer.MAX_VALUE) value = 0;
            scoutingBeingEntered.setBallsFromHopper(value);
        }
        valueAsSring = timesCollectedFromFloorInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            int value = Integer.parseInt(valueAsSring);
            if (value == Integer.MAX_VALUE) value = 0;
            scoutingBeingEntered.setBallsFromFloor(value);
        }
        valueAsSring = fuelScoredLowGoalCycleInput.getText().toString();
        if (!valueAsSring.isEmpty()) {
            int value = Integer.parseInt(valueAsSring);
            if (value == Integer.MAX_VALUE) value = 0;
            scoutingBeingEntered.setFuelInLowCycle(value);
        }
        if (highGoalAccuracyScoutPoor.isChecked()) {
            scoutingBeingEntered.setHighGoalAccuracy("highGoalAccuracyScoutPoor");
        } else if (highGoalAccuracyScoutOk.isChecked()) {
            scoutingBeingEntered.setHighGoalAccuracy("highGoalAccuracyScoutOk");
        } else if (highGoalAccuracyScoutGreat.isChecked()) {
            scoutingBeingEntered.setHighGoalAccuracy("highGoalAccuracyScoutGreat");
        }
        scoutingBeingEntered.setDidScale(didScaleInput.isChecked());
        if (scaledLeft.isChecked()) {
            scoutingBeingEntered.setWhereScaled("scaledLeft");
        } else if (scaledCenter.isChecked()) {
            scoutingBeingEntered.setWhereScaled("scaledCenter");
        } else if (scaledRight.isChecked()) {
            scoutingBeingEntered.setWhereScaled("scaledRight");
        }
        scoutingBeingEntered.setScoutingComments(scoutingComments.getText().toString());
        scoutingBeingEntered.setMatchScouted(matchScoutedInput.isChecked());
    }

    private void setupMatchList() {
        matchMap.clear();
        String event_key = settings.getString(getResources().getString(R.string.pref_event_key), "");
        try (Cursor matchCursor = dbHelper.createMatchCursor(dbHelper.getEvent(event_key))) {
            while (matchCursor.moveToNext()) {
                String compLevel = matchCursor.getString(matchCursor.getColumnIndex(DatabaseHelper.TABLE_MATCHES_COMP_LEVEL));
                if(compLevel.contentEquals("qm")) {
                    String matchNumber = matchCursor.getString(matchCursor.getColumnIndex(DatabaseHelper.TABLE_MATCHES_MATCH_NUMBER));
                    String matchKey = matchCursor.getString(matchCursor.getColumnIndex(DatabaseHelper.TABLE_MATCHES_KEY));
                    matchMap.put(matchNumber, matchKey);
                }
            }
        }
    }

    private final TextWatcher matchTextEntered = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if(matchMap.containsKey(qualMatchNumber.getText().toString())){
                int teamIndex = settings.getInt(getString(R.string.team_selected), Integer.MAX_VALUE);
                boolean redAlliance = settings.getBoolean(getString(R.string.red_alliance), true);
                allKnowingLinear.setVisibility(View.VISIBLE);
                String key = matchMap.get(qualMatchNumber.getText().toString());
                Match match = dbHelper.getMatch(key);
                String teamId = "";
                if(redAlliance){
                    teamId = match.getAlliances().getRed().getTeams().get(teamIndex);
                }
                else if(!redAlliance){
                    teamId = match.getAlliances().getBlue().getTeams().get(teamIndex);
                }
                teamNumber = Integer.parseInt(teamId.replace("frc",""));

                ActionBar bar = getSupportActionBar();
                if(bar != null) {
                    bar.setTitle(getString(R.string.scouting_title) + " - " + String.valueOf(teamNumber));
                }
            }
            else{
                allKnowingLinear.setVisibility(View.INVISIBLE);
            }
        }
    };

    private void clearData() {
        crossedBaseLineAutoInput.setChecked(false);
        hoppersDumpedAutoInput.setText("");
        putGearLeftAuto.setChecked(false);
        putGearCenterAuto.setChecked(false);
        putGearRightAuto.setChecked(false);
        scoresHighDuringAuto.setChecked(false);
        scoresLowDuringAuto.setChecked(false);
        doesntScoreDuringAuto.setChecked(false);
        gearsScoredTeleInput.setText("0");
        numberOfGearsFromFloorInput.setText("0");
        numberOfGearsFromHumanInput.setText("0");
        numberOfBallsScoredHighGoalInput.setText("0");
        timesCollectedFromHumanInput.setText("0");
        timesCollectedFromHopperInput.setText("0");
        timesCollectedFromFloorInput.setText("0");
        fuelScoredLowGoalCycleInput.setText("0");
        highGoalAccuracyScoutPoor.setChecked(false);
        highGoalAccuracyScoutOk.setChecked(false);
        highGoalAccuracyScoutGreat.setChecked(false);
        didScaleInput.setChecked(false);
        scaledLeft.setChecked(false);
        scaledCenter.setChecked(false);
        scaledRight.setChecked(false);
        scoutingComments.setText("");
        matchScoutedInput.setChecked(false);
    }

  private final View.OnClickListener submitButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            saveData();
            if((scoutingBeingEntered != null) && scoutingBeingEntered.isMatchScouted() && !scoutingBeingEntered.getScoutingComments().isEmpty()) {
                TeamData.setTeamData(teamNumber, settings.getString(getString(R.string.pref_event), ""));
                TeamData teamData = TeamData.getCurrentTeam();
                teamData.setStudent(settings.getString(getString(R.string.pref_scouter), ""));
                teamData.addScoutingData(scoutingBeingEntered);
                dbHelper.createScoutingData(scoutingBeingEntered);
                utility.uploadScoutingData(ScoutingScreen.this, false);
                clearData();
            }
            else if ((scoutingBeingEntered != null) && !scoutingBeingEntered.isMatchScouted()) {
                utility.alertUser(ScoutingScreen.this, getString(R.string.scouting_not_done), getString(R.string.check_submit_buttom)).show();
            }
            else if ((scoutingBeingEntered != null) && scoutingBeingEntered.getScoutingComments().isEmpty()) {
                utility.alertUser(ScoutingScreen.this, getString(R.string.scouting_not_done), getString(R.string.comments_required)).show();
            }
        }
    };
}