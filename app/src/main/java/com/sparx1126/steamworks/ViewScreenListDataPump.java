package com.sparx1126.steamworks;


import android.util.Pair;

import org.gosparx.scouting.aerialassist.dto.BenchmarkingData;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sparx1126.steamworks.R.string.autoAbilities;

public class ViewScreenListDataPump {
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
        if(drivesSpeed == "") {
            drives.add("<font color=\"black\"><b>Speed: </b></font> ");
        }
        else {
            drives.add("<font color=\"black\"><b>Speed: </b></font> " + drivesSpeed + "m/s");
        }
        drives.add("<font color=\"black\"><b>Can play defense: </b></font>" + benchmarkingData.isCanPlayDefenseBenchButton());
        List<String> shooting = new ArrayList<String>();
        shooting.add("<font color=\"black\"><b>Can Shoot High: </b></font>" + benchmarkingData.isAbilityToShootHighGoalBenchButton());
        shooting.add("<font color=\"black\"><b>Ability to shoot low: </b></font>" + benchmarkingData.isAbilityToShootLowGoalBenchButton());
        shooting.add("<font color=\"black\"><b>Type of shooter: </b></font>" + benchmarkingData.getTypeOfShooterBenchInput());
        String ballsPerSecond = "";
        if(benchmarkingData.getBallsPerSecondBenchInput() != Double.MAX_VALUE) {
            ballsPerSecond = String.valueOf(benchmarkingData.getBallsPerSecondBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Balls per second: </b></font>" + ballsPerSecond);
        String ballsInCycleInput = "";
        if(benchmarkingData.getBallsInCycleBenchInput() != Integer.MAX_VALUE) {
            ballsInCycleInput = String.valueOf(benchmarkingData.getBallsInCycleBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Balls in one cycle: </b></font>" + ballsInCycleInput);
        String cycleTimeHighBenchInput = "";
        if(benchmarkingData.getCycleTimeHighBenchInput() != 2147483647) {
            cycleTimeHighBenchInput = String.valueOf(benchmarkingData.getCycleTimeHighBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Cycle time High: </b></font>" + cycleTimeHighBenchInput);
        String shootingRangeBenchInput ="";
        if(benchmarkingData.getShootingRangeBenchInput() != Double.MAX_VALUE) {
            shootingRangeBenchInput = String.valueOf(benchmarkingData.getShootingRangeBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Shooting range: </b></font>" + shootingRangeBenchInput);
        shooting.add("<font color=\"black\"><b>Preferred shooting place: </b></font>" + benchmarkingData.getPreferredShootingLocationBenchInput());
        String accuracyHighBenchInput ="";
        if(benchmarkingData.getAccuracyHighBenchInput() != Double.MAX_VALUE) {
            accuracyHighBenchInput = String.valueOf(benchmarkingData.getAccuracyHighBenchInput());
        }
        if(accuracyHighBenchInput == "") {
            shooting.add("<font color=\"black\"><b>High Goal accuracy: </b></font>" + accuracyHighBenchInput);
        }
        else {
            shooting.add("<font color=\"black\"><b>High Goal accuracy: </b></font>" + accuracyHighBenchInput + "%");
        }
        shooting.add("<font color=\"black\"><b>Can get balls from Hopper: </b></font>"  + benchmarkingData.isPickupBallHopperBenchButton());
        shooting.add("<font color=\"black\"><b>Can get balls from Floor: </b></font>"  + benchmarkingData.isPickupBallFloorBenchButton());
        shooting.add("<font color=\"black\"><b>Can get balls from Human: </b></font>"  + benchmarkingData.isPickupBallHumanBenchButton());
        String preferredBall = "";
        if(benchmarkingData.getPickupBallPreferredBenchInput() == "radioBallHuman") {
            preferredBall = "Human Player";
        }
        shooting.add("<font color=\"black\"><b>Prefers to get balls from: </b></font>" + preferredBall);
        String maximumBallCapacityBenchInput="";
        if(maximumBallCapacityBenchInput == "") {
            shooting.add("<font color=\"black\"><b>can hold: </b></font>" + maximumBallCapacityBenchInput);
        }
        else {
            shooting.add("<font color=\"black\"><b>can hold: </b></font>" + maximumBallCapacityBenchInput + " balls");
        }
        if(benchmarkingData.getMaximumBallCapacityBenchInput() != Integer.MAX_VALUE) {
            maximumBallCapacityBenchInput = String.valueOf(benchmarkingData.getMaximumBallCapacityBenchInput());
        }
        List<String> gears = new ArrayList<String>();
        gears.add("<font color=\"black\"><b>Can Score Gears: </b></font>"  + benchmarkingData.isCanScoreGearsBenchButton());
        gears.add("<font color=\"black\"><b>Can get gears from floor: </b></font>"  + benchmarkingData.isPickupGearFloorBenchButton());
        gears.add("<font color=\"black\"><b>Can get gears from retrieval: </b></font>" + benchmarkingData.isPickupGearRetrievalBenchButton());
        gears.add("<font color=\"black\"><b>Prefers to get gears from retrieval: </b></font>"  + benchmarkingData.getRadioPreferredGear());
        gears.add("<font color=\"black\"><b>Can score gears left: </b></font>" + benchmarkingData.isCanGearLeftBench());
        String cycleTimeGearsBenchInput="";
        if(benchmarkingData.getCycleTimeGearsBenchInput() != Integer.MAX_VALUE) {
            cycleTimeGearsBenchInput = String.valueOf(benchmarkingData.getCycleTimeGearsBenchInput());
        }
        gears.add("<font color=\"black\"><b>Cycle time: </b></font>"  + cycleTimeGearsBenchInput);

        List<String> lowGoal = new ArrayList<String>();
        lowGoal.add("<font color=\"black\"><b>Can low goal: </b></font>"  + benchmarkingData.isAbilityToShootLowGoalBenchButton());
        String cycleTimeLowBenchInput="";
        if(benchmarkingData.getCycleTimeLowBenchInput() != Integer.MAX_VALUE) {
            cycleTimeLowBenchInput = String.valueOf(benchmarkingData.getCycleTimeLowBenchInput());
        }
        lowGoal.add("<font color=\"black\"><b>Cycle time: </b></font>"  + cycleTimeLowBenchInput);
        String cycleNumberLowBenchInput="";
        if(benchmarkingData.getCycleNumberLowBenchInput() != Integer.MAX_VALUE) {
            cycleNumberLowBenchInput = String.valueOf(benchmarkingData.getCycleNumberLowBenchInput());
        }
        lowGoal.add("<font color=\"black\"><b>Number of cycles: </b></font>" + cycleNumberLowBenchInput);

        List<String> scaling = new ArrayList<String>();
        scaling.add("<font color=\"black\"><b>Can scale: </b></font>" + benchmarkingData.isAbilityScaleBenchButton());
        scaling.add("<font color=\"black\"><b>Places they can scale from: " + benchmarkingData.getPlacesCanScaleBenchInput());
        scaling.add("<font color=\"black\"><b>Prefers to scale from: </b></font>" + benchmarkingData.getPreferredPlacesScaleInput());

        List<String> auto = new ArrayList<String>();
        String autoAbilities = "";
        if(benchmarkingData.getAutoAbilitiesBench() != null) {
            autoAbilities = benchmarkingData.getAutoAbilitiesBench();
        }
        auto.add("<font color=\"black\"><b>Can </b></font>" + autoAbilities);

        List<String> otherComments = new ArrayList<String>();
        otherComments.add("<font color=\"black\"><b>Comments: </b></font>" + benchmarkingData.getCommentsBench());



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