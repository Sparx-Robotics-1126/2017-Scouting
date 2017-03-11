//push was successful YAYAYAYAYAYAYAYAYAYAY
package com.sparx1126.steamworks;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
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

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.BenchmarkingData;
import org.gosparx.scouting.aerialassist.dto.Event;
import org.gosparx.scouting.aerialassist.dto.TeamData;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;
import org.gosparx.scouting.aerialassist.networking.NetworkCallback;
import org.gosparx.scouting.aerialassist.networking.NetworkHelper;
import org.gosparx.scouting.aerialassist.networking.SparxPosting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import static android.view.View.INVISIBLE;
import static org.gosparx.scouting.aerialassist.networking.NetworkHelper.isNetworkAvailable;

public class MainScreen extends AppCompatActivity {
    private Button benchmarkAuto;
    private Button view;
    private Button scout;
    private ArrayList<String> eventsWeAreInArray;
    private ArrayAdapter<String> eventNamesAdapter;
    private DatabaseHelper dbHelper;
    private BlueAlliance blueAlliance;
    private Utility utility;
    private Spinner eventSpinner;
    private AutoCompleteTextView scouterText;
    private EditText teamText;
    private boolean eventSelected = false;
    private boolean nameSelected = false;
    private boolean teamSelected = false;
    private boolean eventFilter = true;
    private SharedPreferences settings;
    private static final int COMPETITION_Threshold = 1000;
    private Map<String, String> eventNamesToKey;
    private Vector<String> teamsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        blueAlliance = BlueAlliance.getInstance(this);
        dbHelper = DatabaseHelper.getInstance(this);
        utility = Utility.getInstance();

        eventNamesToKey = new HashMap<>();

        scout = (Button)findViewById(R.id.scoutButton);
        scout.setOnClickListener(buttonClicked);
        scout.setVisibility(View.INVISIBLE);

        benchmarkAuto = (Button)findViewById(R.id.benchmarkButton);
        benchmarkAuto.setOnClickListener(buttonClicked);
        benchmarkAuto.setVisibility(View.INVISIBLE);

        view = (Button)findViewById(R.id.viewButton);
        view.setOnClickListener(buttonClicked);
        view.setVisibility(View.INVISIBLE);

