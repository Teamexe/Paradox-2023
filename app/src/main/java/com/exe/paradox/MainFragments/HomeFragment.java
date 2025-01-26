package com.exe.paradox.MainFragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exe.paradox.Adapters.BannerPageAdapter;
import com.exe.paradox.Level1Activity;
import com.exe.paradox.Level2Activity;
import com.exe.paradox.R;
import com.exe.paradox.Tools.Method;
import com.exe.paradox.Tools.TimeUtils;
import com.exe.paradox.rest.api.APIMethods;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;
import com.exe.paradox.rest.response.HomeRP;
import com.google.gson.Gson;

public class HomeFragment extends Fragment {

    HomeRP homeRP;

    ProgressBar progressBar;
    LinearLayout mainLayout;

    TextView middleTxt;
    TextView topTxt;
    TextView bottomTxt;
    TextView teamNameTxt;
    TextView playerNameTxt;
    LinearLayout teamInfoLayout;
    LinearLayout levelLayout;
    ViewPager viewPager;
    public static boolean isLevel1Running = true;


    public HomeFragment() {
        // Required empty public constructor
    }

    public void getData(){
        if (homeRP == null){
            fetchHome();
        } else {
            showData();
        }
    }

    private void fetchHome() {

        progressBar.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);

        Log.i("eta", "fetching home");
        APIMethods.getHome(new APIResponseListener<HomeRP>() {
            @Override
            public void success(HomeRP response) {
                homeRP = response;
                showData();
            }

            @Override
            public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                progressBar.setVisibility(View.GONE);
                Method.showFailedAlert(getActivity(), code + " - " +  message);
                //Todo Show Non Connectivity Status
                //Todo Show message more effectively with retry btn
            }
        });

    }

    private void showData() {
        playerNameTxt.setText(homeRP.getPlayerName());
        if (homeRP.isSolo()){
            teamNameTxt.setText("(Playing Solo)");
        } else {
            teamNameTxt.setText(homeRP.getTeamName());
        }

        if (homeRP.isLevelActive()){
            topTxt.setText("LEVEL  :");
            middleTxt.setText(homeRP.getLevelName());
            bottomTxt.setText(getString(R.string.enter));//string contains << >> character
            levelEndsInTxt.setVisibility(View.VISIBLE);
            TimeUtils.TimeDifference timeDifference = new TimeUtils().getTimeDifference();
            TimeUtils.getDifference(timeDifference, homeRP.getLevelEndsAt());
            levelEndsInTxt.setText(String.format("Level %d ends in %s %s", homeRP.getLevel(), timeDifference.time, timeDifference.units));
        } else {
            levelEndsInTxt.setVisibility(View.GONE);
            //Todo: Implement a counter and update once counter ends
            TimeUtils.TimeDifference timeDifference = new TimeUtils().getTimeDifference();
            topTxt.setText("Level " + homeRP.getLevelName() + " starting in");
            TimeUtils.getDifference(timeDifference, homeRP.getLevelStartsAt());
            middleTxt.setText(timeDifference.time);
            bottomTxt.setText(timeDifference.units);
        }

        //Todo: If the level is locked update UI

        levelLayout.setOnClickListener(view -> onLevelClick());
        teamInfoLayout.setOnClickListener(view -> changeTeam());
        progressBar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);

        //showingBanners

        if (homeRP.getLeaderboard() == null || homeRP.getLeaderboard().size() < 3){
            viewPager.setVisibility(View.GONE);
        } else {
            viewPager.setVisibility(View.VISIBLE);
            viewPager.setAdapter(null);
            BannerPageAdapter adapter = new BannerPageAdapter(getChildFragmentManager(), homeRP.getBanners(), homeRP.getLeaderboard(), getActivity());
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(0);
            viewPager.setCurrentItem(0);
        }

        if (homeRP.getLevel() == 1){
            isLevel1Running = true;
        } else {
            isLevel1Running = false;
        }
        if (homeRP.getLevel() == -1){
            topTxt.setText("GAME OVER");
            middleTxt.setVisibility(View.GONE);
            bottomTxt.setTextSize(14);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,40,0,20);
            bottomTxt.setLayoutParams(params);
            Typeface face= Typeface.createFromAsset(getActivity().getAssets(), "fonts/poppins.ttf");
            bottomTxt.setTypeface(face);

            teamInfoLayout.setVisibility(View.GONE);
            bottomTxt.setText("Paradox ended for this year. This year's paradox has been a success and team .EXE is thankful for that.\nHope to see you again next year at NIMBUS\n\n Developed by Aditya Rana, Akhil Jamwal and Aryan Prashar");
            levelEndsInTxt.setVisibility(View.VISIBLE);
            levelEndsInTxt.setText("To check results please head over to leaderboard section.");
        }



    }

    private void changeTeam() {
        //Todo: show dialog to change solo/team/join other team
    }

    private void onLevelClick() {

        if (homeRP.getLevel() == -1){
            Toast.makeText(getActivity(), "PARADOX is over for now! See you next year at NIMBUS again.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (homeRP.isLevelLocked()){
            Toast.makeText(getActivity(), "You have not qualified for level " + homeRP.getLevel(), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!homeRP.isLevelActive()){
            Toast.makeText(getActivity(), "Level is not yet active!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (homeRP.getLevel() == 1){
            Intent i = new Intent(getActivity(), Level1Activity.class);
            i.putExtra("homeRP", new Gson().toJson(homeRP));
            startActivity(i);
        }

        if (homeRP.getLevel() == 2){
            Intent i = new Intent(getActivity(), Level2Activity.class);
            i.putExtra("homeRP", new Gson().toJson(homeRP));
            startActivity(i);
        }
        //Todo launch another activity or toast that level is not yet unlocked
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(v);
        getData();
        return v;
    }

    TextView levelEndsInTxt;

    private void findViews(View v) {
        topTxt = v.findViewById(R.id.topTxt);
        middleTxt = v.findViewById(R.id.middleTxt);
        bottomTxt = v.findViewById(R.id.bottomTxt);
        playerNameTxt = v.findViewById(R.id.nameTxt);
        teamNameTxt = v.findViewById(R.id.teamTxt);

        teamInfoLayout = v.findViewById(R.id.teamInfoLayout);
        levelEndsInTxt = v.findViewById(R.id.endsInText);
        levelLayout = v.findViewById(R.id.levelLayout);

        progressBar = v.findViewById(R.id.progressBar);
        mainLayout = v.findViewById(R.id.mainLayout);
        viewPager = v.findViewById(R.id.viewPager);
    }
}