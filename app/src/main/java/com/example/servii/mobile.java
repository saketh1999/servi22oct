package com.example.servii;

import com.google.firebase.database.Exclude;

public class mobile {
    private String Mobile;

    public mobile(){

    }

    public mobile(String mobile) {
        Mobile = mobile;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
