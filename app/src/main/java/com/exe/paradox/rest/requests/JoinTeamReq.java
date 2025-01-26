package com.exe.paradox.rest.requests;

import com.google.firebase.auth.FirebaseAuth;

public class JoinTeamReq {
    String uid;
    String teamId;

    public JoinTeamReq(String teamId) {
        this.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.teamId = teamId;
    }

    public JoinTeamReq(String uid, String teamId) {
        this.uid = uid;
        this.teamId = teamId;
    }
}
