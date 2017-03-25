//push was successful YAYAYAYAYAYAYAYAYAYAY
package com.sparx1126.steamworks;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sparx1126.steamworks.components.Utility;

import org.gosparx.scouting.aerialassist.BenchmarkingData;
import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.ScoutingData;
import org.gosparx.scouting.aerialassist.TeamData;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;
import org.gosparx.scouting.aerialassist.networking.NetworkCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.view.View.INVISIBLE;
import static org.gosparx.scouting.aerialassist.networking.NetworkHelper.isNetworkAvailable;

public class MainScreen extends AppCompatActivity {
    private BlueAlliance blueAlliance;
    private DatabaseHelper dbHelper;
    private Utility utility;
    private Spinner eventSpinner;
    private AutoCompleteTextView scouterText;
    private String[] students;
    private EditText teamText;
    private Button benchmarkAuto;
    private Button scout;
    private Button view;
    private Button teamChecklist;
    private SharedPreferences settings;
    private List<String> eventsNearToday;
    private List<String> eventsWeAreInArray;
    private ArrayAdapter<String> eventNamesAdapter;
    private Map<String, String> eventNamesToKey;
    private List<String> teamsList;
    private boolean eventSelected = false;
    private boolean eventFilter = true;
    private static final int COMPETITION_Threshold = 4;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.main_menu_music);
        mediaPlayer.start();
        blueAlliance = BlueAlliance.getInstance(this);
        dbHelper = DatabaseHelper.getInstance(this);
        utility = Utility.getInstance();

        eventSpinner = (Spinner) findViewById(R.id.eventSpinner);
        eventSpinner.setOnTouchListener(spinnerOnTouch);
        eventSpinner.setOnItemSelectedListener(spinnerOnItemClick);

        scouterText = (AutoCompleteTextView) findViewById(R.id.scouterText);
        scouterText.addTextChangedListener(scouterTextEntered);
        students = getResources().getStringArray(R.array.students);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, students);
        scouterText.setAdapter(adapter);

        teamText = (EditText) findViewById(R.id.teamText);
        teamText.addTextChangedListener(teamTextEntered);
        teamText.setVisibility(View.INVISIBLE);

        benchmarkAuto = (Button) findViewById(R.id.benchmarkButton);
        benchmarkAuto.setOnClickListener(buttonClicked);
        benchmarkAuto.setVisibility(View.INVISIBLE);

        scout = (Button) findViewById(R.id.scoutButton);
        scout.setOnClickListener(buttonClicked);
        scout.setVisibility(View.INVISIBLE);

        view = (Button) findViewById(R.id.viewButton);
        view.setOnClickListener(buttonClicked);
        view.setVisibility(View.INVISIBLE);

        teamChecklist = (Button) findViewById(R.id.teamChecklist);
        teamChecklist.setOnClickListener(buttonClicked);
        teamChecklist.setVisibility(View.INVISIBLE);


        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);

        eventsNearToday = new ArrayList<>();
        eventsWeAreInArray = new ArrayList<>();
        eventNamesToKey = new HashMap<>();
        teamsList = new ArrayList<>();

        restorePreferences();
    }


    @Override
    protected void onStart() {
        super.onStart();
        utility.downloadEventSpinnerData(this, false, new NetworkCallback() {
            @Override
            public void handleFinishDownload(final boolean success) {
                MainScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (success) {
                            Dialog alert = utility.createDialog(MainScreen.this, getString(R.string.downloading_data), getString(R.string.please_wait_team_download));
                            alert.show();
                            fillInEventsNearToday(dbHelper.createEventNameCursor());
                            downloadTeamData(dbHelper.createEventNameCursor(), alert);
                        }
                    }
                });
            }
        });
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
                utility.uploadBenchmarkingData(this, true);
                return true;
            case R.id.menu_updload_pictures:
                utility.uploadPictures(this, true);
                return true;
            case R.id.menu_updload_scouting:
                utility.uploadScoutingData(this, true);
                return true;
            case R.id.refresh_events:
                utility.downloadEventSpinnerData(this, true, new NetworkCallback() {
                    @Override
                    public void handleFinishDownload(final boolean success) {
                        MainScreen.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (success) {
                                    Dialog alert = utility.createDialog(MainScreen.this, getString(R.string.downloading_data), getString(R.string.please_wait_team_download));
                                    alert.show();
                                    fillInEventsNearToday(dbHelper.createEventNameCursor());
                                    downloadTeamData(dbHelper.createEventNameCursor(), alert);
                                }
                            }
                        });
                    }
                });
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

    private int getTeamNumber() {
        int value = 0;
        String textEntered = teamText.getText().toString();
        if (!textEntered.isEmpty()) {
            value = Integer.parseInt(textEntered);
        }
        return value;
    }

    private String getEventName() {
        String eventName = "";
        if (eventSpinner.getSelectedItem() != null) {
            eventName = eventSpinner.getSelectedItem().toString();
        }
        return eventName;
    }

    private void restorePreferences() {
        List<BenchmarkingData> benchmarkingDatas = dbHelper.getAllBenchmarkingData();
        for(BenchmarkingData benchmarkingData : benchmarkingDatas) {
            TeamData.setTeamData(benchmarkingData.getTeamNumber(), benchmarkingData.getEventName());
            TeamData.getCurrentTeam().setBenchmarkingData(benchmarkingData);
            TeamData.getCurrentTeam().setStudent(getScouterName());
        }

        List<ScoutingData> scoutingDatas = dbHelper.getAllScoutingDatas();
        for(ScoutingData scoutingData : scoutingDatas) {
            TeamData.setTeamData(scoutingData.getTeamNumber(), scoutingData.getEventName());
            TeamData.getCurrentTeam().addScoutingData(scoutingData);
            TeamData.getCurrentTeam().setStudent(getScouterName());
        }

        String eventName = settings.getString(getResources().getString(R.string.pref_event), "");
        if (!eventName.isEmpty()) {
            eventFilter = settings.getBoolean(getResources().getString(R.string.pref_event_filter), false);
            fillInEventsNearToday(dbHelper.createEventNameCursor());
            setupEventSpinner();
            eventSpinner.setSelection(eventNamesAdapter.getPosition(eventName));
        }
        String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
        if (!scouterName.isEmpty()) {
            scouterText.setText(scouterName);
            scouterText.dismissDropDown();
        }
        String teamNumber = settings.getString(getResources().getString(R.string.pref_team), "");
        if (!teamNumber.isEmpty()) {
            teamText.setText(teamNumber);
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
            TeamData.setTeamData(getTeamNumber(), getEventName());
            TeamData.getCurrentTeam().setStudent(getScouterName());

            Intent intent = new Intent(MainScreen.this, destination);
            startActivity(intent);
        }
    };

    private final View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (getEventName().isEmpty() && (event.getAction() == MotionEvent.ACTION_UP)) {
                setupEventSpinner();
            }
            return false;
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerOnItemClick = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (!getEventName().isEmpty()) {
                if (getEventName().contentEquals(getResources().getString(R.string.filter_off))) {
                    eventSelected = false;
                    eventFilter = false;
                    setupEventSpinner();
                } else if (getEventName().contentEquals(getResources().getString(R.string.filter_on))) {
                    eventSelected = false;
                    eventFilter = true;
                    setupEventSpinner();
                } else {
                    eventSelected = true;
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(getResources().getString(R.string.pref_event), getEventName());
                    editor.putBoolean(getResources().getString(R.string.pref_event_filter), eventFilter);
                    editor.apply();
                    setupTeamList();
                }
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
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
            if (Arrays.asList(students).contains(scouterName)) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(getResources().getString(R.string.pref_scouter), scouterName);
                editor.apply();
            }
            showButtons();
        }
    };

    private final TextWatcher teamTextEntered = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            teamNumberChecker();
        }
    };

    private void teamNumberChecker() {
        String teamTextValue = teamText.getText().toString();
        if (!teamTextValue.isEmpty() && teamsList.contains(teamTextValue)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(getResources().getString(R.string.pref_team), teamTextValue);
            editor.apply();
        }
        showButtons();
    }

    private void showButtons() {
        if (eventSelected && Arrays.asList(students).contains(getScouterName())) {
            teamText.setVisibility(View.VISIBLE);
            if (teamsList.contains(teamText.getText().toString())) {
                benchmarkAuto.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
                teamChecklist.setVisibility(View.VISIBLE);
                scout.setVisibility(View.VISIBLE);
            } else {
                benchmarkAuto.setVisibility(INVISIBLE);
                view.setVisibility(INVISIBLE);
                teamChecklist.setVisibility(INVISIBLE);
                scout.setVisibility(INVISIBLE);
            }
        } else {
            teamText.setVisibility(INVISIBLE);
            benchmarkAuto.setVisibility(INVISIBLE);
            view.setVisibility(INVISIBLE);
            teamChecklist.setVisibility(INVISIBLE);
            scout.setVisibility(INVISIBLE);
        }
    }

    private void setupEventSpinner() {
        Cursor eventDataCur = dbHelper.createEventNameCursor();

        //Left that here because it's a way to dump all of the data into the console
        //System.out.println(DatabaseUtils.dumpCursorToString(eventDataCur));
        if (eventDataCur.getCount() > 0) {
            eventsWeAreInArray.clear();
            for (String eventName : eventsNearToday) {
                eventsWeAreInArray.add(eventName);
            }
            if (eventFilter) {
                for (int i = (eventsWeAreInArray.size() - 1); 0 <= i; i--) {
                    if (!eventsWeAreInArray.get(i).contentEquals(getResources().getString(R.string.our_competition_buckeye)) && !eventsWeAreInArray.get(i).contentEquals(getResources().getString(R.string.our_competition_flr))) {
                        eventsWeAreInArray.remove(i);
                    }
                }
                eventsWeAreInArray.add(getResources().getString(R.string.filter_off));
            } else {
                eventsWeAreInArray.add(getResources().getString(R.string.filter_on));
            }
        }
        eventNamesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventsWeAreInArray);
        eventNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(eventNamesAdapter);
    }

    private void fillInEventsNearToday(Cursor eventDataCur) {
        SimpleDateFormat cursorFormat = new SimpleDateFormat(getString(R.string.day_format), Locale.US);
        long epochToday = utility.getEpoch();
        try {
            while (eventDataCur.moveToNext()) {
                Date dateObj;
                try {
                    String startDateStr = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_START_DATE));
                    dateObj = cursorFormat.parse(startDateStr);
                    long epoch = dateObj.getTime();

                    long ONE_DAY_EPOCH = 86400000;
                    long window = ONE_DAY_EPOCH * COMPETITION_Threshold;
                    if ((epoch >= (epochToday - window)) && (epoch <= (epochToday + window))) {
                        String eventName = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_TITLE));
                        String eventKey = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_KEY));
                        eventsNearToday.add(eventName);
                        eventNamesToKey.put(eventName, eventKey);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            eventDataCur.close();
        }
    }

    private void downloadTeamData(Cursor eventDataCur, final Dialog alert) {
        if (eventDataCur.moveToNext()) {
            if (!isNetworkAvailable(this)) {
                utility.alertUser(this, getString(R.string.no_network), getString(R.string.try_again)).show();
            } else {
                String eventName = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_TITLE));
                final Cursor remainder = eventDataCur;
                if (eventNamesToKey.containsKey(eventName)) {
                    String eventKey = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_KEY));
                    blueAlliance.loadTeams(dbHelper.getEvent(eventKey), new NetworkCallback() {
                        @Override
                        public void handleFinishDownload(final boolean success) {
                            MainScreen.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (success) {
                                        downloadTeamData(remainder, alert);
                                    } else {
                                        utility.alertUser(MainScreen.this, getString(R.string.failure), getString(R.string.team_download_failed)).show();
                                    }
                                }

                            });
                        }
                    });
                } else {
                    downloadTeamData(remainder, alert);
                }
            }
        } else {
            eventDataCur.close();
            alert.dismiss();
            utility.downloadBenchmarkData(this, false, new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    MainScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (success) {
                                utility.downloadScoutingData(MainScreen.this, false, new NetworkCallback() {
                                    @Override
                                    public void handleFinishDownload(final boolean success) {
                                        MainScreen.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                /*if (success) {
                                                    utility.downloadPictures(MainScreen.this, false, new NetworkCallback() {
                                                        @Override
                                                        public void handleFinishDownload(final boolean success) {
                                                            MainScreen.this.runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                }
                                                            });
                                                        }
                                                    });
                                                }*/
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    }

    private void setupTeamList() {
        teamsList.clear();
        try (Cursor teamCursor = dbHelper.createTeamCursor(dbHelper.getEvent(eventNamesToKey.get(getEventName())))) {
            while (teamCursor.moveToNext()) {
                String teamNumber = teamCursor.getString(teamCursor.getColumnIndex(DatabaseHelper.TABLE_TEAMS_TEAM_NUMBER));
                teamsList.add(teamNumber);
            }
        }
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(getResources().getString(R.string.pref_number_teams), TextUtils.join(",", teamsList));
        editor.apply();
        teamNumberChecker();
    }
}