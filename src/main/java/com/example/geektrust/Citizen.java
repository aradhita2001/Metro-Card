package com.example.geektrust;

import java.util.HashMap;

public class Citizen {
    
    private static HashMap<String, Integer> chart;

    static {
        
        chart = new HashMap<String, Integer>();

        chart.put("SENIOR_CITIZEN", 100);
        chart.put("ADULT", 200);
        chart.put("KID", 50);

    }

    public static int getFare(String type){
        return chart.get(type);
    }
}
