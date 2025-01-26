package com.exe.paradox.MainFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exe.paradox.Adapters.LeaderboardRVAdapter;
import com.exe.paradox.R;
import com.exe.paradox.Tools.Method;
import com.exe.paradox.databinding.FragmentLeaderboardBinding;
import com.exe.paradox.rest.api.APIMethods;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;
import com.exe.paradox.rest.response.RanklistRP;

public class LeaderboardFragment extends Fragment implements LeaderboardRVAdapter.LeaderboardListener{

    FragmentLeaderboardBinding binding;
    LeaderboardRVAdapter adapter;
    LinearLayoutManager manager;

    boolean level1Shown = true;
    boolean isLevelSetFlag = true;
    RanklistRP level1RP;
    RanklistRP level2RP;



    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isLevelSetFlag) {
            level1Shown = HomeFragment.isLevel1Running;
            isLevelSetFlag = false;
        }
    }

    private void loadRankList() {
        if (level1Shown) {
            APIMethods.getRanklist(new APIResponseListener<RanklistRP>() {
                @Override
                public void success(RanklistRP response) {
                    level1Shown = true;
                    level1RP = response;
                    showLeaderboard(response);
                }

                @Override
                public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                    binding.progressBar.setVisibility(View.GONE);
                    Method.showFailedAlert(getActivity(), code + " - " + message);
                }
            });
        } else {
            APIMethods.getLevel2ranklist(new APIResponseListener<RanklistRP>() {
                @Override
                public void success(RanklistRP response) {
                    level1Shown = false;
                    level2RP = response;
                    showLeaderboard(response);
                }

                @Override
                public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                    binding.progressBar.setVisibility(View.GONE);
                    if (message.equals("Team not Found")){
                        level1Shown = true;
                        loadRankList();
                    } else
                        Method.showFailedAlert(getActivity(), code + " - " + message);
                }
            });
        }
    }

    private void showLeaderboard(RanklistRP response) {
        binding.progressBar.setVisibility(View.GONE);
        adapter = new LeaderboardRVAdapter(response, this::flipLevel, level1Shown);
        manager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
        loadRankList();
        return binding.getRoot();
    }

    @Override
    public void flipLevel() {
        level1Shown = !level1Shown;

        if (level1Shown && level1RP != null)
            showLeaderboard(level1RP);
        else if (!level1Shown && level2RP != null)
            showLeaderboard(level2RP);
        else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }

        loadRankList();

    }
}