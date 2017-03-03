//push was successful YAYAYAYAYAYAYAYAYAYAY
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
import org.gosparx.scouting.aerialassist.dto.Event;
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

        // restore the event, name, and team
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
                int teamNumber = getTeamNumber();
                editor.putInt(getResources().getString(R.string.pref_team), teamNumber);
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
        //the order matters
        String eventName = settings.getString(getResources().getString(R.string.pref_event), "");
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
        String scouterName = settings.getString(getResources().getString(R.string.pref_scouter), "");
        scouterText.setText(scouterName);
        scouterText.dismissDropDown();
        int teamNumber = settings.getInt(getResources().getString(R.string.pref_team), 0);
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
            // look for i.e. 1126 in my map of already scouted teams
            int teamNumberValue = getTeamNumber();
            // set the currentScouting object I intend to pass to. Set it to NULL which means not
            // created yet. This is a good practice because if useed and set to NULL it creashes better
            ScoutingInfo.addInfo(teamNumberValue, getEventName(), getScouterName());

            Intent intent = new Intent(context, destination);
            startActivity(intent);
        }
    };

    private final View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (getEventName().isEmpty() && (event.getAction() == MotionEvent.ACTION_UP)) {
                downloadEventSpinnerData();
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

    private AlertDialog alertUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.failure);
        builder.setMessage(R.string.download_failed);
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
        builder.setMessage(R.string.please_wait_event);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BlueAlliance.getInstance(MainScreen.this).cancelAll();
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    private void downloadEventSpinnerData() {
        // If the internet is available and we haven't gotten the data the download it
        if (isNetworkAvailable(this) && NetworkHelper.needToLoadEventList(this)) {
            final Dialog alert = createPleaseWaitDialog();
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
                                alertUser().show();
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

    private long getTodayInEpoch() {
        Calendar c = Calendar.getInstance();
        return c.getTime().getTime();
    }

    private ArrayList<String> fillInEventsNearToday(Cursor eventDataCur){
        ArrayList<String> eventsWeAreInArray = new ArrayList<>();
        SimpleDateFormat cursorFormater = new SimpleDateFormat(getString(R.string.day_format), Locale.US);
        long epochToday = getTodayInEpoch();
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
        if (isNetworkAvailable(this)) {
            final Dialog alert = createPleaseWaitDialog();
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
                                alertUser().show();
                            }
                        }

                    });
                }
            });
        }
    }

    private void setupTeamSpinner() {
        Cursor teamCursor = dbHelper.createTeamCursor(getSelectedEvent());
        // this clears the list
        teamsList.clear();
        int index = 0;
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
}