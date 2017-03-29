package com.sparx1126.steamworks;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.sparx1126.steamworks.components.Utility;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;
import org.gosparx.scouting.aerialassist.networking.NetworkCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.gosparx.scouting.aerialassist.networking.NetworkHelper.isNetworkAvailable;

public class AdminScreen extends AppCompatActivity {
    private static final String TAG = "AdminScreen";
    private Utility utility;
    private DatabaseHelper dbHelper;
    private SharedPreferences settings;
    private Spinner eventSpinner;
    private ArrayAdapter<String> eventAdapter;
    private List<String> eventsNearTodayList;
    private List<String> eventsWeAreInList;
    private List<String> eventSpinnerList;
    private Map<String, String> eventNameToKeyMap;
    private boolean eventSelected = false;
    private boolean eventFilter = true;
    private ToggleButton blueSelectedToggle;
    private RadioButton team1Button;
    private RadioButton team2Button;
    private RadioButton team3Button;
    private boolean teamSelected = false;
    private Button stuffSelectedButton;
    private static final int COMPETITION_THRESHOLD = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_screen);

        utility = Utility.getInstance();
        dbHelper = DatabaseHelper.getInstance(this);
        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);

        eventSpinner = (Spinner) findViewById(R.id.eventSpinner);
        eventSpinner.setOnTouchListener(spinnerOnTouch);
        eventSpinner.setOnItemSelectedListener(spinnerOnItemClick);

        eventsNearTodayList = new ArrayList<>();
        eventsWeAreInList = new ArrayList<>();
        eventSpinnerList = new ArrayList<>();
        eventNameToKeyMap = new HashMap<>();

        blueSelectedToggle = (ToggleButton) findViewById(R.id.blueSelectedToggle);

        team1Button = (RadioButton) findViewById(R.id.team1);
        team1Button.setOnClickListener(teamListener);
        team2Button = (RadioButton) findViewById(R.id.team2);
        team2Button.setOnClickListener(teamListener);
        team3Button = (RadioButton) findViewById(R.id.team3);
        team3Button.setOnClickListener(teamListener);

        stuffSelectedButton = (Button) findViewById(R.id.selectStuff);
        stuffSelectedButton.setOnClickListener(selectStuffClicked);
        stuffSelectedButton.setVisibility(View.INVISIBLE);

        restorePreferences();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh_events:
                refresh(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getEventName() {
        String eventName = "";
        if (eventSpinner.getSelectedItem() != null) {
            eventName = eventSpinner.getSelectedItem().toString();
        }
        return eventName;
    }

    private void restorePreferences() {
        String eventName = settings.getString(getResources().getString(R.string.pref_event), "");
        if (!eventName.isEmpty() && (eventAdapter != null)) {
            eventFilter = settings.getBoolean(getResources().getString(R.string.pref_event_filter), true);
            fillInEvents(dbHelper.createEventsWeAreInNameCursor(), eventsWeAreInList);
            fillInEvents(dbHelper.createAllEventsCursor(), eventsNearTodayList);
            if(eventFilter) {
                setupEventSpinner(eventsWeAreInList);
            }
            else {
                setupEventSpinner(eventsNearTodayList);
            }
            eventSpinner.setSelection(eventAdapter.getPosition(eventName));
        }

        int teamIndex = settings.getInt(getResources().getString(R.string.team_selected), Integer.MAX_VALUE);
        if (teamIndex == 0) {
            team1Button.setChecked(true);
        } else if (teamIndex == 1) {
            team2Button.setChecked(true);
        } else if (teamIndex == 2) {
            team3Button.setChecked(true);
        }
    }

    private void refresh(final boolean force) {

        utility.downloadAllEvents(this, force, new NetworkCallback() {
            @Override
            public void handleFinishDownload(final boolean success) {
                AdminScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (success) {
                            fillInEvents(dbHelper.createAllEventsCursor(), eventsNearTodayList);
                            utility.downloadEventsWeAreIn(AdminScreen.this, force, new NetworkCallback() {
                                @Override
                                public void handleFinishDownload(final boolean success) {
                                    AdminScreen.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (success) {
                                                Dialog alert = utility.createDialog(AdminScreen.this, getString(R.string.downloading_data), getString(R.string.please_wait_team_download));
                                                alert.show();
                                                fillInEvents(dbHelper.createEventsWeAreInNameCursor(), eventsWeAreInList);
                                                downloadTeamData(dbHelper.createEventsWeAreInNameCursor(), alert);
                                            }
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

    private final View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (getEventName().isEmpty() && (event.getAction() == MotionEvent.ACTION_UP)) {
                setupEventSpinner(eventsWeAreInList);
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
                    Dialog alert = utility.createDialog(AdminScreen.this, getString(R.string.downloading_data), getString(R.string.please_wait_team_download));
                    alert.show();
                    downloadTeamData(dbHelper.createAllEventsCursor(), alert);
                    setupEventSpinner(eventsNearTodayList);
                } else if (getEventName().contentEquals(getResources().getString(R.string.filter_on))) {
                    eventSelected = false;
                    eventFilter = true;
                    setupEventSpinner(eventsWeAreInList);
                } else {
                    eventSelected = true;
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(getResources().getString(R.string.pref_event), getEventName());
                    editor.putBoolean(getResources().getString(R.string.pref_event_filter), eventFilter);
                    editor.putString(getResources().getString(R.string.pref_event_key), eventNameToKeyMap.get(getEventName()));
                    editor.apply();
                    Log.e(TAG, "Selected event - " + getEventName());
                }

                showButtons();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void setupEventSpinner(List<String> eventList) {
        eventSpinnerList.clear();
        for (String eventName : eventList) {
            eventSpinnerList.add(eventName);
        }
        if (eventFilter) {
            eventSpinnerList.add(getResources().getString(R.string.filter_off));
        } else {
            eventSpinnerList.add(getResources().getString(R.string.filter_on));
        }
        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventSpinnerList);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(eventAdapter);
    }

    private void fillInEvents(Cursor eventDataCur, List<String> events) {
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
                    long window = ONE_DAY_EPOCH * COMPETITION_THRESHOLD;
                    if ((epoch >= (epochToday - window)) && (epoch <= (epochToday + window))) {
                        String eventName = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_TITLE));
                        String eventKey = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_KEY));
                        events.add(eventName);
                        eventNameToKeyMap.put(eventName, eventKey);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            eventDataCur.close();
        }
    }

    private final View.OnClickListener teamListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int teamIndex = Integer.MAX_VALUE;
            if (team1Button.isChecked()) {
                teamIndex = 0;
            } else if (team2Button.isChecked()) {
                teamIndex = 1;
            } else if (team3Button.isChecked()) {
                teamIndex = 2;
            }

            if (teamIndex != Integer.MAX_VALUE) {
                teamSelected = true;
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt(getString(R.string.team_selected), teamIndex);
                editor.apply();
                Log.e(TAG, "Selected teamIndex - " + String.valueOf(teamIndex));
            }
            else {
                teamSelected = false;
            }

            showButtons();
        }
    };

    private final View.OnClickListener selectStuffClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean blueSelected = blueSelectedToggle.isChecked();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(getString(R.string.red_alliance), !blueSelected);
            editor.apply();
            Log.e(TAG, "Selected red alliance - " + String.valueOf(!blueSelected));

            finish();
        }
    };

    private void downloadTeamData(Cursor eventDataCur, final Dialog alert) {
        if (eventDataCur.moveToNext()) {
            if (!isNetworkAvailable(this)) {
                utility.alertUser(this, getString(R.string.no_network), getString(R.string.try_again)).show();
            } else {
                String eventName = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_TITLE));
                final Cursor remainder = eventDataCur;
                if (eventNameToKeyMap.containsKey(eventName)) {
                    String eventKey = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_KEY));
                    BlueAlliance.getInstance(this).loadTeams(dbHelper.getEvent(eventKey), new NetworkCallback() {
                        @Override
                        public void handleFinishDownload(final boolean success) {
                            AdminScreen.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (success) {
                                        downloadTeamData(remainder, alert);
                                    } else {
                                        utility.alertUser(AdminScreen.this, getString(R.string.failure), getString(R.string.team_download_failed)).show();
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
            Dialog alertM = utility.createDialog(AdminScreen.this, getString(R.string.downloading_data), getString(R.string.please_wait_match_download));
            alertM.show();
            if (eventFilter) {
                downloadMatchData(dbHelper.createEventsWeAreInNameCursor(), alertM);
            } else {
                downloadMatchData(dbHelper.createAllEventsCursor(), alertM);
            }
        }
    }

    private void downloadMatchData(Cursor eventDataCur, final Dialog alert) {
        if (eventDataCur.moveToNext()) {
            if (!isNetworkAvailable(this)) {
                utility.alertUser(this, getString(R.string.no_network), getString(R.string.try_again)).show();
            } else {
                String eventName = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_TITLE));
                final Cursor remainder = eventDataCur;
                if (eventNameToKeyMap.containsKey(eventName)) {
                    String eventKey = eventDataCur.getString(eventDataCur.getColumnIndex(DatabaseHelper.TABLE_EVENTS_KEY));
                    BlueAlliance.getInstance(this).loadMatches(dbHelper.getEvent(eventKey), new NetworkCallback() {
                        @Override
                        public void handleFinishDownload(final boolean success) {
                            AdminScreen.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (success) {
                                        downloadMatchData(remainder, alert);
                                    } else {
                                        utility.alertUser(AdminScreen.this, getString(R.string.failure), getString(R.string.match_download_failed)).show();
                                    }
                                }

                            });
                        }
                    });
                } else {
                    downloadMatchData(remainder, alert);
                }
            }
        } else {
            eventDataCur.close();
            alert.dismiss();
            utility.downloadBenchmarkData(this, false, new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    AdminScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (success) {
                                utility.downloadScoutingData(AdminScreen.this, false, new NetworkCallback() {
                                    @Override
                                    public void handleFinishDownload(final boolean success) {
                                        AdminScreen.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
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

    private void showButtons() {
        if (eventSelected && teamSelected) {
            stuffSelectedButton.setVisibility(View.VISIBLE);
        } else {
            stuffSelectedButton.setVisibility(View.INVISIBLE);
        }
    }
}
