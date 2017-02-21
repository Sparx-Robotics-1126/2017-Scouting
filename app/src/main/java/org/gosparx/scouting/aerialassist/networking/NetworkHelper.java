package org.gosparx.scouting.aerialassist.networking;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.Date;

public class NetworkHelper {

    private static final long MS_IN_DAY = 86400000;

    private static final String PREF_FILE_NAME = "NETWORK_PREFERENCES";
    private static final String PREF_LOAD_EVENT_LIST = "LOAD_EVENT_LIST";
    private static final String PREF_LOAD_MATCH = "LOAD_MATCH";
    private static final String PREF_LOAD_TEAMS = "LOAD_TEAMS";

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }


    public static boolean needToLoadEventList(Context context){
        long timeOfLastSync = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .getLong(PREF_LOAD_EVENT_LIST, 0);

        return new Date().after(new Date(timeOfLastSync + NetworkHelper.MS_IN_DAY));
    }

    public static boolean needToLoadMatches(Context c){

        long timeOfLastSync = c.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .getLong(PREF_LOAD_MATCH, 0);

        return new Date().after(new Date(timeOfLastSync + NetworkHelper.MS_IN_DAY));
    }

    public static  boolean needToLoadTeams(Context c){
        long timeOfLastSync = c.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .getLong(PREF_LOAD_TEAMS, 0);
        return new Date().after(new Date(timeOfLastSync + NetworkHelper.MS_IN_DAY));
    }

    static void setLoadedEventList(Context c){
        setPrefCurrentTime(c, PREF_LOAD_EVENT_LIST);
    }

    static void setLoadedMatchList(Context c){
        setPrefCurrentTime(c, PREF_LOAD_MATCH);
    }
    static void setLoadedTeams(Context c){
        setPrefCurrentTime(c, PREF_LOAD_TEAMS);
    }

    private static void setPrefCurrentTime(Context context, String pref){
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putLong(pref, new Date().getTime())
                .apply();
    }
}
