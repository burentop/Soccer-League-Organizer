package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

public class Team implements Comparable<Team> {

    private String mCoach;
    private String mTeamName;
    private List<Player> mRoster;

    public Team(String coach, String teamName) {
        mCoach = coach;
        mTeamName = teamName;
        mRoster = new ArrayList<>();
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

    public List<Player> getPlayers() {
        return mRoster;
    }

    @Override
    public int compareTo(Team other) {
        if (mTeamName.compareTo(other.mTeamName) < 0) return -1;
        else if (mTeamName.compareTo(other.mTeamName) > 0) return 1;
        else return 0;
  }

}