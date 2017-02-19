package org.gosparx.scouting.aerialassist.dto;

import android.os.Parcel;
import android.os.Parcelable;
//ßuild the app amandΩ

public class ScoutingData implements Parcelable {
    private boolean crossedBaseline;
    private int hoppersDumped;
    private boolean gearScoredRightAuto;
    private boolean gearScoredCenterAuto;
    private boolean gearScoredLeftAuto;
    private boolean gearScoredNoneAuto;
    private int gearsScored;
    private int gearsCollectedFromFloor;
    private int gearsFromHuman;
    private boolean scoresHighNeverAuto;
    private boolean scoresHighSometimesAuto;
    private boolean scoresHighOftenAuto;
    private boolean scoresLowNeverAuto;
    private boolean scoresLowSometimesAuto;
    private boolean scoresLowOftenAuto;
    private int ballsInHighCycle;
    private int ballsFromHuman;
    private int ballsFromHopper;
    private int ballsFromFloor;
    private int fuelInLowCycle;
    private int numberOfLowCycles;
    private boolean highGoalAccuracyPoor;
    private boolean highGoalAccuracyOk;
    private boolean highGoalAccuracyGreat;
    private boolean didScaleYes;
    private boolean didScaleNo;
    private String whereScaled;


    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeByte((byte) (crossedBaseline ? 1 : 0));
        out.writeInt(hoppersDumped);
        out.writeByte((byte) (gearScoredRightAuto ? 1 : 0));
        out.writeByte((byte) (gearScoredLeftAuto ? 1 : 0));
        out.writeByte((byte) (gearScoredNoneAuto ? 1 : 0));
        out.writeByte((byte) (gearScoredCenterAuto ? 1 : 0));
        out.writeInt(gearsScored);
        out.writeInt(gearsCollectedFromFloor);
        out.writeInt(gearsFromHuman);
        out.writeByte((byte) (scoresHighNeverAuto ? 1 : 0));
        out.writeByte((byte) (scoresHighSometimesAuto ? 1 : 0));
        out.writeByte((byte) (scoresHighOftenAuto ? 1 : 0));
        out.writeByte((byte) (scoresLowNeverAuto ? 1 : 0));
        out.writeByte((byte) (scoresLowSometimesAuto ? 1 : 0));
        out.writeByte((byte) (scoresLowOftenAuto ? 1 : 0));
        out.writeInt(ballsInHighCycle);
        out.writeInt(ballsFromHuman);
        out.writeInt(ballsFromHopper);
        out.writeInt(ballsFromFloor);
        out.writeInt(fuelInLowCycle);
        out.writeInt(numberOfLowCycles);
        out.writeByte((byte) (highGoalAccuracyPoor ? 1 : 0));
        out.writeByte((byte) (highGoalAccuracyOk ? 1 : 0));
        out.writeByte((byte) (highGoalAccuracyGreat ? 1 : 0));
        out.writeByte((byte) (didScaleYes ? 1 : 0 ));
        out.writeByte((byte) (didScaleNo ? 1 : 0 ));
        out.writeString(whereScaled);
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private ScoutingData(Parcel in) {
        crossedBaseline = in.readByte() != 0;
        hoppersDumped = in.readInt();
        gearScoredRightAuto = in.readByte() != 0;
        gearScoredLeftAuto = in.readByte() != 0;
        gearScoredCenterAuto = in.readByte() != 0;
        gearScoredNoneAuto = in.readByte() != 0;
        gearsScored = in.readInt();
        gearsCollectedFromFloor = in.readInt();
        gearsFromHuman = in.readInt();
        scoresHighNeverAuto = in.readByte() != 0;
        scoresHighSometimesAuto = in.readByte() !=0;
        scoresHighOftenAuto = in.readByte() !=0;
        scoresLowNeverAuto = in.readByte() !=0;
        scoresLowSometimesAuto = in.readByte() !=0;
        scoresLowOftenAuto = in.readByte() !=0;
        ballsInHighCycle = in.readInt();
        ballsFromHuman = in.readInt();
        ballsFromHopper = in.readInt();
        ballsFromFloor = in.readInt();
        fuelInLowCycle = in.readInt();
        numberOfLowCycles = in.readInt();
        highGoalAccuracyPoor = in.readByte() !=0;
        highGoalAccuracyOk = in.readByte() !=0;
        highGoalAccuracyGreat = in.readByte() !=0;
        didScaleYes = in.readByte() !=0;
        didScaleNo = in.readByte() !=0;
        whereScaled = in.readString();
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
    public static final Parcelable.Creator<ScoutingData> CREATOR
            = new Parcelable.Creator<ScoutingData>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public ScoutingData createFromParcel(Parcel in) {
            return new ScoutingData(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public ScoutingData[] newArray(int size) {
            return new ScoutingData[size];
        }
    };


    public int getHoppersDumped() {
        return hoppersDumped;
    }

    public void setHoppersDumped(int hoppersDumped) { this.hoppersDumped = hoppersDumped; }


    public Boolean getCrossedBaseline() {
        return crossedBaseline;
    }

    public void setCrossedBaseline(Boolean crossedBaseline) { this.crossedBaseline = crossedBaseline; }

    public Boolean getGearScoredRightAuto() {
        return gearScoredRightAuto;
    }

    public void setGearScoredRightAuto(Boolean gearScoredRightAuto) { this.gearScoredRightAuto = gearScoredRightAuto; }

    public Boolean getGearScoredLeftAuto() {
        return gearScoredLeftAuto;
    }

    public void setGearScoredLeftAuto(Boolean gearScoredLeftAuto) { this.gearScoredLeftAuto = gearScoredLeftAuto; }


    public Boolean getGearScoredCenterAuto() {
        return gearScoredNoneAuto;
    }

    public void setGearScoredCenterAuto(Boolean gearScoredCenterAuto) { this.gearScoredCenterAuto = gearScoredCenterAuto; }


    public Boolean getGearScoredNoneAuto() {
        return gearScoredNoneAuto;
    }

    public void setGearScoredNoneAuto(Boolean gearScoredNoneAuto) { this.gearScoredNoneAuto = gearScoredNoneAuto; }

    public int getGearsScored() {
        return gearsScored;
    }

    public void setGearsScored(int gearsScored) { this.gearsScored = gearsScored; }


    public int getGearsCollectedFromFloor() {
        return gearsCollectedFromFloor;
    }

    public void setGearsCollectedFromFloor(int gearsCollectedFromFloor) { this.gearsCollectedFromFloor = gearsCollectedFromFloor; }

    public int getGearsFromHuman() {
        return gearsFromHuman;
    }

    public void setGearsFromHuman(int gearsFromHuman) { this.gearsFromHuman = gearsFromHuman; }

    public Boolean getScoresHighNeverAuto() {
        return scoresHighNeverAuto;
    }

    public void setScoresHighNeverAuto(Boolean scoresHighNeverAuto) { this.scoresHighNeverAuto = scoresHighNeverAuto; }

    public Boolean getScoresHighSometimesAuto() {
        return scoresHighSometimesAuto;
    }

    public void setScoresHighSometimesAuto(Boolean scoresHighSometimesAuto) { this.scoresHighSometimesAuto = scoresHighSometimesAuto; }

    public Boolean getScoresHighOftenAuto() {
        return scoresHighOftenAuto;
    }

    public void setScoresHighOftenAuto(Boolean scoresHighOftenAuto) { this.scoresHighOftenAuto = scoresHighOftenAuto; }

    public Boolean getScoresLowNeverAuto() {
        return scoresLowNeverAuto;
    }

    public void setScoresLowNeverAuto(Boolean scoresLowNeverAuto) { this.scoresLowNeverAuto = scoresLowNeverAuto; }

    public Boolean getScoresLowSometimesAuto() {
        return scoresLowSometimesAuto;
    }

    public void setScoresLowOftenAuto(Boolean scoresLowOftenAuto) { this.scoresLowOftenAuto = scoresLowOftenAuto; }

    public Boolean getScoresLowOftenAuto() {
        return scoresLowOftenAuto;
    }

    public void setScoresLowSometimesAuto(Boolean scoresLowSometimesAuto) { this.scoresLowSometimesAuto = scoresLowSometimesAuto; }

    public int getBallsInHighCycle() {
        return ballsInHighCycle;
    }

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

    public Boolean getHighGoalAccuracyPoor() {
        return highGoalAccuracyPoor;
    }

    public void setHighGoalAccuracyPoor(Boolean highGoalAccuracyPoor) { this.highGoalAccuracyPoor = highGoalAccuracyPoor; }

    public Boolean getHighGoalAccuracyOk() {
        return highGoalAccuracyOk;
    }

    public void setHighGoalAccuracyOk(Boolean highGoalAccuracyOk) { this.highGoalAccuracyOk = highGoalAccuracyOk; }

    public Boolean getHighGoalAccuracyGreat() {
        return highGoalAccuracyGreat;
    }

    public void setHighGoalAccuracyGreat(Boolean highGoalAccuracyGreat) { this.highGoalAccuracyGreat = highGoalAccuracyGreat; }

    public Boolean getDidScaleYes() {
        return didScaleYes;
    }

    public void setDidScaleYes(Boolean didScaleYes) { this.didScaleYes = didScaleYes; }

    public Boolean getDidScaleNo() {
        return didScaleNo;
    }

    public void setDidScaleNo(Boolean didScaleNo) { this.didScaleNo = didScaleNo;}

    public String getWhereScaled() {
        return whereScaled;
    }

    public void setWhereScaled(String whereScaled) { this.whereScaled= whereScaled;}

        ScoutingData() {
    }
}
