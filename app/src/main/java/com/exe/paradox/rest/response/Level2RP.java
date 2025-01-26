package com.exe.paradox.rest.response;

import com.exe.paradox.Models.QuestionL2;

public class Level2RP {
    boolean isLevelComplete;
    boolean isAnswerCorrect;
    QuestionL2 nextQuestion;
    TeamDetailsRP.Officer officerType;

    public Level2RP() {
    }

    public boolean isLevelComplete() {
        return isLevelComplete;
    }

    public boolean isAnswerCorrect() {
        return isAnswerCorrect;
    }

    public QuestionL2 getNextQuestion() {
        return nextQuestion;
    }

    public TeamDetailsRP.Officer getOfficerType() {
        return officerType;
    }
}
