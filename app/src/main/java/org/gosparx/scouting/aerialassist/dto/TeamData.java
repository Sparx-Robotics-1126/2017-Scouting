package org.gosparx.scouting.aerialassist.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TeamData {
    private static Map<Integer, TeamData> teamsDataMap;
    private static TeamData currentTeam;
    private BenchmarkingData benchmarkingData;
    private ScoutingData currentScoutingData;
    private Vector<ScoutingData> scoutingDatas;

    private TeamData(BenchmarkingData benchmarkingData) {
        this.benchmarkingData = benchmarkingData;
        currentScoutingData = new ScoutingData(benchmarkingData.getTeamNumber(), benchmarkingData.getEventName(), benchmarkingData.getStudent());
        scoutingDatas = new Vector<>();
    }

    public static synchronized void setTeamData(BenchmarkingData benchmarkingData){
        if(teamsDataMap == null) {
            teamsDataMap = new HashMap<>();
        }

        if(!teamsDataMap.containsKey(benchmarkingData.getTeamNumber())) {
            TeamData teamData = new TeamData(benchmarkingData);
            teamsDataMap.put(teamData.getTeamNumber(), teamData);
        }
        currentTeam = teamsDataMap.get(benchmarkingData.getTeamNumber());
    }

    public static synchronized TeamData getCurrentTeam(){
        return currentTeam;
    }

    public static synchronized Map<Integer, TeamData> getTeamsMap(){
        return teamsDataMap;
    }

    public int getTeamNumber() { return benchmarkingData.getTeamNumber(); }

    public String getEventName() {
        return benchmarkingData.getEventName();
    }

    public String getStudent() { return benchmarkingData.getStudent(); }

    public BenchmarkingData getBenchmarkingData() {
        return benchmarkingData;
    }

    public ScoutingData getCurrentScoutingData() {
        return currentScoutingData;
    }

    public void addScoutingData() {
        scoutingDatas.add(currentScoutingData);
        currentScoutingData = new ScoutingData(getTeamNumber(), getEventName(), getStudent());
    }

    public Vector<ScoutingData> getScoutingDatas() {
        return scoutingDatas;
    }
}

