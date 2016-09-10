package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;

import com.teamtreehouse.model.Players;

public class Team {

    private String mCoach;
    private String mTeamName;
    private List<Player> mRoster;

    public Team(String coach, String teamName) {
        mCoach = coach;
        mTeamName = teamName;
        mRoster = new ArrayList<>();
    }

}