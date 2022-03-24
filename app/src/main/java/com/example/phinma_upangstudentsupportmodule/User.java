package com.example.phinma_upangstudentsupportmodule;

public class User {

    String date, mental, physical, overall;

    public String getDate() {
        return date;
    }

    public String getMental() {
        return mental;
    }

    public String getPhysical() {
        return physical;
    }

    public String getOverall() {
        return overall;
    }

    public String idnumber, firstname, middlename, lastname, section, email, contact, department;

    public User(){

    }

    public User(String idnumber, String firstname, String middlename, String lastname, String section, String email, String contact, String department){
        this.idnumber = idnumber;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.section = section;
        this.email = email;
        this.contact = contact;
        this.department = department;
    }
}
