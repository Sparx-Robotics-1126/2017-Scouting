package com.sparx1126.steamworks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;
import org.gosparx.scouting.aerialassist.networking.NetworkCallback;
import org.gosparx.scouting.aerialassist.networking.NetworkHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    private Spinner eventSpinner;
    private AutoCompleteTextView scouterText;
    private EditText teamText;
    private boolean eventSelected = false;
    private boolean nameSelected = false;
    private boolean teamSelected = false;
    private boolean eventFilter = true;
    private SharedPreferences settings;
    public static final int COMPETITION_YEAR = 2017;
    private static final int COMPETITION_Threshold = 1000;
    private static final String PREFS_NAME = "Sparx-prefs";
    private static final String PREFS_SCOUTER = "scouterText";
    private static final String PREFS_TEAM = "teamNumber";
    private static final String PREFS_EVENT = "eventText";
    private static final String OUR_COMPETITION_BUCKEYE = "2017-03-29 Buckeye Regional";
    private static final String OUR_COMPETITION_FINGERLAKES = "2017-03-15 Finger Lakes Regional ";
    private static final String FILTER_ON = "Turn the event filter on?";
    private static final String FILTER_OFF = "Turn the event filter off?";
    private Map<String, ScoutingInfo> scoutingInfoMap;

    private String getName(){
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
        return eventSpinner.getSelectedItem().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        blueAlliance = BlueAlliance.getInstance(this);
        dbHelper = DatabaseHelper.getInstance(this);

        // If the internet is available and we haven't gotten the data the download it
        if (isNetworkAvailable(this) && NetworkHelper.needToLoadEventList(this)) {

            downloadEventSpinnerData();
        }

        scoutingInfoMap = new HashMap<>();

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, students);
        scouterText.setAdapter(adapter);

        teamText = (EditText) findViewById(R.id.teamText);
        teamText.addTextChangedListener(teamTextEntered);
        teamText.setVisibility(View.INVISIBLE);

        eventSpinner = (Spinner) findViewById(R.id.eventSpinner);
        eventSpinner.setOnTouchListener(spinnerOnTouch);
        eventSpinner.setOnItemSelectedListener(spinnerOnItemClick);

        eventsWeAreInArray = new ArrayList<>();
        eventNamesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventsWeAreInArray); //selected item will look like a spinner set from XML

        settings = getSharedPreferences(PREFS_NAME, 0);

        // restore the event, name, and team
        restorePreferences();
    }
    private void teamNumberChecker(){
        if(!teamText.getText().toString().isEmpty()){
            String[] FLRTeams = getResources().getStringArray(R.array.FLRTeams);
            String[] buckeyeTeams = getResources().getStringArray(R.array.buckeyeTeams);
            SharedPreferences.Editor editor = settings.edit();
            if((Arrays.asList(buckeyeTeams).contains(teamText.getText().toString())) && (eventSpinner.getSelectedItem().toString().contentEquals(OUR_COMPETITION_BUCKEYE))){
                teamSelected = true;
                int teamNumber = getTeamNumber();
                editor.putInt(PREFS_TEAM, teamNumber);
                editor.apply();
                showButtons();
            }
            else if(Arrays.asList(FLRTeams).contains(teamText.getText().toString()) && (eventSpinner.getSelectedItem().toString().contentEquals(OUR_COMPETITION_FINGERLAKES))){
                teamSelected = true;
                int teamNumber = getTeamNumber();
                editor.putInt(PREFS_TEAM, teamNumber);
                editor.apply();
                showButtons();
            }
            else{
                teamSelected = false;
                showButtons();
            }
        }
        else{
            teamSelected = false;
            showButtons();
        }
    }

    private void restorePreferences(){
        //the order matters
        String eventName = settings.getString(PREFS_EVENT, "");
        if(!eventName.isEmpty()) {
            setupEventSpinner();
            if(eventNamesAdapter.getPosition(eventName) != -1) {
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
        String scouterName = settings.getString(PREFS_SCOUTER, "");
        scouterText.setText(scouterName);
        scouterText.dismissDropDown();
        int teamNumber = settings.getInt(PREFS_TEAM, 0);
        if(teamNumber != 0) {
            teamText.setText(String.valueOf(teamNumber));
        }
    }

    // function called by any of the three buttons to switch screens
    private final View.OnClickListener buttonClicked =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // we grab the main screen as a type context for switching
            Context context = MainScreen.this;
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

            // set the currentScouting object I intend to pass to. Set it to NULL which means not
            // created yet. This is a good practice because if useed and set to NULL it creashes better
            ScoutingInfo currentInfo;
            // get the object editable from the teamText number text field on the screen. The intentions
            // is to get the text entered from it.
            Editable editable = teamText.getText();
            // get from the object Editable a String (i.e. it could contain "1126")
            String teamNumber = editable.toString();
            // look for i.e. "1126" in my map of already scouted teams
            if (scoutingInfoMap.containsKey(teamNumber)) {
                // set my temporary variable of scouting info to the one I found inside the map
                currentInfo = scoutingInfoMap.get(teamNumber);
            } else {
                // create a new scouting info because I did not find it in my map
                // which means it hasn't been scouted before
                currentInfo = new ScoutingInfo();
                currentInfo.setEventKey(getEventName());
                currentInfo.setTeamNumber(getTeamNumber());
                currentInfo.addScouter(getName());
                // add the new scouting info into my map so that I can find it in the future
                scoutingInfoMap.put(teamText.getText().toString(), currentInfo);
            }

            Intent intent = new Intent(context, destination);
            intent.putExtra(CommonDefs.SCOUTER_INFO, currentInfo);
            startActivity(intent);
        }
    };

    private final View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP ) {
                setupEventSpinner();
            }
            return false;
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerOnItemClick = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(getEventName().contentEquals(FILTER_OFF)){
                eventFilter = false;
                setupEventSpinner();
            }
            else if(getEventName().contentEquals(FILTER_ON)){
                eventFilter = true;
                setupEventSpinner();
            }
            else if (!getEventName().isEmpty()) {
                String eventName = getEventName();
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(PREFS_EVENT, eventName);
                editor.apply();
                eventSelected = true;
                teamNumberChecker();
                showButtons();
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
            String[] students = getResources().getStringArray(R.array.students);
            if(Arrays.asList(students).contains(scouterText.getEditableText().toString())){
                nameSelected = true;
                SharedPreferences.Editor editor = settings.edit();
                String scouterName = getName();
                editor.putString(PREFS_SCOUTER, scouterName);
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

    private void downloadEventSpinnerData() {
        final Dialog alert = createPleaseWaitDialog();
        alert.show();

        blueAlliance.loadEventList(new NetworkCallback() {
            @Override
            public void handleFinishDownload(final boolean success) {
                MainScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alert.dismiss();
                        if (!success) {
                            alertUser().show();
                        }
                    }
                });
            }
        });
    }

    private AlertDialog alertUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Failure");
        builder.setMessage("Did not successfully download event list!");
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    private AlertDialog createPleaseWaitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.downloading_data);
        builder.setMessage("Please wait while Event data is downloaded...");
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BlueAlliance.getInstance(MainScreen.this).cancelAll();
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    private void setupEventSpinner() {

        Cursor eventDataCur = dbHelper.createEventNameCursor();
        //Left that here because it's a way to dump all of the data into the console
        //System.out.println(DatabaseUtils.dumpCursorToString(eventDataCur));
        eventsWeAreInArray = fillInEventsNeerToday(eventDataCur);
        if(eventFilter) {
            for (int i = (eventsWeAreInArray.size() - 1); 0 <= i; i--) {
                if (!eventsWeAreInArray.get(i).contentEquals(OUR_COMPETITION_BUCKEYE)) {
                    if (!eventsWeAreInArray.get(i).contentEquals(OUR_COMPETITION_FINGERLAKES)) {
                        eventsWeAreInArray.remove(i);
                    }
                }
            }
        }
        if(eventFilter){
            eventsWeAreInArray.add(FILTER_OFF);
        }
        else{
            eventsWeAreInArray.add(FILTER_ON);
        }
        eventNamesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventsWeAreInArray);
        eventNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(eventNamesAdapter);
    }

    private long getTodayInEpoch() {
        Calendar c = Calendar.getInstance();
        return c.getTime().getTime();
    }
    private ArrayList<String> fillInEventsNeerToday(Cursor eventDataCur){
        ArrayList<String> eventsWeAreInArray = new ArrayList<>();
        SimpleDateFormat cursorFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        long epochToday = getTodayInEpoch();
        try {
            while (eventDataCur.moveToNext()) {
                Date dateObj;
                try {
                    String startDateStr = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_START_DATE));
                    dateObj = cursorFormater.parse(startDateStr);
                    long epoch = dateObj.getTime();

                    long ONE_DAY_EPOCH = 86400000;
                    if(epoch >= epochToday - (ONE_DAY_EPOCH * COMPETITION_Threshold)&& epoch <= epochToday + (ONE_DAY_EPOCH * COMPETITION_Threshold))
                    {
                        eventsWeAreInArray.add(eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_TITLE)));
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
}