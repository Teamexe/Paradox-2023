package com.exe.paradox.rest.requests;

import com.google.firebase.auth.FirebaseAuth;

public class HomeReq {
    String uid;
    int page;
    public HomeReq(){
        this.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public HomeReq(int page) {
        this.page = page;
        this.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
