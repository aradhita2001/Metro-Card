package com.example.geektrust;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static MetroSystem metroSystem = new MetroSystem();
    public static void main(String[] args) {
          
        try {
            
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            
            while (sc.hasNextLine()) {
               
               processCommand(sc.nextLine());
            }
            sc.close(); 
        } catch (IOException e) {
        
        }  
    }

    private static void processCommand(String command){
        
        command = command.toLowerCase();
        command = command.replaceAll("[_]", "");
        
        String[] commandParts = command.split(" ");

        if(commandParts[0].equals("balance")){
            metroSystem.balance(commandParts[1], commandParts[2]);
        }
        else if(commandParts[0].equals("checkin")){
            metroSystem.checkIn(commandParts[1], commandParts[2], commandParts[3]);
        }
        else{
            metroSystem.printSummary();
        }
    }
}