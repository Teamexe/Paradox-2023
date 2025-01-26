package com.exe.paradox.rest.response;

import com.exe.paradox.Models.User;

public class TeamDetailsRP {

    public enum Position{CONTROL, FIELD}

    boolean isInTeam;
    String teamName;
    String teamCode;
    Officer controlOfficer;
    Officer fieldOfficer;


    public boolean isInTeam() {
        return isInTeam;
    }
    public String getTeamName() {
        return teamName;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public Officer getControlOffice() {
        return controlOfficer;
    }

    public Officer getFieldOfficer() {
        return fieldOfficer;
    }

    public TeamDetailsRP() {
    }

    public class Officer{
        String name;
        String uid;
        String photoUrl;
        Position position;

        public String getName() {
            return name;
        }

        public String getUid() {
            return uid;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public Position getPosition() {
            return position;
        }

        public Officer() {
        }
    }
}
