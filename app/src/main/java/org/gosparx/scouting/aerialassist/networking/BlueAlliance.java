package org.gosparx.scouting.aerialassist.networking;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sparx1126.steamworks.R;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.Event;
import org.gosparx.scouting.aerialassist.dto.Match;
import org.gosparx.scouting.aerialassist.dto.Team;

import java.util.List;

public class BlueAlliance {
    private static final String GET_MATCH_LIST = "/api/v2/event/{EVENT_KEY}/matches";
    private static final String TAG = "BlueAlliance";
    private static final String BASE_URL = "https://www.thebluealliance.com";
    private static final String GET_EVENT_LIST = "/api/v2/events/{YEAR}";
    private static final String GET_EVENT_WE_ARE_IN_LIST = "/api/v2/team/{TEAM_KEY}/{YEAR}/events";
    private static final String GET_TEAM_LIST = "/api/v2/event/{EVENT_KEY}/teams";
    private static BlueAlliance blueAlliance;
    private Context context;
    private final Ion ion;
    private String versionName;
    private final DatabaseHelper dbHelper;
    private BlueAlliance(Context context){
        this.context = context;
        ion = Ion.getInstance(context, TAG);
        ion.configure().setLogging(TAG, Log.INFO);
        ion.getHttpClient().getSocketMiddleware().setMaxConnectionCount(25);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        ion.configure().setGson(gson);
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        }catch (PackageManager.NameNotFoundException e){
            Log.e(TAG, "Could not find version name.", e);
            versionName = "UNKNOWN";
        }
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public static synchronized BlueAlliance getInstance(Context c) {
        if (blueAlliance == null)
            blueAlliance = new BlueAlliance(c);
        else
            blueAlliance.context = c;
        return blueAlliance;
    }

    public void cancelAll(){
        ion.cancelAll();
    }

    public void loadMatches(final Event event){loadMatches(event, null);}
    public void loadMatches(final Event event, final NetworkCallback callback){
        String request = (BASE_URL+GET_MATCH_LIST).replace("{EVENT_KEY}", event.getKey());
        //String request = (BASE_URL+GET_MATCH_LIST).replace("{EVENT_KEY}", "2016ohcl");
        Ion.with(context)
                .load(request)
                .addHeader("X-TBA-App-Id", "frc1126:scouting-app-2017:" + versionName)
                .noCache()
                .as(new TypeToken<List<Match>>(){})
                .setCallback(new FutureCallback<List<Match>>() {
                    @Override
                    public void onCompleted(Exception e, List<Match> result) {
                        if( e != null){
                            Log.e(TAG, "Issue getting matches from event("+event.getKey()+")", e);

                            if(callback != null)
                                callback.handleFinishDownload(false);
                            return;
                        }
                        for (Match match : result) {
                            System.out.println(match.getMatchNumber());
                            if (dbHelper.doesMatchExist(match))
                                dbHelper.updateMatch(match);
                            else {
                                dbHelper.createMatch(match);
                            }
                        }

                        if(callback != null)
                            callback.handleFinishDownload(true);
                        NetworkHelper.setLoadedMatchList(context);
                    }
                });
    }

    public void loadEventList(String year, final NetworkCallback callback){
        String request = (BASE_URL+GET_EVENT_LIST).replace("{YEAR}", year);
        Ion.with(context)
                .load(request)
                .addHeader(context.getString(R.string.X_TBA), context.getString(R.string.scout_screen_app) + versionName)
                .as(new TypeToken<List<Event>>(){})
                .setCallback(new FutureCallback<List<Event>>() {
                    @Override
                    public void onCompleted(Exception e, final List<Event> result) {
                        if(e != null){
                            Log.e(TAG, "Issue getting event list", e);
                            if(callback != null)
                                callback.handleFinishDownload(false);
                            return;
                        }

                        for(Event event : result) {
                            if(dbHelper.doesEventExist(event))
                                dbHelper.updateEvent(event);
                            else
                                dbHelper.createEvent(event);

                            Log.d(TAG, "Done getting event("+event.getEventCode()+")");
                        }
                        NetworkHelper.setLoadedEventList(context);
                        if(callback != null)
                            callback.handleFinishDownload(true);
                    }
                });
    }

    public void loadEventsWeAreInList(String year, final NetworkCallback callback){
        String request = ((BASE_URL+GET_EVENT_WE_ARE_IN_LIST).replace("{YEAR}", year)).replace("{TEAM_KEY}", context.getString(R.string.frc_1126));
        Ion.with(context)
                .load(request)
                .addHeader(context.getString(R.string.X_TBA), context.getString(R.string.scout_screen_app) + versionName)
                .as(new TypeToken<List<Event>>(){})
                .setCallback(new FutureCallback<List<Event>>() {
                    @Override
                    public void onCompleted(Exception e, final List<Event> result) {
                        if(e != null){
                            Log.e(TAG, context.getString(R.string.issue_event_we_are_in), e);
                            if(callback != null)
                                callback.handleFinishDownload(false);
                            return;
                        }

                        for(Event event : result) {
                            if(dbHelper.doesEventsWeAreInExist(event))
                                dbHelper.updateEventsWeAreIn(event);
                            else
                                dbHelper.createEventsWeAreIn(event);

                            Log.d(TAG, "Done getting event we are in("+event.getEventCode()+")");
                        }
                        NetworkHelper.setLoadedEventsWeAreInList(context);
                        if(callback != null)
                            callback.handleFinishDownload(true);
                    }
                });
    }


    public void loadTeams(final Event event, final NetworkCallback callback){
        String request = (BASE_URL+GET_TEAM_LIST).replace("{EVENT_KEY}", event.getKey());
        System.out.println(request);
        Ion.with(context)
                .load(request)
                .addHeader(context.getString(R.string.X_TBA), context.getString(R.string.scout_screen_app) + versionName)
                .as(new TypeToken<List<Team>>() {})
                .setCallback(new FutureCallback<List<Team>>() {
                    @Override
                    public void onCompleted(Exception e, List<Team> result) {
                        if(e != null || result == null){
                            Log.e(TAG, "Issue getting teams from event("+event.getKey()+")", e);

                            if(callback != null)
                                callback.handleFinishDownload(false);
                            return;
                        }
                        for(Team team : result){
                            if(dbHelper.doesTeamExist(team))
                                dbHelper.updateTeam(team);
                            else
                                dbHelper.createTeam(team);

                            Log.d(TAG, "Done getting team ("+team.getTeamNumber()+")");

                            if(!dbHelper.doesE2TAssociationExist(event.getKey(), team.getKey()))
                                dbHelper.createE2TAssociation(event.getKey(), team.getKey());
                        }
                        NetworkHelper.setLoadedTeamList(context);
                        if(callback != null)
                            callback.handleFinishDownload(true);
                    }
                });
    }

}