package org.gosparx.scouting.aerialassist.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Papa on 2/4/17.
 */

public class ScouterData implements Parcelable {
        private String nameOfScouter;
        private boolean canPickUpBallsFromHopper;
        private boolean canPickUpBallsFromGround;
        private boolean canPickUpGearsFromGround;
        private boolean canPickUpGearsFromRetrieval;
        private String preferredGearRetrieval;
        private float ballCapacity;
        private boolean canShootHigh;
        private String typeOfShooter;
        private float ballsPerSecond;
        private String shootingLocation;
        private String preferredShootingLocation;
        private boolean canShootLow;
        private int lowGoalRating;
        private int highGoalRating;
        private boolean abilityToScale;
        private String placesCanScaleFrom;
        private String preferredScalePlace;
        private String driveSystemDescription;
        private double approxSpeedFeetPerSecond = 0.0;
        private Boolean canScoreInHighGoal = false;
        private Boolean canScoreInLowGoal = false;
        private float highGoalAccuracy;

        // This is where you write the values you want to save to the `Parcel`.
        // The `Parcel` class has methods defined to help you save all of your values.
        // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
        // You may need to make several classes Parcelable to send the data you want.
        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(nameOfScouter);
            out.writeByte((byte) (canPickUpBallsFromHopper ? 1 : 0));
            out.writeByte((byte) (canPickUpBallsFromGround ? 1 : 0));
            out.writeByte((byte) (canPickUpGearsFromGround ? 1 : 0));
            out.writeByte((byte) (canPickUpGearsFromRetrieval ? 1 : 0));
            out.writeString(preferredGearRetrieval);
            out.writeFloat(ballCapacity);
            out.writeFloat(highGoalAccuracy);
            out.writeByte((byte) (canShootHigh ? 1 : 0));
            out.writeString(typeOfShooter);
            out.writeFloat(ballsPerSecond);
            out.writeString(shootingLocation);
            out.writeString(preferredShootingLocation);
            out.writeByte((byte) (canShootLow ? 1 : 0));
            out.writeInt(lowGoalRating);
            out.writeInt(highGoalRating);
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
        private ScouterData(Parcel in) {
            nameOfScouter = in.readString();
            canPickUpBallsFromHopper = in.readByte() != 0;
            canPickUpBallsFromGround = in.readByte() != 0;
            canPickUpGearsFromGround = in.readByte() != 0;
            canPickUpGearsFromRetrieval = in.readByte() != 0;
            preferredGearRetrieval = in.readString();
            ballCapacity = in.readInt();
            canShootHigh = in.readByte() != 0;
            typeOfShooter = in.readString();
            ballsPerSecond = in.readFloat();
            highGoalAccuracy = in.readFloat();
            shootingLocation = in.readString();
            preferredShootingLocation = in.readString();
            canShootLow = in.readByte() != 0;
            lowGoalRating = in.readInt();
            highGoalRating = in.readInt();
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
        public static final Parcelable.Creator<ScouterData> CREATOR
                = new Parcelable.Creator<ScouterData>() {

            // This simply calls our new constructor (typically private) and
            // passes along the unmarshalled `Parcel`, and then returns the new object!
            @Override
            public ScouterData createFromParcel(Parcel in) {
                return new ScouterData(in);
            }

            // We just need to copy this and change the type to match our class.
            @Override
            public ScouterData[] newArray(int size) {
                return new ScouterData[size];
            }
        };

        public String getPreferredGearRetrieval() {
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

        public String getPreferredScalePlace() {
            return preferredScalePlace;
        }

        public void setPreferredScalePlace(String preferredScalePlace) {
            this.preferredScalePlace = preferredScalePlace;
        }

        public String getPlacesCanScaleFrom() {
            return placesCanScaleFrom;
        }

        public void setPlacesCanScaleFrom(String placesCanScaleFrom) {
            this.placesCanScaleFrom = placesCanScaleFrom;
        }

        public void setCanShootLow(Boolean canShootLow) { this.canShootLow = getCanShootHigh(); }

        public Boolean getCanShootLow() {
            return canShootLow;
        }

    public int getHighGoalRating() {
        return highGoalRating;
    }

    public void setHighGoalRating(int highGoalRating) { this.highGoalRating = highGoalRating; }


        public int getLowGoalRating() {
            return lowGoalRating;
        }

        public void setLowGoalRating(int lowGoalRating) { this.lowGoalRating = lowGoalRating; }

        public void setAbilityToScale(Boolean abilityToScale) { this.abilityToScale = getCanShootHigh(); }

        public Boolean getAbilityToScale() {
            return abilityToScale;
        }

        public String getShootingLocation() {
            return shootingLocation;
        }

        public void setShootingLocation(String shootingLocation) {
            this.shootingLocation = shootingLocation; }

        public float getBallsPerSecond() {
            return ballsPerSecond;
        }

        public void setBallsPerSecond(float ballsPerSecond) { this.ballsPerSecond = ballsPerSecond; }


        public float getHighGoalAccuracy() {
        return ballsPerSecond;
    }

        public void setHighGoalAccuracy(float highGoalAccuracy) { this.highGoalAccuracy = highGoalAccuracy; }

        public String getTypeOfShooter() {
            return typeOfShooter;
        }

        public void setTypeOfShooter(String typeOfShooter) {
            this.typeOfShooter = typeOfShooter;
        }

        public void setCanShootHigh(Boolean canShootHigh) { this.canShootHigh = canShootHigh; }

        public Boolean getCanShootHigh() {
            return canShootHigh;
        }


        public float getBallCapacity() {return ballCapacity;}

        public void setBallCapacity(float ballCapacity) { this.ballCapacity = ballCapacity; }


        public Boolean getCanPickUpGearsFromRetrieval() {
            return canPickUpGearsFromRetrieval;
        }

        public void setCanPickUpGearsFromRetrieval(Boolean canPickUpGearsFromRetrieval) { this.canPickUpGearsFromRetrieval = canPickUpGearsFromRetrieval; }

        public Boolean getCanPickUpGearsFromGround() {
            return canPickUpGearsFromGround;
        }

        public void setCanPickUpGearsFromGround(Boolean canPickUpGearsFromGround) { this.canPickUpGearsFromGround = canPickUpGearsFromGround; }

        public Boolean getCanPickUpBallsFromGround() {
            return canPickUpBallsFromGround;
        }

        public void setCanPickUpBallsFromGround(Boolean canPickUpBallsFromGround) { this.canPickUpBallsFromGround = canPickUpBallsFromGround; }

        public Boolean getCanPickUpBallsFromHopper() {
            return canPickUpBallsFromHopper;
        }

        public void setCanPickUpBallsFromHopper(Boolean canPickUpBallsFromHopper) { this.canPickUpBallsFromHopper = canPickUpBallsFromHopper; }

        public String getNameOfScouter() {
            return nameOfScouter;
        }

        public void setNameOfScouter(String nameOfScouter) {
            this.nameOfScouter = nameOfScouter;
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

    public ScouterData() {
    }
}
