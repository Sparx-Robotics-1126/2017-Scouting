package com.sparx1126.steamworks;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> drives = new ArrayList<String>();
        drives.add("Drive System:");
        drives.add("Speed: " + "m/s");
        drives.add("Can play defense:");

        List<String> shooting = new ArrayList<String>();
        shooting.add("Balls: ");
        shooting.add("Balls per second: ");
        shooting.add("Balls in one cycle: ");
        shooting.add("Cycle time: ");
        shooting.add("Shooting range: ");
        shooting.add("Preferred shooting place: ");
        shooting.add("Shooting accuracy: " + "%");
        shooting.add("Can get balls from: ");
        shooting.add("Prefers to get balls from: ");
        shooting.add("can hold: " + "balls");

        List<String> gears = new ArrayList<String>();
        gears.add("Gears: ");
        gears.add("can get gears from: ");
        gears.add("Can score from: ");
        gears.add("Prefers to score from: ");
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
        otherComments.add("COOMOMOMMMMENTS");



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