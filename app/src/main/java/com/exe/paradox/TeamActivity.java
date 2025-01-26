package com.exe.paradox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.exe.paradox.Tools.Method;
import com.exe.paradox.databinding.ActivityTeamBinding;
import com.exe.paradox.rest.api.APIMethods;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;
import com.exe.paradox.rest.response.TeamDetailsRP;
import com.google.gson.Gson;

public class TeamActivity extends AppCompatActivity {

    ActivityTeamBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Todo: Fetch Team Details Here and based on that either show or hide layout with progress bar
        getTeamDetails();
        setJoinTeam();
        setCreateTeam();
    }

    private void getTeamDetails() {
        binding.joiningTeamLayout.setVisibility(View.GONE);
        binding.teamInfoLayout.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);

        APIMethods.getTeamInformation(new APIResponseListener<TeamDetailsRP>() {
            @Override
            public void success(TeamDetailsRP response) {
                binding.progressBar.setVisibility(View.GONE);
                teamDetailsRP = response;
                showTeamDetails();
            }

            @Override
            public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                binding.progressBar.setVisibility(View.GONE);
                Method.showFailedAlert(TeamActivity.this, code + " - " + message);
            }
        });
    }

    private void setCreateTeam() {
        binding.createTeam.setOnClickListener(view ->{
            startActivityForResult(new Intent(TeamActivity.this, CreateTeamActivity.class), 1001);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK){
            teamDetailsRP = new Gson().fromJson(data.getStringExtra("response"), TeamDetailsRP.class);
            showTeamDetails();
        }
    }

    private void setJoinTeam() {
        binding.joinTeam.setOnClickListener(view-> {
            if (binding.teamCodeEt.getText().toString().isEmpty()){
                binding.teamCodeEt.setError("Required");
                return;
            }
            binding.teamCodeEt.setError(null);
            joinTeam(binding.teamCodeEt.getText().toString());
        });
    }

    private void joinTeam(String teamCode) {
        binding.progressBar.setVisibility(View.VISIBLE);
        APIMethods.joinTeam(teamCode, new APIResponseListener<TeamDetailsRP>() {
            @Override
            public void success(TeamDetailsRP response) {
                binding.progressBar.setVisibility(View.GONE);
                teamDetailsRP = response;
                showTeamDetails();
            }

            @Override
            public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                binding.progressBar.setVisibility(View.GONE);
                Method.showFailedAlert(TeamActivity.this, code + " - " + message);
            }
        });
    }

    TeamDetailsRP teamDetailsRP;
    private void showTeamDetails() {
        if (teamDetailsRP.isInTeam()){
            binding.teamInfoLayout.setVisibility(View.VISIBLE);
            binding.joiningTeamLayout.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.GONE);

            binding.teamNameTxt.setText(teamDetailsRP.getTeamName());
            binding.teamCode.setText(teamDetailsRP.getTeamCode());
            if (teamDetailsRP.getControlOffice() != null){
                binding.coTxt.setText(teamDetailsRP.getControlOffice().getName() + " (CO)");
            }
            if (teamDetailsRP.getFieldOfficer() != null
            && teamDetailsRP.getFieldOfficer().getName() != null
            && !teamDetailsRP.getFieldOfficer().getName().isEmpty()) {
                binding.foTxt.setText(teamDetailsRP.getFieldOfficer().getName() + " (FO)");
            } else {
                binding.foTxt.setVisibility(View.GONE);
            }
        } else {
            showJoinTeamLayout();
        }
    }

    private void showJoinTeamLayout() {
        binding.teamInfoLayout.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.GONE);
        binding.joiningTeamLayout.setVisibility(View.VISIBLE);
    }
}