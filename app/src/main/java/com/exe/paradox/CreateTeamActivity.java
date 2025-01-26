package com.exe.paradox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.exe.paradox.Tools.Method;
import com.exe.paradox.databinding.ActivityCreateTeamBinding;
import com.exe.paradox.rest.api.APIMethods;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;
import com.exe.paradox.rest.response.TeamDetailsRP;
import com.google.gson.Gson;

public class CreateTeamActivity extends AppCompatActivity {

    ActivityCreateTeamBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTeamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.createTeam.setOnClickListener(view->{
            if (binding.teamCodeEt.getText().toString().isEmpty()){
                binding.teamCodeEt.setError("Required");
                return;
            }
            binding.teamCodeEt.setError(null);
            createTeam(binding.teamCodeEt.getText().toString());
        });
    }


    private void createTeam(String teamName) {
        binding.progressBar.setVisibility(View.VISIBLE);
        APIMethods.createTeam(teamName, new APIResponseListener<TeamDetailsRP>() {
            @Override
            public void success(TeamDetailsRP response) {
                Toast.makeText(CreateTeamActivity.this, "Team Created Successfully!", Toast.LENGTH_SHORT).show();
                String resp = new Gson().toJson(response);
                Intent data = new Intent();
                data.putExtra("response", resp);
                CreateTeamActivity.this.setResult(RESULT_OK, data);
                CreateTeamActivity.this.finish();
            }

            @Override
            public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                binding.progressBar.setVisibility(View.GONE);
                Method.showFailedAlert(CreateTeamActivity.this, code + " - " + message);
            }
        });
    }
}