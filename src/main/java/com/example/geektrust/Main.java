package com.example.geektrust;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
 
    public static void main(String[] args) {

        MetroSystem metroSystem = new MetroSystem();
          
        try {
            
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            
            while (sc.hasNextLine()) {
               
               processCommand(metroSystem, sc.nextLine());
            }
            sc.close(); 
        } catch (IOException e) {
        
        }  
    }

    /*
     * takes a single line of input and processes that accordingly
     */
    private static void processCommand(MetroSystem metroSystem, String command){
        
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