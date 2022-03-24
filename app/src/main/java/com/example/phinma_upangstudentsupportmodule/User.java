package com.example.phinma_upangstudentsupportmodule;

public class User {

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
