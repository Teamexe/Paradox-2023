package com.exe.paradox.Models;

import java.util.ArrayList;

public class Question {
    int questionNo;
    String _id;
    String questionId;
    String question;
    String image;
    String hint;
    boolean isHintAvailable;

    public String getHint() {
        return hint;
    }

    public boolean isHintAvailable() {
        return isHintAvailable;
    }

    String answer; //Not to be given by server

    public Question() {
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public String getQuestion() {
        return question;
    }

    public String getImage() {
        return image;
    }
}
