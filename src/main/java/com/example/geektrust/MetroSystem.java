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

    public void printSummary(){
        System.out.printf("TOTAL_COLLECTION %s %.0f %d\n", central.getName(), central.getIncome(), central.getDiscount());
        printStationDetails(central);
        System.out.printf("TOTAL_COLLECTION %s %.0f %d\n", airport.getName(), airport.getIncome(), airport.getDiscount());
        printStationDetails(airport);
    }

    private Card getCardFromNumber(String cardNumber){
        for(Card card : cards){
            if(card.getCardNumber().equals(cardNumber))
                return card;
        }

        return null;
    }

    private Station getStationFromName(String stationName){

        try {
            return (Station)thisClass.getDeclaredField(stationName).get(this);
        } catch (Exception e) {
            
        }
        return null;
    }

    private Citizen getCitizenType (String citizenType){
       
        try {
            return (Citizen)thisClass.getDeclaredField(citizenType).get(this);
        } catch (Exception e) {
            
        }
        return null;
    }

    private void completeTransaction(Card card, Station station, Citizen citizen){
        
        int fare = citizen.getFare();
        
        if(card.isReturnJourney(station)){
            fare /= DISCOUNT_FACTOR;
            station.addDiscount(fare);
            card.setLastStation(null);
        }else{
            card.setLastStation(station);
        }

        int remainingCost = card.deductBalance(fare, station);

        double cash = remainingCost * SERVICE_FEE_MULTIPLIER;

        station.addIncome(cash);
    }

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
