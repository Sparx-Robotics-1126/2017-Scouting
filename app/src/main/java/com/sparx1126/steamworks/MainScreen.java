package com.sparx1126.steamworks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import static com.sparx1126.steamworks.NetworkHelper.isNetworkAvailable;

public class MainScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] regionals={"Finger Lakes Regional 2017", "Cleveland Regional 2017"};
    private SimpleCursorAdapter cursorAdapterRegionalNames;
    private DatabaseHelper dbHelper;
    private BlueAlliance blueAlliance;
    private Spinner eventPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        blueAlliance = BlueAlliance.getInstance(this);
        dbHelper = DatabaseHelper.getInstance(this);
        eventPicker = (Spinner) findViewById(R.id.eventPicker);
        downloadEventSpinnerDataIfNecessary();
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
        Cursor filteredEventData;

        //System.out.println(DatabaseUtils.dumpCursorToString(myData));

        try {
            while (allEventData.moveToNext()) {
                System.out.println(allEventData.getString(allEventData.getColumnIndex("start_date")));
            }
        } finally {
            allEventData.close();
        }

        //String myString = DatabaseUtils.dumpCursorToString(myData);
        //System.out.println(myString);

        /*cursorAdapterRegionalNames = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                myData,
                new String[]{"title"},
                new int[]{android.R.id.text1}, 0);

        //bind view to this in order to get its value later
        cursorAdapterRegionalNames.setViewBinder(
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

        );
        //check if this is selected
        eventPicker.setOnItemSelectedListener(this);
        //finally set the value
        eventPicker.setAdapter(cursorAdapterRegionalNames);*/
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
}
