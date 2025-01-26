package com.exe.paradox.Models;

public class QuestionL2 {
    int questionNo;
    String _id;
    String questionId;
    String question;
    String image;
    boolean isAnswerRequired;
    String answer; //Not to be given by server

    String hint;
    boolean isHintAvailable;

    public String getHint() {
        return hint;
    }

    public boolean isHintAvailable() {
        return isHintAvailable;
    }

    public QuestionL2() {
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public String get_id() {
        return _id;
    }

    public String getQuestion() {
        return question;
    }

    public String getImage() {
        return image;
    }

    public boolean isAnswerRequired() {
        return isAnswerRequired;
    }
}
