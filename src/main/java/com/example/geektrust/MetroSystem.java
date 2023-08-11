package com.example.geektrust;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public class MetroSystem {

    final double SERVICE_FEE_MULTIPLIER = 1.02;
    final int DISCOUNT_FACTOR = 2;

    private ArrayList<Card> cards;
    private HashMap<String, Station> stations;

    public MetroSystem() {
        cards = new ArrayList<Card>();
        stations = new HashMap<String, Station>();
        stations.put("CENTRAL",  new Station("CENTRAL"));
        stations.put("AIRPORT", new Station("AIRPORT"));
    }

    /*
     * creates a new card
     * adds the new card to the ArrayList containing all cards
     */
    public void balance(String cardNumber, String balance){
        cards.add(new Card(cardNumber, Integer.parseInt(balance)));
    }

    /*
     * checks fare according to category
     * takes care of billing
     * updates on station data
     */
    public void checkIn(String cardNumber, String citizen, String boardingStation) {

        Card currentCard = getCardFromNumber(cardNumber);
        Station CurrentStation = stations.get(boardingStation);
        
        completeTransaction(currentCard, CurrentStation, citizen);
        CurrentStation.updateCount(citizen);
    }

    /*
     * returns card instance from card number
     */
    private Card getCardFromNumber(String cardNumber){
        for(Card card : cards){
            if(card.getCardNumber().equals(cardNumber))
                return card;
        }

        return null;
    }

    /*
     * takes card, station and citizen instances and carries out the transaction
     */
    private void completeTransaction(Card card, Station station, String citizen){
        
        int fare = Citizen.getFare(citizen); //sets fare according to citizen category
        
        /*
         *checks for return journey and adjusts fare accordingly
         */
        if(card.isReturnJourney(station)){
            fare /= DISCOUNT_FACTOR;
            station.addDiscount(fare);
            card.setLastStation(null);//prevents unlimited return journey
        }else{
            card.setLastStation(station);
        }

        //deduct fare from card and get amount payable in cash
        //deducted amount is updated on station data
        int remainingCost = card.deductBalance(fare, station);

        double cash = remainingCost * SERVICE_FEE_MULTIPLIER; 

        station.addIncome(cash); //update income of the station by cash amount
    }

    /*
     * Prints summary of total buisness in the specified format
     */
    public void printSummary(){
        System.out.printf("TOTAL_COLLECTION %s %.0f %d\n", stations.get("CENTRAL").getName(), stations.get("CENTRAL").getIncome(), stations.get("CENTRAL").getDiscount());
        printStationDetails(stations.get("CENTRAL"));
        System.out.printf("TOTAL_COLLECTION %s %.0f %d\n", stations.get("AIRPORT").getName(), stations.get("AIRPORT").getIncome(), stations.get("AIRPORT").getDiscount());
        printStationDetails(stations.get("AIRPORT"));
    }

    /*
     * print details of buisness done in the station
     */
    private void printStationDetails(Station station){

        HashMap<String, Integer> map = station.getPassengerTypeCount();
        TreeMap<Integer, ArrayList<String>> mapToPrint = new TreeMap<Integer, ArrayList<String>>();

        for(String type : map.keySet()){
            int count = map.get(type);
            ArrayList<String> currentList = mapToPrint.getOrDefault(-count, new ArrayList<String>());
            currentList.add(type);
            mapToPrint.put(-count, currentList);
        }

        System.out.println("PASSENGER_TYPE_SUMMARY");
        for (int count : mapToPrint.keySet()) {
            ArrayList<String> types = mapToPrint.get(count);
            Collections.sort(types);
            for(String type : types){
                System.out.println(type + " " + -count);
            }            
        }
    } 
}
