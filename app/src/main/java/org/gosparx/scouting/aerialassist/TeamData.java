package org.gosparx.scouting.aerialassist;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TeamData {
    private static Map<Integer, TeamData> teamsDataMap;
    private static TeamData currentTeam;
    private int teamNumber;
    private String eventName;
    private String student;
    private BenchmarkingData currentBenchmarkingData;
    private Vector<ScoutingData> scoutingDatas;

    private TeamData(BenchmarkingData benchmarkingData) {
        this.teamNumber = benchmarkingData.getTeamNumber();
        this.eventName = benchmarkingData.getEventName();
        this.student = benchmarkingData.getStudent();
        currentBenchmarkingData = benchmarkingData;
        scoutingDatas = new Vector<>();
    }

    public static synchronized void setTeamData(int teamNumber, String eventName, String student){
        setTeamData(new BenchmarkingData(teamNumber, eventName, student));
    }

    public static synchronized void setTeamData(BenchmarkingData benchmarkingData){
        if(teamsDataMap == null) {
            teamsDataMap = new HashMap<>();
        }

        if(!teamsDataMap.containsKey(benchmarkingData.getTeamNumber())) {
            TeamData teamData = new TeamData(benchmarkingData);
            teamsDataMap.put(benchmarkingData.getTeamNumber(), teamData);
        }
        currentTeam = teamsDataMap.get(benchmarkingData.getTeamNumber());
    }

    public static synchronized void setTeamData(ScoutingData scoutingData){
        setTeamData(new BenchmarkingData(scoutingData.getTeamNumber(), scoutingData.getEventName(), scoutingData.getStudent()));
        currentTeam.addScoutingData(scoutingData);
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

    public BenchmarkingData getBenchmarkingData() { return currentBenchmarkingData; }

    public void addScoutingData(ScoutingData scoutingData) {
        scoutingDatas.add(scoutingData);
    }

    public Vector<ScoutingData> getScoutingDatas() {
        return scoutingDatas;
    }
}

