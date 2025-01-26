package com.exe.paradox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.exe.paradox.Tools.Method;
import com.exe.paradox.databinding.ActivityLevel1Binding;
import com.exe.paradox.rest.api.APIMethods;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;
import com.exe.paradox.rest.response.Level1RP;
import com.squareup.picasso.Picasso;

public class Level1Activity extends AppCompatActivity {
    AlertDialog progressDialog;
    View progressView;
    ActivityLevel1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLevel1Binding.inflate(getLayoutInflater());
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
            View focus = this.getCurrentFocus();
            if (focus != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
            }
            String answer = binding.answerEt.getText().toString();
            answer = answer.trim();
            answer = answer.replace(" ", "");
            if (answer.isEmpty()){
                binding.answerEt.setError("Enter the answer");
                return;
            }

            binding.answerEt.setError(null);
            progressDialog.show();

            APIMethods.submitLevel1Answer(answer, new APIResponseListener<Level1RP>() {
                @Override
                public void success(Level1RP response) {
                    progressDialog.dismiss();
                    verifyAnswer(response);
                }

                @Override
                public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                    progressDialog.dismiss();
                    Method.showFailedAlert(Level1Activity.this, code + " - " +  message);
                }
            });
        });
    }

    private void verifyAnswer(Level1RP response) {
        if (!response.isAnswerCorrect()){
            Toast.makeText(this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
        setQuestion(response);
    }

    private void fetchQuestion() {
        View progressView = LayoutInflater.from(this).inflate(R.layout.dialog_processing, null, false);
        progressDialog = new AlertDialog.Builder(this)
                .setView(progressView)
                .setCancelable(false)
                .show();

        APIMethods.getLevel1Question(new APIResponseListener<Level1RP>() {
            @Override
            public void success(Level1RP response) {
                progressDialog.dismiss();
                setQuestion(response);
            }

            @Override
            public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                progressDialog.dismiss();
                Method.showFailedAlert(Level1Activity.this, code + " - " +  message);
            }
        });
    }

    private void setQuestion(Level1RP response) {
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

        binding.questionNumberTxt.setText("Q"+String.valueOf(response.getNextQuestion().getQuestionNo()));
        binding.questionTxt.setText(response.getNextQuestion().getQuestion());

        Picasso.get()
                .load(response.getNextQuestion().getImage())
                .resize(2048, 2048)
                .into(binding.questionImg);

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
        View progressView = LayoutInflater.from(this).inflate(R.layout.dialog_processing, null, false);
        progressDialog = new AlertDialog.Builder(this)
                .setView(progressView)
                .setCancelable(false)
                .show();

        APIMethods.getLevel1Hint(new APIResponseListener<Level1RP>() {
            @Override
            public void success(Level1RP response) {
                progressDialog.dismiss();
                setQuestion(response);
            }

            @Override
            public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                progressDialog.dismiss();
                Method.showFailedAlert(Level1Activity.this, code + " - " +  message);
            }
        });
    }

}