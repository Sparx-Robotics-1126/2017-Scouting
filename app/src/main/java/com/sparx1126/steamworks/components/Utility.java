package com.sparx1126.steamworks.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.TextView;

import com.sparx1126.steamworks.R;

import org.gosparx.scouting.aerialassist.BenchmarkingData;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;
import org.gosparx.scouting.aerialassist.networking.NetworkCallback;
import org.gosparx.scouting.aerialassist.networking.NetworkHelper;
import org.gosparx.scouting.aerialassist.networking.SparxPosting;

import java.util.Calendar;

import static org.gosparx.scouting.aerialassist.networking.NetworkHelper.isNetworkAvailable;

public class Utility {
    private static Utility utility;
    public static synchronized Utility getInstance(){
        if (utility == null) {
            utility =  new Utility();
        }
        return utility;
    }
    private Activity currentActivity;
    private Utility() {}

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

    public void downloadBenchmarkData(Activity activity, boolean forceDownload) {
        currentActivity = activity;
        // If the internet is available and we haven't gotten the data the download it
        if (!isNetworkAvailable(currentActivity)) {
            utility.alertUser(currentActivity, currentActivity.getString(R.string.no_network), currentActivity.getString(R.string.try_again)).show();
        } else if (NetworkHelper.needToLoadBenchmarkData(currentActivity) || forceDownload) {
            final Dialog alert = utility.createDialog(currentActivity, currentActivity.getString(R.string.downloading_data), currentActivity.getString(R.string.please_wait_benchmarking_download));
            alert.show();
            SparxPosting ss = SparxPosting.getInstance(currentActivity);
            ss.getBenchmarking(new NetworkCallback() {
                @Override
                public void handleFinishDownload(boolean success) {
                    alert.dismiss();
                    if (!success) {
                        utility.alertUser(currentActivity, currentActivity.getString(R.string.failure), currentActivity.getString(R.string.benchmark_download_failed)).show();
                    }
                    else {
                        NetworkHelper.setLoadedBenchmarkData(currentActivity);
                    }
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

    public void downloadScoutingData(Activity activity, boolean forceDownload) {
        currentActivity = activity;
        // If the internet is available and we haven't gotten the data the download it
        if (!isNetworkAvailable(currentActivity)) {
            utility.alertUser(currentActivity, currentActivity.getString(R.string.no_network), currentActivity.getString(R.string.try_again)).show();
        } else if (NetworkHelper.needToLoadBenchmarkData(currentActivity) || forceDownload) {
            final Dialog alert = utility.createDialog(currentActivity, currentActivity.getString(R.string.downloading_data), currentActivity.getString(R.string.please_wait_scouting_download));
            alert.show();
            SparxPosting ss = SparxPosting.getInstance(currentActivity);
            ss.getScouting(new NetworkCallback() {
                @Override
                public void handleFinishDownload(boolean success) {
                    alert.dismiss();
                    if (!success) {
                        utility.alertUser(currentActivity, currentActivity.getString(R.string.failure), currentActivity.getString(R.string.scouting_download_failed)).show();
                    }
                    else {
                        NetworkHelper.setLoadedScoutData(currentActivity);
                    }
                }
            });
        }
    }

    public void uploadPictures(Activity activity) {
        currentActivity = activity;
        if (!isNetworkAvailable(currentActivity)) {
            utility.alertUser(currentActivity, currentActivity.getString(R.string.no_network), currentActivity.getString(R.string.try_again)).show();
        } else {
            final Dialog alert = utility.createDialog(currentActivity, currentActivity.getString(R.string.uploading_data), currentActivity.getString(R.string.please_wait_picture_upload));
            alert.show();
            SparxPosting ss = SparxPosting.getInstance(currentActivity);
            ss.postAllPictures(new NetworkCallback() {
            //ss.postAllPictures2(new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    currentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            if (!success)
                                utility.alertUser(currentActivity, currentActivity.getString(R.string.failure), currentActivity.getString(R.string.picture_upload_failed)).show();
                        }
                    });
                }
            });
        }
    }

    public void downloadPictures(Activity activity, boolean forceDownload) {
        currentActivity = activity;
        // If the internet is available and we haven't gotten the data the download it
        if (!isNetworkAvailable(currentActivity)) {
            utility.alertUser(currentActivity, currentActivity.getString(R.string.no_network), currentActivity.getString(R.string.try_again)).show();
        } else if (NetworkHelper.needToLoadPictures(currentActivity) || forceDownload) {
            final Dialog alert = utility.createDialog(currentActivity, currentActivity.getString(R.string.downloading_data), currentActivity.getString(R.string.please_wait_pictures_download));
            alert.show();
            SparxPosting ss = SparxPosting.getInstance(currentActivity);
            ss.getPictures(new NetworkCallback() {
                @Override
                public void handleFinishDownload(boolean success) {
                    alert.dismiss();
                    if (!success) {
                        utility.alertUser(currentActivity, currentActivity.getString(R.string.failure), currentActivity.getString(R.string.picture_download_failed)).show();
                    }
                    else {
                        NetworkHelper.setLoadedPictures(currentActivity);
                    }
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
