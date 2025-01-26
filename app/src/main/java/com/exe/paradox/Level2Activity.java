package com.exe.paradox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.exe.paradox.Tools.Method;
import com.exe.paradox.databinding.ActivityLevel2Binding;
import com.exe.paradox.rest.api.APIMethods;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;
import com.exe.paradox.rest.response.Level1RP;
import com.exe.paradox.rest.response.Level2RP;
import com.squareup.picasso.Picasso;

public class Level2Activity extends AppCompatActivity {

    ActivityLevel2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLevel2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fetchQuestion();
        setSubmitListener();
    }

    private void setSubmitListener() {
        binding.answerEt.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            binding.submitBtn.performClick();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        binding.submitBtn.setOnClickListener(view ->{
            String answer = binding.answerEt.getText().toString();
            answer = answer.trim();
            answer = answer.replace(" ", "");
            if (answer.isEmpty()){
                binding.answerEt.setError("Enter the answer");
                return;
            }
            binding.answerEt.setError(null);
            binding.progressBar.setVisibility(View.VISIBLE);

            APIMethods.submitLevel2Answer(answer, new APIResponseListener<Level2RP>() {
                @Override
                public void success(Level2RP response) {
                    binding.progressBar.setVisibility(View.GONE);
                    verifyAnswer(response);
                }

                @Override
                public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                    binding.progressBar.setVisibility(View.GONE);
                    Method.showFailedAlert(Level2Activity.this, code + " - " +  message);
                }
            });
        });
    }

    private void verifyAnswer(Level2RP response) {
        if (!response.isAnswerCorrect()){
            Toast.makeText(this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
        setQuestion(response);
    }


    private void fetchQuestion() {
        binding.questionLayout.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);

        APIMethods.getLevel2Question(new APIResponseListener<Level2RP>() {
            @Override
            public void success(Level2RP response) {
                binding.progressBar.setVisibility(View.GONE);
                setQuestion(response);
            }

            @Override
            public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                Method.showFailedAlert(Level2Activity.this, code + " - " +  message);
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setQuestion(Level2RP response) {
        binding.progressBar.setVisibility(View.GONE);
        binding.questionLayout.setVisibility(View.VISIBLE);
        binding.answerEt.setText("");

        if (response.isLevelComplete()){
            //Todo: handle UI when the level is completed!
            Toast.makeText(this, "Level Completed!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (response.getNextQuestion() == null){
            Method.showFailedAlert(this, "No more questions found!");
            return;
        }
        if (response.getNextQuestion().isAnswerRequired()){
            binding.answerEt.setVisibility(View.VISIBLE);
            binding.submitBtn.setVisibility(View.VISIBLE);
        } else {
            binding.submitBtn.setVisibility(View.GONE);
            binding.answerEt.setVisibility(View.GONE);
        }

        binding.questionNumberTxt.setText("Q"+String.valueOf(response.getNextQuestion().getQuestionNo()));
        binding.questionTxt.setText(response.getNextQuestion().getQuestion());

        Picasso.get()
                .load(response.getNextQuestion().getImage())
                .resize(2048, 2048)
                .into(binding.questionImg);


        binding.officerTxt.setText(String.valueOf(response.getOfficerType().getPosition()));

        if (response.getNextQuestion().isHintAvailable()){
            binding.hintLayout.setVisibility(View.VISIBLE);
            binding.hintAvailableTxt.setText("Hint Available:");
            binding.hintTxt.setText("Click to unlock\n(30 points");
            binding.hintLayout.setOnClickListener(view -> getHint());
            if (response.getNextQuestion().getHint() != null
                    && !response.getNextQuestion().getHint().isEmpty()){
                binding.hintLayout.setOnClickListener(null);
                binding.hintAvailableTxt.setText("Hint:");
                binding.hintTxt.setText(response.getNextQuestion().getHint());
            }
        } else {
            binding.hintLayout.setVisibility(View.GONE);
        }
    }

    private void getHint() {
        binding.questionLayout.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);

        APIMethods.getLevel2Hint(new APIResponseListener<Level2RP>() {
            @Override
            public void success(Level2RP response) {
                binding.progressBar.setVisibility(View.GONE);
                setQuestion(response);
            }

            @Override
            public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                Method.showFailedAlert(Level2Activity.this, code + " - " +  message);
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}