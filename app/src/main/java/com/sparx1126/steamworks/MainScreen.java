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
import java.util.Map;

import static org.gosparx.scouting.aerialassist.networking.NetworkHelper.isNetworkAvailable;

public class MainScreen extends AppCompatActivity {
    private Button benchmarkButton;
    private Button viewButton;
    private Button scoutButton;
    private ArrayList<String> eventsWeAreInArray;
    private ArrayAdapter<String> eventNamesAdapter;
    private DatabaseHelper dbHelper;
    private BlueAlliance blueAlliance;
    private Spinner eventSpinner;
    private AutoCompleteTextView scouterText;
    private EditText teamText;
    boolean eventSelected = false;
    boolean nameSelected = false;
    boolean teamSelected = false;
    boolean eventFilter = true;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private static long ONE_DAY_EPOCH = 86400000;
    private static final int COMPETITION_YEAR = 2017;
    private static final int COMPETITION_Threshold = 1000;
    private static final String PREFS_NAME = "Sparx-prefs";
    private static final String PREFS_SCOUTER = "scouterText";
    private static final String PREFS_TEAM = "teamNumber";
    private static final String PREFS_EVENT = "eventText";
    private static final String OUR_COMPETITION_BUCKEYE = "2017-03-29 Buckeye Regional";
    private static final String OUR_COMPETITION_FINGERLAKES = "2017-03-15 Finger Lakes Regional ";
    private static final String FILTER_ON = "Turn the event filter on?";
    private static final String FILTER_OFF = "Turn the event filter off?";

    private Map scoutingInfoMap;

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

        scoutingInfoMap = new HashMap();

        scoutButton = (Button)findViewById(R.id.scoutButton);
        scoutButton.setOnClickListener(buttonClicked);
        scoutButton.setVisibility(View.INVISIBLE);

        benchmarkButton = (Button)findViewById(R.id.benchmarkButton);
        benchmarkButton.setOnClickListener(buttonClicked);
        benchmarkButton.setVisibility(View.INVISIBLE);

        viewButton = (Button)findViewById(R.id.viewButton);
        viewButton.setOnClickListener(buttonClicked);
        viewButton.setVisibility(View.INVISIBLE);

        scouterText = (AutoCompleteTextView) findViewById(R.id.scouterText);
        scouterText.addTextChangedListener(scouterTextEntered);
        // Initialize the auto text complete with the hard coded list in strings.xml
        scouterAutoComplete();

        teamText = (EditText) findViewById(R.id.teamText);
        teamText.addTextChangedListener(teamTextEntered);

        eventSpinner = (Spinner) findViewById(R.id.eventSpinner);
        eventSpinner.setOnTouchListener(spinnerOnTouch);
        eventSpinner.setOnItemSelectedListener(spinnerOnItemClick);

