package com.exe.paradox.MainFragments;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exe.paradox.R;
import com.exe.paradox.Tools.Method;
import com.exe.paradox.databinding.FragmentRulesBinding;
import com.exe.paradox.rest.api.APIMethods;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;
import com.exe.paradox.rest.response.RulesRP;

public class RulesFragment extends Fragment {

    FragmentRulesBinding binding;
    RulesRP rulesRP;
    public RulesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRulesBinding.inflate(inflater, container, false);
        loadRules();
        return binding.getRoot();
    }

    private void loadRules() {
        if (rulesRP != null) {
            setUI();
            return;
        }
        APIMethods.getRules(new APIResponseListener<RulesRP>() {
            @Override
            public void success(RulesRP response) {
                binding.progressBar.setVisibility(View.GONE);
                rulesRP = response;
                setUI();
            }

            @Override
            public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                binding.progressBar.setVisibility(View.GONE);
                Method.showFailedAlert(getActivity(), code + " - " + message);
            }
        });
    }

    private void setUI() {
        binding.progressBar.setVisibility(View.GONE);
        if (rulesRP.getHeader() != null && !rulesRP.getHeader().isEmpty()) {
            binding.headTxt.setVisibility(View.VISIBLE);
            binding.headTxt.setText(rulesRP.getHeader());
        }

        if (rulesRP.getBody() != null && !rulesRP.getBody().isEmpty()) {
            binding.bodyTxt.setVisibility(View.VISIBLE);

            rulesRP.setBody("<b> 1. Eligibility:</b> The game is open to all.<br>" +
                    "       <br><b>2. Participation: </b>Participants can choose to play the game in solo format or" +
                    "          can choose their partners by choice during or at the end of level 1.<br>" +
                    "      <br><b>3. Partner Matching: </b>If any qualified candidate for level 2 from level 1 fails" +
                    "          to get into a team, they will be randomly matched with another unmatched" +
                    "          partner by the organizers.<br>" +
                    "      <br><b> 4. Level 1 Gameplay: </b>Participants will have 12 hours, starting from 10 a.m." +
                    "          15/04/2023 until 10 p.m. 15/04/2023 to complete level 1 and achieve the" +
                    "          highest score possible. Participants will be awarded points for each" +
                    "          successfully solved puzzle. The top 50 candidates with the highest scores" +
                    "          will qualify for level 2.<br>" +
                    "        <br><b>5. Level 2 Gameplay: </b>Participants will be divided into teams of two, with one" +
                    "          team member acting as the control officer and the other as the field" +
                    "          officer. The field officer's role will be to decode the location from the" +
                    "          image shown in Level 2. After decoding the image, the field officer should" +
                    "          visit the place and scan the QR code located there to avail the clue for" +
                    "          solving the question given to the control officer. Points will be awarded" +
                    "          for each successfully solved puzzle or clue. Participants will have to" +
                    "          complete all the puzzles and clues within the specified time frame. Any team" +
                    "          that fails to do so will not be eligible for the leaderboard.<br>" +
                    "          <br><b>6. Hints: </b>Participants can avail hints in questions if they want. However," +
                    "          using a hint will result in a reduction of points by a given amount. The" +
                    "          amount of points that will be deducted for using a hint will be specified" +
                    "          for each question.<br>" +
                    "          <br><b>7. Scoring: </b>Points will be awarded for solving every puzzle and leaderboard" +
                    "          will be maintained for the teams.<br>" +
                    "          <br><b>8. Time limit:</b> Level 2 will start at 10 a.m. 16/04/2023 and end at 8 p.m." +
                    "          16/04/2023.<br>" +
                    "          <br><b>9. Winning Criteria:</b> The top 3 teams in the leaderboard at the end of round two" +
                    "          will be the winners.<br>" +
                    "          <br>10. The decision of the organizers will be final and binding on all" +
                    "          participants.<br>" +
                    "        <br><br>" +
                    "      <h2>TERMS AND CONDITIONS:</h2><br>" +
                    "      " +
                    "        1. Participants are responsible for their own safety while playing the game.<br>" +
                    "        2. Participants are not allowed to use any external aids or consult any sources" +
                    "          other than those provided by the organizers.<br>" +
                    "        3. Any attempt to cheat or tamper with the game will result in immediate" +
                    "          disqualification.<br>" +
                    "        4. Participants are not allowed to disclose any information related to the game" +
                    "          to anyone who has not participated in the game.<br>" +
                    "        5. The organizers reserve the right to disqualify any participant who fails to" +
                    "          follow the rules or behaves in an inappropriate manner.<br>" +
                    "        6. The organizers reserve the right to cancel or modify the game at any time" +
                    "          without prior notice.<br>" +
                    "        7. Participants should ensure that they have a stable internet connection while" +
                    "          playing the game.<br>" +
                    "        8. By participating in the game, participants agree to abide by the rules and" +
                    "          regulations mentioned above." +
                    "          <br><br>" +
                    "      <p>" +
                    "        We hope you enjoy playing \"Paradox\" and wish you all the best for the game!" +
                    "      </p>");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.bodyTxt.setText(Html.fromHtml(rulesRP.getBody(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.bodyTxt.setText(Html.fromHtml(rulesRP.getBody()));
            }
        }
    }
}