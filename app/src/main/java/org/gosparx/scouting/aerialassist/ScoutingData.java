package org.gosparx.scouting.aerialassist;

//ßuild the app amandΩ

public class ScoutingData {
    private final String scoutingKey;
    private final int teamNumber;
    private final String eventName;
    private final String student;
    private boolean crossedBaseline;
    private int hoppersDumped = Integer.MAX_VALUE;
    private boolean gearScoredRightAuto;
    private boolean gearScoredCenterAuto;
    private boolean gearScoredLeftAuto;
    private int gearsScored = Integer.MAX_VALUE;
    private int gearsDelivered = Integer.MAX_VALUE;
    private int gearsCollectedFromFloor = Integer.MAX_VALUE;
    private int gearsFromHuman = Integer.MAX_VALUE;
    private String scoresHighAuto = "";
    private String scoresLowAuto = "";
    private int ballsInHighCycle = Integer.MAX_VALUE;
    private int ballsFromHuman = Integer.MAX_VALUE;
    private int ballsFromHopper = Integer.MAX_VALUE;
    private int ballsFromFloor = Integer.MAX_VALUE;
    private int fuelInLowCycle = Integer.MAX_VALUE;
    private int numberOfLowCycles = Integer.MAX_VALUE;
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

    public String getScoresHighAuto() {
        return scoresHighAuto;
    }

    public void setScoresHighAuto(String scoresHighAuto) { this.scoresHighAuto = scoresHighAuto; }

    public String getScoresLowAuto() {
        return scoresLowAuto;
    }

    public void setScoresLowAuto(String scoresLowAuto) { this.scoresLowAuto = scoresLowAuto; }

    public String getDoesntScoreAuto() { return scoresLowAuto; }

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

    public int getNumberOfLowCycles() {
        return  numberOfLowCycles;
    }

    public void setNumberOfLowCycles(int  numberOfLowCycles) { this.numberOfLowCycles =  numberOfLowCycles; }

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
