package com.sparx1126.steamworks.components;


import org.gosparx.scouting.aerialassist.BenchmarkingData;
import org.gosparx.scouting.aerialassist.ScoutingData;
import org.gosparx.scouting.aerialassist.TeamData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewScreenListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String> > expandableListDetail = new HashMap<>();
        TeamData displayedInfo = TeamData.getCurrentTeam();
        BenchmarkingData benchmarkingData = displayedInfo.getBenchmarkingData();
        boolean benchmarked = benchmarkingData.isBenchmarkingWasDoneButton();
        List<String> drives = new ArrayList<>();
        if (benchmarkingData.getDriveSystem().isEmpty()) {
            drives.add("<font color=\"black\"><b>Drive System: </b></font> ");
        } else {
            drives.add("<font color=\"black\"><b>Drive System: </b></font> " + benchmarkingData.getDriveSystem());
        }

        String drivesSpeed = "";
        if (benchmarkingData.getDrivesSpeed() != Double.MAX_VALUE) {
            drivesSpeed = String.valueOf(benchmarkingData.getDrivesSpeed());
        }
        if (drivesSpeed.isEmpty()) {
            drives.add("<font color=\"black\"><b>Speed: </b></font> ");
        } else {
            drives.add("<font color=\"black\"><b>Speed: </b></font> " + drivesSpeed + "m/s");
        }
        if (!benchmarked) {
            drives.add("<font color=\"black\"><b>Can play defense: </b></font>");
        } else {
            drives.add("<font color=\"black\"><b>Can play defense: </b></font>" + benchmarkingData.isCanPlayDefenseBenchButton());
        }

        List<String> shooting = new ArrayList<>();
        if (!benchmarked) {
            shooting.add("<font color=\"black\"><b>Can shoot high: </b></font>");
        } else {
            shooting.add("<font color=\"black\"><b>Can shoot high: </b></font>" + benchmarkingData.isAbilityToShootHighGoalBenchButton());
        }
        if (!benchmarked) {
            shooting.add("<font color=\"black\"><b>Can shoot low: </b></font>");

        } else {
            shooting.add("<font color=\"black\"><b>Can shoot low: </b></font>" + benchmarkingData.isAbilityToShootLowGoalBenchButton());

        }
        if (benchmarkingData.getTypeOfShooterBenchInput().isEmpty()) {
            shooting.add("<font color=\"black\"><b>Type of shooter: </b></font>");
        } else {
            shooting.add("<font color=\"black\"><b>Type of shooter: </b></font>" + benchmarkingData.getTypeOfShooterBenchInput());
        }
        String ballsPerSecond = "";
        if (benchmarkingData.getBallsPerSecondBenchInput() != Double.MAX_VALUE) {
            ballsPerSecond = String.valueOf(benchmarkingData.getBallsPerSecondBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Balls per second: </b></font>" + ballsPerSecond);
        String ballsInCycleInput = "";
        if (benchmarkingData.getBallsInCycleBenchInput() != Integer.MAX_VALUE) {
            ballsInCycleInput = String.valueOf(benchmarkingData.getBallsInCycleBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Balls in one high cycle: </b></font>" + ballsInCycleInput);
        String cycleTimeHighBenchInput = "";
        if (benchmarkingData.getCycleTimeHighBenchInput() != 2147483647) {
            cycleTimeHighBenchInput = String.valueOf(benchmarkingData.getCycleTimeHighBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Cycle time High: </b></font>" + cycleTimeHighBenchInput);
        String cycleTimeLowBenchInput = "";
        if (benchmarkingData.getCycleTimeLowBenchInput() != Integer.MAX_VALUE) {
            cycleTimeLowBenchInput = String.valueOf(benchmarkingData.getCycleTimeLowBenchInput());
        }
        String cycleNumberLowBenchInput = "";
        if (benchmarkingData.getCycleNumberLowBenchInput() != Integer.MAX_VALUE) {
            cycleNumberLowBenchInput = String.valueOf(benchmarkingData.getCycleNumberLowBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Number of low cycles: </b></font>" + cycleNumberLowBenchInput);
        shooting.add("<font color=\"black\"><b>Cycle time low: </b></font>" + cycleTimeLowBenchInput);
        String shootingRangeBenchInput = "";
        if (benchmarkingData.getShootingRangeBenchInput() != Double.MAX_VALUE) {
            shootingRangeBenchInput = String.valueOf(benchmarkingData.getShootingRangeBenchInput());
        }
        shooting.add("<font color=\"black\"><b>Shooting range: </b></font>" + shootingRangeBenchInput);
        if (benchmarkingData.getPreferredShootingLocationBenchInput().isEmpty()) {
            shooting.add("<font color=\"black\"><b>Preferred shooting place: </b></font>");
        } else {
            shooting.add("<font color=\"black\"><b>Preferred shooting place: </b></font>" + benchmarkingData.getPreferredShootingLocationBenchInput());
        }

        String accuracyHighBenchInput = "";
        if (benchmarkingData.getAccuracyHighBenchInput() != Double.MAX_VALUE) {
            accuracyHighBenchInput = String.valueOf(benchmarkingData.getAccuracyHighBenchInput());
        }
        if (accuracyHighBenchInput.isEmpty()) {
            shooting.add("<font color=\"black\"><b>High Goal accuracy: </b></font>" + accuracyHighBenchInput);
        } else {
            shooting.add("<font color=\"black\"><b>High Goal accuracy: </b></font>" + accuracyHighBenchInput + "%");
        }
        if (!benchmarked) {
            shooting.add("<font color=\"black\"><b>Can get balls from Hopper: </b></font>");

        } else {
            shooting.add("<font color=\"black\"><b>Can get balls from Hopper: </b></font>" + benchmarkingData.isPickupBallHopperBenchButton());

        }
        if (!benchmarked) {
            shooting.add("<font color=\"black\"><b>Can get balls from Floor: </b></font>");

        } else {
            shooting.add("<font color=\"black\"><b>Can get balls from Floor: </b></font>" + benchmarkingData.isPickupBallFloorBenchButton());

        }
        if (!benchmarked) {
            shooting.add("<font color=\"black\"><b>Can get balls from Human: </b></font>");

        } else {
            shooting.add("<font color=\"black\"><b>Can get balls from Human: </b></font>" + benchmarkingData.isPickupBallHumanBenchButton());

        }
        String preferredBall = "";
        if (benchmarkingData.getPickupBallPreferredBenchInput().equals("radioBallHuman")) {
            preferredBall = "Human Player";
        }
        if (benchmarkingData.getPickupBallPreferredBenchInput().equals("radioBallHopper")) {
            preferredBall = "Hopper";
        }
        if (benchmarkingData.getPickupBallPreferredBenchInput().equals("radioBallFloor")) {
            preferredBall = "Floor";
        }
        shooting.add("<font color=\"black\"><b>Prefers to get balls from: </b></font>" + preferredBall);
        String maximumBallCapacity = "";
        if (benchmarkingData.getMaximumBallCapacityBenchInput() != Integer.MAX_VALUE) {
            maximumBallCapacity = Integer.toString(benchmarkingData.getMaximumBallCapacityBenchInput());
        }
        if (maximumBallCapacity.isEmpty()) {
            shooting.add("<font color=\"black\"><b>Can hold: </b></font>");
        } else {
            shooting.add("<font color=\"black\"><b>Can hold: </b></font>" + maximumBallCapacity + " balls");
        }
        List<String> gears = new ArrayList<>();
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can Score Gears: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can Score Gears: </b></font>" + benchmarkingData.isCanScoreGearsBenchButton());
        }
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can score gears left: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can score gears left: </b></font>" + benchmarkingData.isCanGearLeftBench());
        }
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can score gears Center: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can score gears Center: </b></font>" + benchmarkingData.isCanGearCenterBench());
        }
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can score gears Right: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can score gears Right: </b></font>" + benchmarkingData.isCanGearRightBench());
        }
        String preferredGearScoring = "";
        if (benchmarkingData.getRadioPreferredGear().equals("radioGearRight")) {
            preferredGearScoring = "Right";
        }
        if (benchmarkingData.getRadioPreferredGear().equals("radioGearCenter")) {
            preferredGearScoring = "Center";
        }
        if (benchmarkingData.getRadioPreferredGear().equals("radioGearLeft")) {
            preferredGearScoring = "Left";
        }
        gears.add("<font color=\"black\"><b>Prefers to score gears: </b></font>" + preferredGearScoring);
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can get gears from floor: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can get gears from floor: </b></font>" + benchmarkingData.isPickupGearFloorBenchButton());
        }
        if (!benchmarked) {
            gears.add("<font color=\"black\"><b>Can get gears from retrieval: </b></font>");
        } else {
            gears.add("<font color=\"black\"><b>Can get gears from retrieval: </b></font>" + benchmarkingData.isPickupGearRetrievalBenchButton());
        }
        String gearPickup = "";
        if (benchmarkingData.getPickupGearPreferred().equals("radioFloor")) {
            gearPickup = "Floor";
        }
        if (benchmarkingData.getPickupGearPreferred().equals("radioZone")) {
            gearPickup = "Retrieval Zone";
        }
        gears.add("<font color=\"black\"><b>Preferred gear pickup location: </b></font>" + gearPickup);
        String cycleTimeGearsBenchInput = "";
        if (benchmarkingData.getCycleTimeGearsBenchInput() != Integer.MAX_VALUE) {
            cycleTimeGearsBenchInput = String.valueOf(benchmarkingData.getCycleTimeGearsBenchInput());
        }
        gears.add("<font color=\"black\"><b>Cycle time: </b></font>" + cycleTimeGearsBenchInput);

       /* List<String> lowGoal = new ArrayList<String>();


*/

       List<String> scouting = new ArrayList<>();
        scouting.add("<font color=\"black\"><b>Matches scouted: </b></font>" + displayedInfo.getScoutingDatas().size());
        float hoppersDumped = 0;
        float gearsScoredRight = 0;
        float gearsScoredCenter = 0;
        float gearsScoredLeft = 0;
        float didScale = 0;
        float gearsScored = 0;
        float gearsDelivered = 0;
        float gearsCollectedFromFloor = 0;
        float gearsCollectedFromHuman = 0;
        String scoresHighAuto = "";
        for(ScoutingData sd: displayedInfo.getScoutingDatas()){
            hoppersDumped += sd.getHoppersDumped();
            gearsScored += sd.getGearsScored();
            gearsDelivered += sd.getGearsDelivered();
            gearsCollectedFromFloor += sd.getGearsCollectedFromFloor();
            gearsCollectedFromHuman += sd.getGearsFromHuman();

            if(sd.isGearScoredRightAuto()){
                gearsScoredRight++;
            }
            if(sd.isGearScoredCenterAuto()){
                gearsScoredCenter++;
            }
            if(sd.isGearScoredLeftAuto()){
                gearsScoredLeft++;
            }
            if(sd.isDidScale()){
                didScale++;
            }

        }
        hoppersDumped = hoppersDumped/displayedInfo.getScoutingDatas().size();
        gearsScored = gearsScored/displayedInfo.getScoutingDatas().size();
        gearsDelivered = gearsDelivered/displayedInfo.getScoutingDatas().size();
        gearsCollectedFromFloor = gearsCollectedFromFloor/displayedInfo.getScoutingDatas().size();
        gearsCollectedFromHuman = gearsCollectedFromHuman/displayedInfo.getScoutingDatas().size();
        gearsScoredRight = (gearsScoredRight/displayedInfo.getScoutingDatas().size())*100;
        gearsScoredCenter = (gearsScoredCenter/displayedInfo.getScoutingDatas().size())*100;
        gearsScoredLeft = (gearsScoredLeft/displayedInfo.getScoutingDatas().size())*100;
        didScale = (didScale/displayedInfo.getScoutingDatas().size())*100;
        scouting.add("<font color=\"black\"><b>Average hoppers dumped:  </b></font>" + hoppersDumped);
        scouting.add("<font color=\"black\"><b>Percent of matches scaled:  </b></font>" + didScale + "%");
        scouting.add("<font color=\"black\"><b>Percent of gears scored right in auto:  </b></font>" + gearsScoredRight + "%");
        scouting.add("<font color=\"black\"><b>Percent of gears scored center in auto:  </b></font>" + gearsScoredCenter + "%");
        scouting.add("<font color=\"black\"><b>Percent of gears scored left in auto:  </b></font>" + gearsScoredLeft + "%");
        scouting.add("<font color=\"black\"><b>Average gears scored:  </b></font>" + gearsScored);
        scouting.add("<font color=\"black\"><b>Average gears delivered:  </b></font>" + gearsDelivered);
        scouting.add("<font color=\"black\"><b>Average gears collected from floor:  </b></font>" + gearsCollectedFromFloor);
        scouting.add("<font color=\"black\"><b>Average gears collected from human:  </b></font>" + gearsCollectedFromHuman);


        List<String> scaling = new ArrayList<>();
        if (!benchmarked) {
            scaling.add("<font color=\"black\"><b>Can scale: </b></font>");
        } else {
            scaling.add("<font color=\"black\"><b>Can scale: </b></font>" + benchmarkingData.isAbilityScaleBenchButton());
        }
        if (benchmarkingData.getPreferredPlacesScaleInput().isEmpty()) {
            scaling.add("<font color=\"black\"><b>Prefers to scale from: </b></font>");
        } else {
            scaling.add("<font color=\"black\"><b>Prefers to scale from: </b></font>" + benchmarkingData.getPreferredPlacesScaleInput());
        }

        List<String> auto = new ArrayList<>();
        String autoAbilities = "";
        if (benchmarkingData.getAutoAbilitiesBench() != null) {
            autoAbilities = benchmarkingData.getAutoAbilitiesBench();
        }
        auto.add("<font color=\"black\"><b>Can </b></font>" + autoAbilities);

        List<String> otherComments = new ArrayList<>();
        if (benchmarkingData.getCommentsBench().isEmpty()) {
            otherComments.add("<font color=\"black\"><b>Comments: </b></font>");
        } else {
            otherComments.add("<font color=\"black\"><b>Comments: </b></font>" + benchmarkingData.getCommentsBench());
        }
        expandableListDetail.put("Drives", drives);
        expandableListDetail.put("Shooting", shooting);
        expandableListDetail.put("Gears", gears);
        // expandableListDetail.put("Low Goal", lowGoal);
        expandableListDetail.put("Scaling", scaling);
        expandableListDetail.put("Auto", auto);
        expandableListDetail.put("Other Comments", otherComments);
        expandableListDetail.put("Scouting", scouting);
        return expandableListDetail;
    }

}