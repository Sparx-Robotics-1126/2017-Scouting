package org.gosparx.scouting.aerialassist.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Vector;


/**
 * Scouting information collected via team interview
 */
public class ScoutingInfo implements Parcelable {
    private String eventKey;
    private String teamKey;
    private ScouterData currentData;
    public Vector<ScouterData> scouterData;

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

    public ScoutingInfo() {
        scouterData = new Vector<ScouterData>(250);
    }

    public ScouterData getCurrentData() {
        return currentData;
    }

    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(eventKey);
        out.writeString(teamKey);
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private ScoutingInfo(Parcel in) {
        eventKey = in.readString();
        teamKey = in.readString();
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

    public void addScouter(String name) {
        currentData = new ScouterData();
        currentData.setNameOfScouter(name);
        scouterData.add(currentData);
    }
}

