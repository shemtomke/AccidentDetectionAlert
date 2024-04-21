package com.example.accidentdetectionalert.models;

public class Ambulance {
    private int ambulanceId;
    private User user;
    private Hospital hospital;
    private String location;
    private boolean isAvailable;

    public int getAmbulanceId() {
        return ambulanceId;
    }
    public User getUser() {
        return user;
    }
    public Hospital getHospital() {
        return hospital;
    }
    public String getLocation() {
        return location;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setAmbulanceId(int ambulanceId) {
        this.ambulanceId = ambulanceId;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Ambulance(int _ambulanceId, User _user, Hospital _hospital, String _location)
    {
        ambulanceId = _ambulanceId;
        user = _user;
        hospital = _hospital;
        location = _location;
    }
    public Ambulance(User _user, Hospital _hospital, String _location)
    {
        user = _user;
        hospital = _hospital;
        location = _location;
    }
}
