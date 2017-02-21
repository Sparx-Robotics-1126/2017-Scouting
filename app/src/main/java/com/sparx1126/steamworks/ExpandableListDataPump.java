package com.sparx1126.steamworks;


import android.util.Pair;

import org.gosparx.scouting.aerialassist.dto.BenchmarkingData;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String> > expandableListDetail = new HashMap<String, List<String>>();

        ScoutingInfo displayedInfo = ScoutingInfo.getCurrentInfo();
        BenchmarkingData benchmarkingData = displayedInfo.getBenchmarkingData();

        List<String> drives = new ArrayList<String>();
        drives.add("<font color=\"black\"><b>Drive System: </b></font> " + benchmarkingData.getDriveSystem());
        String drivesSpeed = "";
        if(benchmarkingData.getDrivesSpeed() != Double.MAX_VALUE) {
            drivesSpeed = String.valueOf(benchmarkingData.getDrivesSpeed());
        }
        drives.add("<font color=\"black\"><b>Speed: </b></font> " + drivesSpeed + " m/s");
        drives.add("<font color=\"black\"><b>Can play defense: </b></font>" + benchmarkingData.isCanPlayDefenseBenchButton());
        List<String> shooting = new ArrayList<String>();
        shooting.add("<font color=\"black\"><b>Can Shoot High: </b></font>" + benchmarkingData.isAbilityToShootHighGoalBenchButton());
        shooting.add("<font color=\"black\"><b>Ability to shoot low: </b></font>" + benchmarkingData.isAbilityToShootLowGoalBenchButton());
        shooting.add("<font color=\"black\"><b>Type of shooter: </b></font>" + benchmarkingData.getTypeOfShooterBenchInput());
        shooting.add("<font color=\"black\"><b>Balls per second: </b></font>" + benchmarkingData.getBallsPerSecondBenchInput());
        shooting.add("<font color=\"black\"><b><Balls per second: /b></font>" + benchmarkingData.getBallsPerSecondBenchInput());
        shooting.add("<font color=\"black\"><b>Balls in one cycle: </b></font>" + benchmarkingData.getBallsInCycleBenchInput());
        shooting.add("<font color=\"black\"><b>Cycle time High: </b></font>" + benchmarkingData.getCycleTimeHighBenchInput());
        shooting.add("<font color=\"black\"><b>Shooting range: </b></font>" + benchmarkingData.getShootingRangeBenchInput());
        shooting.add("<font color=\"black\"><b>Preferred shooting place: </b></font>" + benchmarkingData.getPreferredShootingLocationBenchInput());
        shooting.add("<font color=\"black\"><b>High Goal  accuracy: </b></font>" + benchmarkingData.getAccuracyHighBenchInput() + "%");
        shooting.add("<font color=\"black\"><b>Can get balls from Hopper: </b></font>"  + benchmarkingData.isPickupBallHopperBenchButton());
        shooting.add("<font color=\"black\"><b>Can get balls from Floor: </b></font>"  + benchmarkingData.isPickupBallFloorBenchButton());
        shooting.add("<font color=\"black\"><b>Can get balls from Human: </b></font>"  + benchmarkingData.isPickupBallHumanBenchButton());
        shooting.add("<font color=\"black\"><b>Prefers to get balls from: </b></font>" + benchmarkingData.getPickupBallPreferredBenchInput());
        shooting.add("<font color=\"black\"><b>can hold: </b></font>" + benchmarkingData.getMaximumBallCapacityBenchInput() + "balls");

        List<String> gears = new ArrayList<String>();
        gears.add("Can Score Gears: ");
        gears.add("can get gears from floor: ");
        gears.add("can get gears from retrieval: ");
        gears.add("Prefers to get gears from retrieval");
        gears.add("Can score gears left: " + benchmarkingData.isCanGearLeftBench());
        gears.add("Cycle time: ");

        List<String> lowGoal = new ArrayList<String>();
        lowGoal.add("Can low goal: ");
        lowGoal.add("Cycle time: ");
        lowGoal.add("Number cycles: ");

        List<String> scaling = new ArrayList<String>();
        scaling.add("Can scaling: ");
        scaling.add("Can scale from: ");
        scaling.add("Prefers to scale from: ");

        List<String> auto = new ArrayList<String>();
        auto.add("can do: ");

        List<String> otherComments = new ArrayList<String>();
        otherComments.add("Comments");



        expandableListDetail.put("Drives", drives);
        expandableListDetail.put("Shooting", shooting);
        expandableListDetail.put("Gears", gears);
        expandableListDetail.put("Low Goal", lowGoal);
        expandableListDetail.put("Scaling", scaling);
        expandableListDetail.put("Auto", auto);
        expandableListDetail.put("Other Comments", otherComments);
        return expandableListDetail;
    }
}