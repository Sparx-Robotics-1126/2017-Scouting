package com.sparx1126.steamworks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.TextView;

import org.gosparx.scouting.aerialassist.dto.TeamData;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;
import org.gosparx.scouting.aerialassist.networking.NetworkCallback;
import org.gosparx.scouting.aerialassist.networking.SparxPosting;

import java.util.Calendar;

import static org.gosparx.scouting.aerialassist.networking.NetworkHelper.isNetworkAvailable;

/**
 * Created by Papa on 3/5/17.
 */

public class Utility {
    private static Utility utility;
    public static synchronized Utility getInstance(){
        if (utility == null) {
            utility =  new Utility();
        }
        return utility;
    }
    private Activity currentActivity;
    private Utility() {};

    public AlertDialog alertUser(Activity activity, String title, String reason) {
        currentActivity = activity;
        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        builder.setTitle(title);
        builder.setMessage(reason);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    public AlertDialog createDialog(Activity activity, String title, String reason) {
        currentActivity = activity;
        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        builder.setTitle(title);
        builder.setMessage(reason);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BlueAlliance.getInstance(currentActivity).cancelAll();
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    public void uploadBenchmarkingData(Activity activity) {
        currentActivity = activity;
        if (!isNetworkAvailable(currentActivity)) {
            utility.alertUser(currentActivity, currentActivity.getString(R.string.no_network), currentActivity.getString(R.string.try_again)).show();
        } else {
            final Dialog alert = utility.createDialog(currentActivity, currentActivity.getString(R.string.uploading_data), currentActivity.getString(R.string.please_wait_benchmarking_upload));
            alert.show();
            SparxPosting ss = SparxPosting.getInstance(currentActivity);
            ss.postAllBenchmarking(new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    currentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            if (!success)
                                utility.alertUser(currentActivity, currentActivity.getString(R.string.failure), currentActivity.getString(R.string.benchmark_upload_failed)).show();
                        }
                    });
                }
            });
        }
    }

    public void uploadScoutingData(Activity activity) {
        currentActivity = activity;
        if (!isNetworkAvailable(currentActivity)) {
            utility.alertUser(currentActivity, currentActivity.getString(R.string.no_network), currentActivity.getString(R.string.try_again)).show();
        } else {
            final Dialog alert = utility.createDialog(currentActivity, currentActivity.getString(R.string.uploading_data), currentActivity.getString(R.string.please_wait_scouting_upload));
            alert.show();
            SparxPosting ss = SparxPosting.getInstance(currentActivity);
            ss.postAllScouting(new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    currentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            if (!success)
                                utility.alertUser(currentActivity, currentActivity.getString(R.string.failure), currentActivity.getString(R.string.scouting_upload_failed)).show();
                        }
                    });
                }
            });
        }
    }

    public long getTodayInEpoch() {
        Calendar c = Calendar.getInstance();
        return c.getTime().getTime();
    }

    public void setStringIntoTextView(TextView item, String _value){
        if((_value != null) && !_value.isEmpty()) {
            item.setText(_value);
        }
    }

    public void setDoubleIntoTextView(TextView item, double value){
        if(value != Double.MAX_VALUE) {
            setStringIntoTextView(item, String.valueOf(value));
        }
    }

    public void setIntegerIntoTextView(TextView item, int value){
        if(value != Integer.MAX_VALUE) {
            setStringIntoTextView(item, String.valueOf(value));
        }
    }
}
