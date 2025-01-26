package com.exe.paradox.rest.response;

import com.exe.paradox.Models.Question;

public class Level1RP {
    boolean isLevelComplete;
    boolean isAnswerCorrect;
    Question nextQuestion;

    public boolean isLevelComplete() {
        return isLevelComplete;
    }

    public boolean isAnswerCorrect() {
        return isAnswerCorrect;
    }

    public Question getNextQuestion() {
        return nextQuestion;
    }

    public Level1RP() {
    }
}
