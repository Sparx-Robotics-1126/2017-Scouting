//push was successful YAYAYAYAYAYAYAYAYAYAY
package com.sparx1126.steamworks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.sparx1126.steamworks.components.Utility;

import org.gosparx.scouting.aerialassist.BenchmarkingData;
import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.ScoutingData;
import org.gosparx.scouting.aerialassist.TeamData;
import org.gosparx.scouting.aerialassist.networking.NetworkCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.INVISIBLE;

public class MainScreen extends AppCompatActivity {
    private static final String TAG = "MainScreen";
    private DatabaseHelper dbHelper;
    private Utility utility;
    private AutoCompleteTextView scouterText;
    private String[] studentList;
    private Button benchmarkAutoButton;
    private Button scoutButton;
    private Button viewButton;
    private Button teamChecklistButon;
    private SharedPreferences settings;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.main_menu_music);

        dbHelper = DatabaseHelper.getInstance(this);
        utility = Utility.getInstance();

        scouterText = (AutoCompleteTextView) findViewById(R.id.scouterText);
        scouterText.addTextChangedListener(scouterTextEntered);
        studentList = getResources().getStringArray(R.array.students);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        scouterText.setAdapter(adapter);
        scouterText.setThreshold(1);

        benchmarkAutoButton = (Button) findViewById(R.id.benchmarkButton);
        benchmarkAutoButton.setOnClickListener(buttonClicked);
        benchmarkAutoButton.setVisibility(View.INVISIBLE);

        scoutButton = (Button) findViewById(R.id.scoutButton);
        scoutButton.setOnClickListener(buttonClicked);
        scoutButton.setVisibility(View.INVISIBLE);

        viewButton = (Button) findViewById(R.id.viewButton);
        viewButton.setOnClickListener(buttonClicked);
        viewButton.setVisibility(View.INVISIBLE);

        teamChecklistButon = (Button) findViewById(R.id.teamChecklist);
        teamChecklistButon.setOnClickListener(buttonClicked);
        teamChecklistButon.setVisibility(View.INVISIBLE);

        restorePreferences();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(settings.getInt("team selected", Integer.MAX_VALUE) == Integer.MAX_VALUE){
            Intent intent = new Intent(MainScreen.this, AdminScreen.class);
            startActivity(intent);
        }
        else {
            ActionBar bar = getSupportActionBar();

            if(bar != null) {
                bar.setTitle(getString(R.string.app_name) + " -  " + settings.getString(getResources().getString(R.string.pref_event), ""));
            }
            mediaPlayer.start();
            setupTeamList();
            setupMatchMap();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_updload_benchmarking:
                utility.uploadBenchmarkingData(this, true, false);
                return true;
            case R.id.menu_updload_pictures:
                utility.uploadPictures(this, true);
                return true;
            case R.id.menu_updload_scouting:
                utility.uploadScoutingData(this, true);
                return true;
            case R.id.refresh_benchmark_data:
                utility.downloadBenchmarkData(MainScreen.this, true, new NetworkCallback() {
                    @Override
                    public void handleFinishDownload(final boolean success) {
                    }
                });
                return true;
            case R.id.refresh_pictures:
                utility.downloadPictures(MainScreen.this, true, new NetworkCallback() {
                    @Override
                    public void handleFinishDownload(final boolean success) {
                    }
                });
                return true;
            case R.id.refresh_scouting_data:
                utility.downloadScoutingData(MainScreen.this, true, new NetworkCallback() {
                    @Override
                    public void handleFinishDownload(final boolean success) {
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getScouterName() {
        return scouterText.getText().toString();
    }
    //Kevin is watching ( ͡° ͜ʖ ͡°)
    //this push thing isn't working ;-;

    private void restorePreferences() {
        String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
        if (!scouterName.isEmpty()) {
            scouterText.setText(scouterName);
            scouterText.dismissDropDown();
        }

        List<BenchmarkingData> benchmarkingDatas = dbHelper.getAllBenchmarkingData();
        for(BenchmarkingData benchmarkingData : benchmarkingDatas) {
            TeamData.setTeamData(benchmarkingData.getTeamNumber(), benchmarkingData.getEventName());
            TeamData.getCurrentTeam().setBenchmarkingData(benchmarkingData);
        }

        List<ScoutingData> scoutingDatas = dbHelper.getAllScoutingDatas();
        for(ScoutingData scoutingData : scoutingDatas) {
            TeamData.setTeamData(scoutingData.getTeamNumber(), scoutingData.getEventName());
            TeamData.getCurrentTeam().addScoutingData(scoutingData);
        }
    }

    // function called by any of the three buttons to switch screens
    private final View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // we create a destination variable. This is the screen we are going to switch to.
            Class destination = null;
            // We set the screen we are going to switch to.
            switch (v.getId()) {
                case R.id.benchmarkButton:
                    destination = BenchmarkScreen.class;
                    break;
                case R.id.scoutButton:
                    destination = ScoutingScreen.class;
                    break;
                case R.id.viewButton:
                    destination = ViewScreen.class;
                    break;
                case R.id.teamChecklist:
                    destination = TeamChecklistScreen.class;
                    break;
            }

            Intent intent = new Intent(MainScreen.this, destination);
            startActivity(intent);
        }
    };

    private final TextWatcher scouterTextEntered = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String scouterName = getScouterName();
            if (Arrays.asList(studentList).contains(scouterName)) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(getResources().getString(R.string.pref_scouter), scouterName);
                editor.apply();
                Log.e(TAG, "Selected scouter - " + scouterName);
            }
            showButtons();
        }
    };
  
    private void showButtons() {
        if (Arrays.asList(studentList).contains(getScouterName())) {
                benchmarkAutoButton.setVisibility(View.VISIBLE);
                viewButton.setVisibility(View.VISIBLE);
                teamChecklistButon.setVisibility(View.VISIBLE);
                scoutButton.setVisibility(View.VISIBLE);
        } else {
            benchmarkAutoButton.setVisibility(INVISIBLE);
            viewButton.setVisibility(INVISIBLE);
            teamChecklistButon.setVisibility(INVISIBLE);
            scoutButton.setVisibility(INVISIBLE);
        }
    }

    private void setupTeamList() {
        List<String> teamsList = new ArrayList<>();
        String event_key = settings.getString(getResources().getString(R.string.pref_event_key), "");
        try (Cursor teamCursor = dbHelper.createTeamCursor(dbHelper.getEvent(event_key))) {
            while (teamCursor.moveToNext()) {
                String teamNumber = teamCursor.getString(teamCursor.getColumnIndex(DatabaseHelper.TABLE_TEAMS_TEAM_NUMBER));
                teamsList.add(teamNumber);

            }
        }
        utility.setTeamList(teamsList);
    }

    private void setupMatchMap() {
        Map<String, String> matchMap = new HashMap<>();
        String event_key = settings.getString(getResources().getString(R.string.pref_event_key), "");
        System.out.println("Hiram3");
        System.out.println(event_key);
        try (Cursor matchCursor = dbHelper.createMatchCursor(dbHelper.getEvent(event_key))) {
            System.out.println("Hiram4");
            while (matchCursor.moveToNext()) {
                System.out.println("Hiram5");
                String compLevel = matchCursor.getString(matchCursor.getColumnIndex(DatabaseHelper.TABLE_MATCHES_COMP_LEVEL));
                System.out.println(compLevel);
                if(compLevel.contentEquals("qm")) {
                    String matchNumber = matchCursor.getString(matchCursor.getColumnIndex(DatabaseHelper.TABLE_MATCHES_MATCH_NUMBER));
                    String matchKey = matchCursor.getString(matchCursor.getColumnIndex(DatabaseHelper.TABLE_MATCHES_KEY));
                    matchMap.put(matchNumber, matchKey);
                }
            }
        }

        utility.setMatchMap(matchMap);
    }
}