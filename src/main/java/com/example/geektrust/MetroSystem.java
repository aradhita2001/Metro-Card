package com.example.geektrust;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public class MetroSystem {

    final double SERVICE_FEE_MULTIPLIER = 1.02;
    final int DISCOUNT_FACTOR = 2;

    private ArrayList<Card> cards;
    private Station central;
    private Station airport;
    private Citizen adult;
    private Citizen seniorcitizen;
    private Citizen kid;
    private Class thisClass;

    public MetroSystem() {
        cards = new ArrayList<Card>();
        central = new Station("CENTRAL");
        airport = new Station("AIRPORT");
        adult = new Adult();
        seniorcitizen = new SeniorCitizen();
        kid = new Kid();
        thisClass = this.getClass();
    }

    /*
     * creates a new card
     * adds the new card to the ArrayList containing all cards
     */
    public void balance(String cardNumber, String balance){
        cards.add(new Card(cardNumber, Integer.parseInt(balance)));
    }

    public void checkIn(String cardNumber, String citizenType, String boardingStation) {

        Card currentCard = getCardFromNumber(cardNumber);
        Station CurrentStation = getStationFromName(boardingStation);
        Citizen citizen = getCitizenType(citizenType); 
        
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
     * returns station instance from station name
     */
    private Station getStationFromName(String stationName){

        try {
            return (Station)thisClass.getDeclaredField(stationName).get(this);
        } catch (Exception e) {
            
        }
        return null;
    }

    /*
     * returns citizen object from citizen type 
     * TODO: Refactor along with Citizen class and subclasses
     */
    private Citizen getCitizenType (String citizenType){
       
        try {
            return (Citizen)thisClass.getDeclaredField(citizenType).get(this);
        } catch (Exception e) {
            
        }
        return null;
    }

    /*
     * takes card, station and citizen instances and carries out the transaction
     */
    private void completeTransaction(Card card, Station station, Citizen citizen){
        
        int fare = citizen.getFare(); //sets fare according to citizen category
        
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
        System.out.printf("TOTAL_COLLECTION %s %.0f %d\n", central.getName(), central.getIncome(), central.getDiscount());
        printStationDetails(central);
        System.out.printf("TOTAL_COLLECTION %s %.0f %d\n", airport.getName(), airport.getIncome(), airport.getDiscount());
        printStationDetails(airport);
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
