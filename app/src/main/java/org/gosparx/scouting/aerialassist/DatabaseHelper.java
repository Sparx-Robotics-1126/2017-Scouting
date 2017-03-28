package org.gosparx.scouting.aerialassist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.gosparx.scouting.aerialassist.dto.Alliance;
import org.gosparx.scouting.aerialassist.dto.Alliances;
import org.gosparx.scouting.aerialassist.dto.Event;
import org.gosparx.scouting.aerialassist.dto.Match;
import org.gosparx.scouting.aerialassist.dto.Team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String TAG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Steamworks";

    // Table Names
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_BENCHMARK = "benchmark";
    private static final String TABLE_SCOUT = "scout";
    private static final String TABLE_TEAMS = "teams";
    private static final String TABLE_E2T = "events_to_teams";
    private static final String TABLE_MATCHES = "matches";

    // Events Column Names
    public static final String TABLE_EVENTS_KEY = "key";
    private static final String TABLE_EVENTS_NAME = "name";
    private static final String TABLE_EVENTS_SHORT_NAME = "short_name";
    private static final String TABLE_EVENTS_EVENT_CODE = "event_code";
    private static final String TABLE_EVENTS_EVENT_TYPE_STRING = "event_type_string";
    private static final String TABLE_EVENTS_EVENT_TYPE = "event_type";
    private static final String TABLE_EVENTS_YEAR = "year";
    private static final String TABLE_EVENTS_LOCATION = "location";
    private static final String TABLE_EVENTS_OFFICIAL = "official";
    public static final String TABLE_EVENTS_START_DATE = "start_date";
    private static final String TABLE_EVENTS_END_DATE = "end_date";
    public static final String TABLE_EVENTS_TITLE = "title";

    // Benchmark Column Names
    public static final String TABLE_BENCHMARK_KEY = "key";
    public static final String TABLE_BENCHMARK_TEAM_NUMBER = "teamNumber";
    public static final String TABLE_BENCHMARK_EVENT_NAME = "eventName";
    public static final String TABLE_BENCHMARK_STUDENT = "student";
    public static final String TABLE_BENCHMARK_DRIVE_SYSTEM = "driveSystem";
    public static final String TABLE_BENCHMARK_DRIVES_SPEED = "drivesSpeed";
    public static final String TABLE_BENCHMARK_CAN_PLAY_DEFENSE_BENCH_BUTTON = "canPlayDefenseBenchButton";
    public static final String TABLE_BENCHMARK_ABILITY_TO_SHOOT_HIGH_GOAL_BENCH_BUTTON = "abilityToShootHighGoalBenchButton";
    public static final String TABLE_BENCHMARK_TYPE_OF_SHOOTER_BENCH_INPUT = "typeOfShooterBenchInput";
    public static final String TABLE_BENCHMARK_BALLS_PER_SECOND_BENCH_INPUT = "ballsPerSecondBenchInput";
    public static final String TABLE_BENCHMARK_BALLS_IN_CYCLE_BENCH_INPUT = "ballsInCycleBenchInput";
    public static final String TABLE_BENCHMARK_CYCLE_TIME_HIGH_BENCH_INPUT = "cycleTimeHighBenchInput";
    public static final String TABLE_BENCHMARK_SHOOTING_RANGE_BENCH_INPUT = "shootingRangeBenchInput";
    public static final String TABLE_BENCHMARK_PREFERRED_SHOOTING_LOCATION_BENCH_INPUT = "preferredShootingLocationBenchInput";
    public static final String TABLE_BENCHMARK_ACCURACY_HIGH_BENCH_INPUT = "accuracyHighBenchInput";
    public static final String TABLE_BENCHMARK_PICKUP_BALL_HOPPER_BENCH_BUTTON = "pickupBallHopperBenchButton";
    public static final String TABLE_BENCHMARK_PICKUP_BALL_FLOOR_BENCH_BUTTON = "pickupBallFloorBenchButton";
    public static final String TABLE_BENCHMARK_PICKUP_BALL_HUMAN_BENCH_BUTTON = "pickupBallHumanBenchButton";
    public static final String TABLE_BENCHMARK_PICKUP_BALL_PREFERRED_BENCH_INPUT = "pickupBallPreferredBenchInput";
    public static final String TABLE_BENCHMARK_MAXIMUM_BALL_CAPACITY_BENCH_INPUT = "maximumBallCapacityBenchInput";
    public static final String TABLE_BENCHMARK_CAN_SCORE_GEARS_BENCH_BUTTON = "canScoreGearsBenchButton";
    public static final String TABLE_BENCHMARK_PICKUP_GEAR_FLOOR_BENCH_BUTTON = "pickupGearFloorBenchButton";
    public static final String TABLE_BENCHMARK_PICKUP_GEAR_RETRIEVAL_BENCH_BUTTON = "pickupGearRetrievalBenchButton";
    public static final String TABLE_BENCHMARK_RADIO_PICKUP_GEAR_PREFERRED = "radioPickupGearPreferred";
    public static final String TABLE_BENCHMARK_CAN_GEAR_LEFT_BENCH = "canGearLeftBench";
    public static final String TABLE_BENCHMARK_CAN_GEAR_CENTER_BENCH = "canGearCenterBench";
    public static final String TABLE_BENCHMARK_CAN_GEAR_RIGHT_BENCH = "canGearRightBench";
    public static final String TABLE_BENCHMARK_RADIO_PREFERRED_GEAR = "radioPreferredGear";
    public static final String TABLE_BENCHMARK_CYCLE_TIME_GEARS_BENCH_INPUT = "cycleTimeGearsBenchInput";
    public static final String TABLE_BENCHMARK_ABILITY_TO_SHOOT_LOW_GOAL_BENCH_BUTTON = "abilityToShootLowGoalBenchButton";
    public static final String TABLE_BENCHMARK_CYCLE_TIME_LOW_BENCH_INPUT = "cycleTimeLowBenchInput";
    public static final String TABLE_BENCHMARK_CYCLE_NUMBER_LOW_BENCH_INPUT = "cycleNumberLowBenchInput";
    public static final String TABLE_BENCHMARK_ABILITY_SCALE_BENCH_BUTTON = "abilityScaleBenchButton";
    public static final String TABLE_BENCHMARK_PLACES_CAN_SCALE_RIGHT = "placesCanScaleRight";
    public static final String TABLE_BENCHMARK_PLACES_CAN_SCALE_CENTER = "placesCanScaleCenter";
    public static final String TABLE_BENCHMARK_PLACES_CAN_SCALE_LEFT = "placesCanScaleLeft";
    public static final String TABLE_BENCHMARK_BENCHMARK_WAS_DONE_BUTTON = "benchmarkWasDoneButton";
    public static final String TABLE_BENCHMARK_PREFERRED_PLACES_SCALE_INPUT = "preferredPlacesScaleInput";
    public static final String TABLE_BENCHMARK_AUTO_ABILITIES_BENCH = "autoAbilitiesBench";
    public static final String TABLE_BENCHMARK_COMMENTS_BENCH = "commentsBench";

    // Benchmark Column Names
    public static final String TABLE_SCOUT_KEY = "key";
    public static final String TABLE_SCOUT_SKEY = "scoutingKey";
    public static final String TABLE_SCOUT_TEAM_NUMBER = "teamNumber";
    public static final String TABLE_SCOUT_EVENT_NAME = "eventName";
    public static final String TABLE_SCOUT_STUDENT = "student";
    public static final String TABLE_SCOUT_CROSSED_BASELINE = "crossedBaseline";
    public static final String TABLE_SCOUT_HOPPERS_DUMPED = "hoppersDumped";
    public static final String TABLE_SCOUT_GEAR_SCORED_RIGHT_AUTO = "gearScoredRightAuto";
    public static final String TABLE_SCOUT_GEAR_SCORED_CENTER_AUTO = "gearScoredCenterAuto";
    public static final String TABLE_SCOUT_GEAR_SCORED_LEFT_AUTO = "gearScoredLeftAuto";
    public static final String TABLE_SCOUT_GEARS_SCORED = "gearsScored";
    public static final String TABLE_SCOUT_GEARS_DELIVERED = "gearsDelivered";
    public static final String TABLE_SCOUT_GEARS_COLLECTED_FROM_FLOOR = "gearsCollectedFromFloor";
    public static final String TABLE_SCOUT_GEARS_FROM_HUMAN = "gearsFromHuman";
    public static final String TABLE_SCOUT_SCORES_HIGH_AUTO = "scoresHighAuto";
    public static final String TABLE_SCOUT_SCORES_LOW_AUTO = "scoresLowAuto";
    public static final String TABLE_SCOUT_BALLS_IN_HIGH_CYCLE = "ballsInHighCycle";
    public static final String TABLE_SCOUT_BALLS_FROM_HUMAN = "ballsFromHuman";
    public static final String TABLE_SCOUT_BALLS_FROM_HOPPER = "ballsFromHopper";
    public static final String TABLE_SCOUT_BALLS_FROM_FLOOR = "ballsFromFloor";
    public static final String TABLE_SCOUT_FUEL_IN_LOW_CYCLE = "fuelInLowCycle";
    public static final String TABLE_SCOUT_NUMBER_OF_LOW_CYCLES = "numberOfLowCycles";
    public static final String TABLE_SCOUT_HIGH_GOAL_ACCURACY = "highGoalAccuracy";
    public static final String TABLE_SCOUT_DID_SCALE = "didScale";
    public static final String TABLE_SCOUT_WHERE_SCALED = "whereScaled";
    public static final String TABLE_SCOUT_MATCH_SCOUTED = "matchScouted";


    // Teams Column Names
    private static final String TABLE_TEAMS_KEY = "key";
    private static final String TABLE_TEAMS_NAME = "name";
    private static final String TABLE_TEAMS_NICKNAME = "nickname";
    public static final String TABLE_TEAMS_TEAM_NUMBER = "team_number";
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
    public static final String TABLE_MATCHES_KEY = "key";
    public static final String TABLE_MATCHES_EVENT_KEY = "event_key";
    public static final String TABLE_MATCHES_MATCH_NUMBER = "match_number";
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

    private static final String CREATE_TABLE_BENCHMARK = "CREATE TABLE " + TABLE_BENCHMARK + "("
            + TABLE_BENCHMARK_KEY + " TEXT PRIMARY KEY, "
            + TABLE_BENCHMARK_TEAM_NUMBER + " INTEGER, "
            + TABLE_BENCHMARK_EVENT_NAME + " TEXT, "
            + TABLE_BENCHMARK_STUDENT + " TEXT, "
            + TABLE_BENCHMARK_DRIVE_SYSTEM + " TEXT, "
            + TABLE_BENCHMARK_DRIVES_SPEED + " REAL, "
            + TABLE_BENCHMARK_CAN_PLAY_DEFENSE_BENCH_BUTTON + " BOOLEAN, "
            + TABLE_BENCHMARK_ABILITY_TO_SHOOT_HIGH_GOAL_BENCH_BUTTON + " BOOLEAN, "
            + TABLE_BENCHMARK_TYPE_OF_SHOOTER_BENCH_INPUT + " TEXT, "
            + TABLE_BENCHMARK_BALLS_PER_SECOND_BENCH_INPUT + " REAL, "
            + TABLE_BENCHMARK_BALLS_IN_CYCLE_BENCH_INPUT + " INTEGER, "
            + TABLE_BENCHMARK_CYCLE_TIME_HIGH_BENCH_INPUT + " INTEGER, "
            + TABLE_BENCHMARK_SHOOTING_RANGE_BENCH_INPUT + " REAL, "
            + TABLE_BENCHMARK_PREFERRED_SHOOTING_LOCATION_BENCH_INPUT + " TEXT, "
            + TABLE_BENCHMARK_ACCURACY_HIGH_BENCH_INPUT + " REAL, "
            + TABLE_BENCHMARK_PICKUP_BALL_HOPPER_BENCH_BUTTON + " BOOLEAN, "
            + TABLE_BENCHMARK_PICKUP_BALL_FLOOR_BENCH_BUTTON + " BOOLEAN, "
            + TABLE_BENCHMARK_PICKUP_BALL_HUMAN_BENCH_BUTTON + " BOOLEAN, "
            + TABLE_BENCHMARK_PICKUP_BALL_PREFERRED_BENCH_INPUT + " TEXT, "
            + TABLE_BENCHMARK_MAXIMUM_BALL_CAPACITY_BENCH_INPUT + " INTEGER, "
            + TABLE_BENCHMARK_CAN_SCORE_GEARS_BENCH_BUTTON + " BOOLEAN, "
            + TABLE_BENCHMARK_PICKUP_GEAR_FLOOR_BENCH_BUTTON + " BOOLEAN, "
            + TABLE_BENCHMARK_PICKUP_GEAR_RETRIEVAL_BENCH_BUTTON + " BOOLEAN, "
            + TABLE_BENCHMARK_RADIO_PICKUP_GEAR_PREFERRED + " TEXT, "
            + TABLE_BENCHMARK_CAN_GEAR_LEFT_BENCH + " BOOLEAN, "
            + TABLE_BENCHMARK_CAN_GEAR_CENTER_BENCH + " BOOLEAN, "
            + TABLE_BENCHMARK_CAN_GEAR_RIGHT_BENCH + " BOOLEAN, "
            + TABLE_BENCHMARK_RADIO_PREFERRED_GEAR + " TEXT, "
            + TABLE_BENCHMARK_CYCLE_TIME_GEARS_BENCH_INPUT + " INTEGER, "
            + TABLE_BENCHMARK_ABILITY_TO_SHOOT_LOW_GOAL_BENCH_BUTTON + " BOOLEAN, "
            + TABLE_BENCHMARK_CYCLE_TIME_LOW_BENCH_INPUT + " INTEGER, "
            + TABLE_BENCHMARK_CYCLE_NUMBER_LOW_BENCH_INPUT + " INTEGER, "
            + TABLE_BENCHMARK_ABILITY_SCALE_BENCH_BUTTON + " BOOLEAN, "
            + TABLE_BENCHMARK_PLACES_CAN_SCALE_RIGHT + " BOOLEAN, "
            + TABLE_BENCHMARK_PLACES_CAN_SCALE_CENTER + " BOOLEAN, "
            + TABLE_BENCHMARK_PLACES_CAN_SCALE_LEFT + " BOOLEAN, "
            + TABLE_BENCHMARK_BENCHMARK_WAS_DONE_BUTTON + " BOOLEAN, "
            + TABLE_BENCHMARK_PREFERRED_PLACES_SCALE_INPUT + " TEXT, "
            + TABLE_BENCHMARK_AUTO_ABILITIES_BENCH + " TEXT, "
            + TABLE_BENCHMARK_COMMENTS_BENCH + " TEXT)";


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
    private static final String CREATE_TABLE_SCOUT = "CREATE TABLE " + TABLE_SCOUT + "("
            + TABLE_SCOUT_KEY + " TEXT PRIMARY KEY, "
            + TABLE_SCOUT_SKEY + " TEXT, "
            + TABLE_SCOUT_TEAM_NUMBER + " INTEGER, "
            + TABLE_SCOUT_EVENT_NAME + " TEXT, "
            + TABLE_SCOUT_STUDENT + " TEXT, "
            + TABLE_SCOUT_CROSSED_BASELINE + " BOOLEAN, "
            + TABLE_SCOUT_HOPPERS_DUMPED + " INTEGER, "
            + TABLE_SCOUT_GEAR_SCORED_RIGHT_AUTO + " BOOLEAN, "
            + TABLE_SCOUT_GEAR_SCORED_CENTER_AUTO + " BOOLEAN, "
            + TABLE_SCOUT_GEAR_SCORED_LEFT_AUTO + " BOOLEAN, "
            + TABLE_SCOUT_GEARS_SCORED + " INTEGER, "
            + TABLE_SCOUT_GEARS_DELIVERED + " INTEGER, "
            + TABLE_SCOUT_GEARS_COLLECTED_FROM_FLOOR + " INTEGER, "
            + TABLE_SCOUT_GEARS_FROM_HUMAN + " INTEGER, "
            + TABLE_SCOUT_SCORES_HIGH_AUTO + " TEXT, "
            + TABLE_SCOUT_SCORES_LOW_AUTO + " TEXT, "
            + TABLE_SCOUT_BALLS_IN_HIGH_CYCLE + " INTEGER, "
            + TABLE_SCOUT_BALLS_FROM_HUMAN + " INTEGER, "
            + TABLE_SCOUT_BALLS_FROM_HOPPER + " INTEGER, "
            + TABLE_SCOUT_BALLS_FROM_FLOOR + " INTEGER, "
            + TABLE_SCOUT_FUEL_IN_LOW_CYCLE + " INTEGER, "
            + TABLE_SCOUT_NUMBER_OF_LOW_CYCLES + " INTEGER, "
            + TABLE_SCOUT_HIGH_GOAL_ACCURACY + " TEXT, "
            + TABLE_SCOUT_DID_SCALE + " BOOLEAN, "
            + TABLE_SCOUT_WHERE_SCALED + " TEXT, "
            + TABLE_SCOUT_MATCH_SCOUTED + " BOOLEAN)";

    private static final SimpleDateFormat ISO6701_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private static DatabaseHelper dbHelper;

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
        db.execSQL(CREATE_TABLE_BENCHMARK);
        db.execSQL(CREATE_TABLE_SCOUT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        throw new NoSuchMethodError();
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

    public void createEvent(Event event){
        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_EVENTS, null, mapEvent(event));
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

    public void updateEvent(Event event){
        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_EVENTS, mapEvent(event), TABLE_EVENTS_KEY + " = ?", new String[]{event.getKey()});
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


    public void createMatch(Match match){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = mapMatch(match);

        db.insert(TABLE_MATCHES, null, values);
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

    public void updateMatch(Match match){
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_MATCHES, mapMatch(match), TABLE_MATCHES_KEY+" = ?", new String[]{match.getKey()});
    }

    public Match getMatch(String matchKey){
        SQLiteDatabase db = getReadableDatabase();
        String selectStatement = "SELECT * FROM " + TABLE_MATCHES
                + " WHERE " + TABLE_MATCHES_KEY + " = ?";

        Cursor c = db.rawQuery(selectStatement, new String[]{matchKey});

        Match match = new Match();

        if(c!=null && c.moveToNext()){
            match.setKey(c.getString(c.getColumnIndex(TABLE_MATCHES_KEY)));
            match.setEventKey(c.getString(c.getColumnIndex(TABLE_MATCHES_EVENT_KEY)));
            match.setMatchNumber(c.getInt(c.getColumnIndex(TABLE_MATCHES_MATCH_NUMBER)));
            match.setSetNumber(c.getInt(c.getColumnIndex(TABLE_MATCHES_SET_NUMBER)));
            match.setCompetitionLevel(c.getString(c.getColumnIndex(TABLE_MATCHES_COMP_LEVEL)));

            Alliances alliances = new Alliances();
            Alliance red = new Alliance();
            red.setTeams(new ArrayList<String>(3));
            Alliance blue = new Alliance();
            blue.setTeams(new ArrayList<String>(3));
            alliances.setBlue(blue);
            alliances.setRed(red);

            blue.setScore(c.getInt(c.getColumnIndex(TABLE_MATCHES_BLUE_SCORE)));
            blue.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_BLUE_ONE)));
            blue.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_BLUE_TWO)));
            blue.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_BLUE_THREE)));

            red.setScore(c.getInt(c.getColumnIndex(TABLE_MATCHES_RED_SCORE)));
            red.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_RED_ONE)));
            red.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_RED_TWO)));
            red.getTeams().add(c.getString(c.getColumnIndex(TABLE_MATCHES_RED_THREE)));
            match.setAlliances(alliances);
        }

        if (c != null) {
            c.close();
        }
        return match;
    }

    public Cursor createMatchCursor(Event event){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_MATCHES,
                new String[]{"*", "rowid As _id"},
                //TABLE_MATCHES_EVENT_KEY + " = ?", new String[]{event.getKey()},
                TABLE_MATCHES_EVENT_KEY + " = ?", new String[]{"2016ohcl"},
                null, null,
                "(CASE " + TABLE_MATCHES_COMP_LEVEL + " "
                        + "WHEN 'qm' THEN 1 "
                        + "WHEN 'qf' THEN 2 "
                        + "WHEN 'sf' THEN 3 "
                        + "WHEN 'f'  THEN 4 "
                        + "ELSE 5 END), "
                        + TABLE_MATCHES_SET_NUMBER +", "
                        + TABLE_MATCHES_MATCH_NUMBER + " ASC",
                null);
    }

    public int getMatchCount(Event event){
        int retVal = 0;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MATCHES + " WHERE " + TABLE_MATCHES_EVENT_KEY + " = ?", new String[]{event.getKey()});

        if(c != null && c.moveToNext())
            retVal = c.getInt(0);

        if (c != null) {
            c.close();
        }

        return retVal;
    }


    public Cursor createEventNameCursor(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(true, TABLE_EVENTS, new String[]{"rowid AS _id, key", "substr(start_date,1,11) || name AS title", TABLE_EVENTS_SHORT_NAME, TABLE_EVENTS_START_DATE},
                "", null, null, null, TABLE_EVENTS_START_DATE + " ASC", null);
    }

    private ContentValues mapBenchmarkingData(BenchmarkingData data){
        ContentValues values = new ContentValues();
        values.put(TABLE_BENCHMARK_KEY, String.valueOf(data.getTeamNumber()));
        values.put(TABLE_BENCHMARK_TEAM_NUMBER, data.getTeamNumber());
        values.put(TABLE_BENCHMARK_EVENT_NAME, data.getEventName());
        values.put(TABLE_BENCHMARK_STUDENT, data.getStudent());
        values.put(TABLE_BENCHMARK_DRIVE_SYSTEM, data.getDriveSystem());
        values.put(TABLE_BENCHMARK_DRIVES_SPEED, data.getDrivesSpeed());
        values.put(TABLE_BENCHMARK_CAN_PLAY_DEFENSE_BENCH_BUTTON, data.isCanPlayDefenseBenchButton());
        values.put(TABLE_BENCHMARK_ABILITY_TO_SHOOT_HIGH_GOAL_BENCH_BUTTON, data.isAbilityToShootHighGoalBenchButton());
        values.put(TABLE_BENCHMARK_TYPE_OF_SHOOTER_BENCH_INPUT, data.getTypeOfShooterBenchInput());
        values.put(TABLE_BENCHMARK_BALLS_PER_SECOND_BENCH_INPUT, data.getBallsPerSecondBenchInput());
        values.put(TABLE_BENCHMARK_BALLS_IN_CYCLE_BENCH_INPUT, data.getBallsInCycleBenchInput());
        values.put(TABLE_BENCHMARK_CYCLE_TIME_HIGH_BENCH_INPUT, data.getCycleTimeHighBenchInput());
        values.put(TABLE_BENCHMARK_SHOOTING_RANGE_BENCH_INPUT, data.getShootingRangeBenchInput());
        values.put(TABLE_BENCHMARK_PREFERRED_SHOOTING_LOCATION_BENCH_INPUT, data.getPreferredShootingLocationBenchInput());
        values.put(TABLE_BENCHMARK_ACCURACY_HIGH_BENCH_INPUT, data.getAccuracyHighBenchInput());
        values.put(TABLE_BENCHMARK_PICKUP_BALL_HOPPER_BENCH_BUTTON, data.isPickupBallHopperBenchButton());
        values.put(TABLE_BENCHMARK_PICKUP_BALL_FLOOR_BENCH_BUTTON, data.isPickupBallFloorBenchButton());
        values.put(TABLE_BENCHMARK_PICKUP_BALL_HUMAN_BENCH_BUTTON, data.isPickupBallHumanBenchButton());
        values.put(TABLE_BENCHMARK_PICKUP_BALL_PREFERRED_BENCH_INPUT, data.getPickupBallPreferredBenchInput());
        values.put(TABLE_BENCHMARK_MAXIMUM_BALL_CAPACITY_BENCH_INPUT, data.getMaximumBallCapacityBenchInput());
        values.put(TABLE_BENCHMARK_CAN_SCORE_GEARS_BENCH_BUTTON, data.isCanScoreGearsBenchButton());
        values.put(TABLE_BENCHMARK_PICKUP_GEAR_FLOOR_BENCH_BUTTON, data.isPickupGearFloorBenchButton());
        values.put(TABLE_BENCHMARK_PICKUP_GEAR_RETRIEVAL_BENCH_BUTTON, data.isPickupGearRetrievalBenchButton());
        values.put(TABLE_BENCHMARK_RADIO_PICKUP_GEAR_PREFERRED, data.getPickupGearPreferred());
        values.put(TABLE_BENCHMARK_CAN_GEAR_LEFT_BENCH, data.isCanGearLeftBench());
        values.put(TABLE_BENCHMARK_CAN_GEAR_CENTER_BENCH, data.isCanGearCenterBench());
        values.put(TABLE_BENCHMARK_CAN_GEAR_RIGHT_BENCH, data.isCanGearRightBench());
        values.put(TABLE_BENCHMARK_RADIO_PREFERRED_GEAR, data.getRadioPreferredGear());
        values.put(TABLE_BENCHMARK_CYCLE_TIME_GEARS_BENCH_INPUT, data.getCycleTimeGearsBenchInput());
        values.put(TABLE_BENCHMARK_ABILITY_TO_SHOOT_LOW_GOAL_BENCH_BUTTON, data.isAbilityToShootLowGoalBenchButton());
        values.put(TABLE_BENCHMARK_CYCLE_TIME_LOW_BENCH_INPUT, data.getCycleTimeLowBenchInput());
        values.put(TABLE_BENCHMARK_CYCLE_NUMBER_LOW_BENCH_INPUT, data.getCycleNumberLowBenchInput());
        values.put(TABLE_BENCHMARK_ABILITY_SCALE_BENCH_BUTTON, data.isAbilityScaleBenchButton());
        values.put(TABLE_BENCHMARK_PLACES_CAN_SCALE_RIGHT, data.isPlacesCanScaleRight());
        values.put(TABLE_BENCHMARK_PLACES_CAN_SCALE_CENTER, data.isPlacesCanScaleCenter());
        values.put(TABLE_BENCHMARK_PLACES_CAN_SCALE_LEFT, data.isPlacesCanScaleLeft());
        values.put(TABLE_BENCHMARK_BENCHMARK_WAS_DONE_BUTTON, data.isBenchmarkingWasDoneButton());
        values.put(TABLE_BENCHMARK_PREFERRED_PLACES_SCALE_INPUT, data.getPreferredPlacesScaleInput());
        values.put(TABLE_BENCHMARK_AUTO_ABILITIES_BENCH, data.getAutoAbilitiesBench());
        values.put(TABLE_BENCHMARK_COMMENTS_BENCH, data.getCommentsBench());

        return values;
    }

    private static BenchmarkingData mapBenchmarking(Cursor c) {
        BenchmarkingData data = new BenchmarkingData(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_TEAM_NUMBER)), c.getString(c.getColumnIndex(TABLE_BENCHMARK_EVENT_NAME)), c.getString(c.getColumnIndex(TABLE_BENCHMARK_STUDENT)));

        data.setDriveSystem(c.getString(c.getColumnIndex(TABLE_BENCHMARK_DRIVE_SYSTEM)));
        data.setDrivesSpeed(c.getDouble(c.getColumnIndex(TABLE_BENCHMARK_DRIVES_SPEED)));
        data.setCanPlayDefenseBenchButton(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_CAN_PLAY_DEFENSE_BENCH_BUTTON)) == 1);
        data.setAbilityToShootHighGoalBenchButton(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_ABILITY_TO_SHOOT_HIGH_GOAL_BENCH_BUTTON)) == 1);
        data.setTypeOfShooterBenchInput(c.getString(c.getColumnIndex(TABLE_BENCHMARK_TYPE_OF_SHOOTER_BENCH_INPUT)));
        data.setBallsPerSecondBenchInput(c.getDouble(c.getColumnIndex(TABLE_BENCHMARK_BALLS_PER_SECOND_BENCH_INPUT)));
        data.setBallsInCycleBenchInput(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_BALLS_IN_CYCLE_BENCH_INPUT)));
        data.setCycleTimeHighBenchInput(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_CYCLE_TIME_HIGH_BENCH_INPUT)));
        data.setShootingRangeBenchInput(c.getDouble(c.getColumnIndex(TABLE_BENCHMARK_SHOOTING_RANGE_BENCH_INPUT)));
        data.setPreferredShootingLocationBenchInput(c.getString(c.getColumnIndex(TABLE_BENCHMARK_PREFERRED_SHOOTING_LOCATION_BENCH_INPUT)));
        data.setAccuracyHighBenchInput(c.getDouble(c.getColumnIndex(TABLE_BENCHMARK_ACCURACY_HIGH_BENCH_INPUT)));
        data.setPickupBallHopperBenchButton(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_PICKUP_BALL_HOPPER_BENCH_BUTTON)) == 1);
        data.setPickupBallFloorBenchButton(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_PICKUP_BALL_FLOOR_BENCH_BUTTON)) == 1);
        data.setPickupBallHumanBenchButton(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_PICKUP_BALL_HUMAN_BENCH_BUTTON)) == 1);
        data.setPickupBallPreferredBenchInput(c.getString(c.getColumnIndex(TABLE_BENCHMARK_PICKUP_BALL_PREFERRED_BENCH_INPUT)));
        data.setMaximumBallCapacityBenchInput(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_MAXIMUM_BALL_CAPACITY_BENCH_INPUT)));
        data.setCanScoreGearsBenchButton(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_CAN_SCORE_GEARS_BENCH_BUTTON)) == 1);
        data.setPickupGearFloorBenchButton(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_PICKUP_GEAR_FLOOR_BENCH_BUTTON)) == 1);
        data.setPickupGearRetrievalBenchButton(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_PICKUP_GEAR_RETRIEVAL_BENCH_BUTTON)) == 1);
        data.setPickupGearPreferred(c.getString(c.getColumnIndex(TABLE_BENCHMARK_RADIO_PICKUP_GEAR_PREFERRED)));
        data.setCanGearLeftBench(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_CAN_GEAR_LEFT_BENCH)) == 1);
        data.setCanGearCenterBench(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_CAN_GEAR_CENTER_BENCH)) == 1);
        data.setCanGearRightBench(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_CAN_GEAR_RIGHT_BENCH)) == 1);
        data.setRadioPreferredGear(c.getString(c.getColumnIndex(TABLE_BENCHMARK_RADIO_PREFERRED_GEAR)));
        data.setCycleTimeGearsBenchInput(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_CYCLE_TIME_GEARS_BENCH_INPUT)));
        data.setAbilityToShootLowGoalBenchButton(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_ABILITY_TO_SHOOT_LOW_GOAL_BENCH_BUTTON)) == 1);
        data.setCycleTimeLowBenchInput(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_CYCLE_TIME_LOW_BENCH_INPUT)));
        data.setCycleNumberLowBenchInput(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_CYCLE_NUMBER_LOW_BENCH_INPUT)));
        data.setAbilityScaleBenchButton(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_ABILITY_SCALE_BENCH_BUTTON)) == 1);
        data.setPlacesCanScaleRight(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_PLACES_CAN_SCALE_RIGHT)) == 1);
        data.setPlacesCanScaleCenter(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_PLACES_CAN_SCALE_CENTER)) == 1);
        data.setPlacesCanScaleLeft(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_PLACES_CAN_SCALE_LEFT)) == 1);
        data.setBenchmarkWasDoneButton(c.getInt(c.getColumnIndex(TABLE_BENCHMARK_BENCHMARK_WAS_DONE_BUTTON)) == 1);
        data.setPreferredPlacesScaleInput(c.getString(c.getColumnIndex(TABLE_BENCHMARK_PREFERRED_PLACES_SCALE_INPUT)));
        data.setAutoAbilitiesBench(c.getString(c.getColumnIndex(TABLE_BENCHMARK_AUTO_ABILITIES_BENCH)));
        data.setCommentsBench(c.getString(c.getColumnIndex(TABLE_BENCHMARK_COMMENTS_BENCH)));

        return data;
    }

    public boolean doesBenchmarkingDataExist(BenchmarkingData data){
        boolean retVal = false;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_BENCHMARK, new String[]{"COUNT(*)"},
                TABLE_BENCHMARK_KEY + " = ?", new String[]{String.valueOf(data.getTeamNumber())}, null, null, null);

        if(c != null && c.moveToNext())
            retVal = c.getInt(0) > 0;
        if(c!=null)
            c.close();
        return retVal;
    }

    public void updateBenchmarkingData(BenchmarkingData data){
        SQLiteDatabase db = getWritableDatabase();

        db.update(TABLE_BENCHMARK, mapBenchmarkingData(data), TABLE_BENCHMARK_KEY + " = ?", new String[]{String.valueOf(data.getTeamNumber())});
    }

    public void createBenchmarkingData(BenchmarkingData data){
        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_BENCHMARK, null, mapBenchmarkingData(data));
    }

    public List<BenchmarkingData> getAllBenchmarkingData(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_BENCHMARK, new String[]{"*"}, null, null, null, null, null);

        List<BenchmarkingData> retVal = new ArrayList<BenchmarkingData>();
        while (c != null && c.moveToNext())
            retVal.add(mapBenchmarking(c));

        if (c != null) {
            c.close();
        }
        return retVal;
    }

    private ContentValues mapScoutingData(ScoutingData data){
        ContentValues values = new ContentValues();
        values.put(TABLE_SCOUT_KEY, data.getScoutingKey());
        values.put(TABLE_SCOUT_SKEY, data.getScoutingKey());
        values.put(TABLE_SCOUT_TEAM_NUMBER, data.getTeamNumber());
        values.put(TABLE_SCOUT_EVENT_NAME, data.getEventName());
        values.put(TABLE_SCOUT_STUDENT, data.getStudent());
        values.put(TABLE_SCOUT_CROSSED_BASELINE, data.isCrossedBaseline());
        values.put(TABLE_SCOUT_HOPPERS_DUMPED, data.getHoppersDumped());
        values.put(TABLE_SCOUT_GEAR_SCORED_RIGHT_AUTO, data.isGearScoredRightAuto());
        values.put(TABLE_SCOUT_GEAR_SCORED_CENTER_AUTO, data.isGearScoredCenterAuto());
        values.put(TABLE_SCOUT_GEAR_SCORED_LEFT_AUTO, data.isGearScoredLeftAuto());
        values.put(TABLE_SCOUT_GEARS_SCORED, data.getGearsScored());
        values.put(TABLE_SCOUT_GEARS_DELIVERED, data.getGearsDelivered());
        values.put(TABLE_SCOUT_GEARS_COLLECTED_FROM_FLOOR, data.getGearsCollectedFromFloor());
        values.put(TABLE_SCOUT_GEARS_FROM_HUMAN, data.getGearsFromHuman());
        values.put(TABLE_SCOUT_SCORES_HIGH_AUTO, data.getScoresHighAuto());
        values.put(TABLE_SCOUT_SCORES_LOW_AUTO, data.getScoresLowAuto());
        values.put(TABLE_SCOUT_BALLS_IN_HIGH_CYCLE, data.getBallsInHighCycle());
        values.put(TABLE_SCOUT_BALLS_FROM_HUMAN, data.getBallsFromHuman());
        values.put(TABLE_SCOUT_BALLS_FROM_HOPPER, data.getBallsFromHopper());
        values.put(TABLE_SCOUT_BALLS_FROM_FLOOR, data.getBallsFromFloor());
        values.put(TABLE_SCOUT_FUEL_IN_LOW_CYCLE, data.getFuelInLowCycle());
        values.put(TABLE_SCOUT_NUMBER_OF_LOW_CYCLES, data.getNumberOfLowCycles());
        values.put(TABLE_SCOUT_HIGH_GOAL_ACCURACY, data.getHighGoalAccuracy());
        values.put(TABLE_SCOUT_DID_SCALE, data.isDidScale());
        values.put(TABLE_SCOUT_WHERE_SCALED, data.getWhereScaled());
        values.put(TABLE_SCOUT_MATCH_SCOUTED, data.isMatchScouted());

        return values;
    }

    private static ScoutingData mapScouting(Cursor c) {
        ScoutingData data = new ScoutingData(c.getString(c.getColumnIndex(TABLE_SCOUT_SKEY)), c.getInt(c.getColumnIndex(TABLE_SCOUT_TEAM_NUMBER)), c.getString(c.getColumnIndex(TABLE_SCOUT_EVENT_NAME)), c.getString(c.getColumnIndex(TABLE_SCOUT_STUDENT)));

        data.setCrossedBaseline(c.getInt(c.getColumnIndex(TABLE_SCOUT_CROSSED_BASELINE)) == 1);
        data.setHoppersDumped(c.getInt(c.getColumnIndex(TABLE_SCOUT_HOPPERS_DUMPED)));
        data.setGearScoredRightAuto(c.getInt(c.getColumnIndex(TABLE_SCOUT_GEAR_SCORED_RIGHT_AUTO)) == 1);
        data.setGearScoredCenterAuto(c.getInt(c.getColumnIndex(TABLE_SCOUT_GEAR_SCORED_CENTER_AUTO)) == 1);
        data.setGearScoredLeftAuto(c.getInt(c.getColumnIndex(TABLE_SCOUT_GEAR_SCORED_LEFT_AUTO)) == 1);
        data.setGearsScored(c.getInt(c.getColumnIndex(TABLE_SCOUT_GEARS_SCORED)));
        data.setGearsDelivered(c.getInt(c.getColumnIndex(TABLE_SCOUT_GEARS_DELIVERED)));
        data.setGearsCollectedFromFloor(c.getInt(c.getColumnIndex(TABLE_SCOUT_GEARS_COLLECTED_FROM_FLOOR)));
        data.setGearsFromHuman(c.getInt(c.getColumnIndex(TABLE_SCOUT_GEARS_FROM_HUMAN)));
        data.setScoresHighAuto(c.getString(c.getColumnIndex(TABLE_SCOUT_SCORES_HIGH_AUTO)));
        data.setScoresLowAuto(c.getString(c.getColumnIndex(TABLE_SCOUT_SCORES_LOW_AUTO)));
        data.setBallsInHighCycle(c.getInt(c.getColumnIndex(TABLE_SCOUT_BALLS_IN_HIGH_CYCLE)));
        data.setBallsFromHuman(c.getInt(c.getColumnIndex(TABLE_SCOUT_BALLS_FROM_HUMAN)));
        data.setBallsFromHopper(c.getInt(c.getColumnIndex(TABLE_SCOUT_BALLS_FROM_HOPPER)));
        data.setBallsFromFloor(c.getInt(c.getColumnIndex(TABLE_SCOUT_BALLS_FROM_FLOOR)));
        data.setFuelInLowCycle(c.getInt(c.getColumnIndex(TABLE_SCOUT_FUEL_IN_LOW_CYCLE)));
        data.setNumberOfLowCycles(c.getInt(c.getColumnIndex(TABLE_SCOUT_NUMBER_OF_LOW_CYCLES)));
        data.setHighGoalAccuracy(c.getString(c.getColumnIndex(TABLE_SCOUT_HIGH_GOAL_ACCURACY)));
        data.setDidScale(c.getInt(c.getColumnIndex(TABLE_SCOUT_DID_SCALE)) == 1);
        data.setWhereScaled(c.getString(c.getColumnIndex(TABLE_SCOUT_WHERE_SCALED)));
        data.setMatchScouted(c.getInt(c.getColumnIndex(TABLE_SCOUT_MATCH_SCOUTED)) == 1);


        return data;
    }

    public void createScoutingData(ScoutingData data){
        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_SCOUT, null, mapScoutingData(data));
    }

    public List<ScoutingData> getAllScoutingDatas(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_SCOUT, new String[]{"*"}, null, null, null, null, null);

        List<ScoutingData> retVal = new ArrayList<>();
        while (c != null && c.moveToNext())
            retVal.add(mapScouting(c));

        if (c != null) {
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

    public void createTeam(Team team){
        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_TEAMS, null, mapTeam(team));
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

    public void updateTeam(Team team){
        getWritableDatabase().update(TABLE_TEAMS, mapTeam(team), TABLE_TEAMS_KEY + " = ?", new String[]{team.getKey()});
    }

    public Cursor createTeamCursor(Event event){
        SQLiteDatabase db = getReadableDatabase();

        return db.query(TABLE_TEAMS+","+TABLE_E2T,
                new String[]{"*", TABLE_TEAMS+".rowid AS _id", TABLE_TEAMS+".key AS team_key"},
                TABLE_TEAMS+"."+TABLE_TEAMS_KEY+" = "+TABLE_E2T+"."+TABLE_E2T_TEAM
                        +" AND "+TABLE_E2T+"."+TABLE_E2T_EVENT+" = ?",
                new String[]{event.getKey()},
                null, null, TABLE_TEAMS+"."+TABLE_TEAMS_TEAM_NUMBER+" ASC");
    }

    public void createE2TAssociation(String eventKey, String teamKey){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_E2T_EVENT, eventKey);
        values.put(TABLE_E2T_TEAM, teamKey);

        db.insert(TABLE_E2T, null, values);
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
}