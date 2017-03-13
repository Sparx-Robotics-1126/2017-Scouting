package com.sparx1126.steamworks.components;


import org.gosparx.scouting.aerialassist.BenchmarkingData;
import org.gosparx.scouting.aerialassist.TeamData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewScreenListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String> > expandableListDetail = new HashMap<String, List<String>>();
        TeamData displayedInfo = TeamData.getCurrentTeam();
        BenchmarkingData benchmarkingData = displayedInfo.getBenchmarkingData();
        boolean benchmarked = benchmarkingData.isBenchmarkingWasDoneButton();
        List<String> drives = new ArrayList<String>();
        if(benchmarkingData.getDriveSystem() == null) {
            drives.add("<font color=\"black\"><b>Drive System: </b></font> ");
        }
        else {
            drives.add("<font color=\"black\"><b>Drive System: </b></font> " + benchmarkingData.getDriveSystem());
        }

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
        if(benchmarked == false) {
            drives.add("<font color=\"black\"><b>Can play defense: </b></font>");
        }
        else {
            drives.add("<font color=\"black\"><b>Can play defense: </b></font>" + benchmarkingData.isCanPlayDefenseBenchButton());
        }

        List<String> shooting = new ArrayList<String>();
        if(benchmarked == false) {
            shooting.add("<font color=\"black\"><b>Can shoot high: </b></font>");
        }
        else {
            shooting.add("<font color=\"black\"><b>Can shoot high: </b></font>" + benchmarkingData.isAbilityToShootHighGoalBenchButton());
        }
        if(benchmarked == false){
            shooting.add("<font color=\"black\"><b>Can shoot low: </b></font>");

        }
        else {
            shooting.add("<font color=\"black\"><b>Can shoot low: </b></font>" + benchmarkingData.isAbilityToShootLowGoalBenchButton());

        }
        if(benchmarkingData.getTypeOfShooterBenchInput() == null) {
            shooting.add("<font color=\"black\"><b>Type of shooter: </b></font>");
        }
        else {
            shooting.add("<font color=\"black\"><b>Type of shooter: </b></font>" + benchmarkingData.getTypeOfShooterBenchInput());
        }
        String ballsPerSecond = "";
        if(benchmarkingData.getBallsPerSecondBenchInput() != Double.MAX_VALUE) {
            ballsPerSecond = String.valueOf(benchmarkingData.getBallsPerSecondBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Balls per second: </b></font>" + ballsPerSecond);
        String ballsInCycleInput = "";
        if(benchmarkingData.getBallsInCycleBenchInput() != Integer.MAX_VALUE) {
            ballsInCycleInput = String.valueOf(benchmarkingData.getBallsInCycleBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Balls in one high cycle: </b></font>" + ballsInCycleInput);
        String cycleTimeHighBenchInput = "";
        if(benchmarkingData.getCycleTimeHighBenchInput() != 2147483647) {
            cycleTimeHighBenchInput = String.valueOf(benchmarkingData.getCycleTimeHighBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Cycle time High: </b></font>" + cycleTimeHighBenchInput);
        String cycleTimeLowBenchInput = "";
        if(benchmarkingData.getCycleTimeLowBenchInput() != Integer.MAX_VALUE) {
            cycleTimeLowBenchInput = String.valueOf(benchmarkingData.getCycleTimeLowBenchInput());
        }
        String cycleNumberLowBenchInput="";
        if(benchmarkingData.getCycleNumberLowBenchInput() != Integer.MAX_VALUE) {
            cycleNumberLowBenchInput = String.valueOf(benchmarkingData.getCycleNumberLowBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Number of low cycles: </b></font>" + cycleNumberLowBenchInput);
        shooting.add("<font color=\"black\"><b>Cycle time low: </b></font>"  + cycleTimeLowBenchInput);
        String shootingRangeBenchInput ="";
        if(benchmarkingData.getShootingRangeBenchInput() != Double.MAX_VALUE) {
            shootingRangeBenchInput = String.valueOf(benchmarkingData.getShootingRangeBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Shooting range: </b></font>" + shootingRangeBenchInput);
        if(benchmarkingData.getPreferredShootingLocationBenchInput() == null) {
            shooting.add("<font color=\"black\"><b>Preferred shooting place: </b></font>");
        }
        else {
            shooting.add("<font color=\"black\"><b>Preferred shooting place: </b></font>" + benchmarkingData.getPreferredShootingLocationBenchInput());
        }

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
        if(benchmarked == false) {
            shooting.add("<font color=\"black\"><b>Can get balls from Hopper: </b></font>");

        }
        else {
            shooting.add("<font color=\"black\"><b>Can get balls from Hopper: </b></font>"  + benchmarkingData.isPickupBallHopperBenchButton());

        }
        if(benchmarked == false) {
            shooting.add("<font color=\"black\"><b>Can get balls from Floor: </b></font>");

        }
        else {
            shooting.add("<font color=\"black\"><b>Can get balls from Floor: </b></font>"  + benchmarkingData.isPickupBallFloorBenchButton());

        }
        if(benchmarked == false) {
            shooting.add("<font color=\"black\"><b>Can get balls from Human: </b></font>");

        }
        else {
            shooting.add("<font color=\"black\"><b>Can get balls from Human: </b></font>"  + benchmarkingData.isPickupBallHumanBenchButton());

        }
        String preferredBall = "";
        if(benchmarkingData.getPickupBallPreferredBenchInput() == "radioBallHuman") {
            preferredBall = "Human Player";
        }
        if(benchmarkingData.getPickupBallPreferredBenchInput() == "radioBallHopper") {
            preferredBall = "Hopper";
        }
        if(benchmarkingData.getPickupBallPreferredBenchInput() == "radioBallFloor") {
            preferredBall = "Floor";
        }
        shooting.add("<font color=\"black\"><b>Prefers to get balls from: </b></font>" + preferredBall);
        String maximumBallCapacity = "";
        if(benchmarkingData.getMaximumBallCapacityBenchInput() != Integer.MAX_VALUE) {
            maximumBallCapacity = Integer.toString(benchmarkingData.getMaximumBallCapacityBenchInput());
        }
        if(maximumBallCapacity == "") {
            shooting.add("<font color=\"black\"><b>Can hold: </b></font>");
        }
        else {
            shooting.add("<font color=\"black\"><b>Can hold: </b></font>" + maximumBallCapacity + " balls");
        }
        if(benchmarkingData.getMaximumBallCapacityBenchInput() != Integer.MAX_VALUE) {
            maximumBallCapacity = String.valueOf(benchmarkingData.getMaximumBallCapacityBenchInput());
        }
        List<String> gears = new ArrayList<String>();
        if(benchmarked == false) {
            gears.add("<font color=\"black\"><b>Can Score Gears: </b></font>");
        }
        else {
            gears.add("<font color=\"black\"><b>Can Score Gears: </b></font>"  + benchmarkingData.isCanScoreGearsBenchButton());
        }
        if(benchmarked == false) {
            gears.add("<font color=\"black\"><b>Can score gears left: </b></font>");
        }
        else {
            gears.add("<font color=\"black\"><b>Can score gears left: </b></font>" + benchmarkingData.isCanGearLeftBench());
        }
        if(benchmarked == false) {
            gears.add("<font color=\"black\"><b>Can score gears Center: </b></font>");
        }
        else {
            gears.add("<font color=\"black\"><b>Can score gears Center: </b></font>" + benchmarkingData.isCanGearCenterBench());
        }
        if(benchmarked == false) {
            gears.add("<font color=\"black\"><b>Can score gears Right: </b></font>");
        }
        else {
            gears.add("<font color=\"black\"><b>Can score gears Right: </b></font>" + benchmarkingData.isCanGearRightBench());
        }
        String preferredGearScoring = "";
        if(benchmarkingData.getRadioPreferredGear() == "radioGearRight") {
            preferredGearScoring = "Right";
        }
        if(benchmarkingData.getRadioPreferredGear() == "radioGearCenter") {
            preferredGearScoring = "Center";
        }
        if(benchmarkingData.getRadioPreferredGear() == "radioGearLeft") {
            preferredGearScoring = "Left";
        }
        gears.add("<font color=\"black\"><b>Prefers to score gears: </b></font>"  + preferredGearScoring);
        if(benchmarked == false) {
            gears.add("<font color=\"black\"><b>Can get gears from floor: </b></font>");
        }
        else{
            gears.add("<font color=\"black\"><b>Can get gears from floor: </b></font>"  + benchmarkingData.isPickupGearFloorBenchButton());
        }
        if(benchmarked == false) {
            gears.add("<font color=\"black\"><b>Can get gears from retrieval: </b></font>");
        }
        else {
            gears.add("<font color=\"black\"><b>Can get gears from retrieval: </b></font>" + benchmarkingData.isPickupGearRetrievalBenchButton());
        }
        String gearPickup = "";
        if(benchmarkingData.getPickupGearPreferred() == "radioFloor") {
            gearPickup = "Floor";
        }
        if(benchmarkingData.getPickupGearPreferred() == "radioZone") {
            gearPickup = "Retrieval Zone";
        }
        gears.add("<font color=\"black\"><b>Preferred gear pickup location: </b></font>" + gearPickup);
        String cycleTimeGearsBenchInput="";
        if(benchmarkingData.getCycleTimeGearsBenchInput() != Integer.MAX_VALUE) {
            cycleTimeGearsBenchInput = String.valueOf(benchmarkingData.getCycleTimeGearsBenchInput());
        }
        gears.add("<font color=\"black\"><b>Cycle time: </b></font>"  + cycleTimeGearsBenchInput);

       /* List<String> lowGoal = new ArrayList<String>();


*/
        List<String> scaling = new ArrayList<String>();
        if(benchmarked == false) {
            scaling.add("<font color=\"black\"><b>Can scale: </b></font>");
        }
        else {
            scaling.add("<font color=\"black\"><b>Can scale: </b></font>" + benchmarkingData.isAbilityScaleBenchButton());
        }
        if(benchmarkingData.getPreferredPlacesScaleInput() == null) {
            scaling.add("<font color=\"black\"><b>Prefers to scale from: </b></font>");
        }
        else {
            scaling.add("<font color=\"black\"><b>Prefers to scale from: </b></font>" + benchmarkingData.getPreferredPlacesScaleInput());
        }

        List<String> auto = new ArrayList<String>();
        String autoAbilities = "";
        if(benchmarkingData.getAutoAbilitiesBench() != null) {
            autoAbilities = benchmarkingData.getAutoAbilitiesBench();
        }
        auto.add("<font color=\"black\"><b>Can </b></font>" + autoAbilities);

        List<String> otherComments = new ArrayList<String>();
        if(benchmarkingData.getCommentsBench() == null) {
            otherComments.add("<font color=\"black\"><b>Comments: </b></font>");
        }
        else {
            otherComments.add("<font color=\"black\"><b>Comments: </b></font>" + benchmarkingData.getCommentsBench());
        }
            expandableListDetail.put("Drives", drives);
            expandableListDetail.put("Shooting", shooting);
            expandableListDetail.put("Gears", gears);
            // expandableListDetail.put("Low Goal", lowGoal);
            expandableListDetail.put("Scaling", scaling);
            expandableListDetail.put("Auto", auto);
            expandableListDetail.put("Other Comments", otherComments);
            return expandableListDetail;
    }

}