package com.sparx1126.steamworks.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.sparx1126.steamworks.R;

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

    public AlertDialog alertUser(Context context, String title, String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    public AlertDialog createDialog(final Context context, String title, String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(reason);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BlueAlliance.getInstance(context).cancelAll();
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    public void downloadEventsWeAreIn(final Activity activity, boolean forceDownload, final NetworkCallback callback) {
        if (NetworkHelper.needToLoadEventsWeAreInList(activity) || forceDownload) {
            if (!isNetworkAvailable(activity)) {
                utility.alertUser(activity, activity.getString(R.string.no_network), activity.getString(R.string.try_again)).show();
            } else {
                final Dialog alert = utility.createDialog(activity, activity.getString(R.string.downloading_data), activity.getString(R.string.please_wait_event_we_are_in_download));
                alert.show();
                BlueAlliance blueAlliance = BlueAlliance.getInstance(activity);
                blueAlliance.loadEventsWeAreInList(activity.getResources().getString(R.string.competition_year), new NetworkCallback() {
                    @Override
                    public void handleFinishDownload(final boolean success) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert.dismiss();
                                if (!success) {
                                    utility.alertUser(activity, activity.getString(R.string.failure), activity.getString(R.string.event_download_failed)).show();
                                }
                                callback.handleFinishDownload(success);
                            }
                        });
                    }
                });
            }
        }
    }

    public void downloadEventsData(final Activity activity, boolean forceDownload, final NetworkCallback callback) {
        if (NetworkHelper.needToLoadEventList(activity) || forceDownload) {
            if (!isNetworkAvailable(activity)) {
                utility.alertUser(activity, activity.getString(R.string.no_network), activity.getString(R.string.try_again)).show();
            } else {
                final Dialog alert = utility.createDialog(activity, activity.getString(R.string.downloading_data), activity.getString(R.string.please_wait_event_download));
                alert.show();
                BlueAlliance blueAlliance = BlueAlliance.getInstance(activity);
                blueAlliance.loadEventList(activity.getResources().getString(R.string.competition_year), new NetworkCallback() {
                    @Override
                    public void handleFinishDownload(final boolean success) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert.dismiss();
                                if (!success) {
                                    utility.alertUser(activity, activity.getString(R.string.failure), activity.getString(R.string.event_download_failed)).show();
                                }
                                callback.handleFinishDownload(success);
                            }
                        });
                    }
                });
            }
        }
    }

    public void uploadBenchmarkingData(final Activity activity, boolean errorOnNoNetwork) {
        if (isNetworkAvailable(activity)) {
            final Dialog alert = utility.createDialog(activity, activity.getString(R.string.uploading_data), activity.getString(R.string.please_wait_benchmarking_upload));
            alert.show();
            SparxPosting ss = SparxPosting.getInstance(activity);
            ss.postAllBenchmarking(new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            if (!success)
                                utility.alertUser(activity, activity.getString(R.string.failure), activity.getString(R.string.benchmark_upload_failed)).show();
                        }
                    });
                }
            });
        } else if(errorOnNoNetwork) {
            utility.alertUser(activity, activity.getString(R.string.no_network), activity.getString(R.string.try_again)).show();
        } else {
            utility.alertUser(activity, activity.getString(R.string.saved), activity.getString(R.string.locally)).show();
        }
    }

    public void downloadBenchmarkData(final Context context, boolean forceDownload, final NetworkCallback callback) {
        if (NetworkHelper.needToLoadBenchmarkData(context) || forceDownload) {
            if (!isNetworkAvailable(context)) {
                utility.alertUser(context, context.getString(R.string.no_network), context.getString(R.string.try_again)).show();
            } else {
                final Dialog alert = utility.createDialog(context, context.getString(R.string.downloading_data), context.getString(R.string.please_wait_benchmarking_download));
                alert.show();
                SparxPosting ss = SparxPosting.getInstance(context);
                ss.getBenchmarking(new NetworkCallback() {
                    @Override
                    public void handleFinishDownload(boolean success) {
                        alert.dismiss();
                        if (!success) {
                            utility.alertUser(context, context.getString(R.string.failure), context.getString(R.string.benchmark_download_failed)).show();
                        }
                        if (callback != null) {
                            callback.handleFinishDownload(success);
                        }
                    }
                });
            }
        }
    }

    public void uploadScoutingData(final Activity activity, boolean errorOnNoNetwork) {
        if (isNetworkAvailable(activity)) {
            final Dialog alert = utility.createDialog(activity, activity.getString(R.string.uploading_data), activity.getString(R.string.please_wait_scouting_upload));
            alert.show();
            SparxPosting ss = SparxPosting.getInstance(activity);
            ss.postAllScouting(new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            if (!success)
                                utility.alertUser(activity, activity.getString(R.string.failure), activity.getString(R.string.scouting_upload_failed)).show();
                        }
                    });
                }
            });
        } else if (errorOnNoNetwork) {
            utility.alertUser(activity, activity.getString(R.string.no_network), activity.getString(R.string.try_again)).show();
        } else {
            utility.alertUser(activity, activity.getString(R.string.saved), activity.getString(R.string.locally)).show();
        }
    }

    public void downloadScoutingData(final Context context, boolean forceDownload, final NetworkCallback callback) {
        if (NetworkHelper.needToLoadScoutData(context) || forceDownload) {
            if (!isNetworkAvailable(context)) {
                utility.alertUser(context, context.getString(R.string.no_network), context.getString(R.string.try_again)).show();
            } else {
                final Dialog alert = utility.createDialog(context, context.getString(R.string.downloading_data), context.getString(R.string.please_wait_scouting_download));
                alert.show();
                SparxPosting ss = SparxPosting.getInstance(context);
                ss.getScouting(new NetworkCallback() {
                    @Override
                    public void handleFinishDownload(boolean success) {
                        alert.dismiss();
                        if (!success) {
                            utility.alertUser(context, context.getString(R.string.failure), context.getString(R.string.scouting_download_failed)).show();
                        }
                        if (callback != null) {
                            callback.handleFinishDownload(success);
                        }
                    }
                });
            }
        }
    }

    public void uploadPictures(final Activity activity, boolean errorOnNoNetwork) {
        if (isNetworkAvailable(activity)) {
            final Dialog alert = utility.createDialog(activity, activity.getString(R.string.uploading_data), activity.getString(R.string.please_wait_picture_upload));
            alert.show();
            SparxPosting ss = SparxPosting.getInstance(activity);
            ss.postAllPictures(new NetworkCallback() {
                //ss.postAllPictures2(new NetworkCallback() {
                @Override
                public void handleFinishDownload(final boolean success) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            if (!success)
                                utility.alertUser(activity, activity.getString(R.string.failure), activity.getString(R.string.picture_upload_failed)).show();
                        }
                    });
                }
            });
        } else if (errorOnNoNetwork) {
            utility.alertUser(activity, activity.getString(R.string.no_network), activity.getString(R.string.try_again)).show();
        }
    }

    public void downloadPictures(final Context context, boolean forceDownload, final NetworkCallback callback) {
        if (NetworkHelper.needToLoadPictures(context) || forceDownload) {
            if (!isNetworkAvailable(context)) {
                utility.alertUser(context, context.getString(R.string.no_network), context.getString(R.string.try_again)).show();
            } else {
                final Dialog alert = utility.createDialog(context, context.getString(R.string.downloading_data), context.getString(R.string.please_wait_pictures_download));
                alert.show();
                SparxPosting ss = SparxPosting.getInstance(context);
                ss.getPictures(new NetworkCallback() {
                    @Override
                    public void handleFinishDownload(boolean success) {
                        alert.dismiss();
                        if (!success) {
                            utility.alertUser(context, context.getString(R.string.failure), context.getString(R.string.picture_download_failed)).show();
                        }
                        if(callback != null) {
                            callback.handleFinishDownload(success);
                        }
                    }
                });
            }
        }
    }

    public long getEpoch() {
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