        scouterText = (AutoCompleteTextView) findViewById(R.id.scouterText);
        scouterText.addTextChangedListener(scouterTextEntered);
        String[] students = getResources().getStringArray(R.array.students);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, students);
        scouterText.setAdapter(adapter);

        teamText = (EditText) findViewById(R.id.teamText);
        teamText.addTextChangedListener(teamTextEntered);
        teamText.setVisibility(View.INVISIBLE);

        eventSpinner = (Spinner) findViewById(R.id.eventSpinner);
        eventSpinner.setOnTouchListener(spinnerOnTouch);
        eventSpinner.setOnItemSelectedListener(spinnerOnItemClick);

        eventsWeAreInArray = new ArrayList<>();
        eventNamesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventsWeAreInArray); //selected item will look like a spinner set from XML

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);

        teamsList = new Vector<>();

        restorePreferences();
    }

    private String getScouterName(){
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

    private String getEventName(){
        String eventName = "";
        if(eventSpinner.getSelectedItem() != null) {
            eventName = eventSpinner.getSelectedItem().toString();
        }
        return eventName;
    }

    public Event getSelectedEvent() {
        return dbHelper.getEvent(eventNamesToKey.get(getEventName()));
    }

    private void teamNumberChecker(){
        String teamTextValue = teamText.getText().toString();
        if(!teamTextValue.isEmpty()){
            //no value until event picker is validated
            SharedPreferences.Editor editor = settings.edit();
            if(teamsList.contains(teamTextValue)){
                teamSelected = true;
                editor.putString(getResources().getString(R.string.pref_team), teamTextValue);
                editor.apply();
            }
            else{
                teamSelected = false;
            }
        }
        else{
            teamSelected = false;
        }
        showButtons();
    }

    private void restorePreferences(){
        String eventName = settings.getString(getResources().getString(R.string.pref_event), "");
        if(!eventName.isEmpty()) {
            if(eventNamesAdapter.getPosition(eventName) != -1) {
                setupEventSpinner();
                eventSpinner.setSelection(eventNamesAdapter.getPosition(eventName));
            }
            else {
                eventFilter = false;
                setupEventSpinner();
                if(eventNamesAdapter.getPosition(eventName) != -1) {
                    eventSpinner.setSelection(eventNamesAdapter.getPosition(eventName));
                }
            }
        }
        String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
        if(!scouterName.isEmpty()) {
            scouterText.setText(scouterName);
            scouterText.dismissDropDown();
        }
        String teamNumber = settings.getString(getResources().getString(R.string.pref_team), "");
        if(!teamNumber.isEmpty()) {
            teamText.setText(teamNumber);
        }
    }

    // function called by any of the three buttons to switch screens
    private final View.OnClickListener buttonClicked =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // we create a destination variable. This is the screen we are going to switch to.
            Class destination = null;
            // We set the screen we are going to swtich to.
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
            }
            BenchmarkingData benchmarkingData = new BenchmarkingData(getTeamNumber(), getEventName(), getScouterName());
            TeamData.setTeamData(benchmarkingData);

            Intent intent = new Intent(MainScreen.this, destination);
            startActivity(intent);
        }
    };

    private final View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (getEventName().isEmpty() && (event.getAction() == MotionEvent.ACTION_UP)) {
                downloadEventSpinnerData(false);
            }
            return false;
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerOnItemClick = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(getEventName().contentEquals(getResources().getString(R.string.filter_off))){
                eventSelected = false;
                eventFilter = false;
                setupEventSpinner();
            }
            else if(getEventName().contentEquals(getResources().getString(R.string.filter_on))){
                eventSelected = false;
                eventFilter = true;
                setupEventSpinner();
            }
            else if (!getEventName().isEmpty()) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(getResources().getString(R.string.pref_event), getEventName());
                editor.apply();
                eventSelected = true;
                utility.downloadBenchmarkData(MainScreen.this, false);
                downloadTeamDataIfNecessary();
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private final TextWatcher scouterTextEntered = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {
            String[] students = getResources().getStringArray(R.array.students);
            String scouterName = getScouterName();
            if(Arrays.asList(students).contains(scouterName)){
                nameSelected = true;
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(getResources().getString(R.string.pref_scouter), scouterName);
                editor.apply();
                showButtons();
            }
            else{
                nameSelected = false;
                showButtons();
            }
        }
    };

    private final TextWatcher teamTextEntered = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {
            teamNumberChecker();
        }
    };

    private void showButtons(){
        if(eventSelected && nameSelected){
            teamText.setVisibility(View.VISIBLE);
            if(teamSelected){
                benchmarkAuto.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
                scout.setVisibility(View.VISIBLE);
            }
            else {
                benchmarkAuto.setVisibility(INVISIBLE);
                view.setVisibility(INVISIBLE);
                scout.setVisibility(INVISIBLE);
            }
        }
        else{
            teamText.setVisibility(INVISIBLE);
            benchmarkAuto.setVisibility(INVISIBLE);
            view.setVisibility(INVISIBLE);
            scout.setVisibility(INVISIBLE);
        }
    }

    private void downloadEventSpinnerData(boolean forceDownload) {
        // If the internet is available and we haven't gotten the data the download it
        if (!isNetworkAvailable(this)) {
            utility.alertUser(this, getString(R.string.no_network), getString(R.string.try_again)).show();
        } else if (NetworkHelper.needToLoadEventList(this) || forceDownload) {
            final Dialog alert = utility.createDialog(this, getString(R.string.downloading_data), getString(R.string.please_wait_event_download));
            alert.show();
            blueAlliance.loadEventList(getResources().getString(R.string.competition_year), new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    MainScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            if (success) {
                                setupEventSpinner();
                            }
                            else {
                                utility.alertUser(MainScreen.this, getString(R.string.failure), getString(R.string.event_download_failed)).show();
                            }
                        }
                    });
                }
            });
        }
    }

    private void setupEventSpinner() {
        Cursor eventDataCur = dbHelper.createEventNameCursor();
        //Left that here because it's a way to dump all of the data into the console
        //System.out.println(DatabaseUtils.dumpCursorToString(eventDataCur));
        if(eventDataCur.getCount() > 0) {
            eventsWeAreInArray = fillInEventsNearToday(eventDataCur);
            if (eventFilter) {
                for (int i = (eventsWeAreInArray.size() - 1); 0 <= i; i--) {
                    if (!eventsWeAreInArray.get(i).contentEquals(getResources().getString(R.string.our_competition_buckeye))) {
                        if (!eventsWeAreInArray.get(i).contentEquals(getResources().getString(R.string.our_competition_flr))) {
                            eventsWeAreInArray.remove(i);
                        }
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

    private ArrayList<String> fillInEventsNearToday(Cursor eventDataCur){
        ArrayList<String> eventsWeAreInArray = new ArrayList<>();
        SimpleDateFormat cursorFormater = new SimpleDateFormat(getString(R.string.day_format), Locale.US);
        long epochToday = utility.getTodayInEpoch();
        try {
            while (eventDataCur.moveToNext()) {
                Date dateObj;
                try {
                    String startDateStr = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_START_DATE));
                    dateObj = cursorFormater.parse(startDateStr);
                    long epoch = dateObj.getTime();

                    long ONE_DAY_EPOCH = 86400000;
                    long window = ONE_DAY_EPOCH * COMPETITION_Threshold;
                    if((epoch >= (epochToday - window)) && (epoch <= (epochToday + window)))
                    {
                        String eventName = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_TITLE));
                        String eventKey = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_KEY));
                        eventsWeAreInArray.add(eventName);
                        eventNamesToKey.put(eventName, eventKey);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            eventDataCur.close();
        }
        return eventsWeAreInArray;
    }

    private void downloadTeamDataIfNecessary() {
        if (NetworkHelper.needToLoadTeams(this) && !isNetworkAvailable(this)) {
            utility.alertUser(this, getString(R.string.no_network), getString(R.string.try_again)).show();
        }
        else {
            final Dialog alert = utility.createDialog(this, getString(R.string.downloading_data), getString(R.string.please_wait_team_download));
            alert.show();
            //get the event
            BlueAlliance ba = BlueAlliance.getInstance(this);
            //load teams for the event
            ba.loadTeams(getSelectedEvent(), new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    MainScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            if (success) {
                                setupTeamSpinner();
                            }
                            else {
                                utility.alertUser(MainScreen.this, getString(R.string.failure), getString(R.string.team_download_failed)).show();
                            }
                        }

                    });
                }
            });
        }
    }

    private void setupTeamSpinner() {
        Cursor teamCursor = dbHelper.createTeamCursor(getSelectedEvent());
        teamsList.clear();
        try {
            while (teamCursor.moveToNext()) {
                String teamnumber = teamCursor.getString(teamCursor.getColumnIndex(DatabaseHelper.TABLE_TEAMS_TEAM_NUMBER));
                teamsList.add(teamnumber);
            }
        } finally {
            teamCursor.close();
        }
        teamNumberChecker();
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
            case R.id.menu_download_events:
                downloadEventSpinnerData(true);
                return true;
            case R.id.menu_updload_data:
                utility.uploadBenchmarkingData(this);
                utility.uploadPictures(this);
                utility.uploadScoutingData(this);
                return true;
            case R.id.refresh_benchmark_data:
                utility.downloadBenchmarkData(this, true);
                return true;
            case R.id.refresh_pictures:
                utility.downloadPictures(this, true);
                return true;
            case R.id.refresh_scouting_data:
                utility.downloadScoutingData(this, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}