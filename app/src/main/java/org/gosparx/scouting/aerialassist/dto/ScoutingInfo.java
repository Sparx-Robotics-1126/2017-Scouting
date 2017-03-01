package org.gosparx.scouting.aerialassist.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ScoutingInfo {
    private static Map<Integer, ScoutingInfo> scoutingInfoMap;
    private static ScoutingInfo currentInfo;

    public static synchronized void addInfo(int teamNumber, String eventName, String student){
        if(scoutingInfoMap == null) {
            scoutingInfoMap = new HashMap<>();
        }

        if(!scoutingInfoMap.containsKey(teamNumber)) {
            ScoutingInfo currentInfo = new ScoutingInfo(teamNumber, eventName, student);
            scoutingInfoMap.put(teamNumber, currentInfo);
        }
        currentInfo = scoutingInfoMap.get(teamNumber);
    }

    public static synchronized ScoutingInfo getCurrentInfo(){
        return currentInfo;
    }

    public static synchronized Map<Integer, ScoutingInfo> getInfoMap(){
        return scoutingInfoMap;
    }

    private int teamNumber = Integer.MAX_VALUE;
    private String eventName;
    private String student;
    private BenchmarkingData benchmarkingData;
    private ScoutingData scoutingBeingEnteredData;
    private Vector<ScoutingData> scoutingDatas;

    private ScoutingInfo(int teamNumber, String eventName, String student) {
        this.teamNumber = teamNumber;
        this.eventName = eventName;
        this.student = student;
        benchmarkingData = new BenchmarkingData(teamNumber, eventName, student);
        scoutingBeingEnteredData = new ScoutingData();
        scoutingDatas = new Vector<>(250);
    }

    public int getTeamNumber() { return teamNumber; }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStudent() { return student; }

    public void setStudent(String student) { this.student = student; }

    public BenchmarkingData getBenchmarkingData() {
        return benchmarkingData;
    }

    public ScoutingData getScoutingBeingEnteredData() {
        return scoutingBeingEnteredData;
    }

    public void addScoutingData() {
        scoutingDatas.add(scoutingBeingEnteredData);
        scoutingBeingEnteredData = new ScoutingData();
    }
}

