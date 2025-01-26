package com.exe.paradox.rest.response;

import com.exe.paradox.Adapters.LeaderboardRVAdapter;
import com.exe.paradox.Models.RankModel;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;

import java.util.ArrayList;

public class RanklistRP {
    RankModel myRank;
    ArrayList<RankModel> leaderboard;
    int page;
    int limit;
    int pages;
    int total;

    public boolean isLoading = false;

    public RankModel getMy_rank() {
        return myRank;
    }

    public ArrayList<RankModel> getLeaderboard() {
        return leaderboard;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public int getPages() {
        return pages;
    }

    public int getTotal() {
        return total;
    }

    public RanklistRP() {
    }

    public boolean areMorePages(){
        return page<pages;
    }

    public void paginate(LeaderboardRVAdapter adapter){
        if (!isLoading){
            isLoading = true;
            adapter.showProgress("Loading more ranks");

            APIResponseListener<RanklistRP> responseListener = new APIResponseListener<RanklistRP>() {
                @Override
                public void success(RanklistRP response) {

                }

                @Override
                public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {

                }
            };
        }
    }
}
