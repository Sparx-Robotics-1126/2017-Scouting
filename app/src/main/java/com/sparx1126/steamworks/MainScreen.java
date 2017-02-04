package com.sparx1126.steamworks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

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
import java.util.Map;

import static org.gosparx.scouting.aerialassist.networking.NetworkHelper.isNetworkAvailable;

public class MainScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button benchmarkAuto;
    private Button view;
    private Button scout;
    private ArrayAdapter<String> cursorAdapterRegionalNames;
    private DatabaseHelper dbHelper;
    private BlueAlliance blueAlliance;
    private Spinner eventPicker;
    private static long ONE_DAY_EPOCH = 86400000;
    private AutoCompleteTextView scouter;
    private AutoCompleteTextView team;
    boolean eventSelected = false;
    boolean nameSelected = false;
    boolean teamSelected = false;
    public static final int COMPETITION_YEAR = 2017;
    public static final int COMPETITION_Threshold = 44;
    public static final String PREFS_NAME = "Sparx-prefs";
    public static final String PREFS_SCOUTER = "scouterName";
    public static final String PREFS_EVENT = "Sparx-prefs";
    public static final String PREFS_Event_SELECTED = "eventSelected";
    public static final String SCOUTER_INFO = "ScouterInfo";
    private Map scoutingInfos;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
private String getName(){
    return scouter.getText().toString();
}
    //Kevin is watching ( ͡° ͜ʖ ͡°)
    //this push thing isn't working ;-;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        blueAlliance = BlueAlliance.getInstance(this);
        dbHelper = DatabaseHelper.getInstance(this);
        if (isNetworkAvailable(this) && NetworkHelper.needToLoadEventList(this)) {
            downloadEventSpinnerData();
        }
        scoutingInfos = new HashMap();

        scout = (Button)findViewById(R.id.scout);
        scout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
        scout.setVisibility(View.INVISIBLE);
        benchmarkAuto = (Button)findViewById(R.id.benchmarkAuto);
        benchmarkAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);

            }
        });
        benchmarkAuto.setVisibility(View.INVISIBLE);
        view = (Button)findViewById(R.id.view_data);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);

            }
        });
        view.setVisibility(View.INVISIBLE);

        scouter = (AutoCompleteTextView) findViewById(R.id. scouter);
        scouter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void afterTextChanged(Editable s) {
                String[] students = getResources().getStringArray(R.array.students);
                if(Arrays.asList(students).contains(scouter.getEditableText().toString())){
                    nameSelected = true;
                    showButtons();
                    savePreferences();
                }
                else{
                    benchmarkAuto.setVisibility(View.INVISIBLE);
                    scout.setVisibility(View.INVISIBLE);
                    view.setVisibility(View.INVISIBLE);
                }
            }
        });

        team = (AutoCompleteTextView) findViewById(R.id. team);
        team.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                teamSelected = true;
                showButtons();
            }
        });

        eventPicker = (Spinner) findViewById(R.id.eventPicker);
        scouterAutoComplete();
        eventPicker.setOnTouchListener(spinnerOnTouch);
        restorePreferences();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        String scouterName = getName();
        editor.putString(PREFS_SCOUTER, scouterName);
        editor.apply();
    }
    private void restorePreferences(){
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String scouterName = settings.getString(PREFS_SCOUTER, "");
        scouter.setText(scouterName);
    }
//switching screens
    public void buttonClicked(View view) {
        Context context = MainScreen.this;
        Class destination = null;


        switch (view.getId()) {
            case R.id.benchmarkAuto:
                destination = BenchmarkScreen.class;
                break;
            case R.id.scout:
                destination = ScoutingScreen.class;
                break;
            case R.id.view_data:
                destination = ViewScreen.class;
                break;
        }

        // set the currentScouting object I intend to pass to NOTHING
        ScoutingInfo currentInfo = null;
        // get the object editable from the team number text field on the screen
        Editable editable = team.getText();
        // convert that object to just a sequence of characters (i.e. "1126")
        String teamNumberInAWord = editable.toString();
        // look for i.e. "1126" in my map of already scouted teams
        if (scoutingInfos.containsKey(teamNumberInAWord)) {
            // set my temporary variable of scouting info to the one I found inside the map
            currentInfo = (ScoutingInfo) scoutingInfos.get(teamNumberInAWord);
        } else {
            // create a new scouting info because I did not find it in my map
            // which means it hasn't been scouted before
            currentInfo = new ScoutingInfo();
            currentInfo.setEventKey();
            // add the new scouting info into my map so that I can find it in the future
            scoutingInfos.put(team.getText().toString(), currentInfo);
        }

        Intent intent = new Intent(context, destination);
        intent.putExtra(SCOUTER_INFO, currentInfo);
        startActivity(intent);
    }

    private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP ) {
                System.out.println("your code there");
                setupEventSpinner();

                eventSelected = true;
                showButtons();
            }
            return false;
        }
    };

    private void showButtons(){
        if(eventSelected && nameSelected && teamSelected){
            benchmarkAuto.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            scout.setVisibility(View.VISIBLE);
        }
    };


    private void scouterAutoComplete(){
        String[] students = getResources().getStringArray(R.array.students);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,students);
        scouter.setAdapter(adapter);

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
        cursorAdapterRegionalNames = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventsWeAreInArray); //selected item will look like a spinner set from XML
        cursorAdapterRegionalNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventPicker.setAdapter(cursorAdapterRegionalNames);
        Spinner spnLocale = (Spinner)findViewById(R.id.eventPicker);

        /*spnLocale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("selected");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("not selected");
                return;
            }
        });*/
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

    @Override
    /**
     * check for selected items in spinners
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Event current = dbHelper.getEvent((String) eventPicker.getSelectedView().getTag());
    }

    @Override
    public void onNothingSelected(AdapterView<?> a) {
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("MainScreen Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


}
