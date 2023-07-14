package com.example.geektrust;

public abstract class Citizen {
    
    private String type;
    private int fare;

    public Citizen(String name, int fare){
        this.fare = fare;
        this.type = name;
    }

    public int getFare(){
        return this.fare;
    }

    public String getType(){
        return this.type;
    }
}



