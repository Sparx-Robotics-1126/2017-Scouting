package org.gosparx.scouting.aerialassist.dto;

import java.util.List;

public class Alliance {
    private int score;
    private List<String> teams;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }
}
