package com.exe.paradox.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.exe.paradox.Fragments.BannerFragment;
import com.exe.paradox.Fragments.TopLeaderboardFragment;
import com.exe.paradox.Models.Banner;
import com.exe.paradox.Models.RankModel;
import com.exe.paradox.Models.User;

import java.util.ArrayList;

public class BannerPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Banner> banners;
    private ArrayList<RankModel> leaderboard;
    FragmentManager fragmentManager;

    public BannerPageAdapter(@NonNull FragmentManager fm, ArrayList<Banner> banners, ArrayList<RankModel> leaderboard, Context context) {
        super(fm);
        fragmentManager = fm;
        this.banners = banners;
        this.leaderboard = leaderboard;
    }

    @Override
    public int getCount() {
        if (banners == null)
            banners = new ArrayList<>();
        int lbSize = 0;
        if (leaderboard != null && leaderboard.size() >= 3)
            lbSize = 1;
        return banners.size() + lbSize;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0 && leaderboard != null && leaderboard.size() >= 3){
            TopLeaderboardFragment leaderboardFragment = new TopLeaderboardFragment(leaderboard);
            return leaderboardFragment;
        }

        if (leaderboard != null && leaderboard.size() >= 3){
            position--;
        }
        if (position < banners.size()){
            BannerFragment fragment = new BannerFragment(banners.get(position)
                    .getImageUrl(), banners.get(position).getRedirectUrl(), banners.get(position).getText());
//            if (!banners.get(position).getImageUrl().isEmpty()){
////                fragment.setImageView(banners.get(position).getImageUrl());
//            }
            return fragment;
        } else {
            return null;
        }
    }

    public void addBanner(Banner newBanner){
        banners.add(newBanner);
    }
}
