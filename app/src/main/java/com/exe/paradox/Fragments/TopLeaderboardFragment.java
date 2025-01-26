package com.exe.paradox.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exe.paradox.Models.RankModel;
import com.exe.paradox.Models.User;
import com.exe.paradox.R;
import com.exe.paradox.Tools.Tranformations.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopLeaderboardFragment extends Fragment {

    private ArrayList<RankModel> leaderBoard;
    private String points = " points";

    public TopLeaderboardFragment() {
        // Required empty public constructor
    }

    public TopLeaderboardFragment(ArrayList<RankModel> leaderboard){
        this.leaderBoard = leaderboard;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_top_leaderboard, container, false);
        findViews(v);
        setUpLeaderboard();
        return v;
    }

    private void setUpLeaderboard() {
    }

    private void findViews(View view) {

        ImageView firstImg = view.findViewById(R.id.firstImage);
        ImageView secondImg = view.findViewById(R.id.secondImg);
        ImageView thirdImg = view.findViewById(R.id.thirdImg);
        TextView firstTxt = view.findViewById(R.id.firstNameTxt);
        TextView secondTxt = view.findViewById(R.id.secondNameTxt);
        TextView thirdTxt = view.findViewById(R.id.thirdNameTxt);
        TextView firstScore = view.findViewById(R.id.firstScoreTxt);
        TextView secondScore = view.findViewById(R.id.secondScoreTxt);
        TextView thirdScore = view.findViewById(R.id.thirdScoreTxt);

        if (leaderBoard == null)
            return;

        if (leaderBoard.get(0).getDisplay_picture() != null && !leaderBoard.get(0).getDisplay_picture().isEmpty())
            Picasso.get()
                    .load(leaderBoard.get(0).getDisplay_picture())
                    .transform(new CircleTransform())
                    .into(firstImg);
        firstTxt.setText(leaderBoard.get(0).getUser_name());
        firstScore.setText(leaderBoard.get(0).getScore() + points);

        if (leaderBoard.size() < 2) return;
        if (leaderBoard.get(1).getDisplay_picture() != null
                && !leaderBoard.get(1).getDisplay_picture().isEmpty())
            Picasso.get()
                    .load(leaderBoard.get(1).getDisplay_picture())
                    .transform(new CircleTransform())
                    .into(secondImg);
        secondTxt.setText(leaderBoard.get(1).getUser_name());
        secondScore.setText(leaderBoard.get(1).getScore() + points);

        if (leaderBoard.size() < 3) return;

        if (leaderBoard.get(2).getDisplay_picture() != null
                && !leaderBoard.get(2).getDisplay_picture().isEmpty())
            Picasso.get()
                    .load(leaderBoard.get(2).getDisplay_picture())
                    .transform(new CircleTransform())
                    .into(thirdImg);
        thirdTxt.setText(leaderBoard.get(2).getUser_name());
        thirdScore.setText(leaderBoard.get(2).getScore() + points );
    }
}