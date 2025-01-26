package com.exe.paradox.rest.requests;

import com.google.firebase.auth.FirebaseAuth;

public class CreateTeamReq {
    String teamName;
    String uid;

    public CreateTeamReq(String teamName) {
        this.teamName = teamName;
        this.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
