package com.example.geektrust;

import java.util.HashMap;

public class Station {
    private String name;
    private double income;
    private int discount;
    HashMap<String, Integer> passengerTypeCount;

    public Station(String name) {
        this.name = name;
        this.income = 0;
        this.discount = 0;
        passengerTypeCount = new HashMap<String, Integer>();
    }

    public String getName() {
        return name;
    }
    public double getIncome() {
        return income;
    }
    public int getDiscount() {
        return discount;
    }
    public HashMap<String, Integer> getPassengerTypeCount() {
        return passengerTypeCount;
    }

    public void updateCount(Citizen citizen){

        String type = citizen.getType();
        passengerTypeCount.put(type, passengerTypeCount.getOrDefault(type, 0) + 1);
    }   

    public void addIncome(Double income) {
        this.income += income;
    }

    public void addDiscount(int discount) {
        this.discount += discount;
    } 
}
