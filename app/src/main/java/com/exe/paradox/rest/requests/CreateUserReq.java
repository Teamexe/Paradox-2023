package com.exe.paradox.rest.requests;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class CreateUserReq {
    String uid;
    String email;
    String name;
    String displayPicture;
    public CreateUserReq(){
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if (u != null) {
            this.name = u.getDisplayName();
            if (u.getEmail().contains("@nith.ac.in")){
                String username = u.getEmail().replace("@nith.ac.in", "");
                this.name = name.replace(username, "")
                        .replace(username.toLowerCase(Locale.ROOT), "")
                        .replace(username.toUpperCase(), "")
                        .replace("_", "").trim();
            }
            this.uid = u.getUid();
            this.email = u.getEmail();
            this.displayPicture = u.getPhotoUrl().toString();
        }
    }


}
