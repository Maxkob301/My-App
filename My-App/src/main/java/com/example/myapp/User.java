package com.example.myapp;

public class User {
    private String firstName;
    private String lastName;
    private String LoginName;
    private String passwordName;
    private String countryName;
    private String gender;

    public  User(String firstName,String lastName,String LoginName,String passwordName,String countryName,String gender){
        this.firstName = firstName;
        this.lastName = lastName;
        this.LoginName = LoginName;
        this.passwordName = passwordName;
        this.countryName = countryName;
        this.gender = gender;
    }

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLoginName() {
        return LoginName;
    }

    public String getPasswordName() {
        return passwordName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getGender() {
        return gender;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public void setPasswordName(String passwordName) {
        this.passwordName = passwordName;
    }

}
