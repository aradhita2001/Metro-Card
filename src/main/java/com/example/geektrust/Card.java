package com.example.geektrust;

public class Card {
    private String cardNumber;
    private int balance;
    private Station lastStation;

    public Card(String cardNumber, int balance) {
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    /*
     * sets the lastStation variable to the last station where the card was used
     */
    public void setLastStation(Station station){
        this.lastStation = station;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getBalance() {
        return balance;
    }

    /*
     * deducts cost from card and adds to station
     * if cost is more than balance returns the difference
     * otherwise returns 0
     */
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

    /*
     * checks if the card is used to buy ticket for a return journey
     */
    public boolean isReturnJourney(Station boardingStation){
        if(this.lastStation == null) return false;
        return boardingStation != lastStation;
    }
}
