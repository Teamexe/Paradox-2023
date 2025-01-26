package com.exe.paradox.MainFragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exe.paradox.LoginActivity;
import com.exe.paradox.Models.User;
import com.exe.paradox.R;
import com.exe.paradox.TeamActivity;
import com.exe.paradox.Tools.Method;
import com.exe.paradox.databinding.FragmentLeaderboardBinding;
import com.exe.paradox.databinding.FragmentProfileBinding;
import com.exe.paradox.rest.api.APIMethods;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;



    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        setListeners();
        setPersonalDetails();
        loadData();
        return binding.getRoot();
    }

    private void loadData() {
        binding.levelTxt.setVisibility(View.GONE);
        binding.scoreTxt.setVisibility(View.GONE);
        binding.attemptsTxt.setVisibility(View.GONE);
        binding.coinsTxt.setVisibility(View.GONE);

        binding.level.setVisibility(View.GONE);
        binding.score.setVisibility(View.GONE);
        binding.attempts.setVisibility(View.GONE);
        binding.coins.setVisibility(View.GONE);
        //Todo: Make loading efficient only when there is a update to scores or make a drag down reload


        binding.progressBar.setVisibility(View.VISIBLE);
        APIMethods.getProfile(new APIResponseListener<User>() {
            @Override
            public void success(User response) {
                binding.progressBar.setVisibility(View.GONE);
                setProfile(response);
            }

            @Override
            public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                binding.progressBar.setVisibility(View.GONE);
                Method.showFailedAlert(getActivity(), code + " - " + message);
            }
        });
    }

    private void setProfile(User user) {
        if (user.getName() != null && !user.getName().isEmpty()){
            binding.nameTxt.setVisibility(View.VISIBLE);
            binding.nameTxt.setText(user.getName());
        }

        if (user.getTeamName() != null && !user.getTeamName().isEmpty()){
            binding.teamTxt.setVisibility(View.VISIBLE);
            binding.teamTxt.setText(user.getTeamName());
        } else {
            binding.teamTxt.setText("Join Team");
            binding.teamTxt.setVisibility(View.VISIBLE);
        }

        if (user.getDisplayPicture() != null && !user.getDisplayPicture().isEmpty()){
            binding.profileImg.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(user.getDisplayPicture())
                    .into(binding.profileImg);
        }

        if (user.getLevel() != -1){
            binding.levelTxt.setVisibility(View.VISIBLE);
            binding.level.setVisibility(View.VISIBLE);
            binding.levelTxt.setText(String.valueOf(user.getLevel()));
        }

        if (user.getScore() != -1){
            binding.scoreTxt.setVisibility(View.VISIBLE);
            binding.score.setVisibility(View.VISIBLE);
            binding.scoreTxt.setText(String.valueOf(user.getScore()));
        }

//        if (user.getCoins() != -1){
//            binding.coinsTxt.setVisibility(View.VISIBLE);
//            binding.coins.setVisibility(View.VISIBLE);
//            binding.coinsTxt.setText(String.valueOf(user.getCoins()));
//        }


        if (user.getAttempts() != -1){
            binding.attemptsTxt.setVisibility(View.VISIBLE);
            binding.attempts.setVisibility(View.VISIBLE);
            binding.attemptsTxt.setText(String.valueOf(user.getAttempts()));
        }


    }

    private void setPersonalDetails() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Toast.makeText(getActivity(), "Please log in to continue!", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.nameTxt.setText(user.getDisplayName());
        Picasso.get()
                .load(user.getPhotoUrl())
                .into(binding.profileImg);
    }

    private void setListeners() {
        binding.teamTxt.setOnClickListener(view -> startActivity(new Intent(getActivity(), TeamActivity.class)));
        binding.logoutBtn.setOnClickListener(view ->{
            new AlertDialog.Builder(getActivity())
                    .setTitle("Log out")
                    .setMessage("Are you sure you want to sign out of your account?\nYour progress will remain saved on our servers.")
                    .setCancelable(true)
                    .setPositiveButton("Log out", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    })
                    .setNegativeButton("Cancel", ((dialog, which) -> dialog.dismiss()))
                    .show();
        });
    }
}