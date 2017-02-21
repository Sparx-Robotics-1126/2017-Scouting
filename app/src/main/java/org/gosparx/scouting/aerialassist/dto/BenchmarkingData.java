package org.gosparx.scouting.aerialassist.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class BenchmarkingData implements Parcelable {
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
        private int numberOfGearsScored;
        private int numberOfBallsScored;
        private String comments;
        private boolean canPlayDefense;
        private int ballsInHighCycle;
        private int highCycleTime;
        private float highShootingRange;
        private boolean canPickUpBallsFromHuman;
        private String preferredBallRetrieval;
        private boolean canScoreGears;
        private boolean canScoreGearsRight;
        private boolean canScoreGearsCenter;
        private boolean canScoreGearsLeft;
        private boolean preferredScoreGearsLeft;
        private boolean preferredScoreGearsRight;
        private boolean preferredScoreGearsCenter;
        private int gearsCycleTime;
        private int lowCycleTime;
        private int numberOfLowCycles;
        private boolean canScale;
        private String autoAbilities;



        // This is where you write the values you want to save to the `Parcel`.
        // The `Parcel` class has methods defined to help you save all of your values.
        // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
        // You may need to make several classes Parcelable to send the data you want.
        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(nameOfScouter);
            out.writeString(comments);
            out.writeByte((byte) (canPickUpBallsFromHopper ? 1 : 0));
            out.writeByte((byte) (canPickUpBallsFromGround ? 1 : 0));
            out.writeByte((byte) (canPickUpGearsFromGround ? 1 : 0));
            out.writeByte((byte) (canPickUpGearsFromRetrieval ? 1 : 0));
            out.writeByte((byte) (canPlayDefense ? 1: 0));
            out.writeString(preferredGearRetrieval);
            out.writeFloat(ballCapacity);
            out.writeFloat(highGoalAccuracy);
            out.writeByte((byte) (canShootHigh ? 1 : 0));
            out.writeByte((byte) (canPickUpBallsFromHuman ? 1 : 0));
            out.writeString(typeOfShooter);
            out.writeFloat(ballsPerSecond);
            out.writeFloat(highShootingRange);
            out.writeString(shootingLocation);
            out.writeString(preferredShootingLocation);
            out.writeString(preferredBallRetrieval);
            out.writeByte((byte) (canShootLow ? 1 : 0));
            out.writeInt(lowGoalRating);
            out.writeInt(highGoalRating);
            out.writeInt(highCycleTime);
            out.writeInt(numberOfGearsScored);
            out.writeInt(numberOfBallsScored);
            out.writeByte((byte) (abilityToScale ? 1 : 0));
            out.writeString(placesCanScaleFrom);
            out.writeString(preferredScalePlace);
            out.writeString(driveSystemDescription);
            out.writeDouble(approxSpeedFeetPerSecond);
            out.writeByte((byte) (canScoreInHighGoal ? 1 : 0));
            out.writeByte((byte) (canScoreInLowGoal ? 1 : 0));
            out.writeByte((byte) (canScoreGearsLeft ? 1 : 0));
            out.writeByte((byte) (canScoreGearsCenter ? 1 : 0));
            out.writeByte((byte) (canScoreGearsRight ? 1 : 0));
            out.writeByte((byte) (canScoreGears ? 1 : 0));
            out.writeInt(ballsInHighCycle);
            out.writeByte((byte) (preferredScoreGearsCenter ? 1 : 0));
            out.writeByte((byte) (preferredScoreGearsLeft ? 1 : 0));
            out.writeByte((byte) (preferredScoreGearsRight ? 1 : 0));
            out.writeInt(gearsCycleTime);
        }

        // Using the `in` variable, we can retrieve the values that
        // we originally wrote into the `Parcel`.  This constructor is usually
        // private so that only the `CREATOR` field can access.
        private BenchmarkingData(Parcel in) {
            nameOfScouter = in.readString();
            comments = in.readString();
            canPickUpBallsFromHopper = in.readByte() != 0;
            canPickUpBallsFromGround = in.readByte() != 0;
            canPickUpGearsFromGround = in.readByte() != 0;
            canPickUpGearsFromRetrieval = in.readByte() != 0;
            preferredGearRetrieval = in.readString();
            ballCapacity = in.readInt();
            gearsCycleTime = in.readInt();
            highCycleTime = in.readInt();
            numberOfGearsScored = in.readInt();
            numberOfBallsScored = in.readInt();
            canShootHigh = in.readByte() != 0;
            typeOfShooter = in.readString();
            ballsPerSecond = in.readFloat();
            highShootingRange = in.readFloat();
            highGoalAccuracy = in.readFloat();
            shootingLocation = in.readString();
            preferredShootingLocation = in.readString();
            canShootLow = in.readByte() != 0;
            lowGoalRating = in.readInt();
            highGoalRating = in.readInt();
            abilityToScale = in.readByte() != 0;
            placesCanScaleFrom = in.readString();
            preferredBallRetrieval = in.readString();
            preferredScalePlace = in.readString();
            driveSystemDescription = in.readString();
            approxSpeedFeetPerSecond = in.readDouble();
            canScoreInHighGoal = in.readByte() != 0;
            canScoreInLowGoal = in.readByte() != 0;
            canPlayDefense = in.readByte() != 0;
            canScoreGearsRight = in.readByte() != 0;
            canScoreGearsCenter = in.readByte() != 0;
            canScoreGearsLeft = in.readByte() != 0;
            canScoreGears = in.readByte() != 0;
            canPickUpBallsFromHuman = in.readByte() != 0;
            ballsInHighCycle = in.readInt();
            preferredScoreGearsRight = in.readByte() != 0;
            preferredScoreGearsCenter = in.readByte() != 0;
            preferredScoreGearsLeft = in.readByte() != 0;


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
        public static final Parcelable.Creator<BenchmarkingData> CREATOR
                = new Parcelable.Creator<BenchmarkingData>() {

            // This simply calls our new constructor (typically private) and
            // passes along the unmarshalled `Parcel`, and then returns the new object!
            @Override
            public BenchmarkingData createFromParcel(Parcel in) {
                return new BenchmarkingData(in);
            }

            // We just need to copy this and change the type to match our class.
            @Override
            public BenchmarkingData[] newArray(int size) {
                return new BenchmarkingData[size];
            }
        };

        public String getPreferredGearRetrieval() {
            return preferredGearRetrieval;
        }

        public void setPreferredGearRetrieval(String preferredGearRetrieval) {
            this.preferredGearRetrieval = preferredGearRetrieval;
        }

        public String getPreferredBallRetrieval() {
        return preferredBallRetrieval;
    }

        public void setPreferredBallRetrieval(String preferredBallRetrieval) {
        this.preferredBallRetrieval = preferredBallRetrieval; }


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

        public void setCanShootLow(Boolean canShootLow) { this.canShootLow = canShootLow; }

        public Boolean getCanShootLow() {
            return canShootLow;
        }

        public void setCanPlayDefense(Boolean canPlayDefense) { this.canPlayDefense = canPlayDefense; }

        private Boolean getCanPlayDefense() {
            return canPlayDefense;
        }

        public void setCanPickUpBallsFromHuman(Boolean canPickUpBallsFromHuman) { this.canPickUpBallsFromHuman = canPickUpBallsFromHuman; }

        private Boolean getCanPickUpBallsFromHuman() {return canPickUpBallsFromHuman; }

        public int getBallsInHighCycle() {
        return ballsInHighCycle;
    }

        public void setBallsInHighCycle(int ballsInHighCycle) { this.ballsInHighCycle = ballsInHighCycle; }

        public int getGearsCycleTime() {return gearsCycleTime;}

        public void setGearsCycleTime(int gearCycleTime) { this.gearsCycleTime = gearsCycleTime; }

        public int getHighGoalRating() {
        return highGoalRating;
    }

        public void setHighGoalRating(int highGoalRating) { this.highGoalRating = highGoalRating; }

        public int getHighCycleTime() {
        return highGoalRating;
    }

        public void setHighCycleTime(int highGoalRating) { this.highGoalRating = highGoalRating; }

        public int getLowGoalRating() {
            return lowGoalRating;
        }

        public void setLowGoalRating(int lowGoalRating) { this.lowGoalRating = lowGoalRating; }

        public int getNumberOfGearsScored() {
        return numberOfGearsScored;
    }

        public void setNumberOfGearsScored (int numberOfGearsScored) { this.numberOfGearsScored = numberOfGearsScored; }

        public int getNumberOfBallsScored() {
        return numberOfBallsScored;
    }

        public void setNumberOfBallsScored (int numberOfBallsScored) { this.numberOfBallsScored = numberOfBallsScored; }

        public void setAbilityToScale(Boolean abilityToScale) { this.abilityToScale = abilityToScale; }

        public Boolean getAbilityToScale() {
            return abilityToScale;
        }

        public String getShootingLocation() {
            return shootingLocation;
        }

        public void setShootingLocation(String shootingLocation) {this.shootingLocation = shootingLocation; }

        public String getComments() {
        return comments;
    }

        public void setComments(String comments) {this.comments = comments; }

        public float getBallsPerSecond() {
            return ballsPerSecond;
        }

        public void setBallsPerSecond(float ballsPerSecond) { this.ballsPerSecond = ballsPerSecond; }

        public float getHighShootingRange() {
        return ballsPerSecond;
    }

        public void setHighShootingRange(float ballsPerSecond) { this.ballsPerSecond = ballsPerSecond; }


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

        private Boolean getCanShootHigh() {
            return canShootHigh;
        }


        public float getBallCapacity() {return ballCapacity;}

        public void setBallCapacity(float ballCapacity) { this.ballCapacity = ballCapacity; }


        public Boolean getCanPickUpGearsFromRetrieval() {
            return canPickUpGearsFromRetrieval;
        }

        public void setCanPickUpGearsFromRetrieval(Boolean canPickUpGearsFromRetrieval) { this.canPickUpGearsFromRetrieval = canPickUpGearsFromRetrieval; }

        public Boolean getCanScoreGears() {return canScoreGears;}

        public void setCanScoreGears(Boolean canScoreGears) { this.canScoreGears = canScoreGears; }


        public Boolean getPreferredScoreGearsLeft() {return preferredScoreGearsLeft;}

        public void setPreferredScoreGearsLeft(Boolean preferredScoreGearsLeft) { this.preferredScoreGearsLeft = preferredScoreGearsLeft; }


        public Boolean getPreferredScoreGearsRight() {return preferredScoreGearsRight;}

        public void setPreferredScoreGearsRight(Boolean preferredScoreGearsRight) { this.preferredScoreGearsRight = preferredScoreGearsRight; }

        public Boolean getPreferredScoreGearsCenter() {return preferredScoreGearsCenter;}

        public void setPreferredScoreGearsCenter(Boolean preferredScoreGearsCenter) { this.preferredScoreGearsCenter = preferredScoreGearsCenter; }



        public Boolean getCanScoreGearsLeft() {return canScoreGearsLeft;}

        public void setCanScoreGearsLeft(Boolean canScoreGearsLeft) { this.canScoreGearsLeft = canScoreGearsLeft; }

        public Boolean getCanScoreGearsCenter() {return canScoreGearsCenter;}

        public void setCanScoreGearsCenter(Boolean canScoreGearsCenter) { this.canScoreGearsCenter = canScoreGearsCenter; }

    public Boolean getCanScoreGearsRight() {return canScoreGearsRight;}

    public void setCanScoreGearsRight(Boolean canScoreGearsRight) { this.canScoreGearsRight = canScoreGearsRight; }


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

        void setNameOfScouter(String nameOfScouter) {
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

    BenchmarkingData() {
    }
}
