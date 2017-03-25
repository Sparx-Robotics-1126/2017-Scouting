package org.gosparx.scouting.aerialassist;

//ßuild the app amandΩ

public class ScoutingData {
    private final String scoutingKey;
    private final int teamNumber;
    private final String eventName;
    private final String student;
    private boolean crossedBaseline;
    private int hoppersDumped = 0;
    private boolean gearScoredRightAuto;
    private boolean gearScoredCenterAuto;
    private boolean gearScoredLeftAuto;
    private int gearsScored = 0;
    private int gearsDelivered = 0;
    private int gearsCollectedFromFloor = 0;
    private int gearsFromHuman = 0;
    private int ballsInHighCycle = 0;
    private int ballsFromHuman = 0;
    private int ballsFromHopper = 0;
    private int ballsFromFloor = 0;
    private int fuelInLowCycle = 0;
    private String highGoalAccuracy = "";
    private boolean didScale;
    private String whereScaled = "";
    private boolean matchScouted;
    private String autoShooting;
    private String scoutingComments = "";


    public ScoutingData(String scoutingKey, int teamNumber, String eventName, String student) {
        this.scoutingKey = scoutingKey;
        this.teamNumber = teamNumber;
        this.eventName = eventName;
        this.student = student;
    }

    public String getScoutingKey() {
        return scoutingKey;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public String getEventName() {
        return eventName;
    }

    public String getStudent() {
        return student;
    }

    public Boolean isCrossedBaseline() {
        return crossedBaseline;
    }

    public void setCrossedBaseline(Boolean crossedBaseline) { this.crossedBaseline = crossedBaseline; }

    public int getHoppersDumped() {
        return hoppersDumped;
    }

    public void setHoppersDumped(int hoppersDumped) { this.hoppersDumped = hoppersDumped; }

    public Boolean isGearScoredRightAuto() {
        return gearScoredRightAuto;
    }

    public void setGearScoredRightAuto(Boolean gearScoredRightAuto) { this.gearScoredRightAuto = gearScoredRightAuto; }

    public Boolean isGearScoredLeftAuto() {
        return gearScoredLeftAuto;
    }

    public void setGearScoredLeftAuto(Boolean gearScoredLeftAuto) { this.gearScoredLeftAuto = gearScoredLeftAuto; }

    public Boolean isGearScoredCenterAuto() {
        return gearScoredCenterAuto;
    }

    public void setGearScoredCenterAuto(Boolean gearScoredCenterAuto) { this.gearScoredCenterAuto = gearScoredCenterAuto; }

    public int getGearsScored() {
        return gearsScored;
    }

    public void setGearsScored(int gearsScored) { this.gearsScored = gearsScored; }

    public int getGearsDelivered() {
        return gearsDelivered;
    }

    public void setGearsDelivered(int gearsDelivered) { this.gearsDelivered = gearsDelivered; }

    public int getGearsCollectedFromFloor() {
        return gearsCollectedFromFloor;
    }

    public void setGearsCollectedFromFloor(int gearsCollectedFromFloor) { this.gearsCollectedFromFloor = gearsCollectedFromFloor; }

    public int getGearsFromHuman() {
        return gearsFromHuman;
    }

    public void setGearsFromHuman(int gearsFromHuman) { this.gearsFromHuman = gearsFromHuman; }

    public void setAutoShooting(String autoShooting) { this.autoShooting = autoShooting; }

    public String getAutoShooting() { return autoShooting; }

    public int getBallsInHighCycle() { return ballsInHighCycle; }

    public void setBallsInHighCycle(int ballsInHighCycle) { this.ballsInHighCycle = ballsInHighCycle; }

    public int getBallsFromHuman() {
        return ballsFromHuman;
    }

    public void setBallsFromHuman(int ballsFromHuman) { this.ballsFromHuman = ballsFromHuman; }

    public int getBallsFromHopper() {
        return  ballsFromHopper;
    }

    public void setBallsFromHopper(int  ballsFromHopper) { this.ballsFromHopper =  ballsFromHopper; }

    public int getBallsFromFloor() {
        return  ballsFromFloor;
    }

    public void setBallsFromFloor(int  ballsFromFloor) { this.ballsFromFloor =  ballsFromFloor; }

    public int getFuelInLowCycle() {
        return  fuelInLowCycle;
    }

    public void setFuelInLowCycle(int  fuelInLowCycle) { this.fuelInLowCycle =  fuelInLowCycle; }

    public String getHighGoalAccuracy() {
        return highGoalAccuracy;
    }

    public void setHighGoalAccuracy(String highGoalAccuracy) { this.highGoalAccuracy = highGoalAccuracy; }

    public boolean isDidScale() {
        return didScale;
    }

    public void setDidScale(boolean didScale) { this.didScale = didScale; }

    public String getWhereScaled() {
        return whereScaled;
    }

    public void setWhereScaled(String whereScaled) { this.whereScaled= whereScaled;}

    public String getScoutingComments() { return scoutingComments; }

    public void setScoutingComments(String scoutingComments) { this.scoutingComments = scoutingComments;}

    public boolean isMatchScouted() {
        return matchScouted;
    }

    public void setMatchScouted(boolean matchScouted) {
        this.matchScouted = matchScouted;
    }
}
