package com.example.accidentdetectionalert.models;

public class Police {
    private int policeId;
    private User user;
    private String location;

    public int getPoliceId() {
        return policeId;
    }
    public User getUser() {
        return user;
    }
    public String getLocation() {
        return location;
    }

    public void setPoliceId(int policeId) {
        this.policeId = policeId;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Police(int policeID, User _user, String _location)
    {
        policeId = policeID;
        user = _user;
        location = _location;
    }
}
