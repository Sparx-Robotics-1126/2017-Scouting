package org.gosparx.scouting.aerialassist.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Scouting information collected via team interview
 */
public class ScoutingInfo implements Parcelable {
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

    public ScoutingInfo() {}

    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(nameOfScouter);
        out.writeString(eventKey);
        out.writeString(teamKey);
        out.writeByte((byte) (canPickUpBallsFromHopper ? 1 : 0));
        out.writeByte((byte) (canPickUpBallsFromGround ? 1 : 0));
        out.writeByte((byte) (canPickUpGearsFromGround ? 1 : 0));
        out.writeByte((byte) (canPickUpGearsFromRetrieval ? 1 : 0));
        out.writeString(preferredGearRetrieval);
        out.writeInt(ballCapacity);
        out.writeByte((byte) (canShootHigh ? 1 : 0));
        out.writeString(typeOfShooter);
        out.writeInt(ballsPerSecond);
        out.writeString(shootingLocation);
        out.writeString(preferredShootingLocation);
        out.writeByte((byte) (canShootLow ? 1 : 0));
        out.writeInt(lowGoalRating);
        out.writeByte((byte) (abilityToScale ? 1 : 0));
        out.writeString(placesCanScaleFrom);
        out.writeString(preferredScalePlace);
        out.writeString(driveSystemDescription);
        out.writeDouble(approxSpeedFeetPerSecond);
        out.writeByte((byte) (canScoreInHighGoal ? 1 : 0));
        out.writeByte((byte) (canScoreInLowGoal ? 1 : 0));
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private ScoutingInfo(Parcel in) {
        nameOfScouter = in.readString();
        eventKey = in.readString();
        teamKey = in.readString();
        canPickUpBallsFromHopper = in.readByte() != 0;
        canPickUpBallsFromGround = in.readByte() != 0;
        canPickUpGearsFromGround = in.readByte() != 0;
        canPickUpGearsFromRetrieval = in.readByte() != 0;
        preferredGearRetrieval = in.readString();
        ballCapacity = in.readInt();
        canShootHigh = in.readByte() != 0;
        typeOfShooter = in.readString();
        ballsPerSecond = in.readInt();
        shootingLocation = in.readString();
        preferredShootingLocation = in.readString();
        canShootLow = in.readByte() != 0;
        lowGoalRating = in.readInt();
        abilityToScale = in.readByte() != 0;
        placesCanScaleFrom = in.readString();
        preferredScalePlace = in.readString();
        driveSystemDescription = in.readString();
        approxSpeedFeetPerSecond = in.readDouble();
        canScoreInHighGoal = in.readByte() != 0;
        canScoreInLowGoal = in.readByte() != 0;
    }

    // In the vast majority of cases you can simply return 0 for this.
    // There are cases where you need to use the constant `CONTENTS_FILE_DESCRIPTOR`
    // But this is out of scope of this tutorial
    @Override
    public int describeContents() {
        return 0;
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<ScoutingInfo> CREATOR
            = new Parcelable.Creator<ScoutingInfo>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public ScoutingInfo createFromParcel(Parcel in) {
            return new ScoutingInfo(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public ScoutingInfo[] newArray(int size) {
            return new ScoutingInfo[size];
        }
    };
}

