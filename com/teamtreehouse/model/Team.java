package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

public class Team implements Comparable<Team> {

    private String mCoach;
    private String mTeamName;
    private Set<Player> mRoster;
    private Map<String, Set<Player>> heightReport;

    public Team(String coach, String teamName) {
        mCoach = coach;
        mTeamName = teamName;
        mRoster = new TreeSet<>();
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void addPlayer(Player player) {
        mRoster.add(player);
    }

    public void removePlayer(Player player) {
        mRoster.remove(player);
    }

    @Override
    public String toString() {
        return mTeamName + " - coached by, " + mCoach;
    }

    public Set<Player> getPlayers() {
        return (Set) mRoster;
    }

    public Map<String, Set<Player>> heightReport() {
        heightReport = new TreeMap<>();
        heightReport.put("35-40", new TreeSet<>());
        heightReport.put("41-46", new TreeSet<>());
        heightReport.put("47-50", new TreeSet<>());
        for (Player player : mRoster) {
            int height = player.getHeightInInches();
            if (height >= 35 && height <= 40) {
                heightReport.get("35-40").add(player);
            } else if (height >= 41 && height <= 46) {
                heightReport.get("41-46").add(player);
            } else {
                heightReport.get("47-50").add(player);
            }
        }
        return heightReport;
    }

    @Override
    public int compareTo(Team other) {
        if (mTeamName.compareTo(other.mTeamName) < 0) return -1;
        else if (mTeamName.compareTo(other.mTeamName) > 0) return 1;
        else return 0;
  }

}