        eventsWeAreInArray =  new ArrayList<String>();
        eventNamesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventsWeAreInArray); //selected item will look like a spinner set from XML

        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

        // restore the event, name, and team
        restorePreferences();
    }

    private void restorePreferences(){
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String scouterName = settings.getString(PREFS_SCOUTER, "");
        scouterText.setText(scouterName);
        int teamNumber = settings.getInt(PREFS_TEAM, 0);
        if(teamNumber != 0) {
            teamText.setText(String.valueOf(teamNumber));
        }
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
    }

    // function called by any of the three buttons to switch screens
    public View.OnClickListener buttonClicked =  new View.OnClickListener() {
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
            ScoutingInfo currentInfo = null;
            // get the object editable from the teamText number text field on the screen. The intentions
            // is to get the text entered from it.
            Editable editable = teamText.getText();
            // get from the object Editable a String (i.e. it could contain "1126")
            String teamNumber = editable.toString();
            // look for i.e. "1126" in my map of already scouted teams
            if (scoutingInfoMap.containsKey(teamNumber)) {
                // set my temporary variable of scouting info to the one I found inside the map
                currentInfo = (ScoutingInfo) scoutingInfoMap.get(teamNumber);
            } else {
                // create a new scouting info because I did not find it in my map
                // which means it hasn't been scouted before
                currentInfo = new ScoutingInfo();
                currentInfo.setEventKey(getEventName());
                currentInfo.setTeamNumber(getTeamNumber());
                currentInfo.addScouter(getScouterName());
                // add the new scouting info into my map so that I can find it in the future
                scoutingInfoMap.put(teamText.getText().toString(), currentInfo);
            }

            Intent intent = new Intent(context, destination);
            intent.putExtra(CommonDefs.SCOUTER_INFO, currentInfo);
            startActivity(intent);
        }
    };

    private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP ) {
                setupEventSpinner();
            }
            return false;
        }
    };

    private AdapterView.OnItemSelectedListener spinnerOnItemClick = new AdapterView.OnItemSelectedListener() {
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
                System.out.println(eventName);
                editor.putString(PREFS_EVENT, eventName);
                editor.apply();
                eventSelected = true;
                showButtons();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private TextWatcher scouterTextEntered = new TextWatcher() {

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
                String scouterName = getScouterName();
                editor.putString(PREFS_SCOUTER, scouterName);
                editor.apply();
                showButtons();
            }
            else{
                benchmarkButton.setVisibility(View.INVISIBLE);
                scoutButton.setVisibility(View.INVISIBLE);
                viewButton.setVisibility(View.INVISIBLE);
            }
        }
    };

    private TextWatcher teamTextEntered = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(teamText.getText().toString().contentEquals("")){
                teamSelected = false;
                showButtons();
            }
            else{
                teamSelected = true;
                int teamNumber = getTeamNumber();
                editor.putInt(PREFS_TEAM, teamNumber);
                editor.apply();
                showButtons();
            }
        }
    };

    private void showButtons(){
        if(eventSelected && nameSelected && teamSelected){
            benchmarkButton.setVisibility(View.VISIBLE);
            viewButton.setVisibility(View.VISIBLE);
            scoutButton.setVisibility(View.VISIBLE);
        }
        else{
            benchmarkButton.setVisibility(View.INVISIBLE);
            viewButton.setVisibility(View.INVISIBLE);
            scoutButton.setVisibility(View.INVISIBLE);
        }
    };

    private void scouterAutoComplete(){
        String[] students = getResources().getStringArray(R.array.students);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,students);
        scouterText.setAdapter(adapter);
    }

    private void downloadEventSpinnerData() {
        final Dialog alert = createDownloadDialog("Please wait while Event data is downloaded...");
        alert.show();

        blueAlliance.loadEventList(COMPETITION_YEAR, new NetworkCallback() {
            @Override
            public void handleFinishDownload(final boolean success) {
                MainScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alert.dismiss();
                        if (!success) {
                            alertUser("Failure", "Did not successfully download event list!").show();
                        }
                    }
                });
            }
        });
    }

                //the warriors blew a 3-1 lead
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

    private AlertDialog createDownloadDialog(String message) {
        return createPleaseWaitDialog(message, R.string.downloading_data);
    }

    private AlertDialog createPleaseWaitDialog(String message, int titleID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titleID);
        builder.setMessage(message);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BlueAlliance.getInstance(MainScreen.this).cancelAll();
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    public void setupEventSpinner() {

        Cursor eventDataCur = dbHelper.createEventNameCursor();
        //Left that here because it's a way to dump all of the data into the console
        //System.out.println(DatabaseUtils.dumpCursorToString(eventDataCur));
        ArrayList<String> eventsWeAreInArray = fillInEventsNeerToday(eventDataCur);
        if(eventFilter) {
            for (int i = (eventsWeAreInArray.size() - 1); 0 <= i; i--) {
                //System.out.println(eventsWeAreInArray.get(i));
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
        eventNamesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventsWeAreInArray); //selected item will look like a spinner set from XML
        eventNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(eventNamesAdapter);
        Spinner spnLocale = (Spinner)findViewById(R.id.eventSpinner);
    }

    private long getTodayInEpoch() {
        Calendar c = Calendar.getInstance();
        long epochToday = c.getTime().getTime();
        return epochToday;
    }
    private ArrayList<String> fillInEventsNeerToday(Cursor eventDataCur){
        ArrayList<String> eventsWeAreInArray = new ArrayList<String>();
        SimpleDateFormat cursorFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long epochToday = getTodayInEpoch();
        try {
            while (eventDataCur.moveToNext()) {
                Date dateObj = null;
                try {
                    String startDateStr = eventDataCur.getString(eventDataCur.getColumnIndex("start_date"));
                    dateObj = cursorFormater.parse(startDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long epoch = dateObj.getTime();

                if(epoch >= epochToday - (ONE_DAY_EPOCH * COMPETITION_Threshold)&& epoch <= epochToday + (ONE_DAY_EPOCH * COMPETITION_Threshold))
                {
                    eventsWeAreInArray.add(eventDataCur.getString(eventDataCur.getColumnIndex("title")));
                }
            }
        } finally {
            eventDataCur.close();
        }
        return eventsWeAreInArray;
    }
}