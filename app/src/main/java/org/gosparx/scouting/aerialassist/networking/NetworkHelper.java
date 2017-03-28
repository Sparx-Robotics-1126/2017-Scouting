package org.gosparx.scouting.aerialassist.networking;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.Date;

public class NetworkHelper {

    private static final long MS_IN_DAY = 86400000;

    public static final String PREF_LOAD_MATCH = "LOAD_MATCH";
    private static final String PREF_FILE_NAME = "NETWORK_PREFERENCES";
    private static final String PREF_LOAD_EVENT_LIST = "LOAD_EVENT_LIST";
    private static final String PREF_LOAD_TEAM_LIST = "LOAD_TEAM_LIST";
    private static final String PREF_LOAD_BENCH_DATA = "LOAD_BENCH_DATA";
    private static final String PREF_LOAD_SCOUTING_DATA = "LOAD_SCOUTING_DATA";
    private static final String PREF_LOAD_PICTURES = "LOAD_PICTURES";
    private static final String PREF_LOAD_SCOUT_DATA = "LOAD_SCOUT_DATA";

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static boolean needToLoadEventList(Context context){
        long timeOfLastSync = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .getLong(PREF_LOAD_EVENT_LIST, 0);
        return new Date().after(new Date(timeOfLastSync + NetworkHelper.MS_IN_DAY));
    }

    public static boolean needToLoadTeamList(Context context){
        long timeOfLastSync = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .getLong(PREF_LOAD_TEAM_LIST, 0);
        return new Date().after(new Date(timeOfLastSync + NetworkHelper.MS_IN_DAY));
    }

    public static boolean needToLoadBenchmarkData(Context context){
        long timeOfLastSync = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .getLong(PREF_LOAD_BENCH_DATA, 0);
        return new Date().after(new Date(timeOfLastSync + NetworkHelper.MS_IN_DAY));
    }

    public static boolean needToLoadPictures(Context context){
        long timeOfLastSync = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .getLong(PREF_LOAD_PICTURES, 0);
        return new Date().after(new Date(timeOfLastSync + NetworkHelper.MS_IN_DAY));
    }

    public static boolean needToLoadScoutData(Context context){
        long timeOfLastSync = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .getLong(PREF_LOAD_SCOUTING_DATA, 0);
        return new Date().after(new Date(timeOfLastSync + NetworkHelper.MS_IN_DAY));
    }

    public static boolean needToLoadMatches(Context c){

        long timeOfLastSync = c.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .getLong(PREF_LOAD_MATCH, 0);

        return new Date().after(new Date(timeOfLastSync + NetworkHelper.MS_IN_DAY));
    }

    public static void setLoadedEventList(Context c){
        setPrefCurrentTime(c, PREF_LOAD_EVENT_LIST);
    }

    public static void setLoadedTeamList(Context c){
        setPrefCurrentTime(c, PREF_LOAD_TEAM_LIST);
    }

    public static void setLoadedBenchmarkData(Context c){
        setPrefCurrentTime(c, PREF_LOAD_BENCH_DATA);
    }

    public static void setLoadedPictures(Context c){
        setPrefCurrentTime(c, PREF_LOAD_PICTURES);
    }

    public static void setLoadedScoutData(Context c){
        setPrefCurrentTime(c, PREF_LOAD_SCOUT_DATA);
    }

    public static void setLoadedMatchList(Context c){
        setPrefCurrentTime(c, PREF_LOAD_MATCH);
    }

    private static void setPrefCurrentTime(Context context, String pref){
        context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putLong(pref, new Date().getTime())
                .apply();
    }
}
