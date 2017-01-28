package org.gosparx.scouting.aerialassist.dto;

/**
 * Scouting information collected via team interview
 */
public class ScoutingInfo {

    private String nameOfScouter;
    private String eventKey;
    private String teamKey;
    private boolean canPickUpBallsFromHopper;
    private boolean canPickUpBallsFromGround;
    private boolean canPickUpGearsFromGround;
    private boolean canPickUpGearsFromRetrieval;
    private String preferredGearRetrieval;
    private int ballCapacity;
    private boolean canShootHigh;
    private String typeOfShooter;
    private int ballsPerSecond;
    private String shootingLocation;
    private String preferredShootingLocation;
    private boolean canShootLow;
    private int lowGoalRating;
    private boolean abilityToScale;
    private String placesCanScaleFrom;
    private String preferredScalePlace;
    private String driveSystemDescription;
    private double approxSpeedFeetPerSecond = 0.0;

    private Boolean canScoreInHighGoal = false;
    private Boolean canScoreInLowGoal = false;

    public String preferredGearRetrieval() {
        return preferredGearRetrieval;
    }

    public void setPreferredGearRetrieval(String preferredGearRetrieval) {
        this.preferredGearRetrieval = preferredGearRetrieval;
    }

    public String getPreferredShootingLocation() {
        return preferredShootingLocation;
    }

    public void setPreferredShootingLocation(String preferredShootingLocation) {
        this.preferredShootingLocation = preferredShootingLocation; }


    public String preferredScalePLace() {
        return preferredScalePlace;
    }

    public void preferredScalePlace(String preferredScalePlace) {
        this.preferredScalePlace = preferredScalePlace;
    }
    

    public String placesCanScaleFrom() {
        return placesCanScaleFrom;
    }

    public void placesCanScaleFrom(String placesCanScaleFrom) {
        this.placesCanScaleFrom = placesCanScaleFrom;
    }


    public void canShootLow(Boolean canShootLow) { this.canShootLow = canShootHigh(); }

    public Boolean canShootLow() {
        return canShootLow;
    }

    public int getLowGoalRating() {
        return lowGoalRating;
    }

    public void setLowGoalRating(int lowGoalRating) { this.lowGoalRating = ballsPerSecond(); }

    public void abilityToScale(Boolean abilityToScale) { this.abilityToScale = canShootHigh(); }

    public Boolean abilityToScale() {
        return abilityToScale;
    }



    public String shootingLocation() {
        return shootingLocation;
    }

    public void ShootingLocation(String shootingLocation) {
        this.shootingLocation = shootingLocation; }


    public int ballsPerSecond() {
        return ballsPerSecond;
    }

    public void ballsPerSecond(Boolean ballsPerSecond) { this.ballsPerSecond = ballsPerSecond(); }

    public String typeOfShooter() {
        return typeOfShooter;
    }

    public void typeOfShooter(String typeOfShooter) {
        this.typeOfShooter = typeOfShooter;
    }


    public void canShootHigh(Boolean canShootHigh) { this.canShootHigh = canShootHigh(); }

    public Boolean canShootHigh() {
        return canShootHigh;
    }



    public int ballCapacity() {
        return ballCapacity;
    }

    public void ballCapacity(Boolean canPickUpGearsFromRetrieval) { this.ballCapacity = ballCapacity(); }

    public Boolean canPickUpGearsFromRetrieval() {
        return canPickUpGearsFromRetrieval;
    }

    public void setCanPickUpGearsFromRetrieval(Boolean canPickUpGearsFromRetrieval) { this.canPickUpGearsFromRetrieval = canPickUpGearsFromRetrieval; }

    public Boolean canPickUpGearsFromGround() {
        return canPickUpGearsFromGround;
    }

    public void canPickUpGearsFromGround(Boolean canPickUpGearsFromGround) { this.canPickUpGearsFromGround = canPickUpGearsFromGround; }

    public Boolean canPickUpBallsFromGround() {
        return canPickUpBallsFromGround;
    }

    public void canPickUpBallsFromGround(Boolean canPickUpBallsFromGround) { this.canPickUpBallsFromGround = canPickUpBallsFromGround; }

    public Boolean canPickUpBallsFromHopper() {
        return canPickUpBallsFromHopper;
    }

    public void canPickUpBallsFromHopper(Boolean canPickUpBallsFromHopper) { this.canPickUpBallsFromHopper = canPickUpBallsFromHopper; }

    public String getNameOfScouter() {
        return nameOfScouter;
    }

    public void setNameOfScouter(String nameOfScouter) {
        this.nameOfScouter = nameOfScouter;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(String teamKey) {
        this.teamKey = teamKey;
    }

    public String getDriveSystemDescription() {
        return driveSystemDescription;
    }

    public void setDriveSystemDescription(String driveSystemDescription) {
        this.driveSystemDescription = driveSystemDescription;
    }

    public double getApproxSpeedFeetPerSecond() {
        return approxSpeedFeetPerSecond;
    }

    public void setApproxSpeedFeetPerSecond(double approxSpeedFeetPerSecond) {
        this.approxSpeedFeetPerSecond = approxSpeedFeetPerSecond;
    }



    public Boolean getCanScoreInHighGoal() {
        return canScoreInHighGoal;
    }

    public void setCanScoreInHighGoal(Boolean canScoreInHighGoal) {
        this.canScoreInHighGoal = canScoreInHighGoal;
    }

    public Boolean getCanScoreInLowGoal() {
        return canScoreInLowGoal;
    }

    public void setCanScoreInLowGoal(Boolean canScoreInLowGoal) {
        this.canScoreInLowGoal = canScoreInLowGoal;
    }

}

