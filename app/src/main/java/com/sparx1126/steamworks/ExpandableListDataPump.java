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
        drives.add("Drive System: " + benchmarkingData.getDriveSystem());
        drives.add("Speed: " + benchmarkingData.getDrivesSpeed() + "m/s");
        drives.add("Can play defense: " + benchmarkingData.isCanPlayDefenseBenchButton());
        List<String> shooting = new ArrayList<String>();
        shooting.add("Can Shoot High: " + benchmarkingData.isAbilityToShootHighGoalBenchButton());
        shooting.add("Ability to shoot low: " + benchmarkingData.isAbilityToShootLowGoalBenchButton());
        shooting.add("Type of shooter: " + benchmarkingData.getTypeOfShooterBenchInput());
        shooting.add("Balls per second: " + benchmarkingData.getBallsPerSecondBenchInput());
        shooting.add("Balls per second: " + benchmarkingData.getBallsPerSecondBenchInput());
        shooting.add("Balls in one cycle: " + benchmarkingData.getBallsInCycleBenchInput());
        shooting.add("Cycle time High: " + benchmarkingData.getCycleTimeHighBenchInput());
        shooting.add("Shooting range: " + benchmarkingData.getShootingRangeBenchInput());
        shooting.add("Preferred shooting place: " + benchmarkingData.getPreferredShootingLocationBenchInput());
        shooting.add("High Goal Shooting accuracy: " + benchmarkingData.getAccuracyHighBenchInput() + "%");
        shooting.add("Can get balls from Hopper: "  + benchmarkingData.isPickupBallHopperBenchButton());
        shooting.add("Can get balls from Floor: "  + benchmarkingData.isPickupBallFloorBenchButton());
        shooting.add("Can get balls from Human: "  + benchmarkingData.isPickupBallHumanBenchButton());
        shooting.add("Prefers to get balls from: " + benchmarkingData.getPickupBallPreferredBenchInput());
        shooting.add("can hold: " + benchmarkingData.getMaximumBallCapacityBenchInput() + "balls");

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