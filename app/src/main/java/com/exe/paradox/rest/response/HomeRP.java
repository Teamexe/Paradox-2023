package com.exe.paradox.rest.response;

import com.exe.paradox.Models.Banner;
import com.exe.paradox.Models.LevelData;
import com.exe.paradox.Models.RankModel;
import com.exe.paradox.Models.User;

import java.util.ArrayList;

public class HomeRP {
    String playerName;
    boolean isSolo;
    String teamName;
    boolean isLevelLocked;
    int nextQuestionNumber;

    LevelData levelData;
    ArrayList<Banner> bannerList;
    ArrayList<RankModel> leaderboardTop;

    public HomeRP() {
    }

    public ArrayList<RankModel> getLeaderboard() {
        return leaderboardTop;
    }

    public ArrayList<Banner> getBanners() {
        return bannerList;
    }

    public int getLevel() {
        return levelData.levelNo;
    }

    public String getLevelName(){
        return String.valueOf(levelData.levelNo);
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isSolo() {
        return isSolo;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isLevelActive() {
        return levelData.isLevelActive;
    }

    public boolean isLevelLocked() {
        return isLevelLocked;
    }

    public long getLevelStartsInSeconds() {
        return (long) levelData.levelStartsInSeconds;
    }

    public long getLevelStartsAt() {
        return levelData.levelStartsAt;
    }

    public long getLevelEndsAt() {
        return levelData.levelEndsAt;
    }

    public int getNextQuestionNumber() {
        return nextQuestionNumber;
    }
}
