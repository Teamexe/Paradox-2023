package com.exe.paradox.Models;

public class RankModel {
    String user_id;
    String uid;
    String user_name;
    String name;
    String roll;
    String ref_code;
    String team_code;
    int rank;
    int score;
    String display_picture;
    String image;

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUser_id() {
        if (user_id != null && !user_id.isEmpty())
            return user_id;
        return uid;
    }

    public String getUser_name() {
        if (user_name != null && !user_name.isEmpty())
            return user_name;
        return name;
    }

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }

    public String getDisplay_picture() {
        if (display_picture != null && !display_picture.isEmpty())
            return display_picture;
        return image;
    }

    public RankModel() {
    }
}