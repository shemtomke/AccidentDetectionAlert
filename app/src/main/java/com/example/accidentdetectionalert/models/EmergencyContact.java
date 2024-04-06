package com.example.accidentdetectionalert.models;

public class EmergencyContact {
    public int id;
    public User user;
    public String fullName;
    public String phoneContact;

    public int getId(){return id;}
    public User getUser(){return user;}
    public String getFullName(){return fullName;}
    public String getPhoneContact(){return phoneContact;}
    public void setId(int _id)
    {
        id = _id;
    }
    public void setFullName(String fullname)
    {
        fullName = fullname;
    }
    public void setPhoneContact(String phoneNumber)
    {
        phoneContact = phoneNumber;
    }
    public void setUser(User _user){user = _user;}
    public EmergencyContact(int _id, User _user, String fullname, String phoneNumber)
    {
        id = _id;
        user = _user;
        fullName = fullname;
        phoneContact = phoneNumber;
    }
    public EmergencyContact(String fullname, String phoneNumber)
    {
        fullName = fullname;
        phoneContact = phoneNumber;
    }
}
