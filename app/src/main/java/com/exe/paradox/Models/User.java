package com.exe.paradox.Models;

public class User {
    String name;
    String uid;
    String displayPicture;
    String roll;
    String ref_code;
    String team_code;

    String teamName;
    int coins;
    int level;
    int attempts;
    int score;
    int rank;


    public String getTeamName() {
        return teamName;
    }

    public int getCoins() {
        return coins;
    }

    public int getLevel() {
        return level;
    }

    public int getAttempts() {
        return attempts;
    }


    public String getRoll() {
        return roll;
    }

    public String getRef_code() {
        return ref_code;
    }

    public String getTeam_code() {
        return team_code;
    }

    public int getPoints() {
        return score;
    }
    public int getScore() {
        return score;
    }


    public User() {
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getDisplayPicture() {
        return displayPicture;
    }

    public int getRank() {
        return rank;
    }
}
