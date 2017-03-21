package org.gosparx.scouting.aerialassist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamData {
    private static Map<Integer, TeamData> teamsDataMap;
    private static TeamData currentTeam;
    private final int teamNumber;
    private final String eventName;
    private String student;
    private BenchmarkingData currentBenchmarkingData;
    private List<ScoutingData> scoutingDatas;

    private TeamData(int teamNumber, String eventName) {
        this.teamNumber = teamNumber;
        this.eventName = eventName;
        currentBenchmarkingData = new BenchmarkingData(teamNumber, eventName);
        scoutingDatas = new ArrayList<>();
    }

    public static synchronized void setTeamData(int teamNumber, String eventName){
        if(teamsDataMap == null) {
            teamsDataMap = new HashMap<>();
        }

        if(!teamsDataMap.containsKey(teamNumber)) {
            TeamData teamData = new TeamData(teamNumber, eventName);
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

    public String getEventName() {
        return eventName;
    }

    public String getStudent() { return student; }

    public void setStudent(String student) {this.student = student; }

    public BenchmarkingData getBenchmarkingData() { return currentBenchmarkingData; }

    public void setBenchmarkingData(BenchmarkingData benchmarkingData) { this.currentBenchmarkingData = benchmarkingData; }

    public void addScoutingData(ScoutingData scoutingData) {
        boolean found = false;
        for(ScoutingData sd : scoutingDatas) {
            if(sd.getScoutingKey().contentEquals(scoutingData.getScoutingKey())) {
                found = true;
                break;
            }
        }
        if(!found) {
            scoutingDatas.add(scoutingData);
        }
    }

    public List<ScoutingData> getScoutingDatas() {
        return scoutingDatas;
    }
}

