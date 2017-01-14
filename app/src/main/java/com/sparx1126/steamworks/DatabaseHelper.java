package com.sparx1126.steamworks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Names
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_TEAMS = "teams";
    private static final String TABLE_E2T = "events_to_teams";
    private static final String TABLE_MATCHES = "matches";
    private static final String TABLE_SCOUTING = "scouting";
    private static final String TABLE_BENCHMARKING = "benchmarking";

    // Events Column Names
    private static final String TABLE_EVENTS_KEY = "key";
    private static final String TABLE_EVENTS_NAME = "name";
    private static final String TABLE_EVENTS_SHORT_NAME = "short_name";
    private static final String TABLE_EVENTS_EVENT_CODE = "event_code";
    private static final String TABLE_EVENTS_EVENT_TYPE_STRING = "event_type_string";
    private static final String TABLE_EVENTS_EVENT_TYPE = "event_type";
    private static final String TABLE_EVENTS_YEAR = "year";
    private static final String TABLE_EVENTS_LOCATION = "location";
    private static final String TABLE_EVENTS_OFFICIAL = "official";
    private static final String TABLE_EVENTS_START_DATE = "start_date";
    private static final String TABLE_EVENTS_END_DATE = "end_date";

    // Teams Column Names
    private static final String TABLE_TEAMS_KEY = "key";
    private static final String TABLE_TEAMS_NAME = "name";
    private static final String TABLE_TEAMS_NICKNAME = "nickname";
    private static final String TABLE_TEAMS_TEAM_NUMBER = "team_number";
    private static final String TABLE_TEAMS_WEBSITE = "website";
    private static final String TABLE_TEAMS_CITY = "city";
    private static final String TABLE_TEAMS_STATE = "state";
    private static final String TABLE_TEAMS_COUNTRY = "county";
    private static final String TABLE_TEAMS_LOCATION = "location";

    // Events to Teams Column Names
    private static final String TABLE_E2T_KEY = "key";
    private static final String TABLE_E2T_EVENT = "event";
    private static final String TABLE_E2T_TEAM = "team";

    // Matches Column Names
    private static final String TABLE_MATCHES_KEY = "key";
    private static final String TABLE_MATCHES_EVENT_KEY = "event_key";
    private static final String TABLE_MATCHES_MATCH_NUMBER = "match_number";
    private static final String TABLE_MATCHES_SET_NUMBER = "set_number";
    private static final String TABLE_MATCHES_COMP_LEVEL = "comp_level";
    private static final String TABLE_MATCHES_BLUE_SCORE = "blue_score";
    private static final String TABLE_MATCHES_BLUE_ONE = "blue_one";
    private static final String TABLE_MATCHES_BLUE_TWO = "blue_two";
    private static final String TABLE_MATCHES_BLUE_THREE = "blue_three";
    private static final String TABLE_MATCHES_RED_SCORE = "red_score";
    private static final String TABLE_MATCHES_RED_ONE = "red_one";
    private static final String TABLE_MATCHES_RED_TWO = "red_two";
    private static final String TABLE_MATCHES_RED_THREE = "red_three";

    // Create Tables
    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENTS + "("
            + TABLE_EVENTS_KEY + " TEXT PRIMARY KEY, "
            + TABLE_EVENTS_NAME + " TEXT, "
            + TABLE_EVENTS_SHORT_NAME + " TEXT, "
            + TABLE_EVENTS_EVENT_CODE + " TEXT, "
            + TABLE_EVENTS_EVENT_TYPE_STRING + " TEXT, "
            + TABLE_EVENTS_EVENT_TYPE + " INTEGER, "
            + TABLE_EVENTS_YEAR + " INTEGER, "
            + TABLE_EVENTS_LOCATION + " TEXT, "
            + TABLE_EVENTS_OFFICIAL + " INTEGER, "
            + TABLE_EVENTS_START_DATE + " TEXT, "
            + TABLE_EVENTS_END_DATE + " TEXT)";

    private static final String CREATE_TABLE_TEAMS = "CREATE TABLE " + TABLE_TEAMS + "("
            + TABLE_TEAMS_KEY + " TEXT PRIMARY KEY, "
            + TABLE_TEAMS_TEAM_NUMBER + " INTEGER, "
            + TABLE_TEAMS_NAME + " TEXT, "
            + TABLE_TEAMS_NICKNAME + " TEXT, "
            + TABLE_TEAMS_WEBSITE + " TEXT, "
            + TABLE_TEAMS_CITY + " TEXT, "
            + TABLE_TEAMS_STATE + " TEXT, "
            + TABLE_TEAMS_COUNTRY + " TEXT, "
            + TABLE_TEAMS_LOCATION + " TEXT)";

    private static final String CREATE_TABLE_E2T = "CREATE TABLE " + TABLE_E2T + "("
            + TABLE_E2T_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TABLE_E2T_EVENT + " TEXT, "
            + TABLE_E2T_TEAM + " TEXT)";

    private static final String CREATE_TABLE_MATCH = "CREATE TABLE " + TABLE_MATCHES + "("
            + TABLE_MATCHES_KEY + " TEXT PRIMARY KEY, "
            + TABLE_MATCHES_EVENT_KEY + " TEXT, "
            + TABLE_MATCHES_MATCH_NUMBER + " INTEGER, "
            + TABLE_MATCHES_SET_NUMBER + " INTEGER, "
            + TABLE_MATCHES_COMP_LEVEL + " TEXT, "
            + TABLE_MATCHES_RED_SCORE + " INTEGER, "
            + TABLE_MATCHES_RED_ONE + " TEXT, "
            + TABLE_MATCHES_RED_TWO + " TEXT, "
            + TABLE_MATCHES_RED_THREE + " TEXT, "
            + TABLE_MATCHES_BLUE_SCORE + " INTEGER, "
            + TABLE_MATCHES_BLUE_ONE + " TEXT, "
            + TABLE_MATCHES_BLUE_TWO + " TEXT, "
            + TABLE_MATCHES_BLUE_THREE + " TEXT)";
    public static SimpleDateFormat ISO6701_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String TAG = "DatabaseHelper";


    private static DatabaseHelper dbHelper;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Steamworks";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if(dbHelper == null)
            dbHelper = new DatabaseHelper(context);
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EVENTS);
        db.execSQL(CREATE_TABLE_TEAMS);
        db.execSQL(CREATE_TABLE_E2T);
        db.execSQL(CREATE_TABLE_MATCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        throw new NoSuchMethodError();
    }

    public Cursor createEventNameCursor(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(true, TABLE_EVENTS, new String[]{"rowid AS _id, key", "substr(start_date,1,11) || name AS title", TABLE_EVENTS_SHORT_NAME, TABLE_EVENTS_START_DATE},
                "", null, null, null, TABLE_EVENTS_START_DATE + " ASC", null);
    }

    public Event getEvent(String eventKey){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS
                + " WHERE " + TABLE_EVENTS_KEY + " = ?";

        Cursor c = db.rawQuery(selectQuery, new String[]{eventKey});

        Event event = new Event();
        if(c != null && c.moveToNext()){
            event.setKey(c.getString(c.getColumnIndex(TABLE_EVENTS_KEY)));
            event.setName(c.getString(c.getColumnIndex(TABLE_EVENTS_NAME)));
            event.setShortName(c.getString(c.getColumnIndex(TABLE_EVENTS_SHORT_NAME)));
            event.setEventCode(c.getString(c.getColumnIndex(TABLE_EVENTS_EVENT_CODE)));
            event.setEventTypeString(c.getString(c.getColumnIndex(TABLE_EVENTS_EVENT_TYPE_STRING)));
            event.setEventType(c.getInt(c.getColumnIndex(TABLE_EVENTS_EVENT_TYPE)));
            event.setYear(c.getInt(c.getColumnIndex(TABLE_EVENTS_YEAR)));
            event.setLocation(c.getString(c.getColumnIndex(TABLE_EVENTS_LOCATION)));
            event.setOfficial(c.getInt(c.getColumnIndex(TABLE_EVENTS_OFFICIAL)) == 1);
            try{
                event.setStartDate(ISO6701_FORMAT.parse(c.getString(c.getColumnIndex(TABLE_EVENTS_START_DATE))));
            } catch (ParseException e) {
                Log.e(TAG, "Could not parse Event's start date.", e);
            }try{
                event.setEndDate(ISO6701_FORMAT.parse(c.getString(c.getColumnIndex(TABLE_EVENTS_END_DATE))));
            } catch (ParseException e) {
                Log.e(TAG, "Could not parse Event's end date.", e);
            }
        }
        if(c!=null) {
            c.close();
        }
        return event;
    }

    public boolean doesEventExist(Event event){
        boolean retVal = false;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_EVENTS, new String[]{"COUNT(*)"},
                TABLE_EVENTS_KEY + " = ?", new String[]{event.getKey()}, null, null, null);

        if(c != null && c.moveToNext())
            retVal = c.getInt(0) > 0;
        if(c!=null)
            c.close();
        return retVal;
    }

    private ContentValues mapEvent(Event event){
        ContentValues values = new ContentValues();
        values.put(TABLE_EVENTS_KEY, event.getKey());
        values.put(TABLE_EVENTS_NAME, event.getName());
        values.put(TABLE_EVENTS_SHORT_NAME, event.getShortName());
        values.put(TABLE_EVENTS_EVENT_CODE, event.getEventCode());
        values.put(TABLE_EVENTS_EVENT_TYPE_STRING, event.getEventTypeString());
        values.put(TABLE_EVENTS_EVENT_TYPE, event.getEventType());
        values.put(TABLE_EVENTS_YEAR, event.getYear());
        values.put(TABLE_EVENTS_LOCATION, event.getLocation());
        values.put(TABLE_EVENTS_OFFICIAL, event.isOfficial());
        values.put(TABLE_EVENTS_START_DATE, ISO6701_FORMAT.format(event.getStartDate()));
        values.put(TABLE_EVENTS_END_DATE, ISO6701_FORMAT.format(event.getEndDate()));

        return values;
    }

    public void updateEvent(Event event){
        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_EVENTS, mapEvent(event), TABLE_EVENTS_KEY + " = ?", new String[]{event.getKey()});
    }

    public void createEvent(Event event){
        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_EVENTS, null, mapEvent(event));
    }

    public boolean doesMatchExist(Match match){
        boolean retVal = false;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_MATCHES, new String[]{"COUNT(*)"},
                TABLE_MATCHES_KEY+" = ?", new String[]{match.getKey()}, null, null, null);

        if(c != null && c.moveToNext())
            retVal = c.getInt(0) > 0;

        if (c != null) {
            c.close();
        }
        return retVal;
    }

    private ContentValues mapMatch(Match match){
        ContentValues values = new ContentValues();
        values.put(TABLE_MATCHES_KEY, match.getKey());
        values.put(TABLE_MATCHES_EVENT_KEY, match.getEventKey());
        values.put(TABLE_MATCHES_MATCH_NUMBER, match.getMatchNumber());
        values.put(TABLE_MATCHES_SET_NUMBER, match.getSetNumber());
        values.put(TABLE_MATCHES_COMP_LEVEL, match.getCompetitionLevel());
        values.put(TABLE_MATCHES_BLUE_SCORE, match.getAlliances().getBlue().getScore());
        values.put(TABLE_MATCHES_BLUE_ONE, match.getAlliances().getBlue().getTeams().get(0));
        values.put(TABLE_MATCHES_BLUE_TWO, match.getAlliances().getBlue().getTeams().get(1));
        values.put(TABLE_MATCHES_BLUE_THREE, match.getAlliances().getBlue().getTeams().get(2));
        values.put(TABLE_MATCHES_RED_SCORE, match.getAlliances().getRed().getScore());
        values.put(TABLE_MATCHES_RED_ONE, match.getAlliances().getRed().getTeams().get(0));
        values.put(TABLE_MATCHES_RED_TWO, match.getAlliances().getRed().getTeams().get(1));
        values.put(TABLE_MATCHES_RED_THREE, match.getAlliances().getRed().getTeams().get(2));

        return values;
    }

    public void updateMatch(Match match){
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_MATCHES, mapMatch(match), TABLE_MATCHES_KEY+" = ?", new String[]{match.getKey()});
    }

    public void createMatch(Match match){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = mapMatch(match);

        db.insert(TABLE_MATCHES, null, values);
    }

    public boolean doesTeamExist(Team team){
        boolean retVal = false;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_TEAMS, new String[]{"COUNT(*)"},
                TABLE_TEAMS_KEY + " = ?", new String[]{team.getKey()}, null, null, null);

        if(c != null && c.moveToNext())
            retVal = c.getInt(0) > 0;
        if(c!=null) {
            c.close();
        }
        return retVal;
    }

    private ContentValues mapTeam(Team team){
        ContentValues values = new ContentValues();
        values.put(TABLE_TEAMS_KEY, team.getKey());
        values.put(TABLE_TEAMS_NAME, team.getName());
        values.put(TABLE_TEAMS_NICKNAME, team.getNickname());
        values.put(TABLE_TEAMS_TEAM_NUMBER, team.getTeamNumber());
        values.put(TABLE_TEAMS_WEBSITE, team.getWebsite());
        values.put(TABLE_TEAMS_CITY, team.getCity());
        values.put(TABLE_TEAMS_STATE, team.getState());
        values.put(TABLE_TEAMS_COUNTRY, team.getCountry());
        values.put(TABLE_TEAMS_LOCATION, team.getLocation());
        return values;
    }

    public void updateTeam(Team team){
        getWritableDatabase().update(TABLE_TEAMS, mapTeam(team), TABLE_TEAMS_KEY + " = ?", new String[]{team.getKey()});
    }

    public void createTeam(Team team){
        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_TEAMS, null, mapTeam(team));
    }

    public boolean doesE2TAssociationExist(String eventKey, String teamKey){
        SQLiteDatabase db = getReadableDatabase();
        boolean retVal = false;

        Cursor c = db.query(TABLE_E2T, new String[]{"COUNT(*)"},
                TABLE_E2T_EVENT+" = ? AND "+TABLE_E2T_TEAM+" = ?",
                new String[]{eventKey, teamKey}, null, null, null);

        if(c != null && c.moveToNext())
            retVal = c.getInt(0) > 0;

        if (c != null) {
            c.close();
        }
        return retVal;
    }

    public void createE2TAssociation(String eventKey, String teamKey){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_E2T_EVENT, eventKey);
        values.put(TABLE_E2T_TEAM, teamKey);

        db.insert(TABLE_E2T, null, values);
    }
}
