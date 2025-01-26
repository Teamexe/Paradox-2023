package com.exe.paradox.rest.api;


import com.exe.paradox.Level1Activity;
import com.exe.paradox.Models.User;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;
import com.exe.paradox.rest.requests.CreateTeamReq;
import com.exe.paradox.rest.requests.CreateUserReq;
import com.exe.paradox.rest.requests.HomeReq;
import com.exe.paradox.rest.requests.JoinTeamReq;
import com.exe.paradox.rest.requests.Level1Req;
import com.exe.paradox.rest.response.CreateUserRP;
import com.exe.paradox.rest.response.HomeRP;
import com.exe.paradox.rest.response.Level1RP;
import com.exe.paradox.rest.response.Level2RP;
import com.exe.paradox.rest.response.PrizesRP;
import com.exe.paradox.rest.response.RanklistRP;
import com.exe.paradox.rest.response.RulesRP;
import com.exe.paradox.rest.response.TeamDetailsRP;

public class APIMethods {
    public static void createUser(APIResponseListener<CreateUserRP> listener) {
        CreateUserReq req = new CreateUserReq();
        API.postData(listener, req, EndPoints.createUser, CreateUserRP.class);
    }

    public static void getHome(APIResponseListener<HomeRP> listener){
        HomeReq req = new HomeReq();
        API.postData(listener, req, EndPoints.home, HomeRP.class);
    }

    public static void getLevel1Question(APIResponseListener<Level1RP> listener){
        HomeReq req = new HomeReq();
        API.postData(listener, req, EndPoints.getLevel1Question, Level1RP.class);
    }

    public static void submitLevel1Answer(String answer, APIResponseListener<Level1RP> listener){
        Level1Req req = new Level1Req(answer);
        API.postData(listener, req, EndPoints.submitLevel1Answer, Level1RP.class);
    }


    public static void getLevel2Question(APIResponseListener<Level2RP> listener){
        HomeReq req = new HomeReq();
        API.postData(listener, req, EndPoints.level2Question, Level2RP.class);
    }

    public static void submitLevel2Answer(String answer, APIResponseListener<Level2RP> listener){
        Level1Req req = new Level1Req(answer);
        API.postData(listener, req, EndPoints.submitLevel2Answer, Level2RP.class);
    }

    public static void getRanklist(APIResponseListener<RanklistRP> listener){
        HomeReq req = new HomeReq();
        API.postData(listener, req, EndPoints.rankList, RanklistRP.class);
    }

    //paginated leaderboard
    public static void getRanklist(int page, APIResponseListener<RanklistRP> listener){
        HomeReq req = new HomeReq(page);
        API.postData(listener, req, EndPoints.rankList, RanklistRP.class);
    }

    public static void getLevel2ranklist(APIResponseListener<RanklistRP> listener){
        HomeReq req = new HomeReq();
        API.postData(listener, req, EndPoints.level2rankList, RanklistRP.class);
    }


    public static void getProfile(APIResponseListener<User> listener){
        HomeReq req = new HomeReq();
        API.postData(listener, req, EndPoints.profile, User.class);
    }

    public static void getRules(APIResponseListener<RulesRP> listener){
        HomeReq req = new HomeReq();
        API.postData(listener, req, EndPoints.rules, RulesRP.class);
    }

    public static void getPrizes(APIResponseListener<PrizesRP> listener){
        HomeReq req = new HomeReq();
        API.postData(listener, req, EndPoints.prizes, PrizesRP.class);
    }

    public static void joinTeam(String teamId, APIResponseListener<TeamDetailsRP> listener){
        JoinTeamReq req = new JoinTeamReq(teamId);
        API.postData(listener, req, EndPoints.joinTeam, TeamDetailsRP.class);
    }

    public static void createTeam(String teamName, APIResponseListener<TeamDetailsRP> listener){
        CreateTeamReq req = new CreateTeamReq(teamName);
        API.postData(listener, req, EndPoints.createTeam, TeamDetailsRP.class);
    }

    public static void getTeamInformation(APIResponseListener<TeamDetailsRP> listener){
        HomeReq req = new HomeReq();
        API.postData(listener, req, EndPoints.getTeamDetails, TeamDetailsRP.class);
    }

    public static void getLevel1Hint(APIResponseListener<Level1RP> listener){
        HomeReq req = new HomeReq();
        API.postData(listener, req, EndPoints.unlockLevel1Hint, Level1RP.class);
    }

    public static void getLevel2Hint(APIResponseListener<Level2RP> listener){
        HomeReq req = new HomeReq();
        API.postData(listener, req, EndPoints.unlockLevel1Hint, Level2RP.class);
    }
}