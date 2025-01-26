package com.exe.paradox.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.exe.paradox.Models.RankModel;
import com.exe.paradox.R;
import com.exe.paradox.Tools.Tranformations.CircleTransform;
import com.exe.paradox.rest.response.RanklistRP;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeaderboardRVAdapter extends RecyclerView.Adapter {

    RanklistRP RanklistRP;
    String points = " Points";
    LeaderboardListener listener;
    boolean isLevel1Lb = true;

    public interface LeaderboardListener{
        void flipLevel();
    }

    public LeaderboardRVAdapter(RanklistRP RanklistRP, LeaderboardListener listener, boolean isLevel1Lb){
        this.RanklistRP = RanklistRP;
        this.listener = listener;
        this.isLevel1Lb = isLevel1Lb;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else
            return 1;
    }

    public void showProgress(String message){
        //Todo Show Progress
    }

    public void hideProgress(String message){
        //Todo hide progress
    }

    public void hideProgress(){
        //Todo hide progress without message
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0)
            return new PositionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard_top, parent, false));
        else
            return new RankViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard_part, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof PositionViewHolder){
            PositionViewHolder holder = (PositionViewHolder) viewHolder;
            if (RanklistRP.getLeaderboard() == null
                    || RanklistRP.getLeaderboard().size() == 0){
                holder.infoTxt.setText("Leaderboard is not ready yet please come back later.");
                holder.infoTxt.setVisibility(View.VISIBLE);
                holder.topLayout.setVisibility(View.GONE);
                return;
            }
            holder.infoTxt.setVisibility(View.GONE);
            holder.topLayout.setVisibility(View.VISIBLE);
            ArrayList<RankModel> leaderBoard = RanklistRP.getLeaderboard();


            if (leaderBoard.get(0).getDisplay_picture() != null
                    && !leaderBoard.get(0).getDisplay_picture().isEmpty())
                Picasso.get()
                        .load(leaderBoard.get(0).getDisplay_picture())
                        .transform(new CircleTransform())
                        .into(holder.firstImg);
            holder.firstTxt.setText(leaderBoard.get(0).getUser_name());
            holder.firstScore.setText(leaderBoard.get(0).getScore() + points);

            if (leaderBoard.size() < 2) return;
            if (leaderBoard.get(1).getDisplay_picture() != null
                    && !leaderBoard.get(1).getDisplay_picture().isEmpty())
                Picasso.get()
                        .load(leaderBoard.get(1).getDisplay_picture())
                        .transform(new CircleTransform())
                        .into(holder.secondImg);
            holder.secondTxt.setText(leaderBoard.get(1).getUser_name());
            holder.secondScore.setText(leaderBoard.get(1).getScore() + points);

            if (leaderBoard.size() < 3) return;

            if (leaderBoard.get(2).getDisplay_picture() != null
                    && !leaderBoard.get(2).getDisplay_picture().isEmpty())
                Picasso.get()
                        .load(leaderBoard.get(2).getDisplay_picture())
                        .transform(new CircleTransform())
                        .into(holder.thirdImg);
            holder.thirdTxt.setText(leaderBoard.get(2).getUser_name());
            holder.thirdScore.setText(leaderBoard.get(2).getScore() + points );

            if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()
                    != null && !FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString().isEmpty()){
                Picasso.get()
                        .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                        .transform(new CircleTransform())
                        .into(holder.displayImg);
            }

            if (RanklistRP.getMy_rank() != null && RanklistRP.getMy_rank().getRank() != -1){
                holder.myNameTxt.setText(RanklistRP.getMy_rank().getUser_name());
                holder.rankTxt.setText(String.valueOf(RanklistRP.getMy_rank().getRank()));
                holder.scoreTxt.setText(RanklistRP.getMy_rank().getScore() + points);
            } else {
                if (!isLevel1Lb)
                    holder.myRankLayout.setVisibility(View.GONE);
                holder.rankTxt.setText("-");
                holder.scoreTxt.setText("0" + points);
            }

            if (isLevel1Lb)
                holder.levelTxt.setText("Level 1");
            else
                holder.levelTxt.setText("Level 2");

            holder.levelLayout.setOnClickListener(view -> listener.flipLevel());

        }
        else if (viewHolder instanceof RankViewHolder){
            RankViewHolder holder = (RankViewHolder) viewHolder;
            int index = position +2;
            RankModel rank = RanklistRP.getLeaderboard().get(index);
            holder.nameTxt.setText(rank.getUser_name());
            if (rank.getDisplay_picture() != null
                    && !rank.getDisplay_picture().isEmpty()){
                Picasso.get()
                        .load(rank.getDisplay_picture())
                        .transform(new CircleTransform())
                        .into(holder.displayImg);
            }
            holder.scoreTxt.setText(rank.getScore() + points);

            if (rank.getRank() == 0){
                int r = RanklistRP.getLeaderboard().indexOf(rank) + 1;
                rank.setRank(r);
            }
            holder.rankTxt.setText(rank.getRank() + ".");
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (RanklistRP.getLeaderboard() != null){
            size = RanklistRP.getLeaderboard().size();
        }
        if (size == 0 || size == 1 || size == 2 || size == 3)
            return 1;
        return size -2;
    }

    public class PositionViewHolder extends RecyclerView.ViewHolder{

        TextView levelTxt;
        LinearLayout levelLayout;

        TextView infoTxt;
        LinearLayout topLayout;
        ImageView firstImg;
        ImageView secondImg;
        ImageView thirdImg;

        TextView firstTxt;
        TextView secondTxt;
        TextView thirdTxt;

        TextView firstScore;
        TextView secondScore;
        TextView thirdScore;

        TextView rankTxt;
        TextView nameTxt;
        TextView scoreTxt;
        ImageView displayImg;
        LinearLayout myRankLayout;

        TextView myNameTxt;


        public PositionViewHolder(@NonNull View itemView) {
            super(itemView);

            firstImg = itemView.findViewById(R.id.firstImage);
            secondImg = itemView.findViewById(R.id.secondImg);
            thirdImg = itemView.findViewById(R.id.thirdImg);

            firstTxt = itemView.findViewById(R.id.firstNameTxt);
            secondTxt = itemView.findViewById(R.id.secondNameTxt);
            thirdTxt = itemView.findViewById(R.id.thirdNameTxt);

            firstScore = itemView.findViewById(R.id.firstScoreTxt);
            secondScore = itemView.findViewById(R.id.secondScoreTxt);
            thirdScore = itemView.findViewById(R.id.thirdScoreTxt);

            rankTxt = itemView.findViewById(R.id.rankNumberTxt);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            scoreTxt = itemView.findViewById(R.id.scoreTxt);
            displayImg = itemView.findViewById(R.id.displayImg);

            topLayout = itemView.findViewById(R.id.contentLayout);
            infoTxt = itemView.findViewById(R.id.infoTxt);
            levelTxt = itemView.findViewById(R.id.levelTxt);
            levelLayout  = itemView.findViewById(R.id.levelLayout);
            myRankLayout = itemView.findViewById(R.id.myRankLayout);
            myNameTxt = itemView.findViewById(R.id.nameMineTxt);
        }
    }

    public class RankViewHolder extends RecyclerView.ViewHolder{


        TextView rankTxt;
        TextView nameTxt;
        TextView scoreTxt;
        ImageView displayImg;

        public RankViewHolder(@NonNull View itemView) {
            super(itemView);

            rankTxt = itemView.findViewById(R.id.rankNumberTxt);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            scoreTxt = itemView.findViewById(R.id.scoreTxt);
            displayImg = itemView.findViewById(R.id.displayImg);
        }
    }

}
