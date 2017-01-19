package com.sparx1126.steamworks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.sparx1126.steamworks.NetworkHelper.isNetworkAvailable;

public class MainScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] regionals = {"Finger Lakes Regional 2017", "Cleveland Regional 2017"};
    private ArrayAdapter<String> cursorAdapterRegionalNames;
    private DatabaseHelper dbHelper;
    private BlueAlliance blueAlliance;
    private Spinner eventPicker;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        blueAlliance = BlueAlliance.getInstance(this);
        dbHelper = DatabaseHelper.getInstance(this);
        eventPicker = (Spinner) findViewById(R.id.eventPicker);
        downloadEventSpinnerDataIfNecessary();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void downloadEventSpinnerDataIfNecessary() {
        if (isNetworkAvailable(this) && NetworkHelper.needToLoadEventList(this)) {
            downloadEventSpinnerData();
        } else {
            setupEventSpinner();
        }
    }

    private void downloadEventSpinnerData() {
        final Dialog alert = createDownloadDialog("Please wait while Event data is downloaded...");
        alert.show();

        blueAlliance.loadEventList(2016, new NetworkCallback() {
            @Override
            public void handleFinishDownload(final boolean success) {
                MainScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alert.dismiss();
                        if (!success)
                            alertUser("Failure", "Did not successfully download event list!").show();
                        setupEventSpinner();
//                            mNavigationDrawerFragment.updateDrawerData();
                    }
                });
            }
        });
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
        Cursor allEventData = dbHelper.createEventNameCursor();
        ArrayList<String> eventsWeAreIn = new ArrayList<String>();
        SimpleDateFormat eventFormater = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        //long epochToday = c.getTime().getTime();
        Date tmpToday = null;
        try {
            tmpToday = eventFormater.parse("2016-11-13");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epochToday = tmpToday.getTime();
        System.out.println("Todays date Epoch => " + epochToday);

        //String eventFormatedDate = eventFormater.format(c.getTime());
        String eventFormatedDate = eventFormater.format(tmpToday.getTime());
        System.out.println("Todays date => " + eventFormatedDate);



        //System.out.println(DatabaseUtils.dumpCursorToString(allEventData));

        try {
            while (allEventData.moveToNext()) {
                SimpleDateFormat cursorFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date dateObj = null;
                try {
                    dateObj = cursorFormater.parse(allEventData.getString(allEventData.getColumnIndex("start_date")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long epoch = dateObj.getTime();
                String newDateStr = eventFormater.format(dateObj);


                //if((epochToday <= (epoch + 259200)) && (epochToday >= (epoch - 86400)))
                long myPastEpoch = epochToday - 345600000;
                long myFutureEpoch = epochToday + 345600000;


                if(epoch >= myPastEpoch && epoch <= myFutureEpoch)
                {
                    //eventsWeAreIn.insertData(allEventData.currentRecord);
                    System.out.println("Event time => " + newDateStr);
                    System.out.println("Event time Epoch=> " + epoch);
                    eventsWeAreIn.add(allEventData.getString(allEventData.getColumnIndex("title")));
                }

            }
        } finally {
            allEventData.close();
        }

        cursorAdapterRegionalNames = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventsWeAreIn); //selected item will look like a spinner set from XML
        cursorAdapterRegionalNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /*cursorAdapterRegionalNames = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                allEventData,
                new String[]{"title"},
                new int[]{android.R.id.text1}, 0);*/

        //bind view to this in order to get its value later
        /*cursorAdapterRegionalNames.setViewBinder(
                new SimpleCursorAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int i) {
                        view.setTag(cursor.getString(cursor.getColumnIndex("key")));
                        if (view instanceof TextView) {
                            ((TextView) view).setText(cursor.getString(i));
                        }
                        return true;
                    }
                }

        );*/
        //check if this is selected
        //eventPicker.setOnItemSelectedListener(this);
        //finally set the value
        eventPicker.setAdapter(cursorAdapterRegionalNames);
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
