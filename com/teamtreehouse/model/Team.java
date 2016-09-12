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
    private Map<String, Set<Player>> mHeightReport;
    private Map<String, Integer> mBalanceReport;

    public Team(String coach, String teamName) {
        mCoach = coach;
        mTeamName = teamName;
        mRoster = new TreeSet<>();
        mBalanceReport = new TreeMap<>();
        mHeightReport = new TreeMap<>();
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

    public Map<String, Set<Player>> getHeightReport() {
        createReports();
        return mHeightReport;
    }

    public Map<String, Integer> getBalanceReport() {
        createReports();
        return mBalanceReport;
    }

    public void createReports() {
        mHeightReport = new TreeMap<>();
        mBalanceReport = new TreeMap<>();
        mHeightReport.put("35-40", new TreeSet<>());
        mHeightReport.put("41-46", new TreeSet<>());
        mHeightReport.put("47-50", new TreeSet<>());
        mBalanceReport.put("Experienced", 0);
        mBalanceReport.put("Inexperienced", 0);
        for (Player player : mRoster) {
            int height = player.getHeightInInches();
            if (height >= 35 && height <= 40) {
                mHeightReport.get("35-40").add(player);
            } else if (height >= 41 && height <= 46) {
                mHeightReport.get("41-46").add(player);
            } else {
                mHeightReport.get("47-50").add(player);
            }
            if (player.isPreviousExperience()) {
                Integer count = mBalanceReport.get("Experienced");
                mBalanceReport.put("Experienced", ++count);
            } else {
                Integer count = mBalanceReport.get("Inexperienced");
                mBalanceReport.put("Inexperienced", ++count);
            }
        }
    }

    @Override
    public int compareTo(Team other) {
        if (mTeamName.compareTo(other.mTeamName) < 0) return -1;
        else if (mTeamName.compareTo(other.mTeamName) > 0) return 1;
        else return 0;
  }

}