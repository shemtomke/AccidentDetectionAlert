package com.example.accidentdetectionalert.models;

public class Police {
    private String policeId;
    private User user;
    private String location;

    public String getPoliceId() {
        return policeId;
    }
    public User getUser() {
        return user;
    }
    public String getLocation() {
        return location;
    }

    public void setPoliceId(String policeId) {
        this.policeId = policeId;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Police(String policeID, User _user, String _location)
    {
        policeId = policeID;
        user = _user;
        location = _location;
    }
}
