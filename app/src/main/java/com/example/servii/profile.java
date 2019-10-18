package com.example.servii;

public class profile {
    private String Name;
    private int localityRef;

public profile(){

    }
    public profile(String name, int localityRef) {
        this.Name = name;
        this.localityRef = localityRef;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getLocalityRef() {
        return localityRef;
    }

    public void setLocalityRef(int localityRef) {
        this.localityRef = localityRef;
    }
}
