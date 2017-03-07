package org.gosparx.scouting.aerialassist.networking;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.BenchmarkingData;
import org.gosparx.scouting.aerialassist.dto.Event;
import org.gosparx.scouting.aerialassist.dto.Match;
import org.gosparx.scouting.aerialassist.dto.Scouting;
import org.gosparx.scouting.aerialassist.dto.ScoutingData;
import org.gosparx.scouting.aerialassist.dto.TeamData;
import org.gosparx.scouting.aerialassist.dto.Team;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class SparxPosting {
    private static final String TAG = "SparxPosting";
    //private static final String BASE_URL = "http://172.20.10.6:8080";
    private static final String BASE_URL = "http://scouting-2017-156319.appspot.com";
    private static final String POST_SCOUTING = "/api/2017/v1/ScoutingData";
    private static final String POST_BENCHMARKING = "/api/2017/v1/BenchmarkingData";
    private static final String GET_SCOUTING_BY_TEAM = "/api/2017/v1/ScoutingData/{TEAM_KEY}";
    private static final String GET_SCOUTING_BY_TEAM_EVENT = "/api/2017/v1/ScoutingData/{TEAM_KEY}/{EVENT_KEY}";
    private static final String GET_SCOUTING_BY_TEAM_EVENT_MATCH = "/api/2017/v1/ScoutingData/{TEAM_KEY}/{EVENT_KEY}/{MATCH_KEY}";
    private static final String GET_BENCHMARKING_BY_EVENT = "/api/2017/v1/BenchmarkingData/{EVENT_NAME}";
    private static SparxPosting SparxPosting;
    private Context context;
    private final DatabaseHelper dbHelper;

    private SparxPosting(Context context) {
        this.context = context;
        Ion ion = Ion.getInstance(context, TAG);
        ion.configure().setLogging(TAG, Log.DEBUG);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        ion.configure().setGson(gson);
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public static synchronized SparxPosting getInstance(Context context){
        if(SparxPosting == null)
            SparxPosting = new SparxPosting(context);
        SparxPosting.context = context;
        return SparxPosting;
    }

    public void getScouting(Team team, NetworkCallback callback){
        String request = (BASE_URL + GET_SCOUTING_BY_TEAM).replace("{TEAM_KEY}", team.getKey());
        getScouting(request, callback);
    }

    public void getScouting(Team team, Event event, NetworkCallback callback) {
        String request = (BASE_URL + GET_SCOUTING_BY_TEAM_EVENT)
                .replace("{TEAM_KEY}", team.getKey())
                .replace("{EVENT_KEY}", event.getKey());
        getScoutingNoNameOrMatch(request, callback);
    }

    public void getScouting(Team team, Event event, Match match){
        String request = (BASE_URL + GET_SCOUTING_BY_TEAM_EVENT_MATCH)
                .replace("{TEAM_KEY}", team.getKey())
                .replace("{EVENT_KEY}", event.getKey())
                .replace("{MATCH_KEY}", match.getKey());
        getScouting(request, null);
    }

    private void getScouting(String request, final NetworkCallback callback){
        Ion.with(context)
                .load(request)
                .as(new TypeToken<List<Scouting>>(){})
                .setCallback(new FutureCallback<List<Scouting>>() {
                    @Override
                    public void onCompleted(Exception e, List<Scouting> result) {
                        if (e != null) {
                            Log.e(TAG, "Issue getting scouting_screen data.", e);
                            callback.handleFinishDownload(false);
                            return;
                        }

                        for (Scouting sd : result) {
                            if (dbHelper.doesScoutingExist(sd))
                                dbHelper.updateScouting(sd);
                            else
                                dbHelper.createScouting(sd);
                        }
                        callback.handleFinishDownload(true);
                    }
                });
    }

    private void getScoutingNoNameOrMatch(String request, final NetworkCallback callback) {
        Ion.with(context)
                .load(request)
                .as(new TypeToken<List<Scouting>>() {
                })
                .setCallback(new FutureCallback<List<Scouting>>() {
                    @Override
                    public void onCompleted(Exception e, List<Scouting> result) {
                        if (e != null) {
                            Log.e(TAG, "Issue getting scouting_screen data.", e);
                            callback.handleFinishDownload(false);
                            return;
                        }

                        for (Scouting sd : result) {
                            if (dbHelper.doesScoutingExistNoNameOrMatch(sd))
                                dbHelper.updateScouting(sd);
                            else
                                dbHelper.createScouting(sd);
                        }
                        callback.handleFinishDownload(true);
                    }
                });
    }

    public void postAllBenchmarking(final NetworkCallback callback) {
        final Map<Integer, TeamData> teamsMap = TeamData.getTeamsMap();
        String request = (BASE_URL + POST_BENCHMARKING);
        if((teamsMap == null) || teamsMap.isEmpty()) {
            Log.e(TAG, "No benchmarking to Upload!");
            System.out.println("No benchmarking to Upload!");
            callback.handleFinishDownload(true);
        }
        else {
            final NetworkCallback subCallback = new NetworkCallback() {
                int size = teamsMap.size();
                boolean hasFailed = false;
                @Override
                public void handleFinishDownload(boolean success) {
                    if(hasFailed) {
                        return;
                    }
                    if(!success) {
                        callback.handleFinishDownload(false);
                        hasFailed = true;
                        return;
                    }
                    size -= 1;
                    if(size <= 0) {
                        callback.handleFinishDownload(true);
                    }
                }
            };

            Iterator it = teamsMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                TeamData teamData = (TeamData)pair.getValue();
                BenchmarkingData benchmarkingData = (BenchmarkingData) teamData.getBenchmarkingData();
                if(benchmarkingData.isBenchmarkingWasDoneButton()) {
                    Ion.with(context)
                            .load(request)
                            .setJsonPojoBody(benchmarkingData)
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    if ((e != null) || ((result != null) && (!result.isEmpty()))) {
                                        Log.e(TAG, "Issue saving Benchmarking to Server!", e);
                                        System.out.println("Issue saving Benchmarking to Server!");
                                        subCallback.handleFinishDownload(false);
                                    }
                                    else {
                                        System.out.println("Uploading");
                                        subCallback.handleFinishDownload(true);
                                    }
                                }
                            });
                }
                else {
                    Log.e(TAG, "Benchmarking was not DONE!");
                    System.out.println("Benchmarking was not DONE!");
                    subCallback.handleFinishDownload(false);
                }
            }
        }
    }

    public void postAllScouting(final NetworkCallback callback) {
        final Map<Integer, TeamData> teamsMap = TeamData.getTeamsMap();
        String request = (BASE_URL + POST_SCOUTING);
        if((teamsMap == null) || teamsMap.isEmpty()) {
            Log.e(TAG, "No scouting to Upload!");
            System.out.println("No scouting to Upload!");
            callback.handleFinishDownload(true);
        }
        else {
            final NetworkCallback subCallback = new NetworkCallback() {
                int size = teamsMap.size();
                boolean hasFailed = false;

                @Override
                public void handleFinishDownload(boolean success) {
                    if (hasFailed) {
                        return;
                    }
                    if (!success) {
                        callback.handleFinishDownload(false);
                        hasFailed = true;
                        return;
                    }
                    size -= 1;
                    if (size <= 0) {
                        callback.handleFinishDownload(true);
                    }
                }
            };

            Iterator it = teamsMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                TeamData teamData = (TeamData) pair.getValue();
                if(!teamData.getScoutingDatas().isEmpty()) {
                    Iterator itSC = teamData.getScoutingDatas().iterator();
                    while (itSC.hasNext()) {
                        ScoutingData scoutingData = (ScoutingData) itSC.next();
                        if (scoutingData.isMatchScouted()) {
                            Ion.with(context)
                                    .load(request)
                                    .setJsonPojoBody(scoutingData)
                                    .asString()
                                    .setCallback(new FutureCallback<String>() {
                                        @Override
                                        public void onCompleted(Exception e, String result) {
                                            if ((e != null) || ((result != null) && (!result.isEmpty()))) {
                                                Log.e(TAG, "Issue saving Scouting to Server!", e);
                                                System.out.println("Server Error");
                                                subCallback.handleFinishDownload(false);
                                            }
                                            else {
                                                System.out.println("Uploading");
                                                subCallback.handleFinishDownload(true);
                                            }
                                        }
                                    });
                        } else {
                            Log.e(TAG, "Scouting was not DONE!");
                            System.out.println("Scouting was not DONE!");
                            subCallback.handleFinishDownload(false);
                        }
                    }
                }
                else {
                    Log.e(TAG, "No scouting to Upload!");
                    System.out.println("No scouting to Upload!");
                    callback.handleFinishDownload(false);
                }
            }
        }
    }

    public void getBenchmarking(String eventName, final NetworkCallback callback){
        String request = (BASE_URL + GET_BENCHMARKING_BY_EVENT)
                .replace("{EVENT_NAME}", eventName);
        Ion.with(context)
                .load(request)
                .as(new TypeToken<List<BenchmarkingData>>(){})
                .setCallback(new FutureCallback<List<BenchmarkingData>>() {
                    @Override
                    public void onCompleted(Exception e, List<BenchmarkingData> result) {
                        if (e != null) {
                            Log.e(TAG, "Issue getting benchmarking data.", e);
                            callback.handleFinishDownload(false);
                            return;
                        }

                        for (BenchmarkingData sd : result) {
                            TeamData.setTeamData(sd);
                        }
                        callback.handleFinishDownload(true);
                    }
                });
    }
}