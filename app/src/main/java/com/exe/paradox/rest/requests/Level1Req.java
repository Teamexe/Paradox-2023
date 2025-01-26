package com.exe.paradox.rest.requests;

import com.google.firebase.auth.FirebaseAuth;

public class Level1Req {
    String answer;
    String uid;

    public Level1Req(String answer) {
        this.answer = answer;
        this.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
