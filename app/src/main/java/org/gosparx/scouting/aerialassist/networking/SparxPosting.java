package org.gosparx.scouting.aerialassist.networking;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.gosparx.scouting.aerialassist.DatabaseHelper;
import org.gosparx.scouting.aerialassist.dto.Event;
import org.gosparx.scouting.aerialassist.dto.Match;
import org.gosparx.scouting.aerialassist.dto.Scouting;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;
import org.gosparx.scouting.aerialassist.dto.Team;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SparxPosting {
    private static final String TAG = "SparxPosting";
    private static final String BASE_URL = "http://scouting-2016.appspot.com";
    private static final String POST_SCOUTING = "/api/2016/v1/ScoutingData";
    private static final String POST_BENCHMARKING = "/api/2016/v1/BenchmarkingData";
    private static final String GET_SCOUTING_BY_TEAM = "/api/20165/v1/ScoutingData/{TEAM_KEY}";
    private static final String GET_SCOUTING_BY_TEAM_EVENT = "/api/2016/v1/ScoutingData/{TEAM_KEY}/{EVENT_KEY}";
    private static final String GET_SCOUTING_BY_TEAM_EVENT_MATCH = "/api/2016/v1/ScoutingData/{TEAM_KEY}/{EVENT_KEY}/{MATCH_KEY}";
    private static final String GET_BENCHMARKING_BY_TEAM_EVENT = "/api/2016/v1/BenchmarkingData/{TEAM_KEY}/{EVENT_KEY}";
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

    public void postAllScouting(final NetworkCallback callback) {
        final List<Scouting> scoutingList = dbHelper.getAllScoutingNeedingSyncing();
        String request = (BASE_URL + POST_SCOUTING);
        if(scoutingList.isEmpty())
            callback.handleFinishDownload(true);
        final NetworkCallback subCallback = new NetworkCallback() {
            int size = scoutingList.size();
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

        for (final Scouting scouting : scoutingList) {
            Ion.with(context)
                    .load(request)
                    .setJsonPojoBody(scouting)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            if ((e != null) || ((result != null) && (!result.isEmpty()))) {
                                Log.e(TAG, "Issue saving to Server!", e);
                                System.out.println("Server Error");
                                subCallback.handleFinishDownload(false);
                            } else {
                                dbHelper.setDoneSyncing(scouting);
                                System.out.println("Uploading");
                                subCallback.handleFinishDownload(true);
                            }
                        }
                    });
        }
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
                            Log.e(TAG, "Issue getting scouting data.", e);
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
                            Log.e(TAG, "Issue getting scouting data.", e);
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
        final Map<Integer, ScoutingInfo> scoutingInfoMap = ScoutingInfo.getInfoMap();
        String request = (BASE_URL + POST_BENCHMARKING);
        if(scoutingInfoMap.isEmpty())
            callback.handleFinishDownload(true);
        final NetworkCallback subCallback = new NetworkCallback() {
            int size = scoutingInfoMap.size();
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

        Iterator it = scoutingInfoMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ScoutingInfo scoutingInfo = (ScoutingInfo)pair.getValue();
            Ion.with(context)
                    .load(request)
                    .setJsonPojoBody(scoutingInfo)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            if ((e != null) || ((result != null) && (!result.isEmpty()))) {
                                Log.e(TAG, "Issue saving ScoutingInfo to Server!", e);
                                System.out.println("Server Error");
                                subCallback.handleFinishDownload(false);
                            }
                        }
                    });
        }
    }
}