package org.gosparx.scouting.aerialassist.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TeamData {
    private static Map<Integer, TeamData> teamsDataMap;
    private static TeamData currentTeam;
    private int teamNumber = Integer.MAX_VALUE;
    private String eventName;
    private String student;
    private BenchmarkingData benchmarkingData;
    private ScoutingData currentScoutingData;
    private Vector<ScoutingData> scoutingDatas;

    private TeamData(int teamNumber, String eventName, String student) {
        this.teamNumber = teamNumber;
        this.eventName = eventName;
        this.student = student;
        benchmarkingData = new BenchmarkingData(teamNumber, eventName, student);
        currentScoutingData = new ScoutingData(teamNumber, eventName, student);
        scoutingDatas = new Vector<>();
    }

    public static synchronized void setTeamData(int teamNumber, String eventName, String student){
        if(teamsDataMap == null) {
            teamsDataMap = new HashMap<>();
        }

        if(!teamsDataMap.containsKey(teamNumber)) {
            TeamData teamData = new TeamData(teamNumber, eventName, student);
            teamsDataMap.put(teamNumber, teamData);
        }
        currentTeam = teamsDataMap.get(teamNumber);
    }

    public static synchronized TeamData getCurrentTeam(){
        return currentTeam;
    }

    public static synchronized Map<Integer, TeamData> getTeamsMap(){
        return teamsDataMap;
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

    public ScoutingData getCurrentScoutingData() {
        return currentScoutingData;
    }

    public void addScoutingData() {
        scoutingDatas.add(currentScoutingData);
        currentScoutingData = new ScoutingData(teamNumber, eventName, student);
    }

    public Vector<ScoutingData> getScoutingDatas() {
        return scoutingDatas;
    }
}

