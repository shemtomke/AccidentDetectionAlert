package com.example.accidentdetectionalert.models;

public class User {
    private String userId;
    private String email;
    private String password;
    private String fullName;

    public String getUserId(){return userId;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getFullName(){return fullName;}
    public void setUserId(String _userId)
    {
        userId = _userId;
    }
    public void setEmail(String _email)
    {
        email = _email;
    }
    public void setPassword(String _password)
    {
        password = _password;
    }
    public void setFullName(String _fullName)
    {
        fullName = _fullName;
    }
}
