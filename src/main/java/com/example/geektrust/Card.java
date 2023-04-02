package com.example.geektrust;

public class Card {
    private String cardNumber;
    private int balance;
    private Station lastStation;

    public Card(String cardNumber, int balance) {
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getBalance() {
        return balance;
    }

    public int deductBalance(int cost, Station station){
        
        if (balance >= cost){
            balance -= cost;
            station.addIncome((double) cost);
            cost =  0;
        }else{
            station.addIncome((double) balance);
            cost = cost - balance;
            balance = 0;
        }

        return cost;
    }

    public void setLastStation(Station station){
        this.lastStation = station;
    }

    public boolean isReturnJourney(Station boardingStation){
        if(this.lastStation == null) return false;
        return boardingStation != lastStation;
    }
}
